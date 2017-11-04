package com.enjoyf.framework.jdbc.exception;

import com.enjoyf.framework.exception.JoymeException;

public class JoymeDBException extends JoymeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4946855364148624338L;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JoymeDBException() {
        super();
    }

    public JoymeDBException(String message) {
        super(message);
    }

    public JoymeDBException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JoymeDBException(Exception e, String message, String errorCode) {
        super(e, message, errorCode);
    }

    public JoymeDBException(Exception e) {
        super(e);
    }

    public JoymeDBException(Exception e, String errorCode) {
        super(e, errorCode);
    }

    public JoymeDBException(String message, String errorCode, boolean recordFlag) {
        super(message, errorCode, recordFlag);
    }

    public JoymeDBException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message, errorCode, recordFlag, displayOnScreen);
    }
}
