package com.allens.lib_base.retrofit.tool;

import android.util.Log;

import androidx.annotation.NonNull;

import com.allens.lib_base.log.LogHelper;
import com.allens.lib_base.retrofit.download.bean.DownLoadBean;
import com.allens.lib_base.retrofit.download.impl.OnDownLoadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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

    public static DownLoadBean downToFile(ResponseBody responseBody, String savePath, String fileName, OnDownLoadListener loadListener) {
        DownLoadBean loadBean = new DownLoadBean();
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {

                String path = FileTool.getString(savePath, fileName);
                LogHelper.d("download save path %s", path);
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                long allLength = responseBody.contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                long currentLength = 0; //当前的长度
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, allLength);
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    Log.e("log>>>>", "download read " + len);
                    mappedBuffer.put(buffer, 0, len);
                    currentLength = currentLength + len;
                    final int terms = (int) (((float) currentLength) / (allLength) * 100); // 计算百分比
                    if (loadListener != null) {
                        loadListener.update(currentLength, allLength, currentLength == allLength);
                        loadListener.onProgress(terms);
                    }
                }
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
