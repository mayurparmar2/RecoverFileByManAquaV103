package com.demo.filerecovery.model.modul.recoveryphoto.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class AlbumPhoto implements Parcelable {
    public static final Parcelable.Creator<AlbumPhoto> CREATOR = new Parcelable.Creator<AlbumPhoto>() {

        @Override
        public AlbumPhoto createFromParcel(Parcel parcel) {
            return new AlbumPhoto(parcel);
        }


        @Override
        public AlbumPhoto[] newArray(int i) {
            return new AlbumPhoto[i];
        }
    };
    long lastModified;
    ArrayList<PhotoModel> listPhoto;
    String str_folder;

    public AlbumPhoto() {
    }

    protected AlbumPhoto(Parcel parcel) {
        this.str_folder = parcel.readString();
        this.listPhoto = parcel.createTypedArrayList(PhotoModel.CREATOR);
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

    public ArrayList<PhotoModel> getListPhoto() {
        return this.listPhoto;
    }

    public void setListPhoto(ArrayList<PhotoModel> arrayList) {
        this.listPhoto = arrayList;
    }
}
