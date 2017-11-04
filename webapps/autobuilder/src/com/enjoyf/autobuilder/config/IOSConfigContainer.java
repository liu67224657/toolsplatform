package com.enjoyf.autobuilder.config;

public class IOSConfigContainer {
    public static String ios_root_folder = "";
    public static String ios_src_upload_folder = "";
    public static String ios_resource_upload_folder = "";

    public static String ios_output_dir = "";
    public static String ios_download_dir = "";


    public static String getTargetSourceFolder() {
        return ios_root_folder + "/src";
    }

    public static String getTargetIconsFolder() {
        return ios_root_folder + "/build/icons";
    }

    public static String getTargetProfilesFolder() {
        return ios_root_folder + "/build/profiles";
    }

    public static String getTargetImagesFolder() {
        return ios_root_folder + "/build/images";
    }

    public static String getTargetShellFolder() {
        return ios_root_folder + "/shell";
    }

    public static String getAppStoreOutputDir() {
        return ios_root_folder + "/build/ipas/appstore";
    }

    public static String getJailbreakOutputDir() {
        return ios_root_folder + "/build/ipas/jailbreak";
    }
}
