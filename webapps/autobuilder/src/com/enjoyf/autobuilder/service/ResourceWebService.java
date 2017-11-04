package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.Resource;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-29 上午10:31
 * Description:
 */
public class ResourceWebService {
    UploadService uploadService = new UploadService();
    ResourceService resourceServce = new ResourceService();
    IosResourceService iosResourceService = new IosResourceService();

    public Resource uploadResource(MultipartFile srcZipFile, String code, String version, String name) throws IOException, JoymeServiceException, JoymeDBException {
        String path = AndroidConfigContainer.resource_upload_folder + "/" + version + "/" + code;

        uploadService.saveAndUnzipFile(srcZipFile, path, "resrouce.zip");
        Resource resource = null;

        List<Resource> resourceList = resourceServce.queryResource(null, code, version);
        if (resourceList == null || resourceList.size() == 0) {
            resource = new Resource();
            resource.setResourceCode(code);
            resource.setResourceAssets(path + "/assets");
            resource.setResourceRes(path + "/res");
            resource.setResourceVersion(version);
            resource.setResourceName(name);
            resourceServce.insertResource(null, resource);
        } else {
            resource = resourceList.get(0);
        }

        return resource;
    }

    public IosResource uploadIOSResource(MultipartFile srcZipFile, String code, String version, String name) throws IOException, JoymeServiceException, JoymeDBException {
        String path = IOSConfigContainer.ios_resource_upload_folder + "/" + version + "/" + code;

        uploadService.saveAndUnzipFile(srcZipFile, path, "resrouce.zip");
        IosResource resource = null;

        List<IosResource> resourceList = iosResourceService.queryResource(null, code, version);
        if (resourceList == null || resourceList.size() == 0) {
            resource = new IosResource();
            resource.setResourceCode(code);
            resource.setResourceIcons(path + "/icons");
            resource.setResourceImages(path + "/images");
            resource.setResourceProfiles(path + "/profiles");
            resource.setResourceVersion(version);
            resource.setResourceDir(path);
            resource.setResourceName(name);
            iosResourceService.insertIosResource(null, resource);
        } else {
            resource = resourceList.get(0);
        }

        return resource;
    }

}
