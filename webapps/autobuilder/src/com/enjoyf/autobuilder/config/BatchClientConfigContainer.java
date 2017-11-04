package com.enjoyf.autobuilder.config;

public class BatchClientConfigContainer {
    public static String batch_client_root_folder = "";
    public static String batch_template_upload_folder = "";
    public static String batch_batckground_upload_folder = "";
    public static String batch_build_all_cmd = "";
    public static String batch_build_one_cmd = "";
    public static String batch_apk_output_dir = "";
    public static String batch_apk_download_dir = "";
    public static String batch_icon_upload_folder = "";
    public static String batch_loading_upload_folder = "";

    public static String batch_ios_root_folder = "";
    public static String batch_ios_output_dir = "";
    public static String batch_ios_download_dir = "";
    public static String batch_ios_shell_folder = "";


    public static String getTargetSourceFolder() {
        return batch_client_root_folder + "/src";
    }

    public static String getTargetLibFolder() {
        return batch_client_root_folder + "/libs";
    }

    public static String getTargetResFolder() {
        return batch_client_root_folder + "/res";
    }

    public static String getTargetAssetFolder() {
        return batch_client_root_folder + "/assets";
    }

    public static String getAndroidManifestFile() {
        return batch_client_root_folder + "/AndroidManifest.xml.temp";
    }

    public static String getLayoutFolder() {
        return batch_client_root_folder + "/res/layout";
    }

    public static String getStringsFile() {
        return batch_client_root_folder + "/res/values/strings.xml";
    }

    public static String getBuildFolder() {
        return batch_client_root_folder + "/src/com/joyme/app/android";
    }

    public static String getIOSSrcFolder() {
        return batch_ios_root_folder + "/src";
    }

    public static String getAppStoreOutputDir() {
        return batch_ios_root_folder + "/build/ipas/appstore";
    }

    public static String getJailbreakOutputDir() {
        return batch_ios_root_folder + "/build/ipas/jailbreak";
    }
}
