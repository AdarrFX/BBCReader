package com.example.bbcreader;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchFeedTask extends AsyncTask<Void, Void, List<RssItem>> {
    private static final String RSS_URL = "https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    private RssAdapter adapter;

    public FetchFeedTask(RssAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<RssItem> doInBackground(Void... voids) {
        List<RssItem> items = new ArrayList<>();
        try {

            // With help from:
            // https://developer.android.com/develop/connectivity/network-ops/xml

            URL url = new URL(RSS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            RssItem currentItem = null;

            // Loop through lines until end of XML file
            // While the PullParser is not at the end
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("item")) {
                            currentItem = new RssItem();
                        } else if (currentItem != null) {
                            if (tagName.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tagName.equals("description")) {
                                currentItem.setDescription(parser.nextText());
                            } else if (tagName.equals("pubDate")) {
                                currentItem.setPubDate(parser.nextText());
                            } else if (tagName.equals("link")) {
                                currentItem.setLink(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("item") && currentItem != null) {
                            items.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    protected void onPostExecute(List<RssItem> items) {
        adapter.clear();
        adapter.addAll(items);
    }
}
