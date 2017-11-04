package com.enjoyf.webapps.joyme.webpage.controller.js360;

import com.enjoyf.util.FileUtil;
import com.enjoyf.webapps.joyme.webpage.controller.BaseController;
import com.enjoyf.webapps.joyme.webpage.dto.Js360Obj;
import com.enjoyf.webapps.joyme.webpage.weblogic.Js360WebLogic;
import com.enjoyf.webapps.joyme.webpage.weblogic.UploadWebLogic;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-15 下午2:23
 * Description:
 */
@Controller
@RequestMapping(value = "/js360/")
public class JsGeneratorController extends BaseController {

    @Resource(name = "uploadWebLogic")
    private UploadWebLogic uploadWebLogic;

    @Resource(name = "js360WebLogic")
    private Js360WebLogic js360WebLogic;

    private final String EXCEL_360_PATH = getUploadPath() + "/" + "js360.xlsx";

    @RequestMapping("/page")
    public ModelAndView generatorPage() {
        Map<String, Object> messageMap = new HashMap<String, Object>();

        if (FileUtil.isFileOrDirExist(EXCEL_360_PATH)) {
            File file = new File(EXCEL_360_PATH);
            messageMap.put("excelPath", file.getAbsoluteFile());
            messageMap.put("excelDate", new Date(file.lastModified()));
        }

        return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
    }

    @RequestMapping("/uploadexcel")
    public ModelAndView handleImport(@RequestParam(value = "excelfile", required = false) MultipartFile excelfile,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        Map<String, Object> messageMap = new HashMap<String, Object>();
        //用户上传根路径
        String uploadPath = getUploadPath();

        try {
            File file = uploadWebLogic.saveFile(excelfile, uploadPath, "js360.xlsx");

            messageMap.put("excelPath", file.getAbsoluteFile());
            messageMap.put("excelDate", new Date(file.lastModified()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
    }


    @RequestMapping("/calmd5")
    public ModelAndView handleImport(@RequestParam(value = "tmppath", required = false) MultipartFile tmpPath) throws IOException {

        Map<String, Object> messageMap = new HashMap<String, Object>();


        try {
//            if (!FileUtil.isFileOrDirExist(path)) {
//                messageMap.put("error", "excel.not.exists");
//                return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
//            }
//            File file = new File(path);
            String uploadPath = getUploadPath();
            File file = uploadWebLogic.saveFile(tmpPath, uploadPath, "temp.apk");

            String md5 = js360WebLogic.calFileMd5(file);

            messageMap.put("md5", md5);
        } catch (Exception e) {
            messageMap.put("error", e.getMessage());
            e.printStackTrace();
        }

        return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
    }


    @RequestMapping("/genjsobj")
    public ModelAndView handleImport(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        Map<String, Object> messageMap = new HashMap<String, Object>();

        if (!FileUtil.isFileOrDirExist(EXCEL_360_PATH)) {
            messageMap.put("error", "excel.not.exists");
            return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
        }
        File file = new File(EXCEL_360_PATH);
        try {
            Map<String, Js360Obj> map = js360WebLogic.praseByExcel(file);

            String objString = js360WebLogic.genJsObjectByExcel(map);

            String htmlString = js360WebLogic.genHtmlStringByExcel(map);

            messageMap.put("objString", objString);
            messageMap.put("htmlString", htmlString);
        } catch (Exception e) {
            messageMap.put("error", e.getMessage());
            e.printStackTrace();
        }

        return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
    }


}
