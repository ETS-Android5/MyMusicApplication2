package com.peter.android.mymusicapplication.models;

import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;

public class AudioPlayerActivityModel  extends Observable {
    public int previousSelected = -1;
    public int currentSelected = -1;
    ArrayList<AudioBlogModel> listOfBlogsUI= new ArrayList<>();
    AudioBlogModel currentAudioBlog = null;//now playing
    private int sbProgressValue=-1;
    private String tvTimeString="";
    private String tvDurationString="";

    public ArrayList<AudioBlogModel> getListOfBlogsUI() {
        return listOfBlogsUI;
    }

    public void setCurrentAudioBlog(AudioBlogModel currentAudioBlog) {
        if(!this.currentAudioBlog.equals(currentAudioBlog)){
        this.currentAudioBlog = currentAudioBlog;
        setChanged();
        notifyObservers();}
    }

    public void setCurrentSelected(int currentSelected) {
        if(this.currentSelected != currentSelected){
            if(this.currentSelected != -1) {
                setPreviousSelected(this.currentSelected);
                listOfBlogsUI.get(this.currentSelected).setSelected(false);
            }
            this.currentSelected = currentSelected;
            listOfBlogsUI.get(this.currentSelected).setSelected(true);
            setCurrentAudioBlog(listOfBlogsUI.get(this.currentSelected));
        setChanged();
        notifyObservers();
        }
    }

    private void setPreviousSelected(int previousSelected) {
        if(this.previousSelected != previousSelected) {
            this.previousSelected = previousSelected;
        }
    }

    public int getSbProgressValue() {
        return sbProgressValue;
    }

    public void setSbProgressValue(int sbProgressValue) {
        if(this.sbProgressValue != sbProgressValue) {
            this.sbProgressValue = sbProgressValue;
            setChanged();
            notifyObservers();
        }
    }

    public String getTvTimeString() {
        return tvTimeString;
    }

    public void setTvTimeString(String tvTimeString) {
        if(!this.tvTimeString.equals(tvTimeString)){
        this.tvTimeString = tvTimeString;
        setChanged();
        notifyObservers();}
    }

    public String getTvDurationString() {
        return tvDurationString;
    }

    public void setTvDurationString(String tvDurationString) {
        if(!this.tvDurationString.equals(tvDurationString)) {
            this.tvDurationString = tvDurationString;
            setChanged();
            notifyObservers();
        }
    }
}
