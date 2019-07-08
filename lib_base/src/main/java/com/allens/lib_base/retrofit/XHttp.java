package com.allens.lib_base.retrofit;

import android.util.Log;

import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.base.BaseFragment;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadManager;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.interceptor.HeardInterceptor;
import com.allens.lib_base.retrofit.provider.ObservableProvider;
import com.allens.lib_base.retrofit.subscriber.BeanObserver;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class XHttp {

    @Getter
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
        ObservableProvider.getObservableGet(parameter, url, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doGet(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservableGet(parameter, url, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPost(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservablePost(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }


    public <T> void doPost(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservablePost(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }


    public <T> void doBody(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservableBody(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doBody(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservableBody(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPut(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservablePut(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doPut(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservablePut(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doDelete(BaseFragment context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservableDelete(parameter, listener)
                .compose(context.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public <T> void doDelete(BaseActivity context, Class<T> tClass, String parameter, final OnHttpListener<T> listener) {
        ObservableProvider.getObservableDelete(parameter, listener)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BeanObserver<T>(tClass, listener));
    }

    public void doDownload(String url, String filePath, OnDownLoadListener loadListener) {
        LogHelper.d("click download");
        DownLoadManager.startDownLoad(url, filePath, loadListener);
    }

}
