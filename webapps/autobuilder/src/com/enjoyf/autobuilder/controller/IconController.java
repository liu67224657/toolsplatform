package com.enjoyf.autobuilder.controller;

import com.enjoyf.autobuilder.bean.BatchClientImage;
import com.enjoyf.autobuilder.service.ImageWebService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.im4java.core.IM4JavaException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-16 下午12:18
 * Description:
 */
@Controller
@RequestMapping("/batch/icon")
public class IconController {
    ImageWebService webService = new ImageWebService();


    @RequestMapping("/uploadpage.do")
    public ModelAndView uploadPage() {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        return new ModelAndView("/batch/icon/uploadpage", mapMessage);
    }

    @RequestMapping("/upload.do")
    public ModelAndView upload(@RequestParam(value = "img", required = false) MultipartFile img,
                               @RequestParam(value = "platform", required = false) Integer platform) {
        Map<String, Object> mapMessage = new HashMap<String, Object>();

        try {
            BatchClientImage backgroudImg = webService.uploadBackgroud(img, platform);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IM4JavaException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/batch/background/uploadpage.do");
    }
}
