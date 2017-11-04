package com.enjoyf.cms.factory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created by zhitaoshi on 2015/9/21.
 */
public class Response301Process extends AbstractProcess {
    @Override
    public void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        URL url = new URL(requestURL);
        String redirectUrl = requestURL.replace(url.getPath(), "") + url.getPath().replaceAll("//", "/");
        response.setStatus(301);
        response.setHeader("Location", redirectUrl);
        response.setHeader("Connection", "close" );
    }
}
