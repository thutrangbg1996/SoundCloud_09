package com.emddi.mymusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emddi.mymusic.data.model.Track;
import com.emddi.mymusic.screen.player.PlayerMusicListener;
import com.emddi.mymusic.utils.Constants;
import com.emddi.mymusic.utils.MediaPlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.emddi.mymusic.utils.MediaPlayerState.PAUSED;
import static com.emddi.mymusic.utils.MediaPlayerState.PLAYING;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        PlayerMusicListener, MediaPlayer.OnCompletionListener {
    public static final String ACTION_PLAY = "action play";
    public static final String ACTION_PAUSE = "action pause";
    public static final String ACTION_SKIP_TO_NEXT = "skip to nextTrack";
    public static final String ACTION_SKIP_TO_PREVIOUS = "skip to backTrack";
    private List<Track> mTracks;
    private MediaPlayer mMediaPlayer = null;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private int mPosition;
    private final IBinder mBinder = new MusicBinder();
    private List<PlayerMusicListener> mListeners;
    private int mState;

    @Override
    public void onCreate() {
        super.onCreate();
        mListeners = new ArrayList<>();
    }

    public void setListeners(PlayerMusicListener listeners) {
        mListeners.add(listeners);
    }

    public void removeListener(PlayerMusicListener listener) {
        mListeners.remove(listener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    getData(intent);
                    initializeMediaPlayer();
                    play(mTracks.get(mPosition));
                    break;
                case ACTION_PAUSE:
                    break;
                case ACTION_SKIP_TO_NEXT:
                    break;
                case ACTION_SKIP_TO_PREVIOUS:
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    public void play(Track track) {
        if (mMediaPlayer == null) return;
        mMediaPlayer.setOnCompletionListener(this);
        loadMedia(track);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        loadTrackSuccess(true);
        mState = PLAYING;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) mMediaPlayer.release();
    }

    @Override
    public void onFail(String error) {
        for (PlayerMusicListener listener : mListeners) {
            listener.onFail(error);
        }
    }

    @Override
    public void onChangeMediaState(@MediaPlayerState.MediaState int mediaState) {
        for (PlayerMusicListener listener : mListeners) {
            listener.onChangeMediaState(mediaState);
        }
    }

    @Override
    public void onShuffle(int shuffle) {
        for (PlayerMusicListener listener : mListeners) {
            listener.onShuffle(shuffle);
        }
    }

    @Override
    public void loadTrackSuccess(boolean loadSuccess) {
        for (PlayerMusicListener listener : mListeners) {
            listener.loadTrackSuccess(loadSuccess);
        }
    }

    @Override
    public void playTrack(Track track) {
        for (PlayerMusicListener listener : mListeners) {
            listener.playTrack(track);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playTrack(mTracks.get(mPosition));
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public long getCurrentDuration() {
        if (mMediaPlayer == null) return 0;
        return mMediaPlayer.getCurrentPosition();
    }

    public void loadMedia(Track track) {
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(track.getUri());
        } catch (Exception e) {
        }

        try {
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
        }

    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }


    public void loadTrack() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            mState = PLAYING;
            for (PlayerMusicListener listener : mListeners) {
                listener.onChangeMediaState(mState);
            }
        }
    }

    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            stopUpdatingCallbackWithPosition(true);
        }
    }

    public void pauseTrack() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mState = PAUSED;
            for (PlayerMusicListener listener : mListeners) {
                listener.onChangeMediaState(mState);
            }
        }
    }

    public int getState() {
        return mState;
    }

    public void nextTrack() {
        if (mPosition == mTracks.size() - 1) mPosition = -1;
        reset();
        mPosition++;
        loadMedia(mTracks.get(mPosition));
    }

    public void backTrack() {
        if (mPosition < 1) mPosition = mTracks.size();
        if (isPlaying())
        reset();
        mPosition--;
        loadMedia(mTracks.get(mPosition));
    }

    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    public void setLooping(boolean looping) {
        mMediaPlayer.setLooping(looping);
    }

    public Boolean isLooping() {
        return mMediaPlayer.isLooping();
    }

    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
        }
    }

    public Track getTrack() {
        return mTracks.get(mPosition);
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    private void getData(Intent intent) {
        if (intent.getExtras() != null) {
            mTracks = intent.getExtras().getParcelableArrayList(Track.class.getName());
            mPosition = intent.getExtras().getInt(Constants.POSITION);
        }
    }

    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
    }
}
