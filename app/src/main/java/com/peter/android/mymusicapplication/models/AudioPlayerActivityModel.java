package com.peter.android.mymusicapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import hybridmediaplayer.MediaSourceInfo;

public class AudioPlayerActivityModel  extends Observable implements Parcelable {
    public AudioPlayerActivityModel(){
        super();
    }
    protected AudioPlayerActivityModel(Parcel in) {
        previousSelected = in.readInt();
        currentSelected = in.readInt();
        listOfBlogsUI = in.createTypedArrayList(AudioBlogModel.CREATOR);
        currentAudioBlog = in.readParcelable(AudioBlogModel.class.getClassLoader());
        sbProgressValue = in.readInt();
        tvTimeString = in.readString();
        tvDurationString = in.readString();
        playing = in.readInt() == 1;
    }

    public static final Creator<AudioPlayerActivityModel> CREATOR = new Creator<AudioPlayerActivityModel>() {
        @Override
        public AudioPlayerActivityModel createFromParcel(Parcel in) {
            return new AudioPlayerActivityModel(in);
        }

        @Override
        public AudioPlayerActivityModel[] newArray(int size) {
            return new AudioPlayerActivityModel[size];
        }
    };

    public String getName(){
        return "Audio Blog Demo";
    }
    public int previousSelected = -1;
    public int currentSelected = -1;
    ArrayList<AudioBlogModel> listOfBlogsUI= new ArrayList<>();
    AudioBlogModel currentAudioBlog = null;//now playing
    private int sbProgressValue=-1;
    private String tvTimeString="";
    private String tvDurationString="";
    private boolean playing = false;

    public boolean setPlaying(boolean playing) {
        if(playing != this.playing) {
            this.playing = playing;
            setChanged();
            notifyObservers();
            return true;
        }else return false;
    }

    public boolean isPlaying(){
        return this.playing;
    }

    public ArrayList<AudioBlogModel> getListOfBlogsUI() {
        return listOfBlogsUI;
    }

    public void addToListOfBlogsUI(AudioBlogModel audioBlogModel) {
        listOfBlogsUI.add(audioBlogModel);
    }

    public void setCurrentAudioBlog(AudioBlogModel currentAudioBlog) {
        if(currentAudioBlog ==null)
            return;
if(this.currentAudioBlog == null || this.currentAudioBlog.equals(currentAudioBlog)) {
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

    public List<MediaSourceInfo> getMediaSourceInfoList() {

        ArrayList<MediaSourceInfo> mediaSourceInfoList = new ArrayList<>();
        for(AudioBlogModel song:listOfBlogsUI){
            mediaSourceInfoList.add(song.getMediaSourceInfo());
        }

        return mediaSourceInfoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(previousSelected);
        parcel.writeInt(currentSelected);
        parcel.writeTypedList(listOfBlogsUI);
        parcel.writeParcelable(currentAudioBlog, i);
        parcel.writeInt(sbProgressValue);
        parcel.writeString(tvTimeString);
        parcel.writeString(tvDurationString);
        parcel.writeInt(this.playing?1:0);
    }
}
