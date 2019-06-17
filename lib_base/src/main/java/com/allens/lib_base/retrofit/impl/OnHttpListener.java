package com.allens.lib_base.retrofit.impl;

import java.util.Map;

public interface OnHttpListener<T> {

    void onMap(Map<String, Object> map);

    void onSuccess(T t);

    void onError(Throwable e);
}