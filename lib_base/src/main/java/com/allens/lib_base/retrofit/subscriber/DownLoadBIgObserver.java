package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadBigManager;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadBIgObserver extends BaseObserver<ResponseBody> {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_SIZE = Math.max(3, Math.min(CPU_COUNT - 1, 5));
    //核心线程数
    private static final int CORE_POOL_SIZE = THREAD_SIZE;

    //最大线程数目
    private int mThreadSize = CORE_POOL_SIZE;
    //下载地址
    private String url;
    private String key, path;


    public DownLoadBIgObserver(String url, String key, String path) {
        this.key = key;
        this.path = path;
        this.url = url;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }


    @Override
    public void onNext(ResponseBody response) {
        //获取文件的大小
        long contentLength = response.contentLength();
        LogHelper.i("下载大文件 文件总大小 %s, thread size %s", contentLength, mThreadSize);
        if (contentLength <= -1) {
            return;
        }
        for (int i = 0; i < mThreadSize; i++) {
            //初始化的时候，需要读取数据库
            //每个线程的下载的大小threadSize
            long threadSize = contentLength / mThreadSize;
            //开始下载的位置
            long start = i * threadSize;
            //结束下载的位置
            long end = start + threadSize - 1;
            if (i == mThreadSize - 1) {
                end = contentLength - 1;
            }

            String format = String.format("bytes=" + "%s-%s", start, end);
            LogHelper.i("启动下载 %s, 每个线程下载大小 %s, path %s", format, threadSize, path);
            DownLoadBigManager.getDownLoadBigResponse(url, format)
                    .subscribe(new DownLoadThreadObserver(start, key, path));
        }
    }

    private static byte[] readBytesFromInputStream(InputStream br_right, long skip, int length2) throws IOException {
        br_right.skip(skip);
        int readSize;
        byte[] bytes = null;
        bytes = new byte[length2];

        long length_tmp = length2;
        long index = 0;// start from zero
        while ((readSize = br_right.read(bytes, (int) index, (int) length_tmp)) != -1) {
            length_tmp -= readSize;
            if (length_tmp == 0) {
                break;
            }
            index = index + readSize;
        }
        return bytes;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
