package com.enjoyf.autobuilder.bean;


public class BatchAndroidParamBean {
    private String code;
    private String appName;
    private String title;
    private AndroidTemplate template;
    private BatchClientImage icon;
    private BatchClientImage loading;
    private BatchClientImage backgroudImg;

    private String versionCode;
    private String versionName;
    private String shareContent; //分享内容

    //todo
    private String appkey="test";
    private String umenKey="umenket";
    private String packageReplaceFlag = "framework";
        private String weixinKey="";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AndroidTemplate getTemplate() {
        return template;
    }

    public void setTemplate(AndroidTemplate template) {
        this.template = template;
    }

    public BatchClientImage getBackgroudImg() {
        return backgroudImg;
    }

    public void setBackgroudImg(BatchClientImage backgroudImg) {
        this.backgroudImg = backgroudImg;
    }

    public String getPackageReplaceFlag() {
        return packageReplaceFlag;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getUmenKey() {
        return umenKey;
    }

    public void setUmenKey(String umenKey) {
        this.umenKey = umenKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public BatchClientImage getIcon() {
        return icon;
    }

    public void setIcon(BatchClientImage icon) {
        this.icon = icon;
    }

    public BatchClientImage getLoading() {
        return loading;
    }

    public void setLoading(BatchClientImage loading) {
        this.loading = loading;
    }

    public String getWeixinKey() {
        return weixinKey;
    }

    public void setWeixinKey(String weixinKey) {
        this.weixinKey = weixinKey;
    }
}
