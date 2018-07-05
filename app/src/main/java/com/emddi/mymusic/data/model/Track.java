package com.emddi.mymusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.emddi.mymusic.BuildConfig;
import com.emddi.mymusic.utils.Constants;

public class Track implements Parcelable {
    private String mArtworkUrl;
    private String mDescription;
    private boolean mDownloadable;
    private String mDownloadUrl;
    private long mDuration;
    private int mId;
    private int mLikesCount;
    private String mTitle;
    private String mUri;
    private String mUsername;
    private String mAvatarUrl;
    private int mPlaybackCount;

    public Track() {
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {

        @Override
        public Track createFromParcel(Parcel parcel) {
            return new Track(parcel);
        }

        @Override
        public Track[] newArray(int i) {
            return new Track[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mArtworkUrl);
        parcel.writeString(mDescription);
        parcel.writeByte((byte) (mDownloadable ? 1 : 0));
        parcel.writeString(mDownloadUrl);
        parcel.writeLong(mDuration);
        parcel.writeInt(mId);
        parcel.writeInt(mLikesCount);
        parcel.writeString(mTitle);
        parcel.writeString(mUri);
        parcel.writeString(mUsername);
        parcel.writeString(mAvatarUrl);
        parcel.writeInt(mPlaybackCount);
    }

    public Track(Parcel parcel) {
        mArtworkUrl = parcel.readString();
        mDescription = parcel.readString();
        mDownloadable = parcel.readByte() != 0;
        mDownloadUrl = parcel.readString();
        mDuration = parcel.readLong();
        mId = parcel.readInt();
        mLikesCount = parcel.readInt();
        mTitle = parcel.readString();
        mUri = parcel.readString();
        mUsername = parcel.readString();
        mAvatarUrl = parcel.readString();
        mPlaybackCount = parcel.readInt();
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.mDownloadable = downloadable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        this.mLikesCount = likesCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        this.mUri = uri + Constants.STREAM + Constants.CLIENT_ID + BuildConfig.API_KEY;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        this.mPlaybackCount = playbackCount;
    }

    public interface TrackAttribute {
        String COLLECTION = "collection";
        String TRACK = "track";
        String ARTWORK_URL = "artwork_url";
        String DESCRIPTION = "description";
        String DOWNLOADABLE = "downloadable";
        String DOWNLOAD_URL = "download_url";
        String DURATION = "duration";
        String ID = "id";
        String PLAYBACK_COUNT = "playback_count";
        String TITLE = "title";
        String URI = "uri";
        String USER = "user";
        String AVATAR_URL = "avatar_url";
        String LIKES_COUNT = "likes_count";
        String USERNAME = "username";
        String LARGE_IMAGE_SIZE = "large";
        String BETTER_IMAGE_SIZE = "original";
    }

    public static class TrackBuilder {

        private Track mTrack;

        public TrackBuilder() {
            mTrack = new Track();
        }

        public TrackBuilder setArtworkUrl(String artworkUrl) {
            mTrack.setArtworkUrl(artworkUrl);
            return this;
        }

        public TrackBuilder setDescription(String description) {
            mTrack.setDescription(description);
            return this;
        }

        public TrackBuilder setDownloadUrl(String downloadUrl) {
            mTrack.setDownloadUrl(downloadUrl);
            return this;
        }

        public TrackBuilder setDownloadable(boolean downloadable) {
            mTrack.setDownloadable(downloadable);
            return this;
        }

        public TrackBuilder setDuration(long duration) {
            mTrack.setDuration(duration);
            return this;
        }

        public TrackBuilder setPlaybackCount(int playbackCount) {
            mTrack.setPlaybackCount(playbackCount);
            return this;
        }

        public TrackBuilder setId(int id) {
            mTrack.setId(id);
            return this;
        }

        public TrackBuilder setTitle(String title) {
            mTrack.setTitle(title);
            return this;
        }

        public TrackBuilder setUri(String uri) {
            mTrack.setUri(uri);
            return this;
        }

        public TrackBuilder setUsername(String username) {
            mTrack.setUsername(username);
            return this;
        }

        public TrackBuilder setAvatarUrl(String avatarUrl) {
            mTrack.setAvatarUrl(avatarUrl);
            return this;
        }

        public TrackBuilder setLikesCount(int likesCount) {
            mTrack.setLikesCount(likesCount);
            return this;
        }

        public Track build() {
            return mTrack;
        }
    }
}
