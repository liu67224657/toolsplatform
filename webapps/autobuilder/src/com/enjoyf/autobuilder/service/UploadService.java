package com.enjoyf.autobuilder.service;

import com.enjoyf.autobuilder.util.ZipUtil;
import com.enjoyf.util.FileUtil;
import org.im4java.core.IM4JavaException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-29 上午9:37
 * Description:
 */
public class UploadService {
    public void saveAndUnzipFile(MultipartFile multipartFile, String path, String fileName) throws IOException {

        if (!FileUtil.isFileOrDirExist(path)) {
            FileUtil.createDirectory(path);
        }

        String filePath = path + "/" + fileName;

        File file = new File(filePath);

        FileCopyUtils.copy(multipartFile.getBytes(), file);

        System.out.println(file.getAbsolutePath());

        //解压
//        ZipUtil.unzip(filePath, path);
        ZipUtil.unzip(file, path);

        //删除zip文件
        file.delete();
    }


    public String saveFile(MultipartFile multipartFile, String path, String fileName) throws IOException, InterruptedException, IM4JavaException {

        if (!FileUtil.isFileOrDirExist(path)) {
            FileUtil.createDirectory(path);
        }

        String filePath = path + "/" + fileName;

        File file = new File(filePath);

        FileCopyUtils.copy(multipartFile.getBytes(), file);

        return filePath;
    }


    public String resizeFile(File file, String path, String fileName, int width, int height) throws IOException, InterruptedException, IM4JavaException {

        if (!FileUtil.isFileOrDirExist(path)) {
            FileUtil.createDirectory(path);
        }

        int postition = file.getName().lastIndexOf(".");
        String prefixFileName = fileName.substring(0, postition);
        String extName = fileName.substring(postition);

        String returnPath = path + "/" + prefixFileName + "_" + width + "_" + height + extName;

        ImageGenerator.resizeImage(file.getAbsolutePath(), width, height, 100.00, returnPath);

        System.out.println(returnPath);

        return returnPath;
    }


}
