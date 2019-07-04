package com.allens.lib_base.retrofit.download;

import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.base.BaseFragment;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.provider.HttpProvider;
import com.allens.lib_base.retrofit.subscriber.DownLoadObserver;
import com.allens.lib_base.retrofit.tool.FileTool;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class DownLoadManager {

    public static void doDownLoad(String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, url)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .subscribe(new DownLoadObserver(key, url, downLoadPath, loadListener));
    }

    public static void doDownLoadByFragment(BaseFragment fragment, String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, url)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DownLoadObserver(key, url, downLoadPath, loadListener));
    }

    public static void doDownLoadByActivity(BaseActivity activity, String key, String url, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, url)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new DownLoadObserver(key, url, downLoadPath, loadListener));
    }


    public static void doDownLoad(String key, String url, String fileName, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, fileName)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .subscribe(new DownLoadObserver(key, fileName, downLoadPath, loadListener));
    }

    public static void doDownLoadByFragment(BaseFragment fragment, String key, String url, String fileName, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, fileName)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DownLoadObserver(key, fileName, downLoadPath, loadListener));
    }

    public static void doDownLoadByActivity(BaseActivity activity, String key, String url, String fileName, String downLoadPath, OnDownLoadListener loadListener) {
        if (FileTool.isAlreadyDownLoadFromUrl(downLoadPath, fileName)) {
            loadListener.already(key, downLoadPath);
            return;
        }
        loadListener.onStart(key);
        HttpProvider.getObservableDownLoad(url)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new DownLoadObserver(key, fileName, downLoadPath, loadListener));
    }


    public static Observable<ResponseBody> getDownLoadBigResponse(String url, String rang) {
        if (rang == null) {
            rang = "bytes=0-";
        }
        return HttpProvider.getObservableDownLoadBig(url, rang);
    }
}
