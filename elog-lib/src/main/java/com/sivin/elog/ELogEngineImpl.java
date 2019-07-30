package com.sivin.elog;

import android.app.Application;
import android.util.Log;

public class ELogEngineImpl {

    //全局过滤的Tag
    static String globalTag = "ELog:";

    private static ELogEngineImpl engine;

    private Application app;

    //日志的父目录
     String logDirectory = "ELog";

    //日志名
    String logName = "elog.log";

    //是否开启显示控制台日志
    private boolean enableConsole = true;

    //控制台日志显示级别
    private Level logLevel = Level.INFO;

    //是否开启显示控制台日志
    private boolean enableFileLog = true;




    private ELogEngineImpl() {
    }

    static ELogEngineImpl get() {
        if (engine == null) {
            synchronized (ELogEngine.class) {
                if (engine == null) {
                    engine = new ELogEngineImpl();
                }
            }
        }
        return engine;
    }

    Application getApp(){
        return app;
    }

    void printConsole(Level level, String tag, String msg) {
        if (!enableConsole) return;
//        if (consoleLevel.level <= level.level) {
//            showConsoleLog(level, tag, msg);
//        }
    }


    void init(Application application) {
        app = application;
        if(enableFileLog){
            ELogWriter.init();
        }
    }


    void setEnableConsoleLog(boolean enable) {
        enableConsole = enable;
    }

    void setConsoleLogLevel(Level level) {
//        if (level == null) return;
//        consoleLevel = level;
    }


    void setEnableFileLog(boolean enable) {
        enableFileLog = enable;
    }

    void setFileLogLevel(Level level) {
        if (level == null) return;
//        fileLogLevel = level;
    }


    void setGlobalTag(String tag) {
        if (tag == null) return;
        if (tag.trim().equals("")) return;
        globalTag = tag;
    }


    private static void showConsoleLog(Level level, String tag, String log) {
        tag = "[" + tag + "]";
        switch (level) {
            case ERROR:
                Log.e(tag, log);
                break;
            case WARN:
                Log.w(tag, log);
                break;
            case DEBUG:
                Log.d(tag, log);
                break;
            case INFO:
                Log.i(tag, log);
                break;
            case VERBOSE:
                Log.v(tag, log);
        }
    }


}
