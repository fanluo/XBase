package com.allens.lib_base.log;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

public class LogHelper {


    @Getter
    @Setter
    private static boolean isOpen = false;


    /***
     * 初始化
     * @param context 上下文  建议使用Application
     * @param tag tag
     * @param path 文件位置
     * @param maxFileSize 单个文件最大 单位M
     * @param maxFile 最大文件数目
     */
    public static void init(Context context, String tag, String path, int maxFileSize, int maxFile) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        HandlerThread ht = new HandlerThread("log." + path);
        ht.start();

        Handler handler = new MyLogStrategy.WriteHandler(ht.getLooper(), context, path, maxFileSize * 1024 * 1000, maxFile);
        DiskLogStrategy diskLogStrategy = new DiskLogStrategy(handler);

        FormatStrategy csvFormatStrategy = CsvFormatStrategy.newBuilder()
                .tag(tag)
                .logStrategy(diskLogStrategy)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy));
    }


    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (isOpen) {
            Logger.log(priority, tag, message, throwable);
        }
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.d(message, args);
            print(message, args);
        }
    }

    private static void print(String message, @Nullable Object... args) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        System.out.println("Log>>>> " + "[" + element.getFileName() + "]" + "[" + element.getMethodName() + "]" + ":[" + element.getLineNumber() + "]" + String.format(message, args));
    }

    private static void print(Object object) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        System.out.println("Log>>>> " + "[" + element.getFileName() + "]" + "[" + element.getMethodName() + "]" + ":[" + element.getLineNumber() + "]" + object);
    }

    private static void print(Throwable throwable, String message, Object... args) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        System.out.println("Log>>>> " + "[" + element.getFileName() + "]" + "[" + element.getMethodName() + "]" + ":[" + element.getLineNumber() + "]" + String.format(message, args) + " throwable " + throwable.getMessage());
    }

    public static void d(@Nullable Object object) {
        if (isOpen) {
            Logger.d(object);
            print(object);
        }
    }


    public static void e(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.e(message, args);
            print(message, args);
        }
    }


    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.e(throwable, message, args);
            print(throwable, message, args);
        }
    }


    public static void i(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.i(message, args);
            print(message, args);
        }
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.v(message, args);
            print(message, args);
        }

    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.w(message, args);
            print(message, args);
        }
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        if (isOpen) {
            Logger.wtf(message, args);
            print(message, args);
        }
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json) {
        if (isOpen) {
            Logger.json(json);
            print(json);
        }
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml) {
        if (isOpen) {
            Logger.xml(xml);
            print(xml);
        }
    }

}
