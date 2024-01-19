package com.demo.filerecovery.model.modul.recoveryvideo.Model;

import android.os.Parcel;
import android.os.Parcelable;


public class VideoModel implements Parcelable {
    public static final Parcelable.Creator<VideoModel> CREATOR = new Parcelable.Creator<VideoModel>() {

        @Override
        public VideoModel createFromParcel(Parcel parcel) {
            return new VideoModel(parcel);
        }


        @Override
        public VideoModel[] newArray(int i) {
            return new VideoModel[i];
        }
    };
    boolean isCheck;
    long lastModifiedPhoto;
    String pathPhoto;
    long sizePhoto;
    String timeDuration;
    String typeFile;

    public VideoModel(String str, long j, long j2, String str2, String str3) {
        this.isCheck = false;
        this.pathPhoto = str;
        this.lastModifiedPhoto = j;
        this.sizePhoto = j2;
        this.typeFile = str2;
        this.timeDuration = str3;
    }

    protected VideoModel(Parcel parcel) {
        this.isCheck = false;
        this.pathPhoto = parcel.readString();
        this.lastModifiedPhoto = parcel.readLong();
        this.sizePhoto = parcel.readLong();
        this.isCheck = parcel.readByte() != 0;
        this.typeFile = parcel.readString();
        this.timeDuration = parcel.readString();
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

    public String getTypeFile() {
        return this.typeFile;
    }

    public void setTypeFile(String str) {
        this.typeFile = str;
    }

    public String getTimeDuration() {
        return this.timeDuration;
    }

    public void setTimeDuration(String str) {
        this.timeDuration = str;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pathPhoto);
        parcel.writeLong(this.lastModifiedPhoto);
        parcel.writeLong(this.sizePhoto);
        parcel.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        parcel.writeString(this.typeFile);
        parcel.writeString(this.timeDuration);
    }
}
