package com.enjoyf.autobuilder.parse.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.autobuilder.bean.BatchAndroidParamBean;
import com.enjoyf.autobuilder.bean.BatchIOSParamBean;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.autobuilder.util.FileUtil;
import org.apache.commons.io.FileUtils;

import com.enjoyf.autobuilder.bean.AndroidParamBean;
import com.enjoyf.autobuilder.bean.IOSParamBean;
import com.enjoyf.autobuilder.parse.IParseFactory;

public class AbstractParseIOSFactory implements IParseFactory {

    private final static String CHARSET = "utf-8";
    private final static String REPLEACEMENT_GAMEGUIDE = "gameguide";
    protected IOSParamBean paramBean = new IOSParamBean();
    private List tempContextList = new ArrayList();

    @Override
    public void setIOSParamBean(IOSParamBean bean) {
        this.paramBean = bean;
    }

    @Override
    public void setBatchAndroidParamBean(BatchAndroidParamBean bean) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setBatchIosParamBean(BatchIOSParamBean bean) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean execute() throws Exception {
        copySrc();

//        复制icon
        copyIcon();

        // copy res
        copyImage();
        // copy 证书
        copyProfile();
        //copy shell
        copyShell();

        createOuputDir();

        // copy plist
//        copyPlist();
        //remove CrashReport
//        removeCrashReport();

        //replace configfiles
        replaceConfigFiles();
        replaceChannelFiles();
        ;
        replaceEnvironmentFiles();

//        removeCrashReport();
        return true;
    }

    private void createOuputDir() throws FileNotFoundException {
        if (!FileUtil.isFileOrDirExist(IOSConfigContainer.getAppStoreOutputDir())) {
            FileUtil.createDirectory(IOSConfigContainer.getAppStoreOutputDir());
        }

        if (!FileUtil.isFileOrDirExist(IOSConfigContainer.getJailbreakOutputDir())) {
            FileUtil.createDirectory(IOSConfigContainer.getJailbreakOutputDir());
        }
    }

    /**
     * APP_ID,674484336
     * ITUNES_URL,https://itunes.apple.com/cn/app/zhe-mi-gong-e-for-wo-jiaomt/id674484336?ls=1&mt=8
     * EFMobClick_AppKey,522461bf56240be8d401ec28
     * INTERFACE_URL,api.joyme.com
     * APP_KEY,3RWIp0r0F3sboFj9qSHD0Ii
     * CONST_CHANNEL_ID,AppStore
     * SHARE_STR,我在使用“@replaceStr”（http://r001.joyme.com/r001/app/XJY_gameguide_joyme.ipa）查看“%@”很实用，你也下一个试试看吧！
     * WEBVIEW_HOST,http://marticle.joyme.com
     * CACHE_ZIP_FILENAME,xjy_cache
     * EFAboutVC_appLabel,着迷攻略 for 星纪元
     * EFRightTB_moreappUrl,http://html.joyme.com/mobile/moreapp.html
     */
    protected void replaceConfigFiles() throws IOException {
        String sourceProfileFile = paramBean.getTargetFileFolder() + "/configfile";

        File constantsFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        List tempList = getConfigTextList(list);
        FileUtils.writeLines(constantsFile, CHARSET, tempList, "\r\n");
    }

    protected void replaceChannelFiles() throws IOException {
        String sourceProfileFile = paramBean.getTargetFileFolder() + "/channelfile";

        File constantsFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        List tempList = getChannelTextList(list);
        FileUtils.writeLines(constantsFile, CHARSET, tempList, "\r\n");
    }

    protected void replaceEnvironmentFiles() throws IOException {
        String sourceProfileFile = paramBean.getTargetFileFolder() + "/environmentfile";

        File constantsFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        List tempList = getEnvironmentTextList(list);
        FileUtils.writeLines(constantsFile, CHARSET, tempList, "\r\n");
    }

    private List getConfigTextList(List list) {
        List tempList = new ArrayList();

        String cachePrifix = paramBean.getPackageReplaceStr().replace(REPLEACEMENT_GAMEGUIDE, "");
        for (Object object : list) {
            String valueStr = object.toString().trim();

            if (valueStr.startsWith("APP_ID")) {
                valueStr = "APP_ID," + paramBean.getAppId();
            } else if (valueStr.startsWith("ITUNES_URL")) {
                valueStr = "ITUNES_URL," + paramBean.getItunesUrl();
            } else if (valueStr.startsWith("EFMobClick_AppKey")) {
                valueStr = "EFMobClick_AppKey," + paramBean.getUmenKey();
            } else if (valueStr.startsWith("INTERFACE_URL")) {
                if (paramBean.getBetaDomain() != null && paramBean.getBetaDomain().length() > 0) {
                    valueStr = "INTERFACE_URL," + paramBean.getBetaDomain();
                } else {
                    valueStr = "INTERFACE_URL," + paramBean.getOnlineDomain();
                }
            } else if (valueStr.startsWith("APP_KEY")) {
                valueStr = "APP_KEY," + paramBean.getAppKey();
            } else if (valueStr.startsWith("WEBVIEW_HOST")) {
                valueStr = "WEBVIEW_HOST," + paramBean.getWebViewHost();
            } else if (valueStr.startsWith("CACHE_ZIP_FILENAME")) {
                valueStr = "CACHE_ZIP_FILENAME," + cachePrifix + "_cache";
            } else if (valueStr.startsWith("EFAboutVC_appLabel")) {
                valueStr = "EFAboutVC_appLabel," + paramBean.getAboutTitle();
            } else if (valueStr.startsWith("EFRightTB_moreappUrl")) {
                valueStr = "EFRightTB_moreappUrl,http://html.joyme.com/mobile/moreapp.html";
            } else if (valueStr.startsWith("SHARE_STR")) {
                valueStr = "SHARE_STR,我在使用“" + paramBean.getAboutTitle() + "”（http://r001.joyme.com/r001/app/" + cachePrifix.toUpperCase() + "_gameguide_joyme.ipa）查看“%@”很实用，你也下一个试试看吧！";
            } else if (valueStr.startsWith("APP_TYPE")) {
                valueStr = "APP_TYPE," + paramBean.getAppType();
            }

            tempList.add(valueStr);
        }

        return tempList;
    }

