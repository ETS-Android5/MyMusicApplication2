package com.peter.android.mymusicapplication;


import android.os.Parcel;
import android.os.Parcelable;

import hybridmediaplayer.MediaSourceInfo;

public class Song implements Parcelable {
    private String title;
    private String artist;
    private String url;
    private String imageUrl;
    private int duration;
    public Song(){

    }

    protected Song(Parcel in) {
        title = in.readString();
        artist = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        duration = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {

        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

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

        this.imageUrl = imageUrl;
    }

    public MediaSourceInfo getmMediaSourceInfo() {
        return new MediaSourceInfo.Builder().setTitle(title).setAuthor(artist).setUrl(url).setImageUrl(imageUrl).build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeString(url);
        parcel.writeString(imageUrl);
        parcel.writeInt(duration);
    }
}
