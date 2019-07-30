package com.sivin.elog;

public enum Level {

    /**
     * 错误
     */
    ERROR(1,"ERROR"),

    /**
     * 警告
     */
    WARN(2,"WARN"),

    /**
     * debug
     */
    DEBUG(3,"DEBUG"),

    /**
     * 通知
     */
    INFO(4,"INFO"),

    /**
     * 全部
     */
    VERBOSE(5,"VERBOSE");

    /**
     * 日志int映射
     */
    int level;

    /**
     * 日志名称
     */
    String name;

    Level(int level,String name) {
        this.level = level;
    }

}