package com.enjoyf.cms.factory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-11
 * Time: 上午12:22
 * To change this template use File | Settings | File Templates.
 */
public interface IProcess {
    void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException;
}

