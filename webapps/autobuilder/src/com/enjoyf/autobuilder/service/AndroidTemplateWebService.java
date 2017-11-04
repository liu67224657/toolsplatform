package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.AndroidTemplate;
import com.enjoyf.autobuilder.bean.AppPlatform;
import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public class AndroidTemplateWebService {

    private UploadService uploadService = new UploadService();
    private AndroidTemplateService templateService = new AndroidTemplateService();

    public AndroidTemplate uploadTemplate(MultipartFile zipFile, String code, String version, int platform) throws IOException, JoymeServiceException, JoymeDBException {
        String path = BatchClientConfigContainer.batch_template_upload_folder + "/" + platform + "/" + version + "/" + code;
        uploadService.saveAndUnzipFile(zipFile, path, "template.zip");
        String filePath = path;
        AndroidTemplate template = null;
        List<AndroidTemplate> srcList = templateService.queryTemplate(null, code, version, platform);
        if (srcList == null || srcList.size() == 0) {
            template = new AndroidTemplate();
            template.setTemplateCode(code);
            template.setTemplateVersion(version);
            template.setTemplateUrl(filePath);
            template.setPlatform(platform);
            templateService.insertAndroidTemplate(null, template);
        } else {
            template = srcList.get(0);
        }
        return template;
    }

}