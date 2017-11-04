package com.enjoyf.framework.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.enjoyf.framework.exception.JoymeException;

public final class LogService {
    public static Logger baseDaoLog = null;
    public static Logger systemLog = null;
    public static Logger freshLog = null;
    public static boolean isDebug = false;
    public final static String DEFAULT_ERROR_CODE = "99999999";
    static {
        if (baseDaoLog == null) {
            baseDaoLog = Logger.getLogger("BaseDao");
        }
        if (systemLog == null) {
            systemLog = Logger.getLogger("System");
        }
        if(freshLog == null){
            freshLog = Logger.getLogger("Fresh");
        }
    }

    //    public static void errorLog(Logger logger, Exception e) {
    //        errorLog(logger, null, null, e);
    //    }
    //
    //    public static void errorLog(Logger logger, String logInfo, Exception e) {
    //        errorLog(logger, null, logInfo, e);
    //    }

    public static void errorLog(Logger logger, String type, String logInfo, Exception e,boolean isDisplayToScreen) {
        boolean recordFlag = true;
//         isDisplayToScreen = false;
        if (e instanceof JoymeException) {
            JoymeException e1 = (JoymeException) e;
            recordFlag = e1.isRecordFlag();
            isDisplayToScreen = e1.isDisplayOnScreen();
        }

        if (recordFlag) {
            if (e != null) {
                logger.error("==========EXCEPTION!!!====ERROR!!!====================");
                String errorCode = getErrorCode(e);
                String info = "=====";
                if (type != null)
                    info += "It is a " + type + " Error,";
                info += "The code is :" + errorCode + "=====";
                logger.error(info);
                String msg = e.getMessage();
                if (logInfo == null) {
                    logInfo = msg;
                }
                logger.error(logInfo);
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter);
                e.printStackTrace(writer);
                StringBuffer buffer = stringWriter.getBuffer();
                logger.error(buffer.toString());
                logger.error("===================================================");
            } else {
                logger.error(logInfo);
            }
        }

        if (isDebug || isDisplayToScreen) {
            e.printStackTrace();
        }
    }

    //    public static void errorLog(String logInfo, String logType, Exception e, boolean isDisplayToScreen) {
    //        errorLog(baseDaoLog, null, logType, logInfo, e, isDisplayToScreen);
    //    }
    //
    //    public static void errorBaseDaoLog(String logInfo, Exception e, boolean isDisplayToScreen) {
    //        errorLog(baseDaoLog, null, "BaseDao", logInfo, e, isDisplayToScreen);
    //    }

    public static void errorBaseDaoLog(String logInfo, Exception e) {
        errorLog(baseDaoLog, "BaseDao", logInfo, e,false);
    }

    public static void errorBaseDaoLog(String logInfo) {
        errorLog(baseDaoLog, "BaseDao", logInfo, null,false);
    }

    //    public static void errorSystemLog(String logInfo, Exception e, boolean isDisplayToScreen) {
    //        errorLog(systemLog, null, "System", logInfo, e, isDisplayToScreen);
    //    }
    //
    //    public static void errorSystemLog(String logInfo, Exception e, String errorCode, boolean isDisplayToScreen) {
    //        errorLog(systemLog, errorCode, "System", logInfo, e, isDisplayToScreen);
    //    }
    /**
     * 默认不打到控制台
     * @param logInfo 错误信息
     * @param e 堆栈
     */
    public static void errorSystemLog(String logInfo, Exception e) {
        errorLog(systemLog, "System", logInfo, e,true);
    }

    public static void errorFreshLog(String logInfo, Exception e) {
        errorLog(freshLog, "Fresh", logInfo, e,true);
    }

    //    public static void errorSystemLog(String logInfo, Exception e, String errorCode) {
    //        errorLog(systemLog, errorCode, "System", logInfo, e, false);
    //    }
    /**
     * 默认不打到控制台(不包含堆栈异常)
     * @param logInfo 错误信息
     */
    public static void errorSystemLog(String logInfo) {
        errorLog(systemLog, "System", logInfo, null,false);
    }

    private static void warnLog(Logger logger, String logInfo, Exception e) {
        logger.warn("========================WARNING!!!=================");
        logger.warn(logInfo);
        if (e != null) {
            logger.warn(e.getMessage());
        }
        logger.warn("===================================================");
    }

    public static void warnBaseDaoLog(String logInfo, Exception e) {
        warnLog(baseDaoLog, logInfo, e);
    }

    public static void warnBaseDaoLog(String logInfo) {
        warnLog(baseDaoLog, logInfo, null);
    }

    public static void warnSystemLog(String logInfo, Exception e) {
        warnLog(systemLog, logInfo, e);
    }

    public static void warnSystemLog(String logInfo) {
        warnLog(systemLog, logInfo, null);
    }

    private static void infoLog(Logger logger, String logInfo, boolean isDisplayOnScreen) {
        logger.info(logInfo);
        if(isDisplayOnScreen)
            System.out.println(logInfo);
    }

    public static void infoBaseDaoLog(String logInfo, boolean isDisplayOnScreen) {
        infoLog(baseDaoLog, logInfo , isDisplayOnScreen);
    }

    public static void infoSystemLog(String logInfo, boolean isDisplayOnScreen) {
        infoLog(systemLog, logInfo , isDisplayOnScreen);
    }

    public static void infoFreshLog(String logInfo, boolean isDisplayOnScreen) {
        infoLog(freshLog, logInfo , isDisplayOnScreen);
    }
    
    public static void infoSystemLog(String logInfo) {
        infoLog(systemLog, logInfo , false);
    }

    private static void debugLog(Logger logger, String logInfo, boolean isDisplayOnScreen) {
        logger.debug(logInfo);
        if(isDisplayOnScreen)
            System.out.println(logInfo);
    }

    public static void debugBaseDaoLog(String logInfo, boolean isDisplayOnScreen) {
        debugLog(baseDaoLog, logInfo, isDisplayOnScreen);
    }

    public static void debugStstemLog(String logInfo, boolean isDisplayOnScreen) {
        debugLog(systemLog, logInfo, isDisplayOnScreen);
    }

    private static String getErrorCode(Exception e) {
        if (e == null) {
            return DEFAULT_ERROR_CODE;
        }

        try {
            Class clazz = Class.forName(e.getClass().getName());
            Method m1 = clazz.getMethod("getErrorCode", null);
            if (m1 != null) {
                String errorCode = (String) m1.invoke(e, null);
                return errorCode;
            }
            return DEFAULT_ERROR_CODE;
        } catch (Exception e2) {
            return DEFAULT_ERROR_CODE;
        }

    }

}
