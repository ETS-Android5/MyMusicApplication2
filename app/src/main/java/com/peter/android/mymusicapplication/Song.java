package com.peter.android.mymusicapplication;


import hybridmediaplayer.MediaSourceInfo;

public class Song {
    private final MediaSourceInfo.Builder mMediaSourceInfo;
    private String title;
    private String artist;
    private String url;
    private String imageUrl;
    private int duration;
    public Song(){
       mMediaSourceInfo = new MediaSourceInfo.Builder();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        mMediaSourceInfo.setTitle(title);
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        mMediaSourceInfo.setAuthor(artist);
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        mMediaSourceInfo.setUrl(url);
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mMediaSourceInfo.setImageUrl(imageUrl);
        this.imageUrl = imageUrl;
    }

    public MediaSourceInfo getmMediaSourceInfo() {
        return mMediaSourceInfo.build();
    }
}
