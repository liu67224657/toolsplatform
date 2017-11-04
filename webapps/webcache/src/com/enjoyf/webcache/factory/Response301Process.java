package com.enjoyf.webcache.factory;

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
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String host = request.getHeader("Host");
        String requestURL = request.getRequestURL().toString();
        URL url = new URL(requestURL);
        String path = url.getPath();
        if (path.endsWith("/")) {
            path += "index.html";
        } else {
            if (!path.contains(".html")) {
                path += "/index.html";
            }
        }
        String redirectUrl = request.getScheme() + host + path.replaceAll("^/+", "") + "?" + request.getQueryString();
        response.setStatus(301);
        response.setHeader("Location", redirectUrl);
        response.setHeader("Connection", "close" );
    }
}
