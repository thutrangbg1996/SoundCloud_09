package com.emddi.mymusic.screen.player;

import com.emddi.mymusic.base.BasePresenterImpl;

public class PlayerPresenter extends BasePresenterImpl<PlayerContract.View> implements
        PlayerContract.Presenter {
    public PlayerPresenter(PlayerContract.View view) {
        super(view);
    }
}
