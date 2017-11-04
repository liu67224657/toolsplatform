package com.enjoyf.webcache.factory;

import com.enjoyf.util.FileUtil;
import com.enjoyf.webcache.util.WebCacheUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 网站sitemap的对外输出，由于之前的功能是做在cmsiamge下面的，那块的功能还没有接过来，所以sitemap的对外输出在webcache，而缓存文件的生成在cmsimage
 * Created by zhitaoshi on 2015/9/18.
 */
public class SiteMapProcess extends AbstractProcess {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURL = request.getRequestURL().toString();
        URL url = new URL(requestURL);
        String host = url.getHost();
        String path = url.getPath();
        //直接读取静态缓存文件 输出
        String filePath = WebCacheUtil.getSiteMapPath(host, path);
        File fileXml = new File(filePath);
        if (fileXml.exists()) {
            FileUtil.displayFile(response, fileXml, "application/xml");
        } else {
            response.setStatus(500);
            chain.doFilter(request, response);
        }
        return;
    }
}
