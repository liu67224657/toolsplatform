package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.bean.AppPlatform;
import com.enjoyf.autobuilder.bean.BatchClientImage;
import com.enjoyf.autobuilder.config.BatchClientConfigContainer;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.im4java.core.IM4JavaException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ImageWebService {

    private UploadService uploadService = new UploadService();
    private BackgroudImgService service = new BackgroudImgService();

    private static final String SIZE_XB = "xb";
    private static final String SIZE_B = "b";
    private static final String SIZE_M = "m";
    private static final String SIZE_S = "s";

    public BatchClientImage uploadBackgroud(MultipartFile file, int platform) throws IOException, JoymeServiceException, JoymeDBException, IM4JavaException, InterruptedException {
        String path = BatchClientConfigContainer.batch_batckground_upload_folder + "/" + platform;
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = String.valueOf(System.currentTimeMillis()) + extName;
        String filePath = uploadService.saveFile(file, path, fileName);

        File orgFile = new File(filePath);

        Map<String, String> map = resizeBGByPlatform(orgFile, path, fileName, platform);

        String fileXB = map.get(SIZE_XB);
        String fileB = map.get(SIZE_B);
        String fileM = map.get(SIZE_M);
        BatchClientImage img = new BatchClientImage();

        img.setPic(filePath);
        img.setPicXB(fileXB);
        img.setPicB(fileB);
        img.setPicM(fileM);
        img.setPlatform(platform);
        img.setPicType(BatchClientImage.IMAGE_TYPE_BACKGROUND);
        service.insertBackgroudImg(null, img);
        return img;
    }


    public BatchClientImage uploadLoading(MultipartFile file, int platform) throws IOException, JoymeServiceException, JoymeDBException, IM4JavaException, InterruptedException {
        String path = BatchClientConfigContainer.batch_loading_upload_folder + "/" + platform;
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = String.valueOf(System.currentTimeMillis()) + extName;
        String filePath = uploadService.saveFile(file, path, fileName);

        File orgFile = new File(filePath);

        Map<String, String> map = resizeLoadingByPlatform(orgFile, path, fileName, platform);

        String fileXB = map.get(SIZE_XB);
        String fileB = map.get(SIZE_B);
        String fileM = map.get(SIZE_M);
        BatchClientImage img = new BatchClientImage();

        img.setPic(filePath);
        img.setPicXB(fileXB);
        img.setPicB(fileB);
        img.setPicM(fileM);
        img.setPlatform(platform);
        img.setPicType(BatchClientImage.IMAGE_TYPE_LOADINGPAGE);
        service.insertBackgroudImg(null, img);
        return img;
    }

    public BatchClientImage uploadIcon(MultipartFile file, int platform) throws IOException, JoymeServiceException, JoymeDBException, IM4JavaException, InterruptedException {
        String path = BatchClientConfigContainer.batch_icon_upload_folder + "/" + platform;
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = String.valueOf(System.currentTimeMillis()) + extName;
        String filePath = uploadService.saveFile(file, path, fileName);

        File orgFile = new File(filePath);

        Map<String, String> map = resizeIconByPlatform(orgFile, path, fileName, platform);

        String fileXB = map.get(SIZE_XB);
        String fileB = map.get(SIZE_B);
        String fileM = map.get(SIZE_M);
        String fileS = map.get(SIZE_S);
        BatchClientImage img = new BatchClientImage();

        img.setPic(filePath);
        img.setPicXB(fileXB);
        img.setPicB(fileB);
        img.setPicM(fileM);
        img.setPicS(fileS);
        img.setPlatform(platform);
        img.setPicType(BatchClientImage.IMAGE_TYPE_ICON);
        service.insertBackgroudImg(null, img);
        return img;
    }

    private Map<String, String> resizeLoadingByPlatform(File orgFile, String path, String fileName, int platform) throws IOException, IM4JavaException, InterruptedException {
        String fileXB = "";
        String fileB = "";
        String fileM = "";
        if (platform == AppPlatform.APP_PLATFORM_ANDROID) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 800, 1200);
            fileB = uploadService.resizeFile(orgFile, path, fileName, 480, 800);
            fileM = uploadService.resizeFile(orgFile, path, fileName, 320, 480);
        } else if (platform == AppPlatform.APP_PLATFORM_IOS) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 640, 1136);
            fileB = uploadService.resizeFile(orgFile, path, fileName, 640, 960);
            fileM = uploadService.resizeFile(orgFile, path, fileName, 320, 480);
        }

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(SIZE_XB, fileXB);
        returnMap.put(SIZE_B, fileB);
        returnMap.put(SIZE_M, fileM);

        return returnMap;
    }

    private Map<String, String> resizeBGByPlatform(File orgFile, String path, String fileName, int platform) throws IOException, IM4JavaException, InterruptedException {
        String fileXB = "";
        String fileB = "";
        String fileM = "";
        if (platform == AppPlatform.APP_PLATFORM_ANDROID) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 800, 1200);
            fileB = uploadService.resizeFile(orgFile, path, fileName, 480, 800);
            fileM = uploadService.resizeFile(orgFile, path, fileName, 320, 480);
        } else if (platform == AppPlatform.APP_PLATFORM_IOS) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 640, 1008);
            fileB = uploadService.resizeFile(orgFile, path, fileName, 640, 832);
            fileM = uploadService.resizeFile(orgFile, path, fileName, 320, 416);
        }

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(SIZE_XB, fileXB);
        returnMap.put(SIZE_B, fileB);
        returnMap.put(SIZE_M, fileM);

        return returnMap;
    }


    private Map<String, String> resizeIconByPlatform(File orgFile, String path, String fileName, int platform) throws IOException, IM4JavaException, InterruptedException {
        String fileXB = "";
        String fileB = "";
        String fileM = "";
        String fileS = "";
        if (platform == AppPlatform.APP_PLATFORM_ANDROID) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 96, 96);
            fileB = uploadService.resizeFile(orgFile, path, fileName, 72, 72);
            fileM = uploadService.resizeFile(orgFile, path, fileName, 48, 48);
            fileS = uploadService.resizeFile(orgFile, path, fileName, 24, 24);
        } else if (platform == AppPlatform.APP_PLATFORM_IOS) {
            fileXB = uploadService.resizeFile(orgFile, path, fileName, 1024, 1024);
        }

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(SIZE_XB, fileXB);
        returnMap.put(SIZE_B, fileB);
        returnMap.put(SIZE_M, fileM);
        returnMap.put(SIZE_S, fileS);
        return returnMap;
    }

}

