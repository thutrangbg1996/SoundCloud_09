package com.emddi.mymusic.data.source;

import com.emddi.mymusic.data.model.Genre;

public interface GenreDataSource {
    interface RemoteDataSource {
        void getTrackRemote(OnFetchDataListener<Genre> listener);

        void getMoreTrackRemote(String type, int limit, int offset,
                                OnFetchDataListener<Genre> listener);
    }

    interface OnFetchDataListener<T> {
        void onSuccess(T data);

        void onFailure(String error);

    }
}
