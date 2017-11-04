package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.IosSrc;
import com.enjoyf.autobuilder.bean.Src;
import com.enjoyf.autobuilder.service.IosSrcService;
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
@RequestMapping("/src/ios")
public class IOSSrcController {

    IosSrcService iosSrcService = new IosSrcService();
    SrcWebService srcWebService = new SrcWebService();

    @RequestMapping("/page.do")
    public ModelAndView page() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        try {
            List<IosSrc> list = iosSrcService.querySrc(null);
            mapMessage.put("srcList", list);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/src/ios/page", mapMessage);
    }

    @RequestMapping("/uploadpage.do")
    public ModelAndView uploadPage() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        return new ModelAndView("/src/ios/uploadpage", mapMessage);
    }

    @RequestMapping("/upload.do")
    public ModelAndView upload(@RequestParam(value = "srczip", required = false) MultipartFile srczip,
                               @RequestParam(value = "version", required = false) String version,
                               @RequestParam(value = "type", required = false) String type) {
        Map<String, Object> mapMessage = new HashMap<String, Object>();


        try {
            IosSrc src = srcWebService.uploadIosSrc(srczip, version, type);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/src/ios/page.do", mapMessage);
    }
}
