package com.emddi.mymusic.data.source.remote.network;

import android.os.AsyncTask;
import com.emddi.mymusic.callback.ResponseListenner;
import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.model.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceBuilder extends AsyncTask<String, Void, String> {
    private ResponseListenner<Genre> mResponseListenner;
    private Exception mException;

    public ServiceBuilder(ResponseListenner<Genre> listener) {
        mResponseListenner = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            connection.disconnect();

        } catch (Exception e) {
            setException(e);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (mResponseListenner == null) return;
        if (getException() == null) {
            Genre genresModel = new Genre(parseTrackJSONObject(data));
            mResponseListenner.onReponse(genresModel);
        } else {
            mResponseListenner.onFailure(getException().getMessage());
        }
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        this.mException = exception;
    }

    private List<Track> parseTrackJSONObject(String data) {
        ArrayList<Track> trackModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String collection = jsonObject.getString(Track.TrackAttribute.COLLECTION);
            JSONArray arrayCollection = new JSONArray(collection);
            for (int i = 0; i < arrayCollection.length(); i++) {
                String track = arrayCollection.getJSONObject(i).getString(Track.TrackAttribute.TRACK);
                JSONObject jsonObjectTrack = new JSONObject(track);
                trackModelArrayList.add(setTrack(jsonObjectTrack));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackModelArrayList;
    }

    private Track setTrack(JSONObject jsonObjectTrack) throws JSONException {
        Track.TrackBuilder builder = new Track.TrackBuilder();
        String user = jsonObjectTrack.optString(Track.TrackAttribute.USER);
        JSONObject jsonObjectUser = new JSONObject(user);
        builder.setArtworkUrl(jsonObjectTrack.optString(Track.TrackAttribute.ARTWORK_URL))
                .setDescription(jsonObjectTrack.optString(Track.TrackAttribute.ARTWORK_URL))
                .setDownloadable(jsonObjectTrack.optBoolean(Track.TrackAttribute.DOWNLOADABLE))
                .setDownloadUrl(jsonObjectTrack.optString(Track.TrackAttribute.DOWNLOAD_URL))
                .setDuration(jsonObjectTrack.optLong(Track.TrackAttribute.DURATION))
                .setId(jsonObjectTrack.optInt(Track.TrackAttribute.DURATION))
                .setLikesCount(jsonObjectTrack.optInt(Track.TrackAttribute.LIKES_COUNT))
                .setPlaybackCount(jsonObjectTrack.optInt(Track.TrackAttribute.PLAYBACK_COUNT))
                .setTitle(jsonObjectTrack.optString(Track.TrackAttribute.TITLE))
                .setUri(jsonObjectTrack.optString(Track.TrackAttribute.URI))
                .setUsername(jsonObjectUser.optString(Track.TrackAttribute.USERNAME))
                .setAvatarUrl(jsonObjectTrack.optString(Track.TrackAttribute.AVATAR_URL));
        return builder.build();
    }
}
