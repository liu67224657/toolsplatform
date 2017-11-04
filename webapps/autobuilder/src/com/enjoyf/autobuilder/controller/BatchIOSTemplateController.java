package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.AndroidTemplate;
import com.enjoyf.autobuilder.bean.AppPlatform;
import com.enjoyf.autobuilder.service.AndroidTemplateService;
import com.enjoyf.autobuilder.service.AndroidTemplateWebService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-16 下午12:18
 * Description:
 */
@Controller
@RequestMapping("/batch/ios/template")
public class BatchIOSTemplateController {
    AndroidTemplateService templateService = new AndroidTemplateService();
    AndroidTemplateWebService webService = new AndroidTemplateWebService();

    @RequestMapping("/page.do")
    public ModelAndView page() {
        List<AndroidTemplate> list = null;
        try {
            list = templateService.queryTemplate(null, AppPlatform.APP_PLATFORM_IOS);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("list", list);

        return new ModelAndView("/batch/ios/page", map);
    }


    @RequestMapping("/uploadpage.do")
    public ModelAndView uploadPage() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        return new ModelAndView("/batch/ios/uploadpage", mapMessage);
    }

    @RequestMapping("/upload.do")
    public ModelAndView upload(@RequestParam(value = "zip", required = false) MultipartFile srczip,
                               @RequestParam(value = "version", required = false) String version,
                               @RequestParam(value = "code", required = false) String code) {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        try {
            AndroidTemplate androidTemplate = webService.uploadTemplate(srczip, code, version, AppPlatform.APP_PLATFORM_IOS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/batch/ios/template/page.do", mapMessage);
    }
}
