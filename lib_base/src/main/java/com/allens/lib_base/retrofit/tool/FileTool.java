package com.allens.lib_base.retrofit.tool;

import android.util.Log;

import androidx.annotation.NonNull;

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

    public static boolean downToFile(ResponseBody responseBody, String savePath) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                File file = new File(savePath);
                if (!file.exists())
                    file.mkdirs();
                long allLength = responseBody.contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, allLength);
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    Log.e("log>>>>", "download read " + len);
                    mappedBuffer.put(buffer, 0, len);
                }
                return true;
            } catch (Throwable e) {
                Log.e("log>>>>", "download error 1 " + e.getMessage());
                return false;
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
            return false;
        }
    }
}
