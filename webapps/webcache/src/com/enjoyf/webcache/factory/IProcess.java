package com.enjoyf.webcache.factory;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
   void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JoymeServiceException, JoymeDBException;
}

