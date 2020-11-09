package com.peter.android.mymusicapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.peter.android.mymusicapplication.LoadSomePostsQuery;
import com.peter.android.mymusicapplication.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Observable;

public class AudioBlogModel  extends Observable implements Parcelable {
    boolean selected = false;
    boolean keepListening = false;
    String id="-1";
    Date publishedAt = new Date();
    String title ="";

    Double audioSize=-1.0;
    String audioFileName="";
    String url="";
    public AudioBlogModel(LoadSomePostsQuery.Post post){
        this.id = post.id();
        this.publishedAt = Utils.getDate((String) post.publishedAt());
        this.title = post.title();
        this.audioSize = post.audio().size();
        this.audioFileName = post.audio().fileName();
        this.url = url;
    }

    public AudioBlogModel(boolean selected, boolean keepListening, String id, Date publishedAt, String title, Double audioSize, String audioFileName, String url) {
        this.selected = selected;
        this.keepListening = keepListening;
        this.id = id;
        this.publishedAt = publishedAt;
        this.title = title;
        this.audioSize = audioSize;
        this.audioFileName = audioFileName;
        this.url = url;
    }

    protected AudioBlogModel(Parcel in) {
        selected = in.readByte() != 0;
        keepListening = in.readByte() != 0;
        id = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            audioSize = null;
        } else {
            audioSize = in.readDouble();
        }
        audioFileName = in.readString();
        url = in.readString();
    }

    public static final Creator<AudioBlogModel> CREATOR = new Creator<AudioBlogModel>() {
        @Override
        public AudioBlogModel createFromParcel(Parcel in) {
            return new AudioBlogModel(in);
        }

        @Override
        public AudioBlogModel[] newArray(int size) {
            return new AudioBlogModel[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setChanged();
        notifyObservers();
    }

    public boolean isKeepListening() {
        return keepListening;
    }

    public void setKeepListening(boolean keepListening) {
        this.keepListening = keepListening;
        setChanged();
        notifyObservers();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setChanged();
        notifyObservers();
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public String getReadableFormat(){
        return Utils.getReadableFormat(publishedAt);
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
        setChanged();
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setChanged();
        notifyObservers();
    }

    public Double getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(Double audioSize) {
        this.audioSize = audioSize;
        setChanged();
        notifyObservers();
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
        setChanged();
        notifyObservers();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        setChanged();
        notifyObservers();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeByte((byte) (keepListening ? 1 : 0));
        parcel.writeString(id);
        parcel.writeString(title);
        if (audioSize == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(audioSize);
        }
        parcel.writeString(audioFileName);
        parcel.writeString(url);
    }

    @Override
    public String toString() {
        return "AudioBlogModel{" +
                "selected=" + selected +
                ", keepListening=" + keepListening +
                ", id='" + id + '\'' +
                ", publishedAt=" + publishedAt +
                ", title='" + title + '\'' +
                ", audioSize=" + audioSize +
                ", audioFileName='" + audioFileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
