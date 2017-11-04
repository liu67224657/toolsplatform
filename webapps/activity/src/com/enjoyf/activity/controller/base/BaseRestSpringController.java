package com.enjoyf.activity.controller.base;

import com.enjoyf.activity.util.HTTPUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * BaseAction定义了一些web层基础方法
 */
public class BaseRestSpringController {
    protected String getIp(HttpServletRequest request) {
        return HTTPUtil.getRemoteAddr(request);
    }
}
