package com.peter.android.mymusicapplication.models;

import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;

public class AudioPlayerActivityModel  extends Observable {
    ArrayList<AudioBlogModel> listOfBlogsUI= new ArrayList<>();
    AudioBlogModel currentAudioBlog = null;
    public int sbProgressValue;
    public String tvTimeString;
    private String tvDurationString;

    public ArrayList<AudioBlogModel> getListOfBlogsUI() {
        return listOfBlogsUI;
    }

    public void setCurrentAudioBlog(AudioBlogModel currentAudioBlog) {
        this.currentAudioBlog = currentAudioBlog;
        setChanged();
        notifyObservers();
    }

}
