package com.enjoyf.wiki.util;

import com.enjoyf.util.FileUtil;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.wiki.container.PropertiesContainer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zhimingli on 2015/5/26.
 */
public class UploadQiniuUtil {
    private static String UOLOAD_URL = "http://up001.joyme.com/json/upload/qiniu";

    private static String getPath() {
        return PropertiesContainer.getInstance().getCacheFolder() + "/cardopionin";
    }

    public static String uploadQiniu(MultipartFile file) {

        if (file == null) {
            return null;
        }
        String retuenURL = null;

        String fileLocalPath = null;

        try {
            String path = getPath();
            if (!FileUtil.isFileOrDirExist(path)) {
                FileUtil.createDirectory(path);
            }
            fileLocalPath = path + "/" + file.getOriginalFilename();
            File file1 = new File(fileLocalPath);
            CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) file;
            commonsMultipartFile.transferTo(file1);

            HttpResult result = new HttpClientManager().postMultipart(UOLOAD_URL,
                    new HttpParameter[]{
                            new HttpParameter("Filedata", file1),
                            new HttpParameter("at", "joymeplatform"),
                            new HttpParameter("filetype", "original")
                    });
            if (result.getReponseCode() == 200) {

                //{"status_code":"1","billingStatus":false,"msg":"上传成功","result":["http://joymepic.qiniudn.com/qiniu/original/2015/05/92/68faa9920503d04f0e0be7d07d29b6992581.png"]}
                JSONObject object = JSONObject.fromObject(result.getResult());
                if (object.containsKey("result")) {
                    retuenURL = (String) ((JSONArray) object.get("result")).get(0);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                FileUtil.deleteFileOrDir(fileLocalPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return retuenURL;
    }
}


