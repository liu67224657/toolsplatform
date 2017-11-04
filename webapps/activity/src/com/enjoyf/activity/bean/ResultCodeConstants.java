package com.enjoyf.activity.bean;

import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;


public class ResultCodeConstants {

    protected static final int BASE_WXOP_RESCODE=10000;

    protected static final int BASE_USER_RESCODE=20000;
    protected static final int BASE_GOODS_RESCODE=30000;

    public static final ResultCodeConstants PARAM_ISEMPTY = new ResultCodeConstants(0, "param.is.empty");

    public static final ResultCodeConstants PHONE_IS_ERROR = new ResultCodeConstants(0, "phone.is.error");
    public static final ResultCodeConstants SUCCESS = new ResultCodeConstants(1, "success");
    public static final ResultCodeConstants ERROR = new ResultCodeConstants(-1, "error");

    private String msg;
    private int code;
    private String extMsg;

    public ResultCodeConstants(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public String getExtMsg() {
        return extMsg;
    }

    public void setExtMsg(String extMsg) {
        this.extMsg = extMsg;
    }

    public String getJsonString() {
        return getJsonObject().toString();
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rs", String.valueOf(this.getCode()));
        jsonObject.put("msg", this.getMsg());
        return jsonObject;
    }


    public static String resultCheckCallback(JSONObject jsonObject, String callback) {
        if (StringUtil.isEmpty(callback)) {
            return jsonObject.toString();
        } else {
            return callback + "([" + jsonObject.toString() + "])";
        }
    }

    public String getJsonString(String callback) {
        String jsonString=getJsonString();
        return StringUtil.isEmpty(callback)?jsonString:callback + "([" + jsonString + "])";
    }

}
