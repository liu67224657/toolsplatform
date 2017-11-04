package com.enjoyf.webapps.joyme.webpage.controller.lscs;

import com.enjoyf.util.FileUtil;
import com.enjoyf.util.JsonBinder;
import com.enjoyf.webapps.joyme.webpage.controller.BaseController;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.CardJson;
import com.enjoyf.webapps.joyme.webpage.dto.lscs.CategoryJson;
import com.enjoyf.webapps.joyme.webpage.weblogic.UploadWebLogic;
import com.enjoyf.webapps.joyme.webpage.weblogic.lscs.LscsWebLogic;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-15 下午2:23
 * Description:
 */
@Controller
@RequestMapping(value = "/lscs/")
public class LscsController extends BaseController {

    @Resource(name = "uploadWebLogic")
    private UploadWebLogic uploadWebLogic;

    @Resource(name = "lscsWebLogic")
    private LscsWebLogic lscsWebLogic;


    private final String EXCEL_LSCS_NAME = "lscscard.xlsx";
    private final String EXCEL_LSCS_PATH = getUploadPath() + "/" + EXCEL_LSCS_NAME;

    private final String EXCEL_LSCS_CATEGORY_NAME = "lscscategory.xlsx";
    private final String EXCEL_LSCS_CATEGORY_PATH = getUploadPath() + "/" + EXCEL_LSCS_CATEGORY_NAME;


    @RequestMapping("/page")
    public ModelAndView generatorPage() {
        Map<String, Object> messageMap = new HashMap<String, Object>();

        if (FileUtil.isFileOrDirExist(EXCEL_LSCS_PATH)) {
            File file = new File(EXCEL_LSCS_PATH);
            messageMap.put("excelPath", file.getAbsoluteFile());
            messageMap.put("excelDate", new Date(file.lastModified()));
        }

        if (FileUtil.isFileOrDirExist(EXCEL_LSCS_CATEGORY_PATH)) {
            File file = new File(EXCEL_LSCS_CATEGORY_PATH);
            messageMap.put("excelCatePath", file.getAbsoluteFile());
            messageMap.put("excelCateDate", new Date(file.lastModified()));
        }

        return new ModelAndView("/views/jsp/lscs/lscspage", messageMap);
    }

    @RequestMapping("/uploadexcel")
    public ModelAndView handleImport(@RequestParam(value = "excelfile", required = false) MultipartFile excelfile,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        Map<String, Object> messageMap = new HashMap<String, Object>();
        //用户上传根路径
        String uploadPath = getUploadPath();

        try {
            File file = uploadWebLogic.saveFile(excelfile, uploadPath, EXCEL_LSCS_NAME);

            messageMap.put("excelPath", file.getAbsoluteFile());
            messageMap.put("excelDate", new Date(file.lastModified()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/views/jsp/lscs/lscspage", messageMap);
    }


    @RequestMapping("/uploadcategory")
    public ModelAndView uploadCategory(@RequestParam(value = "excelfile", required = false) MultipartFile excelfile,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        Map<String, Object> messageMap = new HashMap<String, Object>();
        //用户上传根路径
        String uploadPath = getUploadPath();

        try {
            File file = uploadWebLogic.saveFile(excelfile, uploadPath, EXCEL_LSCS_CATEGORY_NAME);

            messageMap.put("excelPath", file.getAbsoluteFile());
            messageMap.put("excelDate", new Date(file.lastModified()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/lscs/page");
    }

    @RequestMapping("/genjsobj")
    public ModelAndView handleImport(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {

        Map<String, Object> messageMap = new HashMap<String, Object>();

        if (!FileUtil.isFileOrDirExist(EXCEL_LSCS_PATH)) {
            messageMap.put("error", "excel.not.exists");
            return new ModelAndView("/views/jsp/jsobj360/js360page", messageMap);
        }
        File file = new File(EXCEL_LSCS_PATH);

        File categoryFile = new File(EXCEL_LSCS_CATEGORY_PATH);
        try {
            List<CardJson> arrayList = lscsWebLogic.praseByExcel(file);


            List<CategoryJson> categoryJsonList = lscsWebLogic.praseCategoryByExcel(categoryFile);

            messageMap.put("objString", JsonBinder.buildNormalBinder().toJson(arrayList));
            messageMap.put("categoryString", JsonBinder.buildNormalBinder().toJson(categoryJsonList));
        } catch (Exception e) {
            messageMap.put("error", e.getMessage());
            e.printStackTrace();
        }

        return new ModelAndView("/views/jsp/lscs/lscspage", messageMap);
    }


}
