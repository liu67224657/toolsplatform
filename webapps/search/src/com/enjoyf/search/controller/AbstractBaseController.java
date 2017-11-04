package com.enjoyf.search.controller;

import com.enjoyf.framework.log.LogService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-8-5
 * Time: 上午12:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBaseController {

    protected void writeJson(HttpServletResponse response,String s){
        response.setContentType("text/json; charset=utf-8");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            LogService.errorSystemLog(" occured IOException.", e);

        }
    }
}

