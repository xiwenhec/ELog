package com.sivin.elog;

import android.app.Application;

/**
 * @author Sivin 2019/7/19
 */
public class ELogEngine {

    private ELogEngine(){
        throw new RuntimeException("you should not initialize ELogEngine class");
    }


    /**
     * 初始化
     * @param application application
     */
    public static void init(Application application){
        ELogEngineImpl engine = ELogEngineImpl.get();
        engine.init(application);
    }


    /**
     * 设置全局TAG
     * @param tag tag:默认是"ELog:"
     */
    public static void setGlobalTag(String tag){
        ELogEngineImpl engine = ELogEngineImpl.get();
        engine.setGlobalTag(tag);
    }


    /**
     * 设置log存放的目录
     * @param directory log存放的目录 默认是："ELog"
     */
    public static void setLogDirectory(String directory){

    }


    /**
     * 设置存放log的名字
     * @param fileName 日志名 默认是： "elog.log"
     */
    public static void setLogFileName(String fileName){

    }

    /**
     * 设置控制台显示日志级别
     * @param level level
     */
    public static void setLogLevel(Level level) {
        ELogEngineImpl engine = ELogEngineImpl.get();
//        engine.setLogLevel(level);
    }


    /**
     * 是否开启文件日志写入
     * @param enable true :开启，false：关闭
     */
    public static void enableFileLog(boolean enable){

    }

    /**
     * 注销log
     */
    public static void deinit(){

    }


}




