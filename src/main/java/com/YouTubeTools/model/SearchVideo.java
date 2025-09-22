package com.YouTubeTools.model;

import java.util.List;

public class SearchVideo {
    private Video primaryVideo;
    private List<Video> relatedVideos;

    public Video getPrimaryVideo() { return primaryVideo; }
    public void setPrimaryVideo(Video primaryVideo) { this.primaryVideo = primaryVideo; }

    public List<Video> getRelatedVideos() { return relatedVideos; }
    public void setRelatedVideos(List<Video> relatedVideos) { this.relatedVideos = relatedVideos; }
}
