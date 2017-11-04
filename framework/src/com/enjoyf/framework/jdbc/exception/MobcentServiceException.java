package com.enjoyf.framework.jdbc.exception;

import com.enjoyf.framework.exception.MobcentException;

public class MobcentServiceException extends MobcentException {
    /**
     * 
     */
    private static final long serialVersionUID = 8319538244126274217L;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public MobcentServiceException() {
        super();
    }

    public MobcentServiceException(String message) {
        super(message);
    }

    public MobcentServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public MobcentServiceException(Exception e) {
        super(e);
    }

    public MobcentServiceException(Exception e, String errorCode) {
        super(e, errorCode);
    }

    public MobcentServiceException(String message, String errorCode, boolean recordFlag) {
        super(message, errorCode, recordFlag);
    }

    public MobcentServiceException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message, errorCode, recordFlag, displayOnScreen);
    }
}
