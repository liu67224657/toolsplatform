package com.enjoyf.framework.jdbc.exception;

import com.enjoyf.framework.exception.JoymeException;

public class JoymeServiceException extends JoymeException {
    /**
     * 
     */
    private static final long serialVersionUID = 8319538244126274217L;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JoymeServiceException() {
        super();
    }

    public JoymeServiceException(String message) {
        super(message);
    }

    public JoymeServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public JoymeServiceException(Exception e) {
        super(e);
    }

    public JoymeServiceException(Exception e, String errorCode) {
        super(e, errorCode);
    }

    public JoymeServiceException(String message, String errorCode, boolean recordFlag) {
        super(message, errorCode, recordFlag);
    }

    public JoymeServiceException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message, errorCode, recordFlag, displayOnScreen);
    }
}
