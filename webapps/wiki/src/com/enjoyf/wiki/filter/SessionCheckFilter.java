package com.enjoyf.wiki.filter;

import com.enjoyf.util.ToolsLoginUtil;
import com.enjoyf.wiki.container.PropertiesContainer;

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
//                response.sendRedirect(request.getContextPath() + "/wiki/ac/login.jsp");
//                return;
//            }
//        }

        if (!ToolsLoginUtil.isLogin(request, 102) && !request.getRequestURI().contains("/ac/login.jsp")) {
            response.sendRedirect(PropertiesContainer.getInstance().getWikiLoginPage());
        } else {
            chain.doFilter(request, response);
        }
        // chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
