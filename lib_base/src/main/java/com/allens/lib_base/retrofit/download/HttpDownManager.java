package com.allens.lib_base.retrofit.download;

public class HttpDownManager {

    /*单利对象*/
    private volatile static HttpDownManager INSTANCE;

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }



}
