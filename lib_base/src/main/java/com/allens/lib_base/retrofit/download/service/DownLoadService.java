package com.allens.lib_base.retrofit.download.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.DownLoadBigManager;
import com.allens.lib_base.retrofit.subscriber.DownLoadBIgObserver;
import com.allens.lib_base.retrofit.tool.FileTool;

public class DownLoadService extends IntentService {


    /***
     * 比较规范的action条件,包名+action+功能意义的描述
     */
    private static final String ACTION_DOWNLOAD = "com.allens.lib_base.retrofit.download.service.DownLoadService.Download";

    /***
     * 规范的key的写法
     */
    private static final String EXTRA_URL = "com.allens.lib_base.retrofit.download.service.DownLoadService.url";
    //下载地址
    private static final String EXTRA_PATH = "com.allens.lib_base.retrofit.download.service.DownLoadService.path";
    private static final String EXTRA_KEY = "com.allens.lib_base.retrofit.download.service.DownLoadService.key";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownLoadService(String name) {
        super("download --->");
    }

    public DownLoadService() {
        super("download --->");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogHelper.i("download service create");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogHelper.i("download service onHandleIntent");
        if (intent != null) {
            //获取action条件
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                //获取字符串内容
                final String param1 = intent.getStringExtra(EXTRA_URL);
                final String key = intent.getStringExtra(EXTRA_KEY);
                final String path = intent.getStringExtra(EXTRA_PATH);
                start(param1, key, path);
            }
        }
    }

    /***
     *
     * 启动单线程下载
     *
     * @param url 下载地址
     * @param key tag
     * @param path 下载位置
     */
    private void start(String url, String key, String path) {
        DownLoadBigManager.getDownLoadBigResponse(url, null)
                .subscribe(new DownLoadBIgObserver(key, url, path));
    }


    public static void startDownLoad(BaseActivity context, String key, String FileName, String downLoadPath, String url) {
        String filePath = FileTool.getString(downLoadPath, FileName);
        Intent intent = new Intent(context, DownLoadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_KEY, key);
        intent.putExtra(EXTRA_PATH, filePath);
        context.startService(intent);
    }
}
