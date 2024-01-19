package com.demo.filerecovery.model.modul.recoverydocument.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class AlbumDocument implements Parcelable {
    public static final Parcelable.Creator<AlbumDocument> CREATOR = new Parcelable.Creator<AlbumDocument>() {

        @Override
        public AlbumDocument createFromParcel(Parcel parcel) {
            return new AlbumDocument(parcel);
        }


        @Override
        public AlbumDocument[] newArray(int i) {
            return new AlbumDocument[i];
        }
    };
    private long lastModified;
    private List<DocumentModel> listDocument;
    private String str_folder;

    public AlbumDocument() {
    }

    public AlbumDocument(String str, List<DocumentModel> list, long j) {
        this.str_folder = str;
        this.listDocument = list;
        this.lastModified = j;
    }

    protected AlbumDocument(Parcel parcel) {
        this.str_folder = parcel.readString();
        this.listDocument = parcel.createTypedArrayList(DocumentModel.CREATOR);
        this.lastModified = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.str_folder);
        parcel.writeTypedList(this.listDocument);
        parcel.writeLong(this.lastModified);
    }

    public String getStr_folder() {
        return this.str_folder;
    }

    public void setStr_folder(String str) {
        this.str_folder = str;
    }

    public List<DocumentModel> getListDocument() {
        return this.listDocument;
    }

    public void setListDocument(ArrayList<DocumentModel> arrayList) {
        this.listDocument = arrayList;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long j) {
        this.lastModified = j;
    }
}
