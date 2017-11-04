package com.enjoyf.autobuilder.bean;

public class IOSParamBean {

    // 源文件地址
    private String sourceFileFolder = null;
    // 打包程序中源文件的地址
    private String targetFileFolder = null;
    private String targetShellFolder = null;

    // res文件地址   被替换的文件地址
    private String targetIconsFolder = null;
    // 被替换的文件地址
    private String targetProfilesFolder = null;
    // 被替换的文件地址
    private String targetImagesFolder = null;

    // res文件地址
    private String sourceResFolder = null;
    private String targetResFolder = null;

    // 要替换的库名
    private String packageReplaceStr = null;
    // 被替换的库名的标记
    private String packageReplaceFlag = "tgameguide";

    private String appId;
    private String itunesUrl;
    private String umenKey;
    private String onlineDomain;
    private String betaDomain;
    private String appKey;
    private String webViewHost;
    private String aboutTitle;
    private String version;

    private String appName;
    private String appType;//wiki/cms

    private String channel;//channel
    private String environment;//environment
	private boolean debug;//debug

    public String getAboutTitle() {
        return aboutTitle;
    }

    public void setAboutTitle(String aboutTitle) {
        this.aboutTitle = aboutTitle;
    }

    public String getWebViewHost() {
        return webViewHost;
    }

    public void setWebViewHost(String webViewHost) {
        this.webViewHost = webViewHost;
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getOnlineDomain() {
        return onlineDomain;
    }

    public void setOnlineDomain(String onlineDomain) {
        this.onlineDomain = onlineDomain;
    }

    public String getUmenKey() {
        return umenKey;
    }

    public void setUmenKey(String umenKey) {
        this.umenKey = umenKey;
    }

    public String getItunesUrl() {
        return itunesUrl;
    }

    public void setItunesUrl(String itunesUrl) {
        this.itunesUrl = itunesUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSourceFileFolder() {
        return sourceFileFolder;
    }

    public void setSourceFileFolder(String sourceFileFolder) {
        this.sourceFileFolder = sourceFileFolder;
    }

    public String getTargetFileFolder() {
        return targetFileFolder;
    }

    public void setTargetFileFolder(String targetFileFolder) {
        this.targetFileFolder = targetFileFolder;
    }

    public String getTargetIconsFolder() {
        return targetIconsFolder;
    }

    public void setTargetIconsFolder(String targetIconsFolder) {
        this.targetIconsFolder = targetIconsFolder;
    }

    public String getTargetProfilesFolder() {
        return targetProfilesFolder;
    }

    public void setTargetProfilesFolder(String targetProfilesFolder) {
        this.targetProfilesFolder = targetProfilesFolder;
    }

    public String getTargetImagesFolder() {
        return targetImagesFolder;
    }

    public void setTargetImagesFolder(String targetImagesFolder) {
        this.targetImagesFolder = targetImagesFolder;
    }

    public String getSourceResFolder() {
        return sourceResFolder;
    }

    public void setSourceResFolder(String sourceResFolder) {
        this.sourceResFolder = sourceResFolder;
    }

    public String getTargetResFolder() {
        return targetResFolder;
    }

    public void setTargetResFolder(String targetResFolder) {
        this.targetResFolder = targetResFolder;
    }

    public String getPackageReplaceStr() {
        return packageReplaceStr;
    }

    public void setPackageReplaceStr(String packageReplaceStr) {
        this.packageReplaceStr = packageReplaceStr;
    }

    public String getPackageReplaceFlag() {
        return packageReplaceFlag;
    }

    public void setPackageReplaceFlag(String packageReplaceFlag) {
        this.packageReplaceFlag = packageReplaceFlag;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBetaDomain() {
        return betaDomain;
    }

    public void setBetaDomain(String betaDomain) {
        this.betaDomain = betaDomain;
    }

    public String getTargetShellFolder() {
        return targetShellFolder;
    }

    public void setTargetShellFolder(String targetShellFolder) {
        this.targetShellFolder = targetShellFolder;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
