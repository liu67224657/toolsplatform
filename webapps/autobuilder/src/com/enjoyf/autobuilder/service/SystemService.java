package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.util.SystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-28 下午5:08
 * Description:
 */
public class SystemService {

    private static SystemUtil util = new SystemUtil();


    public void loadProperties() throws IOException {
        String metaInfPath = util.getMetaInfFolderPath();
        String path = metaInfPath + "/cache.properties";
        InputStream is = new FileInputStream(new File(path));
        try {
            Properties prop = new Properties();
            prop.load(is);
            AndroidConfigContainer.rootFolder = prop.getProperty("root_folder");
            AndroidConfigContainer.src_upload_folder = prop.getProperty("src_upload_folder");
            AndroidConfigContainer.resource_upload_folder = prop.getProperty("resource_upload_folder");
            AndroidConfigContainer.build_all_cmd = prop.getProperty("build_all_cmd");
            AndroidConfigContainer.build_one_cmd = prop.getProperty("build_one_cmd");
            AndroidConfigContainer.apk_output_dir = prop.getProperty("apk_output_dir");
            AndroidConfigContainer.apk_download_dir = prop.getProperty("apk_download_dir");


            IOSConfigContainer.ios_root_folder = prop.getProperty("ios_root_folder");
            IOSConfigContainer.ios_src_upload_folder = prop.getProperty("ios_src_upload_folder");
            IOSConfigContainer.ios_resource_upload_folder = prop.getProperty("ios_resource_upload_folder");
            IOSConfigContainer.ios_output_dir = prop.getProperty("ios_output_dir");
            IOSConfigContainer.ios_download_dir = prop.getProperty("ios_download_dir");

            BatchClientConfigContainer.batch_template_upload_folder = prop.getProperty("batch_template_upload_folder");
            BatchClientConfigContainer.batch_batckground_upload_folder = prop.getProperty("batch_batckground_upload_folder");
            BatchClientConfigContainer.batch_client_root_folder = prop.getProperty("batch_client_root_folder");
            BatchClientConfigContainer.batch_build_all_cmd = prop.getProperty("batch_build_all_cmd");
            BatchClientConfigContainer.batch_build_one_cmd = prop.getProperty("batch_build_one_cmd");
            BatchClientConfigContainer.batch_apk_output_dir = prop.getProperty("batch_apk_output_dir");
            BatchClientConfigContainer.batch_apk_download_dir = prop.getProperty("batch_apk_download_dir");
            BatchClientConfigContainer.batch_icon_upload_folder = prop.getProperty("batch_icon_upload_folder");
            BatchClientConfigContainer.batch_loading_upload_folder = prop.getProperty("batch_loading_upload_folder");

            BatchClientConfigContainer.batch_ios_root_folder = prop.getProperty("batch_ios_root_folder");
            BatchClientConfigContainer.batch_ios_output_dir = prop.getProperty("batch_ios_output_dir");
            BatchClientConfigContainer.batch_ios_download_dir = prop.getProperty("batch_ios_download_dir");
            BatchClientConfigContainer.batch_ios_shell_folder = prop.getProperty("batch_ios_shell_folder");



        } finally {
            if (is != null)
                is.close();
        }
    }

}
