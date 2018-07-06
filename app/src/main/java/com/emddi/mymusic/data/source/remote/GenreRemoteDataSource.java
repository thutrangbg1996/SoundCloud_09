package com.emddi.mymusic.data.source.remote;

import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.source.GenreDataSource;
import com.emddi.mymusic.data.source.remote.network.ServiceBuilder;
import com.emddi.mymusic.utils.APIServiceUtils;

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {
    private static GenreRemoteDataSource sInstance;

    public static GenreRemoteDataSource getInstance() {
        if (null == sInstance) {
            sInstance = new GenreRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getTrackRemote(GenreDataSource.OnFetchDataListener<Genre> listener) {
        for (int i = 0; i < APIServiceUtils.LIST_TRACK_GENRES.size(); i++) {
            String url = APIServiceUtils.createUrl(APIServiceUtils.LIST_TRACK_GENRES.get(i),
                    APIServiceUtils.ApiGenre.GENRE_LIMIT_DEFAULT,
                    APIServiceUtils.ApiGenre.GENRE_OFFSET_DEFAULT);
            new ServiceBuilder(listener).execute(url);
        }
    }

    @Override
    public void getMoreTrackRemote(String type, int limit, int offset,
                                   GenreDataSource.OnFetchDataListener<Genre> listener) {

    }
}
