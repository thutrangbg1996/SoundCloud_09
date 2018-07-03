package com.emddi.mymusic.callback;

import java.util.List;

public  interface ResponseListenner<T> {
    void onReponse(T data);

    void onFailure(String error);

}
