package com.emddi.mymusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Genre implements Parcelable {
    private String mType;
    private List<Track> mTrack;

    public Genre(Parcel parcel) {
        mTrack = parcel.createTypedArrayList(Track.CREATOR);
        mType = parcel.readString();
    }

    public Genre(List<Track> trackModels) {
        mTrack = trackModels;
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel parcel) {
            return new Genre(parcel);
        }

        @Override
        public Genre[] newArray(int i) {
            return new Genre[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mType);
        parcel.writeTypedList(mTrack);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public List<Track> getListTrack() {
        return mTrack;
    }

    public void setListTrack(List<Track> listTrack) {
        this.mTrack = listTrack;
    }
}
