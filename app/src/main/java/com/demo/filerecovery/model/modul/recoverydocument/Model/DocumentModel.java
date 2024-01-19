package com.demo.filerecovery.model.modul.recoverydocument.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class DocumentModel implements Parcelable {
    public static final Parcelable.Creator<DocumentModel> CREATOR = new Parcelable.Creator<DocumentModel>() {

        @Override
        public DocumentModel createFromParcel(Parcel parcel) {
            return new DocumentModel(parcel);
        }


        @Override
        public DocumentModel[] newArray(int i) {
            return new DocumentModel[i];
        }
    };
    private boolean isSelected;
    private long lastModified;
    private String pathDocument;
    private long sizeDocument;

    protected DocumentModel(Parcel parcel) {
        this.pathDocument = parcel.readString();
        this.lastModified = parcel.readLong();
        this.sizeDocument = parcel.readLong();
        this.isSelected = parcel.readByte() != 0;
    }

    public DocumentModel(String str, long j, long j2) {
        this.pathDocument = str;
        this.lastModified = j;
        this.sizeDocument = j2;
        this.isSelected = false;
    }

    @Override
    public int describeContents() {
        return 1;
    }

    public Boolean getSelected() {
        return Boolean.valueOf(this.isSelected);
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(Boolean bool) {
        this.isSelected = bool.booleanValue();
    }

    public String getPathDocument() {
        return this.pathDocument;
    }

    public void setPathDocument(String str) {
        this.pathDocument = str;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long j) {
        this.lastModified = j;
    }

    public long getSizeDocument() {
        return this.sizeDocument;
    }

    public void setSizeDocument(long j) {
        this.sizeDocument = j;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pathDocument);
        parcel.writeLong(this.lastModified);
        parcel.writeLong(this.sizeDocument);
        parcel.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }
}
