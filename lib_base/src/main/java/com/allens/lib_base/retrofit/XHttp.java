package com.allens.lib_base.retrofit;

import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.base.BaseFragment;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadManager;
import com.allens.lib_base.retrofit.download.service.DownLoadService;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.interceptor.HeardInterceptor;
import com.allens.lib_base.retrofit.pool.RxApiManager;
import com.allens.lib_base.retrofit.provider.HttpProvider;
import com.allens.lib_base.retrofit.subscriber.BeanObserver;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class XHttp {

    private static String url;

    public static final class Builder {
        public static final int DEFAULT_READ_TIMEOUT = 10;
        public static final int DEFAULT_WRITE_TIMEOUT = 10;
        public static final long DEFAULT_CONNECT_TIMEOUT = 10;
        public static final boolean retryOnConnectionFailure = false;


        public Builder() {
            HttpManager.create();
        }

        public Builder writeTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().writeTime(true);
            HttpManager.create().getOkhttpBuilder().writeTimeout(timeout, unit);
            return this;
        }

        public Builder readTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().readTime(true);
            HttpManager.create().getOkhttpBuilder().readTimeout(timeout, unit);
            return this;
        }

        public Builder connectTimeout(int timeout, TimeUnit unit) {
            HttpManager.create().getHttpConfigBuilder().connectTime(true);
            HttpManager.create().getOkhttpBuilder().connectTimeout(timeout, unit);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            url = baseUrl;
            HttpManager.create().getRetrofitBuilder().baseUrl(baseUrl);
            return this;
        }

        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            HttpManager.create().getHttpConfigBuilder().retryOnConnectionFailure(true);
            HttpManager.create().getOkhttpBuilder().retryOnConnectionFailure(retryOnConnectionFailure);
            return this;
        }

        public Builder isLog(Boolean isLog) {
            HttpManager.create().getHttpConfigBuilder().isLog(isLog);
            return this;
        }

        public Builder isLogToFile(Boolean toFile) {
            HttpManager.create().getHttpConfigBuilder().logToFile(toFile);
            return this;
        }

        public Builder addHeard(final Map<String, String> headMap) {
            if (headMap != null && headMap.size() > 0) {
                HttpManager.create().getOkhttpBuilder().addInterceptor(HeardInterceptor.register(headMap));
                return this;
            }
            return this;
        }


        public XHttp build() {
            HttpManager.create().build();
            return new XHttp();
        }
    }

    public <T> T getApiService(Class<T> tClass) {
        return HttpManager.create().getService(tClass);
    }

    public <T> void doGet(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableGet(parameter, url, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doGet(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableGet(parameter, url, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPost(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservablePost(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }


    public <T> void doPost(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservablePost(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }


    public <T> void doBody(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableBody(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doBody(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableBody(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPut(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservablePut(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPut(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservablePut(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doDelete(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableDelete(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doDelete(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        HttpProvider.getObservableDelete(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    /***
     * 下载文件，启动线程去下载，下载小文件，图片等
     * @param key key
     * @param url 下载地址
     * @param downLoadPath 下载的文件目录 例如 sdcard/美女文件夹
     * @param loadListener 下载监听
     */
    public void doDownLoad(String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoad(key, url, downLoadPath, loadListener);
    }

    public void doDownLoadBindFragment(BaseFragment fragment, String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoadByFragment(fragment, key, url, downLoadPath, loadListener);
    }

    public void doDownLoadBindActivtity(BaseActivity activity, String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoadByActivity(activity, key, url, downLoadPath, loadListener);
    }

    /***
     * 下载文件，启动线程去下载，下载小文件，图片等
     * @param key key
     * @param FileName 文件名称 例如 美女.png
     * @param url 下载地址 例如 sdcard/美女文件夹
     * @param downLoadPath 下载的文件目录
     * @param loadListener 下载监听
     */
    public void doDownLoad(String key, String url, String FileName, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoad(key, url, FileName, downLoadPath, loadListener);
    }

    public void doDownLoadBindActivtity(BaseActivity activity, String key, String url, String FileName, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoadByActivity(activity, key, url, FileName, downLoadPath, loadListener);
    }

    public void doDownLoadBindActivtity(BaseFragment fragment, String key, String url, String FileName, String downLoadPath, OnDownLoadListener loadListener) {
        DownLoadManager.doDownLoadByFragment(fragment, key, url, FileName, downLoadPath, loadListener);
    }

    public void downLoadBig(BaseActivity activity, String FileName, String downLoadPath, String url) {
        DownLoadService.startDownLoad(activity, url, FileName, downLoadPath, url);
    }


    public void cancelDownLoad(String key) {
        LogHelper.i("cancel download key %s", key);
        RxApiManager.newInstances().cancel(key);
    }

    public void cancelAllDownload() {
        RxApiManager.newInstances().cancelAll();
    }

}
