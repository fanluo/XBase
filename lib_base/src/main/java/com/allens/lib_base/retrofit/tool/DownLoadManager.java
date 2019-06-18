package com.allens.lib_base.retrofit.tool;

import android.annotation.SuppressLint;
import android.util.Log;

import com.allens.lib_base.retrofit.impl.OnDownLoadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class DownLoadManager {


    //下载小文件
    public void downLoad(ResponseBody responseBody, String filepath, String key, OnDownLoadListener loadListener) {
        FileOutputStream fos = null;
        InputStream inputStream = responseBody.byteStream();
        long length = responseBody.contentLength();// 流的大小
        try {
            fos = new FileOutputStream(filepath, true);
            int n = 0;
            int currentLength = 0; //当前的长度
            byte[] buf = new byte[1024];
            while ((n = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, n);
                currentLength = currentLength + n;
                final int terms = (int) (((float) currentLength) / (length) * 100); // 计算百分比
                handlerSuccess(key, terms, loadListener);
            }
            handlerFinish(key, filepath, loadListener);
        } catch (Throwable e) {
            handlerFailed(key, e, loadListener);
        } finally {
            try {
                fos.close();
                inputStream.close();
            } catch (IOException e) {

            }

        }
    }

    @SuppressLint("CheckResult")
    private void handlerFinish(String key, String filepath, OnDownLoadListener loadListener) {
        Flowable.just(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> loadListener.onSuccess(data, filepath));
    }

    @SuppressLint("CheckResult")
    private void handlerFailed(String key, Throwable e, OnDownLoadListener loadListener) {
        Flowable.just(e)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(throwable -> loadListener.onError(key, e));
    }

    @SuppressLint("CheckResult")
    private void handlerSuccess(String key, int terms, OnDownLoadListener loadListener) {
        Flowable.just(terms)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> loadListener.onProgress(key, terms));
    }

}
