package com.enjoyf.autobuilder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.enjoyf.autobuilder.service.FileWebService;

@Controller
public class UploadController {

    @RequestMapping("/upload/uploadFile.do")
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileWebService service = new FileWebService();
        service.upload(request, response, "D:/uploadfile/");
        return null;
    }
}
