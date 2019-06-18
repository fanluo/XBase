package com.allens.lib_base.retrofit.tool;

import androidx.annotation.NonNull;

import java.io.File;
import java.net.URL;

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
}
