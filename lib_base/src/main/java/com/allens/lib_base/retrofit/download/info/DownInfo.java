package com.allens.lib_base.retrofit.download.info;

import com.allens.lib_base.retrofit.download.enums.DownState;
import com.allens.lib_base.retrofit.download.impl.HttpDownOnNextListener;
import com.allens.lib_base.retrofit.impl.ApiService;

import lombok.Data;

@Data
public class DownInfo {
    private long id;
    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;
    /*state状态数据库保存*/
    private DownState state;
    /*url*/
    private String url;
    /*是否需要实时更新下载进度,避免线程的多次切换*/
    private boolean updateProgress;
    /*回调监听*/
    private HttpDownOnNextListener listener;
    /*下载唯一的HttpService*/
    private ApiService service;
    /*超时设置*/
    private  int connectTimeOut=6;
}
