package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.Resource;
import com.enjoyf.autobuilder.bean.Src;
import com.enjoyf.autobuilder.service.ResourceService;
import com.enjoyf.autobuilder.service.ResourceWebService;
import com.enjoyf.autobuilder.service.SrcService;
import com.enjoyf.autobuilder.service.SrcWebService;
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
 * Create time:  13-8-28 下午5:40
 * Description:
 */
@Controller
@RequestMapping("/resource/android")
public class AndroidResourceController {

    ResourceService resourceService = new ResourceService();
    ResourceWebService resourceWebService = new ResourceWebService();

    @RequestMapping("/page.do")
    public ModelAndView page() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        try {
            List<Src> list = resourceService.queryResource(null);
            mapMessage.put("resourceList", list);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/resource/page", mapMessage);
    }

    @RequestMapping("/uploadpage.do")
    public ModelAndView uploadPage() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        return new ModelAndView("/resource/uploadpage", mapMessage);
    }

    @RequestMapping("/upload.do")
    public ModelAndView upload(@RequestParam(value = "resourcezip", required = false) MultipartFile srczip,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "version", required = false) String version,
                               @RequestParam(value = "code", required = false) String code) {
        Map<String, Object> mapMessage = new HashMap<String, Object>();


        try {
            Resource resource = resourceWebService.uploadResource(srczip, code, version, name);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/resource/android/page.do", mapMessage);
    }
}
