package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-30 下午2:11
 * Description:
 */
public class BuildWebService {

    BuildLogService buildLogService = new BuildLogService();

    public String copyOutputApk(String pkgName) {
        String destDir = AndroidConfigContainer.apk_download_dir + "/" + pkgName + "/" + System.currentTimeMillis();
        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs();
        }
        File srcDirFile = new File(AndroidConfigContainer.apk_output_dir);
        if (!srcDirFile.exists()) {
            srcDirFile.mkdirs();
        }
        System.out.print("srcDir = " + srcDirFile.getAbsolutePath());
        File[] files = srcDirFile.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(pkgName.toUpperCase()) && file.getAbsolutePath().endsWith(".apk")) {
                try {
                    FileUtils.copyFile(file, new File(destDir + "/" + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return destDir;
    }

    public String copyOutputIpa(String pkgName) {
        String destDir = IOSConfigContainer.ios_download_dir + "/" + pkgName + "/" + System.currentTimeMillis();
        System.out.println("----dest dir --------" + destDir);

        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs();
        }

        File srcDirFile = new File(IOSConfigContainer.getJailbreakOutputDir());
        File[] files = srcDirFile.listFiles();
        for (File file : files) {
            System.out.println("----copy jailbreak ipas--------" + file.getName());
            if (file.getAbsolutePath().endsWith(".ipa")) {
                System.out.println("----start copy jailbreak ipas--------" + file.getName());
                try {
                    String fileName = destDir + "/" + file.getName();
                    System.out.println("-----------ouput ipa file:-----------------------" + fileName);
                    FileUtils.copyFile(file, new File(destDir + "/" + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File appStroeOutputFile = new File(IOSConfigContainer.getAppStoreOutputDir());
        File[] appStroeOutputFiles = appStroeOutputFile.listFiles();

        for (File file : appStroeOutputFiles) {
            System.out.println("----copy appstore ipas--------" + file.getName());
            if (file.getAbsolutePath().endsWith(".ipa")) {
                System.out.println("----start copy jailbreak ipas--------" + file.getName());
                try {
                    String fileName = destDir + "/" + file.getName();
                    System.out.println("-----------ouput ipa file:-----------------------" + fileName);
                    FileUtils.copyFile(file, new File(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return destDir;
    }

    public String copyBatchOutputApk(String pkgName) {
        String destDir = BatchClientConfigContainer.batch_apk_download_dir + "/" + pkgName + "/" + System.currentTimeMillis();
        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs();
        }
        File srcDirFile = new File(BatchClientConfigContainer.batch_apk_output_dir);
        if (!srcDirFile.exists()) {
            srcDirFile.mkdirs();
        }
        System.out.print("srcDir = " + srcDirFile.getAbsolutePath());
        File[] files = srcDirFile.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(pkgName.toUpperCase()) && file.getAbsolutePath().endsWith(".apk")) {
                try {
                    FileUtils.copyFile(file, new File(destDir + "/" + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return destDir;
    }

    public String copyBatchOutputIpas(String pkgName) {
        String destDir = BatchClientConfigContainer.batch_ios_download_dir + "/" + pkgName + "/" + System.currentTimeMillis();
        System.out.println("----dest dir --------" + destDir);

        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs();
        }

        File srcDirFile = new File(BatchClientConfigContainer.getJailbreakOutputDir());
        File[] files = srcDirFile.listFiles();
        for (File file : files) {
            System.out.println("----copy jailbreak ipas--------" + file.getName());
            if (file.getAbsolutePath().endsWith(".ipa")) {
                System.out.println("----start copy jailbreak ipas--------" + file.getName());
                try {
                    String fileName = destDir + "/" + file.getName();
                    System.out.println("-----------ouput ipa file:-----------------------" + fileName);
                    FileUtils.copyFile(file, new File(destDir + "/" + file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File appStroeOutputFile = new File(BatchClientConfigContainer.getAppStoreOutputDir());
        File[] appStroeOutputFiles = appStroeOutputFile.listFiles();

        for (File file : appStroeOutputFiles) {
            System.out.println("----copy appstore ipas--------" + file.getName());
            if (file.getAbsolutePath().endsWith(".ipa")) {
                System.out.println("----start copy jailbreak ipas--------" + file.getName());
                try {
                    String fileName = destDir + "/" + file.getName();
                    System.out.println("-----------ouput ipa file:-----------------------" + fileName);
                    FileUtils.copyFile(file, new File(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return destDir;
    }



    public List<String> getFileNames(String filePath) {
        List<String> returnList = new ArrayList<String>();

        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            returnList.add(file.getName());
        }
        return returnList;
    }


    public static void main(String[] args) {
        AndroidConfigContainer.apk_download_dir = "E:/opt/android_builder/apk";
        AndroidConfigContainer.apk_output_dir = "E:/opt/android_builder/ChannelApks/apk/output";
        new BuildWebService().copyOutputApk("mxhzw");
    }
}
