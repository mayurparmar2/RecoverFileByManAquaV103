package com.demo.filerecovery.model.modul.recoveryvideo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class AlbumVideo implements Parcelable {
    public static final Parcelable.Creator<AlbumVideo> CREATOR = new Parcelable.Creator<AlbumVideo>() {

        @Override
        public AlbumVideo createFromParcel(Parcel parcel) {
            return new AlbumVideo(parcel);
        }


        @Override
        public AlbumVideo[] newArray(int i) {
            return new AlbumVideo[i];
        }
    };
    long lastModified;
    ArrayList<VideoModel> listPhoto;
    String str_folder;

    public AlbumVideo() {
    }

    protected AlbumVideo(Parcel parcel) {
        this.str_folder = parcel.readString();
        this.listPhoto = parcel.createTypedArrayList(VideoModel.CREATOR);
        this.lastModified = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long j) {
        this.lastModified = j;
    }

    public String getStr_folder() {
        return this.str_folder;
    }

    public void setStr_folder(String str) {
        this.str_folder = str;
    }

    public ArrayList<VideoModel> getListPhoto() {
        return this.listPhoto;
    }

    public void setListPhoto(ArrayList<VideoModel> arrayList) {
        this.listPhoto = arrayList;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.str_folder);
        parcel.writeTypedList(this.listPhoto);
        parcel.writeLong(this.lastModified);
    }
}
