package com.allens.lib_base.retrofit.subscriber;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadBigManager;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class DownLoadBIgObserver extends BaseObserver<ResponseBody> {
    //最大线程数目
    private int mThreadSize;
    //下载地址
    private String url;

    public DownLoadBIgObserver(String url, int corePoolSize) {
        this.mThreadSize = corePoolSize;
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
            LogHelper.i("启动下载 %s, 每个线程下载大小 %s", format, threadSize);
//            DownLoadBigManager.getDownLoadBigResponse(url, format)
//                    .subscribe(new DownLoadThreadObserver());
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
