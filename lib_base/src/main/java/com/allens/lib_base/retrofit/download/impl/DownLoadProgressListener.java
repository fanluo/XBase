package com.allens.lib_base.retrofit.download.impl;

public interface DownLoadProgressListener {
    /**
     * 下载进度
     *
     * @param read  读取
     * @param count 总共长度
     * @param done  是否完成
     */
    void update(long read, long count, boolean done);
}
