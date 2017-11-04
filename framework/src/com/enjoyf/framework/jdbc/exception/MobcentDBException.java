package com.enjoyf.framework.jdbc.exception;

import com.enjoyf.framework.exception.MobcentException;

public class MobcentDBException extends MobcentException {
    /**
     * 
     */
    private static final long serialVersionUID = 4946855364148624338L;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public MobcentDBException() {
        super();
    }

    public MobcentDBException(String message) {
        super(message);
    }

    public MobcentDBException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MobcentDBException(Exception e, String message, String errorCode) {
        super(e, message, errorCode);
    }

    public MobcentDBException(Exception e) {
        super(e);
    }

    public MobcentDBException(Exception e, String errorCode) {
        super(e, errorCode);
    }

    public MobcentDBException(String message, String errorCode, boolean recordFlag) {
        super(message, errorCode, recordFlag);
    }

    public MobcentDBException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message, errorCode, recordFlag, displayOnScreen);
    }
}
