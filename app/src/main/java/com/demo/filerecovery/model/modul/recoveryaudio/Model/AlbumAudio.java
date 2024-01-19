package com.demo.filerecovery.model.modul.recoveryaudio.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class AlbumAudio implements Parcelable {
    public static final Parcelable.Creator<AlbumAudio> CREATOR = new Parcelable.Creator<AlbumAudio>() {

        @Override
        public AlbumAudio createFromParcel(Parcel parcel) {
            return new AlbumAudio(parcel);
        }


        @Override
        public AlbumAudio[] newArray(int i) {
            return new AlbumAudio[i];
        }
    };
    long lastModified;
    ArrayList<AudioModel> listPhoto;
    String str_folder;

    public AlbumAudio() {
    }

    protected AlbumAudio(Parcel parcel) {
        this.str_folder = parcel.readString();
        this.listPhoto = parcel.createTypedArrayList(AudioModel.CREATOR);
        this.lastModified = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.str_folder);
        parcel.writeTypedList(this.listPhoto);
        parcel.writeLong(this.lastModified);
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

    public ArrayList<AudioModel> getListPhoto() {
        return this.listPhoto;
    }

    public void setListPhoto(ArrayList<AudioModel> arrayList) {
        this.listPhoto = arrayList;
    }
}
