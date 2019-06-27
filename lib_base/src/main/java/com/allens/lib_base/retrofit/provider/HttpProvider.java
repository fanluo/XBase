package com.allens.lib_base.retrofit.provider;

import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.retrofit.HttpManager;
import com.allens.lib_base.retrofit.compose.RxComposeManager;
import com.allens.lib_base.retrofit.impl.ApiService;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;
import com.allens.lib_base.retrofit.subscriber.BeanObserver;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.UrlTool;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class HttpProvider {


    public static <T> Observable<ResponseBody> getObservableGet(String parameter, String url, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        String getUrl = url + "/" + parameter;
        if (map.size() > 0) {
            String param = UrlTool.prepareParam(map);
            if (param.trim().length() >= 1) {
                getUrl += "?" + param;
            }
        }
        return HttpManager.create().getService(ApiService.class)
                .doGet(getUrl)
                .compose(RxComposeManager.applyMain());
    }


    public static <T> Observable<ResponseBody> getObservablePost(String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        return HttpManager.create().getService(ApiService.class)
                .doPost(parameter, map)
                .compose(RxComposeManager.applyMain());
    }


    public static <T> Observable<ResponseBody> getObservableBody(String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        JSONObject json = new JSONObject(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
        return HttpManager.create().getService(ApiService.class)
                .doBody(parameter, requestBody)
                .compose(RxComposeManager.applyMain());
    }


    public static <T> Observable<ResponseBody> getObservablePut(String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        return HttpManager.create().getService(ApiService.class)
                .doPut(parameter, map)
                .compose(RxComposeManager.applyMain());
    }


    public static <T> Observable<ResponseBody> getObservableDelete(String parameter, final OnHttpListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        return HttpManager.create().getService(ApiService.class)
                .doDelete(parameter, map)
                .compose(RxComposeManager.applyMain());
    }


    public static Observable<ResponseBody> getObservableDownLoad(String url) {
        return HttpManager.create().getService(ApiService.class)
                .downloadSmallFile(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
