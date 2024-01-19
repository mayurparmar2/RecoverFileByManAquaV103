package com.demo.filerecovery.model.modul.recoveryaudio.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class AudioModel implements Parcelable {
    public static final Parcelable.Creator<AudioModel> CREATOR = new Parcelable.Creator<AudioModel>() {

        @Override
        public AudioModel createFromParcel(Parcel parcel) {
            return new AudioModel(parcel);
        }


        @Override
        public AudioModel[] newArray(int i) {
            return new AudioModel[i];
        }
    };
    boolean isCheck;
    long lastModifiedPhoto;
    String pathPhoto;
    long sizePhoto;

    public AudioModel(String str, long j, long j2) {
        this.isCheck = false;
        this.pathPhoto = str;
        this.lastModifiedPhoto = j;
        this.sizePhoto = j2;
    }

    protected AudioModel(Parcel parcel) {
        this.isCheck = false;
        this.pathPhoto = parcel.readString();
        this.lastModifiedPhoto = parcel.readLong();
        this.sizePhoto = parcel.readLong();
        this.isCheck = parcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLastModified() {
        return this.lastModifiedPhoto;
    }

    public void setLastModified(long j) {
        this.lastModifiedPhoto = j;
    }

    public boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(boolean z) {
        this.isCheck = z;
    }

    public long getSizePhoto() {
        return this.sizePhoto;
    }

    public void setSizePhoto(long j) {
        this.sizePhoto = j;
    }

    public String getPathPhoto() {
        return this.pathPhoto;
    }

    public void setPathPhoto(String str) {
        this.pathPhoto = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pathPhoto);
        parcel.writeLong(this.lastModifiedPhoto);
        parcel.writeLong(this.sizePhoto);
        parcel.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }
}
