package com.allens.lib_base.retrofit.download;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.subscriber.DownLoadBIgObserver;

public class DownLoadService extends IntentService {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int THREAD_SIZE = Math.max(3, Math.min(CPU_COUNT - 1, 5));
    //核心线程数
    private static final int CORE_POOL_SIZE = THREAD_SIZE;

    /***
     * 比较规范的action条件,包名+action+功能意义的描述
     */
    private static final String ACTION_DOWNLOAD = "com.allens.lib_base.retrofit.download.DownLoadService.Download";

    /***
     * 规范的key的写法
     */
    private static final String EXTRA_URL = "com.allens.lib_base.retrofit.download.DownLoadService.url";

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
                start(param1);
            }
        }
    }

    private void start(String url) {
        DownLoadBigManager.getDownLoadBigResponse(url, null)
                .subscribe(new DownLoadBIgObserver(url, CORE_POOL_SIZE));
    }


    public static void startDownLoad(Context context, String url) {
        Intent intent = new Intent(context, DownLoadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }
}
