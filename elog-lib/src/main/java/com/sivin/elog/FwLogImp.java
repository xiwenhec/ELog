package com.sivin.elog;

import android.content.Context;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

class FwLogImp {

    private static FwLogImp instance = null;
    private static ArrayList<String> levelArray = new ArrayList<>();
    private static SimpleDateFormat sdf;
    private static ELogWriter logWriter;


    static void init(Context context, String sdkVersion) {
        if (instance == null) {
            sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
            ELogEntity.init(context);
           // checkWriter();
            instance = new FwLogImp();
        }

//        ELogEntity.getInstance().setVersion(sdkVersion);
//        ELogEntity.getInstance().setConsoleLogLevel(Level.VERBOSE);
//        ELogEntity.getInstance().setMonitorLevel(ELog.INFO_LEVEL);
    }


    void setConsoleLogLevel(int level) {
        ELogEntity.getInstance().setConsoleLogLevel(level);
    }

    void setMonitorLevel(int level) {
        ELogEntity.getInstance().setMonitorLevel(level);
    }

    void writeLog(long tid, long timestamp, Level level, String tag, String msg) {
        if (ELogEntity.getInstance().getLogMode() != 0) {
//            if (level <= ELogEntity.getInstance().getConsoleLogLevel()) {
//                showConsoleLog(level, tag, msg);
//            }
        }
//        if (level <= ELogEntity.getInstance().getMonitorLevel() && checkWriter()) {
//            String gmtTime = sdf.format(new Date(timestamp));
//            String sb = gmtTime + " " + tid + " " + levelArray.get(level) + " [" + tag + "] " + msg;
//            logWriter.writer(sb);
//        }
    }





    void deInit() {
        logWriter.shutdown();
        logWriter = null;
        sdf = null;
        instance = null;
    }

    static {
        levelArray.add("N");
        levelArray.add("F");
        levelArray.add("E");
        levelArray.add("W");
        levelArray.add("I");
        levelArray.add("D");
        levelArray.add("V");
    }

    static FwLogImp getInstance() {
        return instance;
    }

}
