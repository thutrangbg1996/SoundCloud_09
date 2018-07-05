package com.emddi.mymusic.callback;

public  interface ResponseListenner<T> {
    void onSuccess(T data);

    void onFailure(String error);

}
