package com.emddi.mymusic.data.repository;

import android.util.Log;

import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.source.GenreDataSource;
import com.emddi.mymusic.data.source.remote.GenreRemoteDataSource;
import com.emddi.mymusic.data.source.remote.network.ServiceBuilder;
import com.emddi.mymusic.utils.APIServiceUtils;

public class GenreRepository implements GenreDataSource.RemoteDataSource{
    private static GenreRepository sGenreReopository;
    private GenreDataSource.RemoteDataSource mRemoteDataSource;

    private GenreRepository(GenreDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static GenreRepository getInstance() {
        if (sGenreReopository == null) {
            sGenreReopository = new GenreRepository(GenreRemoteDataSource.getInstance());
        }
        return sGenreReopository;
    }

    @Override
    public void getTrackRemote(GenreDataSource.OnFetchDataListener<Genre> listener) {
       mRemoteDataSource.getTrackRemote(listener);
    }

    @Override
    public void getMoreTrackRemote(String type, int limit, int offset,
                                   GenreDataSource.OnFetchDataListener<Genre> listener) {

    }
}
