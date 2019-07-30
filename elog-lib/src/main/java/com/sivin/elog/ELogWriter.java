//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sivin.elog;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;

class ELogWriter {
    private static final String LOG_WRITE_THREAD_NAME = "com.";
    private static ELogWriter instance;

    private static ELogWriter get() {
        if (instance == null) {
            synchronized (ELogWriter.class) {
                if (instance == null) {
                    instance = new ELogWriter();
                }
            }
        }
        return instance;
    }


    static void init(){
        get();
    }


    private WriterThread mWriterThread;

    private ELogWriter() {
        mWriterThread = new WriterThread(LOG_WRITE_THREAD_NAME);
        mWriterThread.start();
        mWriterThread.waitUntilReady();

        Application app = ELogEngineImpl.get().getApp();
        if(app == null){
            return;
        }
        if(permitPermissions(app.getApplicationContext())){
            mWriterThread.getHandler().tryOpenLogFile();
        }
    }


    /**
     * 尝试打开文件
     */
    void tryOpenLogFile() {
        mWriterThread.getHandler().tryOpenLogFile();
    }


    boolean isWriterEnable() {
        if (mWriterThread == null) {
            return false;
        }
        if (!mWriterThread.mReady) {
            return false;
        }
        if (mWriterThread.fileWriter == null) {
            return false;
        }
        if (mWriterThread.bufWriter == null) {
            return false;
        }
        return true;
    }

    /**
     * 关闭日志写入线程
     */
    void shutdown() {
        WriterHandler handler = mWriterThread.getHandler();
        if (handler != null) {
            handler.shutdown();
        }
    }

    private static class WriterThread extends Thread {

        private final Object mStartLock = new Object();
        private boolean mReady = false;

        private WriterHandler mHandler;

        private FileWriter fileWriter;
        private BufferedWriter bufWriter;

        WriterThread(String name) {
            super(name);
        }

        WriterHandler getHandler() {
            return mHandler;
        }

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new WriterHandler(this);
            synchronized (mStartLock) {
                mReady = true;
                mStartLock.notify();
            }
            Looper.loop();
            release();
            mReady = false;
        }

        void waitUntilReady() {
            synchronized (mStartLock) {
                while (!mReady) {
                    try {
                        mStartLock.wait();
                    } catch (InterruptedException ie) { /* not expected */ }
                }
            }
        }

        private void shutdown() {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                looper.quit();
            }
        }

        private void openLogFile() {
            try {
                File logDir = new File(ELogEngineImpl.get().logDirectory);
                if (!logDir.exists()) {
                    logDir.mkdir();
                }
                if (!logDir.exists()) {
                    return;
                }
                File logFile = new File(logDir, ELogEngineImpl.get().logName);
                if (logFile.exists()) {
                    if (logFile.length() > 1024 * 1024 * 20) {
                        logFile.delete();
                        logFile.createNewFile();
                    }
                } else {
                    logFile.createNewFile();
                }
                fileWriter = new FileWriter(logFile, true);
                bufWriter = new BufferedWriter(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
                fileWriter = null;
            }
        }

        private void write(String log) {
            try {
                if (bufWriter != null) {
                    bufWriter.write(log);
                    bufWriter.newLine();
                    bufWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void release() {
            mHandler = null;
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WriterHandler extends Handler {

        private static final int ON_MSG_OPEN_FILE = 0;
        private static final int ON_MSG_AVAILABLE = 1;
        private static final int ON_MSG_SHUTDOWN = 2;

        private WeakReference<WriterThread> mWeakRenderThread;

        WriterHandler(WriterThread thread) {
            mWeakRenderThread = new WeakReference<>(thread);
        }

        void tryOpenLogFile() {
            sendMessage(obtainMessage(ON_MSG_OPEN_FILE));
        }

        void writerMsg(String msg) {
            sendMessage(obtainMessage(ON_MSG_AVAILABLE, 0, 0, msg));
        }

        void shutdown() {
            sendMessage(obtainMessage(ON_MSG_SHUTDOWN));
        }


        @Override
        public void handleMessage(Message msg) {
            WriterThread writerThread = mWeakRenderThread.get();
            if (writerThread == null) {
                return;
            }
            int what = msg.what;
            switch (what) {
                case ON_MSG_OPEN_FILE:
                    writerThread.openLogFile();
                    break;

                case ON_MSG_AVAILABLE:
                    writerThread.write((String) msg.obj);
                    break;

                case ON_MSG_SHUTDOWN:
                    writerThread.shutdown();
                    break;
            }
        }
    }


    private static String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    /**
     * 检测是集合权限是否开启
     *
     * @param ctx
     * @return true-已开启权限，false-没有权限
     */
    private static boolean permitPermissions(Context ctx) {
        for (String permission : permissions) {
            if (permitPermission(ctx, permission)) {
                return true;
            }
        }
        return false;
    }


    private static boolean permitPermission(Context ctx, String permission) {
        if (ctx == null) return false;
        return ContextCompat.checkSelfPermission(ctx, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }


}
