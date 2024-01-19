package com.demo.filerecovery;

import android.util.Log;


import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;

import java.util.ArrayList;
import java.util.List;


public class StorageCommon {
    private ArrayList<AudioModel> listAudio = new ArrayList<>();
    private List<AudioModel> listAudioScanSelect = new ArrayList();
    private ArrayList<DocumentModel> listDocument = new ArrayList<>();
    private List<DocumentModel> listDocumentsScanSelect = new ArrayList();
    private ArrayList<PhotoModel> listPhoto = new ArrayList<>();
    private List<PhotoModel> listPhotoScanSelect = new ArrayList();
    private ArrayList<VideoModel> listVideo = new ArrayList<>();
    private List<VideoModel> listVideoScanSelect = new ArrayList();

    public List<PhotoModel> getListPhotoScanSelect() {
        return this.listPhotoScanSelect;
    }

    public void setListPhotoScanSelect(List<PhotoModel> list) {
        this.listPhotoScanSelect = list;
    }

    public List<DocumentModel> getListDocumentsScanSelect() {
        return this.listDocumentsScanSelect;
    }

    public void setListDocumentsScanSelect(List<DocumentModel> list) {
        this.listDocumentsScanSelect = list;
    }

    public List<VideoModel> getListVideoScanSelect() {
        return this.listVideoScanSelect;
    }

    public void setListVideoScanSelect(List<VideoModel> list) {
        this.listVideoScanSelect = list;
    }

    public List<AudioModel> getListAudioScanSelect() {
        return this.listAudioScanSelect;
    }

    public void setListAudioScanSelect(List<AudioModel> list) {
        this.listAudioScanSelect = list;
    }


    public ArrayList<DocumentModel> getListDocument() {
        return this.listDocument;
    }

    public void setListDocument(ArrayList<DocumentModel> arrayList) {
        this.listDocument = arrayList;
    }

    public ArrayList<PhotoModel> getListPhoto() {
        return this.listPhoto;
    }

    public void setListPhoto(ArrayList<PhotoModel> arrayList) {
        Log.d("StorageCommon", "setListPhoto: ");
        this.listPhoto = arrayList;
    }

    public ArrayList<VideoModel> getListVideo() {
        return this.listVideo;
    }

    public void setListVideo(ArrayList<VideoModel> arrayList) {
        this.listVideo = arrayList;
    }

    public ArrayList<AudioModel> getListAudio() {
        return this.listAudio;
    }

    public void setListAudio(ArrayList<AudioModel> arrayList) {
        this.listAudio = arrayList;
    }


}
