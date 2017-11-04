package com.enjoyf.webcache.filter;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.factory.ProcessFactory;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Servlet Filter implementation class URLFilter
 */
public class URLFilter implements Filter {

    private static Set<String> excludedExt = new HashSet<String>();

    public URLFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * 通过过滤器，拦截访问请求，抓取源站的内容页面，解析处理，对外输出
     *
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        if (isExcludedExt(request)) {
            chain.doFilter(req, resp);
            return;
        }

        try {
            String requestUrl = request.getRequestURL().toString();
            if (requestUrl.indexOf(";jsessionid") > 0) {
                //如果有sessionid，去掉sessionid，redirect
                requestUrl = requestUrl.substring(0, requestUrl.indexOf(";jsessionid"));
                response.sendRedirect(requestUrl);
            } else {
                doURLFilter(request, response, chain);
            }
        } catch (JoymeServiceException e) {
            LogService.errorSystemLog("webcache doFilter occur Exception.e", e);
            chain.doFilter(req, resp);
        } catch (JoymeDBException e) {
            LogService.errorSystemLog("webcache doFilter occur Exception.e", e);
            chain.doFilter(req, resp);
        } catch (Exception e) {
            LogService.errorSystemLog("webcache doFilter occur Exception.e", e);
            chain.doFilter(req, resp);
        }
    }

    private void doURLFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, JoymeServiceException, JoymeDBException {
        String requestURL = request.getRequestURL().toString();
        //检查访问的url，判断是否需要做解析处理
        int processCode = ProcessFactory.checkProcess(requestURL);
        ProcessFactory.getFactory(processCode).process(request, response, chain);
    }


    public void init(FilterConfig fConfig) throws ServletException {
        String excludedExts = fConfig.getInitParameter("excludedExts");
        if (StringUtil.isEmpty(excludedExts)) {
            return;
        }

        CollectionUtils.addAll(excludedExt, excludedExts.split(","));
    }

    private boolean isExcludedExt(HttpServletRequest req) {
        String servletPath = req.getServletPath();

        if (servletPath.lastIndexOf(".") < 0) {
            return false;
        }

        String ext = servletPath.substring(servletPath.lastIndexOf(".")+1, servletPath.length());
        return excludedExt.contains(ext);
    }

}
