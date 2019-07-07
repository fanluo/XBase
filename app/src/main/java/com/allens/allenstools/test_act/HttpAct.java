package com.allens.allenstools.test_act;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.allenstools.TestBean;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.XHttp;
import com.allens.lib_base.retrofit.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.impl.OnHttpListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Route(path = "/act/http")
public class HttpAct extends BaseActivity {

    private XHttp xHttp;

    @Override
    public int getContentViewId() {
        return R.layout.activity_http;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        permissionRequest(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        LogHelper.i("给了权限");
                        toast("给了权限");
                    } else {
                        LogHelper.i("拒绝权限");
                        toast("拒绝权限");
                    }
                });

        xHttp = new XHttp.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .baseUrl("http://134.175.88.222/")
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();


        $(R.id.button_get).setOnClickListener(v -> {
            do_body();
        });
    }

    private void do_body() {
        LogHelper.i("[网络请求 start]");
        xHttp.doBody(this, TestBean.class, "/app/new_word_list", new OnHttpListener<TestBean>() {
            @Override
            public void onMap(Map<String, Object> map) {
                map.put("at", "bc55768398bebca94edfcdd65b3b4777");
                map.put("pageIndex", 1);
                map.put("appid", "HITVxaFRrwGwyZ2P");
                map.put("id", "cca7ff68df2b");
                map.put("pageSize", 20);
                map.put("sn", "XB02A0A184601000999");
                map.put("lang", "cn");
            }


            @Override
            public void onSuccess(TestBean s) {
                LogHelper.i("get 请求成功 %s, thread %s", s.toString(), Thread.currentThread().getName());
                ((TextView) $(R.id.text)).setText("请求成功--->" + s.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogHelper.i("get 请求失败 %s, thread %s", e.getMessage(), Thread.currentThread().getName());
                ((TextView) $(R.id.text)).setText("请求失败--->" + e.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.i("activity finish ");
        xHttp.cancelAllDownload();
    }
}
