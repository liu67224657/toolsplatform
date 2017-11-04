package com.enjoyf.autobuilder.parse.impl;

import com.enjoyf.autobuilder.bean.*;
import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.autobuilder.util.FileUtil;
import com.enjoyf.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefualtBatchIOSFactory extends AbstractParseIOSFactory {

    private final static String CHARSET = "utf-8";
    private final static String REPLEACEMENT_GAMEGUIDE = "gameguide";
    protected BatchIOSParamBean bean = new BatchIOSParamBean();
    private List tempContextList = new ArrayList();


    @Override
    public void setBatchIosParamBean(BatchIOSParamBean bean) {
        this.bean = bean;
    }

    @Override
    public boolean execute() throws Exception {
        copySrc();

//        复制icon
        copyIcon();

        copyShell();

        createOuputDir();

        replaceConfigFiles();

        replaceChannelFiles();

        replaceEnvironmentFiles();

        return true;
    }

    private void createOuputDir() throws FileNotFoundException {
        if (!FileUtil.isFileOrDirExist(BatchClientConfigContainer.getAppStoreOutputDir())) {
            FileUtil.createDirectory(BatchClientConfigContainer.getAppStoreOutputDir());
        }

        if (!FileUtil.isFileOrDirExist(BatchClientConfigContainer.getJailbreakOutputDir())) {
            FileUtil.createDirectory(BatchClientConfigContainer.getJailbreakOutputDir());
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
        String sourceProfileFile = bean.getTemplate().getTemplateUrl()  + "/src/configfile";

        File sourceFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(sourceFile, CHARSET);

        List tempList = getConfigTextList(list);
        FileUtils.writeLines(new File(BatchClientConfigContainer.getIOSSrcFolder()+"/configfile"), CHARSET, tempList, "\r\n");
    }

    protected void replaceChannelFiles() throws IOException {
        String sourceProfileFile = BatchClientConfigContainer.getIOSSrcFolder() + "/channelfile";

        File constantsFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        List tempList = getChannelTextList(list);
        FileUtils.writeLines(constantsFile, CHARSET, tempList, "\r\n");
    }

    protected void replaceEnvironmentFiles() throws IOException {
        String sourceProfileFile = BatchClientConfigContainer.getIOSSrcFolder() + "/environmentfile";

        File constantsFile = new File(sourceProfileFile);
        List list = FileUtils.readLines(constantsFile, CHARSET);

        List tempList = getEnvironmentTextList(list);
        FileUtils.writeLines(constantsFile, CHARSET, tempList, "\r\n");
    }

    private List getConfigTextList(List list) {
        List tempList = new ArrayList();

        String cachePrifix = bean.getCode().replace(REPLEACEMENT_GAMEGUIDE, "");
        for (Object object : list) {
            String valueStr = object.toString().trim();

            if (valueStr.startsWith("EFMobClick_AppKey")) {
                valueStr = "EFMobClick_AppKey," + bean.getUmenKey();
            } else if (valueStr.startsWith("APP_KEY")) {
                valueStr = "APP_KEY," + bean.getAppkey();
            } else if (valueStr.startsWith("CACHE_ZIP_FILENAME")) {
                valueStr = "CACHE_ZIP_FILENAME," + cachePrifix + "_cache";
            } else if (valueStr.startsWith("EFAboutVC_appLabel")) {
                valueStr = "EFAboutVC_appLabel," + bean.getTitle();
            } else if (valueStr.startsWith("EFRightTB_moreappUrl")) {
                valueStr = "EFRightTB_moreappUrl,http://html.joyme.com/mobile/moreapp.html";
            } else if (valueStr.startsWith("SHARE_STR")) {
                valueStr = "SHARE_STR," + bean.getShareContent();
            } else if (valueStr.startsWith("APP_TYPE")) {
                valueStr = "APP_TYPE,CMS";
            } else if (valueStr.startsWith("CMS_URL")) {
                valueStr = "CMS_URL ,http://marticle.joyme.com/marticle/game/" + cachePrifix + ".html";
            }

            tempList.add(valueStr);
        }

        return tempList;
    }

    private List getChannelTextList(List list) {
        List tempList = new ArrayList();

        String cachePrifix = bean.getCode().replace(REPLEACEMENT_GAMEGUIDE, "");
        String channelStr = bean.getIosChannel();
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

        String cachePrifix = bean.getCode().replace(REPLEACEMENT_GAMEGUIDE, "");
        String envStr = "com";
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

    protected void removeCrashReport() throws IOException {
        String targetFolder = BatchClientConfigContainer.getIOSSrcFolder() + "/" + bean.getCode() + "/ThirdPart/CrashReport";
        File file = new File(targetFolder);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
    }

    protected void copySrc() throws IOException {
        String sourceFolder = bean.getTemplate().getTemplateUrl() + "/src";
        String targetFolder = BatchClientConfigContainer.getIOSSrcFolder();

        copyFolderWithDeleteTargetFrist(sourceFolder, targetFolder);
    }

    protected void copyShell() throws IOException {

        String sourceFolder = BatchClientConfigContainer.batch_ios_shell_folder ;
        File file = new File(sourceFolder);

        String targetFolder = BatchClientConfigContainer.getIOSSrcFolder();
        File targetFile = new File(targetFolder);
        File[] shellFiles = file.listFiles();
        for (File shell : shellFiles) {
            FileCopyUtils.copy(shell, new File(targetFolder + "/" + shell.getName()));
        }

    }

    protected void copyFolderWithDeleteTargetFrist(String sourceFolder, String targetFolder) throws IOException {
        File sourceFile = new File(sourceFolder);
        File targetFile = new File(targetFolder);
        if (targetFile.exists()) {
            try {
                FileUtil.delFolder(targetFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileUtils.copyDirectory(sourceFile, targetFile);
    }

    //

    protected void copyIcon() throws IOException {
        BatchClientImage img = bean.getIcon();
        if (img != null && !StringUtil.isEmpty(img.getPicXB())) {
            File src = new File(img.getPicXB());
            File target = new File(BatchClientConfigContainer.getIOSSrcFolder() + "/1024.png");
            FileCopyUtils.copy(src, target);

//            File srcB = new File(img.getPicB());
//            File targetB = new File(BatchClientConfigContainer.getIOSSrcFolder() + "/Icon@2x.png");
//            FileCopyUtils.copy(srcB, targetB);
//
//            File srcM = new File(img.getPicM());
//            File targetM = new File(BatchClientConfigContainer.getIOSSrcFolder() + "/Icon-72.png");
//            FileCopyUtils.copy(srcM, targetM);
//
//            File srcS = new File(img.getPicS());
//            File targetS = new File(BatchClientConfigContainer.getIOSSrcFolder() + "/Icon.png");
//            FileCopyUtils.copy(srcS, targetS);
        }
    }

    //
    protected void copyFolder(String sourceFolder, String targetFolder) throws IOException {
        File sourceFile = new File(sourceFolder);
        File targetFile = new File(targetFolder);

        FileUtils.copyDirectory(sourceFile, targetFile);
    }

    @Override
    public void setAndroidParamBean(AndroidParamBean bean) {
    }

}
