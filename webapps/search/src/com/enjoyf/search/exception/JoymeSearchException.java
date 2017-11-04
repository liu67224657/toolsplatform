package com.enjoyf.search.exception;

import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-10-10
 * Time: 上午11:46
 * To change this template use File | Settings | File Templates.
 */
public class JoymeSearchException extends JoymeServiceException {
    public static final JoymeSearchException PARAM_NOT_EXISTS = new JoymeSearchException("param.not.exists.", "-1020");

    public static final JoymeSearchException SYSTEM_ERROR = new JoymeSearchException("system error.", "-1");

    public static final JoymeSearchException CORE_NOT_EXISTS_EXCEPTION = new JoymeSearchException("solrcore is not exists.", "-1005");

    public static final JoymeSearchException FIELD_NOT_EXISTS_EXCEPTION = new JoymeSearchException("search field is not exists.", "-1010");

    public static final JoymeSearchException FIELD_TYPE_ILLEGEL_EXCEPTION = new JoymeSearchException("search field type illegl.", "-1031");


    public JoymeSearchException(String message, String errorCode) {
        super(message, errorCode);
    }
}
