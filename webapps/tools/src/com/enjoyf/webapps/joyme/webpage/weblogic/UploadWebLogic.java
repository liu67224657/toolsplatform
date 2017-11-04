package com.enjoyf.webapps.joyme.webpage.weblogic;

import com.enjoyf.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-15 下午6:15
 * Description:
 */
@Service(value = "uploadWebLogic")
public class UploadWebLogic {

    /**
     * 上传excel文件
     * @param multipartFile
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     */
    public File saveFile(MultipartFile multipartFile, String path, String fileName) throws IOException {

        if (!FileUtil.isFileOrDirExist(path)) {
            FileUtil.createDirectory(path);
        }

        File file = new File(path + "/" + fileName);

        FileCopyUtils.copy(multipartFile.getBytes(), file);

        System.out.println(file.getAbsolutePath());

        return file;
    }
}
