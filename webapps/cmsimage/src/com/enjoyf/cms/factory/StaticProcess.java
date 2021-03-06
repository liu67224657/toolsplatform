package com.enjoyf.cms.factory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public class StaticProcess extends AbstractProcess {
    @Override
    public void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        chain.doFilter(req, resp);
        return;
    }
}
