package com.allens.lib_base.retrofit.download.impl;


/**
 * 成功回调处理
 */
public interface DownloadProgressListener {
    /**
     * 下载进度
     *
     * @param read      读取长度
     * @param count     总长度
     * @param done      是否完成
     */
    void update(long read, long count, boolean done);
}
