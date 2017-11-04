package com.enjoyf.framework.exception;

public abstract class MobcentException extends Exception {
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

    public MobcentException() {
        super();
    }

    public MobcentException(String message) {
        super(message);
    }

    public MobcentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MobcentException(Exception e, String message, String errorCode) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public MobcentException(String message, String errorCode, boolean recordFlag) {
        super(message);
        this.errorCode = errorCode;
        this.recordFlag = recordFlag;
    }

    public MobcentException(String message, String errorCode, boolean recordFlag, boolean displayOnScreen) {
        super(message);
        this.errorCode = errorCode;
        this.recordFlag = recordFlag;
        this.isDisplayOnScreen = displayOnScreen;
    }

    public MobcentException(Exception e) {
        super(e);
    }

    public MobcentException(Exception e, String errorCode) {
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

}
