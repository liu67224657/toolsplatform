package com.joyme.controller;

import jsontojava.com.jsontojava.JsonToJava;
import jsontojava.com.jsontojava.OutputOption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhimingli
 * Date: 2015/02/03
 * Time: 19:05
 */
@Controller
public class JsontoJavaController {

    @RequestMapping({"/jsontojava"})
    public void json2java(HttpServletRequest req, HttpServletResponse resp){
        JsonToJava jsonToJava;
        try {
            jsonToJava = new JsonToJava();
            jsonToJava.setUrl(req.getParameter("url"));
            jsonToJava.setPackage(req.getParameter("package"));
            jsonToJava.setBaseType(req.getParameter("class"));
            jsonToJava.setJson(req.getParameter("json"));
            String[] options = req.getParameterValues("options");
            if (options != null) {
                String[] arr$ = options; int len$ = arr$.length; for (int i$ = 0; i$ < len$; ++i$) { String option = arr$[i$];
                    jsonToJava.addOutputOption(OutputOption.valueOf(option));
                }
            }
            System.out.println(options);
            jsonToJava.fetchJson();
            ByteArrayOutputStream out = (ByteArrayOutputStream)jsonToJava.outputZipFile(new ByteArrayOutputStream());

            byte[] data = out.toByteArray();
            resp.setContentType("application/zip");
            resp.setContentLength(data.length);
            resp.setHeader("Content-Disposition", "inline; filename=" + jsonToJava.getPackage() + ".zip");

            resp.getOutputStream().write(data);
        }
        catch (Exception ex)
        {
        }
    }
}