    private List getChannelTextList(List list) {
        List tempList = new ArrayList();

        String cachePrifix = paramBean.getPackageReplaceStr().replace(REPLEACEMENT_GAMEGUIDE, "");
        String channelStr = paramBean.getChannel();
        if (null != channelStr && !"".equals(channelStr)) {
            String[] channels = channelStr.split(",");
            for (String channel : channels) {
                if (channel.contains("91")) {
                    tempList.add("91市场,91");
                } else if (channel.contains("tongbutui")) {
                    tempList.add("同步推,tongbutui");
                } else if (channel.contains("pp")) {
                    tempList.add("PP助手,pp");
                } else if (channel.contains("joyme")) {
                    tempList.add("JOYME,joyme");
                }
            }
        }
        return tempList;
    }

    private List getEnvironmentTextList(List list) {
        List tempList = new ArrayList();

        String cachePrifix = paramBean.getPackageReplaceStr().replace(REPLEACEMENT_GAMEGUIDE, "");
        String envStr = paramBean.getEnvironment();
        if (null != envStr && !"".equals(envStr)) {
            String[] envs = envStr.split(",");
            for (String env : envs) {
                if (env.contains("alpha")) {
                    tempList.add("alpha,alpha");
                } else if (env.contains("beta")) {
                    tempList.add("beta,beta");
                } else if (env.contains("dev")) {
                    tempList.add("dev,dev");
                } else if (env.contains("com")) {
                    tempList.add("com,com");
                }
            }
        }
        return tempList;
    }

    protected void changeItem(String suffix, String replace) {
        List tempList = new ArrayList();
        tempList.addAll(tempContextList);
        tempContextList.clear();

        for (int i = 0; i < tempList.size(); i++) {
            String line = (String) tempList.get(i);
            line = line.replaceAll(suffix, replace);
            tempContextList.add(line);
        }

    }


    private void copyProfile() throws IOException {
        String sourceProfileFile = paramBean.getSourceResFolder() + "/profiles";
        String tagretProfileFile = paramBean.getTargetProfilesFolder();

        copyFolder(sourceProfileFile, tagretProfileFile);
    }

    protected void removeCrashReport() throws IOException {
        String targetFolder = paramBean.getTargetFileFolder() + "/" + paramBean.getPackageReplaceStr() + "/ThirdPart/CrashReport";
        File file = new File(targetFolder);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
    }
//
//    protected void copyPlist() throws IOException {
//        String sourceFolder = paramBean.getSourceFileFolder() + "/plists";
//        String targetFolder = paramBean.getTargetFileFolder() + "/NATIVETEMPLATE/" + paramBean.getSourceRootFolderName() + "/Resources";
//
//        copyFolder(sourceFolder, targetFolder);
//    }

//    protected void copyRes() throws IOException {
//        String sourceFolder = paramBean.getSourceFileFolder() + "/images/res";
//        String targetFolder = paramBean.getTargetFileFolder();
//
//        copyFolder(sourceFolder, targetFolder);
//    }

    protected void copyImage() throws IOException {
        String sourceFolder = paramBean.getSourceResFolder() + "/images";
        String targetFolder = paramBean.getTargetImagesFolder();

        copyFolder(sourceFolder, targetFolder);
    }

    protected void copySrc() throws IOException {
        String sourceFolder = paramBean.getSourceFileFolder() + "/src";
        String targetFolder = paramBean.getTargetFileFolder();

        copyFolderWithDeleteTargetFrist(sourceFolder, targetFolder);
    }

    protected void copyShell() throws IOException {
//        String sourceFolder = paramBean.getSourceFileFolder() + "/shell";
        String sourceFolder = IOSConfigContainer.getTargetShellFolder();
        String targetFolder = paramBean.getTargetFileFolder();


        copyFolder(sourceFolder, targetFolder);
    }

    protected void copyFolderWithDeleteTargetFrist(String sourceFolder, String targetFolder) throws IOException {
        File sourceFile = new File(sourceFolder);
        File targetFile = new File(targetFolder);
        if (targetFile.exists()) {
            try {
//                FileUtils.deleteDirectory(targetFile);
                FileUtil.delFolder(targetFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileUtils.copyDirectory(sourceFile, targetFile);
    }

    //

    protected void copyIcon() throws IOException {
        String sourceFolder = paramBean.getSourceResFolder() + "/icons";
        String targetFolder = paramBean.getTargetIconsFolder();

        copyFolder(sourceFolder, targetFolder);
    }

    //
    protected void copyFolder(String sourceFolder, String targetFolder) throws IOException {
        File sourceFile = new File(sourceFolder);
        File targetFile = new File(targetFolder);

        FileUtils.copyDirectory(sourceFile, targetFile);
    }

    @Override
    public void setAndroidParamBean(AndroidParamBean bean) {
        // TODO Auto-generated method stub

    }

}
