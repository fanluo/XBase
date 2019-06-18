package com.allens.allenstools.test_act;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allens.allenstools.R;
import com.allens.lib_base.base.BaseActivity;
import com.allens.lib_base.retrofit.XHttp;
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

    @Override
    protected void initListener() {
        xHttp = new XHttp.Builder(this)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .baseUrl("https://www.wanandroid.com")
                .retryOnConnectionFailure(false)
                .isLog(true)
                .isLogToFile(true)
//                .addHeard()
                .build();


        $(R.id.button3).setOnClickListener(v -> {
            xHttp.doGet(String.class, "/wxarticle/chapters/json", new OnHttpListener<String>() {
                @Override
                public void onMap(Map<String, Object> map) {
                    map.put("size","1");
                }

                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(Throwable e) {

                }
            });
        });
    }
}
