package com.enjoyf.search.util;

import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-8-5
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
public class ResultMsg {
    private String code;
    private String msg;


    public static final ResultMsg SUCCESS = new ResultMsg("1", "success");

    public static final ResultMsg SYSTEM_ERROR = new ResultMsg("-1", "system.error");
    public static final ResultMsg SERVER_ERROR = new ResultMsg("-1000", "server.error");
    public static final ResultMsg CORE_NOT_EXISTS = new ResultMsg("-1005", "core.not.exists");
    public static final ResultMsg FIELD_NOT_EXISTS = new ResultMsg("-1010", "field.not.exists");
    public static final ResultMsg FIELD_ERROR = new ResultMsg("-1011", "field.error");

    public static final ResultMsg PARAM_NOT_EXISTS = new ResultMsg("-1020", "param.not.exists");

    public static final ResultMsg FIELDMAP_IS_NULL = new ResultMsg("-1021", "fieldmap.is.null");


    public ResultMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public String returnJsonStr() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rs", this.code);
        jsonObject.put("msg", this.msg);
        return jsonObject.toString();
    }
}
