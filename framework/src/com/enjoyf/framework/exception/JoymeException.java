package com.enjoyf.framework.exception;

public abstract class JoymeException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -6632953182555279552L;
    /* 是否打印在out文件中 */
    public boolean isDisplayOnScreen = false;
    /* 是否打印在文件中  */
    public boolean recordFlag = true;
    public String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public JoymeException() {
        super();
    }

    public JoymeException(String message) {
        super(message);
    }

    public JoymeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JoymeException(Exception e, String message, String errorCode) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public JoymeException(String message, String errorCode, boolean recordFlag) {
        super(message);
        this.errorCode = errorCode;
        this.recordFlag = recordFlag;
    }

    public JoymeException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message);
        this.errorCode = errorCode;
        this.recordFlag = recordFlag;
        this.isDisplayOnScreen = displayOnScreen;
    }

    public JoymeException(Exception e) {
        super(e);
    }

    public JoymeException(Exception e, String errorCode) {
        super(e);
        this.errorCode = errorCode;
    }

    public boolean isRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(boolean recordFlag) {
        this.recordFlag = recordFlag;
    }

    public boolean isDisplayOnScreen() {
        return isDisplayOnScreen;
    }

    public void setDisplayOnScreen(boolean isDisplayOnScreen) {
        this.isDisplayOnScreen = isDisplayOnScreen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoymeException that = (JoymeException) o;

        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return errorCode != null ? errorCode.hashCode() : 0;
    }
}
