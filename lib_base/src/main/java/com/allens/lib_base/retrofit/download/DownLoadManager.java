package com.allens.lib_base.retrofit.download;

import android.annotation.SuppressLint;
import android.util.Log;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.impl.OnDownLoadBigListener;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class DownLoadManager {


    //下载小文件
    public boolean downLoad(ResponseBody responseBody, String filepath, String key, OnDownLoadListener loadListener) {
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
            return true;
        } catch (Throwable e) {
            handlerFailed(key, e, loadListener);
            return false;
        } finally {
            try {
                if (fos != null)
                    fos.close();
                inputStream.close();
            } catch (IOException e) {

            }
        }
    }

    /***
     * 单线程下载
     * @param responseBody body
     * @param filepath 文件位置
     * @param key tag
     * @param loadListener 建立监听
     * @return 是否下载成功
     */
    public boolean downLoad(ResponseBody responseBody, String filepath, String key, OnDownLoadBigListener loadListener) {
        FileOutputStream fos = null;
        InputStream inputStream = responseBody.byteStream();
        long length = responseBody.contentLength();// 流的大小
        try {
            fos = new FileOutputStream(filepath, true);
            int n = 0;
            int currentLength = 0; //当前的长度
            byte[] buf = new byte[1024 * 4];
            while ((n = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, n);
                currentLength = currentLength + n;
                final int terms = (int) (((float) currentLength) / (length) * 100); // 计算百分比
                handlerSuccess(key, terms, loadListener);
            }
            return true;
        } catch (Throwable e) {
            handlerFailed(key, e, loadListener);
            return false;
        } finally {
            try {
                if (fos != null)
                    fos.close();
                inputStream.close();
            } catch (IOException e) {

            }
        }
    }

    public boolean downLoadBIg(ResponseBody responseBody, String filepath, String key, long seek, OnDownLoadListener loadListener) {
        RandomAccessFile fos = null;
        InputStream inputStream = responseBody.byteStream();
        long length = responseBody.contentLength();// 流的大小
        try {
            fos = new RandomAccessFile(filepath, "rw");
            fos.seek(seek);
            int n = 0;
            int currentLength = 0; //当前的长度
            byte[] buf = new byte[1024 * 4];
            while ((n = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, n);
                currentLength = currentLength + n;
                final int terms = (int) (((float) currentLength) / (length) * 100); // 计算百分比
                handlerSuccess(key, terms, loadListener);
            }
            return true;
        } catch (Throwable e) {
            handlerFailed(key, e, loadListener);
            return false;
        } finally {
            try {
                if (fos != null)
                    fos.close();
                inputStream.close();
            } catch (IOException e) {

            }
        }
    }

    @SuppressLint("CheckResult")
    private void handlerFailed(String key, Throwable e, OnDownLoadListener loadListener) {
        Flowable.just(e)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(throwable -> loadListener.onError(key, e));
    }

    @SuppressLint("CheckResult")
    private void handlerFailed(String key, Throwable e, OnDownLoadBigListener loadListener) {
        Flowable.just(e)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(throwable -> loadListener.onError(key, e));
    }

    private int lastTerms = -1;

    @SuppressLint("CheckResult")
    private void handlerSuccess(String key, int terms, OnDownLoadListener loadListener) {
        if (terms > lastTerms) {
            this.lastTerms = terms;
            Flowable.just(terms)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> loadListener.onProgress(key, terms));
        }
    }

    @SuppressLint("CheckResult")
    private void handlerSuccess(String key, int terms, OnDownLoadBigListener loadListener) {
        if (terms > lastTerms) {
            this.lastTerms = terms;
            Flowable.just(terms)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> loadListener.onProgress(key, terms));
        }
    }

}
