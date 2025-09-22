package com.YouTubeTools.model;

import java.util.List;

public class Video {
    private String title;
    private String description;
    private String url;
    private List<String> tags;
    private String channelTitle;

    public Video() {}

    public Video(String title) {
        this.title = title;
    }

    public Video(String title, String description, String url, List<String> tags, String channelTitle) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.tags = tags;
        this.channelTitle = channelTitle;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getChannelTitle() { return channelTitle; }
    public void setChannelTitle(String channelTitle) { this.channelTitle = channelTitle; }
}
