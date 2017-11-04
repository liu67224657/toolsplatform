package com.enjoyf.cms.filter;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.util.ToolsLoginUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionCheckFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        if (!request.getRequestURI().contains("login")) {
//            if (request.getSession().getAttribute("adminUser") == null) {
//                HttpServletResponse response = (HttpServletResponse) resp;
//                response.sendRedirect(request.getContextPath() + "/ac/login.jsp");
//                return;
//            }
//        }
//
//        chain.doFilter(req, resp);

        if (!ToolsLoginUtil.isLogin(request, 104)) {
            String host = request.getServerName().substring(request.getServerName().indexOf("."));
            if (host.contains("joyme.test")) {
                response.sendRedirect("http://tools" + host + ":8081" + "/loginpage?reurl=" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI());
            } else {
                response.sendRedirect("http://tools" + host + "/loginpage?reurl=" + request.getScheme() + "://" + request.getServerName() + request.getRequestURI());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
