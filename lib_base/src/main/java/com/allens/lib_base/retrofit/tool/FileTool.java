package com.allens.lib_base.retrofit.tool;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.bean.DownLoadBean;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;
import com.allens.lib_base.retrofit.download.pool.DownLoadPool;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class FileTool {

    //判断是否已经下载
    public static Boolean isAlreadyDownLoadFromUrl(String downLoadPath, String downLoadUrl) {
        String filePath = createFile(downLoadPath);
        String fileName = getFileName(downLoadUrl);
        String newFilePath = filePath + fileName;
        File file = new File(newFilePath);
        return file.exists();
    }


    //获取下载文件的名称
    public static String getFileName(String downLoadUrl) {
        URL url = null;
        String filename = null;
        try {
            url = new URL(downLoadUrl);
            filename = url.getFile();
            return filename.substring(filename.lastIndexOf("/") + 1);
        } catch (Throwable e) {
            return null;
        }
    }

    //创建文件夹  返回文件夹地址
    public static String createFile(String downLoadPath) {
        String newPath = downLoadPath + File.separator;
        File file = new File(downLoadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return newPath;
    }

    @NonNull
    public static String getString(String downLoadPath, String fileNameOrUrl) {
        String newFilePath = null;
        String filePath = createFile(downLoadPath);
        if (fileNameOrUrl.contains("https://")
                || fileNameOrUrl.contains("http://")
                || fileNameOrUrl.contains("ftp://")
                || fileNameOrUrl.contains("rtsp://")
                || fileNameOrUrl.contains("mms://")
        ) {
            newFilePath = filePath + getFileName(fileNameOrUrl);
        } else {
            newFilePath = filePath + fileNameOrUrl;
        }
        return newFilePath;
    }

    /***
     *
     * @param url 下载地址 又被当做key来使用
     * @param saveCurrentLength 查询到当前保存的长度
     * @param responseBody 请求到的数据
     * @param savePath 保存地址
     * @param fileName 保存文件名称
     * @param handler  用于切换progress 线程
     * @param loadListener 监听
     * @return 自定义的数据对象
     */
    public static DownLoadBean downToFile(String url, long saveCurrentLength, ResponseBody responseBody, String savePath, String fileName, Handler handler, OnDownLoadListener loadListener) {
        DownLoadBean loadBean = new DownLoadBean();
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {

                String path = getString(savePath, fileName);
                LogHelper.d("download save path %s", path);
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                DownLoadPool.getInstance().add(url, path);

                //数据总长度
                long allLength = saveCurrentLength == 0 ? responseBody.contentLength() : saveCurrentLength + responseBody.contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                long currentLength = saveCurrentLength; //当前的长度
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, saveCurrentLength, allLength - saveCurrentLength);
                byte[] buffer = new byte[1024 * 4];
                int len;
                int lastTerms = 0;
                while ((len = inputStream.read(buffer)) != -1) {
//                    Log.e("log>>>>", "download read " + len);
                    mappedBuffer.put(buffer, 0, len);
                    currentLength = currentLength + len;
                    final int terms = (int) (((float) currentLength) / (allLength) * 100); // 计算百分比
                    if (terms != lastTerms) {
                        lastTerms = terms;
                        if (loadListener != null) {
                            long finalCurrentLength = currentLength;
                            Hawk.put(url, currentLength);
                            handler.post(() -> {
                                Disposable disposable = DownLoadPool.getInstance().getHashMap().get(url);
                                if (disposable != null && !disposable.isDisposed()) {
                                    loadListener.update(url, finalCurrentLength, allLength, finalCurrentLength == allLength);
                                    loadListener.onProgress(url, terms);
                                }
                            });
                        }
                    }
                }
                loadBean.setPath(path);
                loadBean.setIsSuccess(true);
                return loadBean;
            } catch (Throwable e) {
                Log.e("log>>>>", "download error 1 " + e.getMessage());
                loadBean.setThrowable(e);
                return loadBean;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (Throwable e) {
            Log.e("log>>>>", "download error 2 " + e.getMessage());
            loadBean.setThrowable(e);
            return loadBean;
        }
    }
}
