package com.example.bbcreader;

public class RssItem {

    private String title;
    private String description;
    private String pubDate;
    private String link;

    // Constructor for initialization
//    RssItem(String title, String description, String pubDate, String link) {
//        this.title = title;
//        this.description = description;
//        this.pubDate = pubDate;
//        this.link = link;
//    }

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
