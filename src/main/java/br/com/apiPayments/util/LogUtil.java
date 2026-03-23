package br.com.apiPayments.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private LogUtil() {}

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void info(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).info(message);
    }

    public static void warn(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).warn(message);
    }

    public static void error(Class<?> clazz, String message, Exception ex) {
        LoggerFactory.getLogger(clazz).error(message, ex);
    }

    public static void debug(Class<?> clazz, String message) {
        LoggerFactory.getLogger(clazz).debug(message);
    }
}
