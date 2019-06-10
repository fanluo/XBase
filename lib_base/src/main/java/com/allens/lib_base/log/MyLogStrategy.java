package com.allens.lib_base.log;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Abstract class that takes care of background threading the file log operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 */
public class MyLogStrategy implements LogStrategy {

    private final Handler handler;

    public MyLogStrategy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void log(int level, String tag, String message) {
        handler.sendMessage(handler.obtainMessage(level, message));
    }

    public static class WriteHandler extends Handler {

        private final String folder;
        private final int maxFileSize;
        private final int maxFile;
        private final Context context;

        public WriteHandler(Looper looper, Context context, String folder, int maxFileSize, int maxFile) {
            super(looper);
            this.context = context;
            this.folder = folder;
            this.maxFileSize = maxFileSize;
            this.maxFile = maxFile;
        }

        @SuppressWarnings("checkstyle:emptyblock")
        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, "log");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) { /* fail silently */ }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            fileWriter.append(content);
        }

        private File getLogFile(String folderName, String fileName) {
            File folder = new File(folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Integer newFileCount = Hawk.get("log_index", 1);
            File newFile;
            File existingFile = null;
            newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));


            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
            }


            if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) {
                    Hawk.put("log_index",newFileCount);
                    delete();
                    return newFile;
                }
                return existingFile;
            }

            return newFile;
        }

        /***
         * create by allens
         *
         *
         *
         * if file size more than maxFile
         * delete fist file
         */
        private void delete() {
            File[] files = getFiles(folder);
            if (files.length > maxFile-1) {
                File lastFile = getLastFile(files);
                if (lastFile != null) {
                    lastFile.delete();
                }
            }
        }

        private File getLastFile(File[] files) {
            long minTime = 0l;
            String path = null;
            for (int i = 0; i < files.length; i++) {

                long time = files[i].lastModified();
                if (i == 0) {
                    path = files[i].getAbsolutePath();
                    minTime = time;
                }

                if (minTime > time) {
                    minTime = time;
                    path = files[i].getAbsolutePath();
                }
            }
            if (path == null) {
                return null;

            }
            return new File(path);
        }


        private File[] getFiles(String path) {
            File file = new File(path);
            return file.listFiles();
        }
    }
}