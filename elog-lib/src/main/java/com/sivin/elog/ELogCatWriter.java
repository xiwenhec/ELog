package com.sivin.elog;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ELogCatWriter {

    // 日志抓取线程
    private static class LogDumper extends Thread {
        private static final String TAG = "LogDumper";

        //logCat进程
        private Process logcatProc;

        private BufferedReader reader = null;
        private boolean running = true;

        private String cmds = null;
        private String pid;
        private FileOutputStream os = null;

        //日志文件
        private File mLogFile;

        public LogDumper(String id, String dir,String logName) {
            pid = id;
            try {
                mLogFile = new File(dir,  logName);
                os = new FileOutputStream(mLogFile);
            } catch (FileNotFoundException e) {
                ELog.e(ELogCatWriter.class, e.getLocalizedMessage());
            }

            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            // cmds = "logcat | grep \"(" + mPID + ")\"";//打印所有日志信息
            // cmds = "logcat -s way"; //打印标签过滤信息
            cmds = "logcat *:e *:w | grep \"(" + pid + ")\"";
        }

        public void stopLogs() {
            running = false;
            ELog.e(ELogCatWriter.class, "ELog stop");
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                reader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
                String line = null;
                while (running && (line = reader.readLine()) != null) {
                    if (!running) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (os != null && line.contains(pid)) {
                        os.write((line + "\n").getBytes());
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (logcatProc != null) {
                    try {
                        reader.close();
                        reader = null;
                    } catch (IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                    os = null;
                }
            }
        }
    }
}
