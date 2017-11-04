package com.enjoyf.webcache.factory;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public abstract class AbstractProcess implements IProcess {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JoymeServiceException, JoymeDBException {
        chain.doFilter(request, response);
    }
}
