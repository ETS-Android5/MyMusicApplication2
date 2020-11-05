package com.peter.android.mymusicapplication;

import java.util.ArrayList;
import java.util.List;

import hybridmediaplayer.MediaSourceInfo;


public class Playlist {
    private List<Song> songs;
    private List<MediaSourceInfo> mediaSourceInfoList;
    private String name;

    public Playlist() {
        songs = new ArrayList<>();
        mediaSourceInfoList= new ArrayList<>();
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
        for(Song song:songs){
            mediaSourceInfoList.add(song.getmMediaSourceInfo());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MediaSourceInfo> getMediaSourceInfoList() {
        return mediaSourceInfoList;
    }
}
