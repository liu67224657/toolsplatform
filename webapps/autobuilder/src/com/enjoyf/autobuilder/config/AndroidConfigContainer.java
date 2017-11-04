package com.enjoyf.autobuilder.config;

public class AndroidConfigContainer {
    public static String rootFolder = "";
    public static String src_upload_folder = "";
    public static String resource_upload_folder = "";
    public static String build_all_cmd = "";
    public static String build_one_cmd = "";
    public static String apk_output_dir = "";
    public static String apk_download_dir = "";
    public static boolean build_flag = true;

    public static String getTargetSourceFolder() {
        return rootFolder + "/src";
    }

    public static String getTargetResFolder() {
        return rootFolder + "/res";
    }

    public static String getTargetAssetFolder() {
        return rootFolder + "/assets";
    }

    public static String getAndroidManifestFile() {
        return rootFolder + "/AndroidManifest.xml.temp";
    }

    public static String getLayoutFolder() {
        return rootFolder + "/res/layout";
    }

    public static String getStringsFile() {
        return rootFolder + "/res/values/strings.xml";
    }

    public static String getBuildFolder() {
        return rootFolder + "/src/com/joyme/app/android";
    }
}
