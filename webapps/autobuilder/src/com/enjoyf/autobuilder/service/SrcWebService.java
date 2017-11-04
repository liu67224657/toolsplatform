package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.IosSrc;
import com.enjoyf.autobuilder.bean.Src;
import com.enjoyf.autobuilder.config.AndroidConfigContainer;
import com.enjoyf.autobuilder.config.IOSConfigContainer;
import com.enjoyf.autobuilder.controller.IOSSrcController;
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
public class SrcWebService {
    UploadService uploadService = new UploadService();
    SrcService srcService = new SrcService();
    IosSrcService iosSrcService = new IosSrcService();

    public Src uploadAndroidSrc(MultipartFile srcZipFile, String version, String type) throws IOException, JoymeServiceException, JoymeDBException {
        String path = AndroidConfigContainer.src_upload_folder + "/" + version + "/" + type;
        uploadService.saveAndUnzipFile(srcZipFile, path, "src.zip");
        String filePath = path + "/src";
        Src src = null;
        List<Src> srcList = srcService.querySrc(null, version, type);
        if (srcList == null || srcList.size() == 0) {
            src = new Src();
            src.setSrcType(type);
            src.setSrcUrl(filePath);
            src.setSrcVersion(version);
            srcService.insertSrc(null, src);
        } else {
            src = srcList.get(0);
        }
        return src;
    }


    public IosSrc uploadIosSrc(MultipartFile srcZipFile, String version, String type) throws IOException, JoymeServiceException, JoymeDBException {
        String path = IOSConfigContainer.ios_src_upload_folder + "/" + version + "/" + type;


        uploadService.saveAndUnzipFile(srcZipFile, path, "src.zip");
        String filePath = path + "/src";

        IosSrc src = null;

        List<IosSrc> srcList = iosSrcService.querySrc(null, version, type);
        if (srcList == null || srcList.size() == 0) {
            src = new IosSrc();
            src.setSrcType(type);
            src.setSrcUrl(path);
            src.setSrcVersion(version);
            iosSrcService.insertIosSrc(null, src);
        } else {
            src = srcList.get(0);
        }

        return src;
    }

}
