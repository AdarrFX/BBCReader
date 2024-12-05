package com.example.bbcreader;

public class RssItem {

    // The RSSItem class holds the article information.

    private String title;
    private String description;
    private String pubDate;
    private String link;


    // public getters and setters used to set and read the data from the object.
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
