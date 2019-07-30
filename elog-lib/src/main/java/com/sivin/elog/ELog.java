package com.sivin.elog;

/**
 * @author Sivin 2019/6/7
 */
public class ELog {


    private ELog() {
        throw new UnsupportedOperationException();
    }

    public static void e(Object obj, String msg) {
        if (obj == null) return;
        e(obj.getClass(), msg);
    }

    public static void e(Class<?> cls, String msg) {
        if (cls == null) return;
        e(cls.getSimpleName(), msg);
    }

    public static void e(String tag, String msg) {
        log(tag, ELogEngineImpl.globalTag + msg, Level.ERROR);
    }



    public static void w(Object obj, String msg) {
        if (obj == null) return;
        w(obj.getClass(), msg);
    }

    public static void w(Class<?> cls, String msg) {
        if (cls == null) return;
        w(cls.getSimpleName(), msg);
    }

    public static void w(String tag, String msg) {
        log(tag, ELogEngineImpl.globalTag + msg, Level.WARN);
    }



    public static void d(Object obj, String msg) {
        if (obj == null) return;
        d(obj.getClass(), msg);
    }

    public static void d(Class<?> cls, String msg) {
        if (cls == null) return;
        d(cls.getSimpleName(), msg);
    }

    public static void d(String tag, String msg) {
        log(tag, ELogEngineImpl.globalTag + msg, Level.DEBUG);
    }




    public static void i(Object obj, String msg) {
        if (obj == null) return;
        i(obj.getClass(), msg);
    }

    public static void i(Class<?> cls, String msg) {
        if (cls == null) return;
        i(cls.getSimpleName(), msg);
    }

    public static void i(String tag, String msg) {
        log(tag, ELogEngineImpl.globalTag + msg, Level.INFO);
    }


    public static void v(Object obj, String msg) {
        if (obj == null) return;
        v(obj.getClass(), msg);
    }
    public static void v(Class<?> cls, String msg) {
        v(cls.getSimpleName(), msg);
    }
    public static void v(String tag, String msg) {
        log(tag, ELogEngineImpl.globalTag + msg, Level.VERBOSE);
    }


    private static void log(String tag, String msg, Level level) {
       // ELogEngineImpl.printConsel(level, tag, msg);
    }
}
