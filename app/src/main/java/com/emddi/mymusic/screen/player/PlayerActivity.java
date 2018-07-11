package com.emddi.mymusic.screen.player;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emddi.mymusic.R;
import com.emddi.mymusic.base.BaseActivity;
import com.emddi.mymusic.data.model.Track;
import com.emddi.mymusic.service.MusicService;
import com.emddi.mymusic.utils.TrackUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerActivity extends BaseActivity<PlayerContract.Presenter> implements
        PlayerContract.View, View.OnClickListener, PlayerMusicListener,
        SeekBar.OnSeekBarChangeListener {
    private static final long DELAY_TIME = 10;
    private ImageView mBack;
    private TextView mTitle;
    private TextView mSingerName;
    private ImageView mSingerImage;
    private ImageView mLike;
    private SeekBar mSeekBar;
    private ImageView mMore;
    private ImageView mLoop;
    private ImageView mSkipToPrevious;
    private ImageView mPlay;
    private ImageView mSkipToNext;
    private ImageView mShuffle;
    private MusicService mMusicService;
    private Boolean mUserIsSeeking = false;
    private Boolean mIsBound;
    private List<Track> mTracks;
    private Boolean mIsShuffle = false;
    private Handler mHandler;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void initView() {
        mBack = findViewById(R.id.image_back);
        mTitle = findViewById(R.id.text_title);
        mSingerName = findViewById(R.id.text_singer_name);
        mSingerImage = findViewById(R.id.image_singer);
        mLike = findViewById(R.id.image_like);
        mSeekBar = findViewById(R.id.seekBar);
        mMore = findViewById(R.id.image_more);
        mLoop = findViewById(R.id.image_loop);
        mSkipToPrevious = findViewById(R.id.image_skip_to_previous);
        mPlay = findViewById(R.id.image_play);
        mSkipToNext = findViewById(R.id.image_skip_to_next);
        mShuffle = findViewById(R.id.image_shuffle);
        setOnclickListener();
        mSeekBar.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.image_like:
                break;
            case R.id.image_more:
                break;
            case R.id.image_loop:
                onLoop();
                break;
            case R.id.image_skip_to_previous:
                backTrack();
                break;
            case R.id.image_play:
                playPauseSong();
                break;
            case R.id.image_skip_to_next:
                nextTrack();
                break;
            case R.id.image_shuffle:
                break;
        }
    }

    @Override
    public void onPrepareLayout() {
        super.onPrepareLayout();
        mHandler = new Handler();
        if (getIntent() == null) return;
        Track track = getIntent().getParcelableExtra(Track.class.getName());
        mTitle.setText(track.getTitle());
        mSingerName.setText(track.getUsername());
        mSeekBar.setMax((int) track.getDuration());
        Glide.with(this)
                .load(TrackUtils.getBetterArtwork(track.getAvatarUrl()))
                .into(mSingerImage);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public PlayerContract.Presenter onCreatePresenter() {
        return new PlayerPresenter(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mIsBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicService.removeListener(this);
        stopService(new Intent(this, MusicService.class));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMusicService.seekTo(seekBar.getProgress());
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            updateSeekBar();
            mTracks = mMusicService.getTracks();
            mMusicService.setListeners(PlayerActivity.this);
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mIsBound = false;
        }
    };

    private void updateSeekBar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
//                    if(mSeekBar != null)
                    mSeekBar.setProgress((int) mMusicService.getCurrentDuration());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }, 0);
    }

    @Override
    public void onFail(String error) {

    }

    @Override
    public void onChangeMediaState(int mediaState) {

    }

    @Override
    public void onShuffle(int shuffle) {

    }

    public void onLoop() {
        if (mMusicService.isLooping()) {
            mMusicService.setLooping(false);
            mLoop.setImageResource(R.drawable.ic_repeat);
        } else {
            mMusicService.setLooping(true);
            mLoop.setImageResource(R.drawable.ic_loop_active);
        }
    }

    @Override
    public void loadTrackSuccess(boolean loadSuccess) {
        mSeekBar.setFocusable(loadSuccess);
    }

    @Override
    public void playTrack(Track track) {
        if (mMusicService == null) return;
        mMusicService.nextTrack();
        updateDetail(mMusicService.getTrack());
    }

    private void playPauseSong() {
        if (mMusicService == null) return;
        if (mMusicService.isPlaying()) {
            mMusicService.pauseTrack();
            mPlay.setImageResource(R.drawable.ic_play);
        } else {
            mMusicService.loadTrack();
            mPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    private void backTrack() {
        mMusicService.backTrack();
        updateDetail(mMusicService.getTrack());
    }

    private void nextTrack() {
        if (mMusicService == null) return;
        mMusicService.nextTrack();
        updateDetail(mMusicService.getTrack());
    }

    private List<Track> getShuffleTracks(List<Track> tracks) {
        List<Track> result = new ArrayList<>();
        result.addAll(tracks);
        Collections.shuffle(result);
        return result;
    }

    private void setOnclickListener() {
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mSingerName.setOnClickListener(this);
        mSingerImage.setOnClickListener(this);
        mLike.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mLoop.setOnClickListener(this);
        mSkipToPrevious.setOnClickListener(this);
        mPlay.setOnClickListener(this);
        mSkipToNext.setOnClickListener(this);
        mShuffle.setOnClickListener(this);
    }

    private void updateDetail(Track track) {
        mTitle.setText(track.getTitle());
        mSingerName.setText(track.getUsername());
        mSeekBar.setMax((int) track.getDuration());
        Glide.with(this)
                .load(TrackUtils.getBetterArtwork(track.getAvatarUrl()))
                .into(mSingerImage);
        updateSeekBar();
    }
}
