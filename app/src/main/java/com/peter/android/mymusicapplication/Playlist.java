package com.peter.android.mymusicapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import hybridmediaplayer.MediaSourceInfo;


public class Playlist implements Parcelable {
    private List<Song> songs;
    private List<MediaSourceInfo> mediaSourceInfoList;
    private String name;

    public Playlist() {
        songs = new ArrayList<>();
        mediaSourceInfoList= new ArrayList<>();
    }

    protected Playlist(Parcel in) {
        songs = in.createTypedArrayList(Song.CREATOR);
        name = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

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
        if(mediaSourceInfoList ==null || mediaSourceInfoList.isEmpty()){
            mediaSourceInfoList= new ArrayList<>();
            for(Song song:songs){
                mediaSourceInfoList.add(song.getmMediaSourceInfo());
            }
        }
        return mediaSourceInfoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(songs);
        parcel.writeString(name);
    }
}
