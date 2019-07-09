package com.allens.lib_base.retrofit.upload;

import com.allens.lib_base.retrofit.upload.bean.UploadBean;

import java.io.File;
import java.util.Map;

public interface OnUploadListener<T> {

    void onMapPart(Map<String, String> map);

    void onMapFile(Map<String, UploadBean> map);

    void onSuccess(T t);

    void onError(Throwable e);
}