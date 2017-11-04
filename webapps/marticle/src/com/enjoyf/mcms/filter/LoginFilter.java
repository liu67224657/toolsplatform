package com.enjoyf.mcms.filter;


import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.util.ToolsLoginUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-1-13 下午2:05
 * Description:
 */
public class LoginFilter implements Filter {


    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;


        if (!ToolsLoginUtil.isLogin(request, 103) && !request.getRequestURI().contains("/ac/login.jsp")) {
            response.sendRedirect(ConfigContainer.getMarticleLoginJsp());
        } else {
            chain.doFilter(request, response);
        }

//        if (request.getSession() == null || request.getSession().getAttribute("ac-user") == null) {
//            response.sendRedirect(request.getContextPath()+"/marticle/ac/login.jsp");
//        }else {
//            chain.doFilter(request,response);
//        }

    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
