package com.emddi.mymusic.screen.player;

import com.emddi.mymusic.data.model.Track;
import com.emddi.mymusic.utils.MediaPlayerState;

public interface PlayerMusicListener {

    void onFail(String error);

    void onChangeMediaState(@MediaPlayerState.MediaState int mediaState);

    void onShuffle(int shuffle);

    void loadTrackSuccess(boolean loadSuccess);

    void playTrack(Track track);

}
