package com.enjoyf.activity.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        Wrapper wrapper = new Wrapper(resp);
        chain.doFilter(request, wrapper);
        byte[] gzipData = gzip(wrapper.getResponseData());
        resp.addHeader("Content-Encoding", "gzip");
        resp.setContentLength(gzipData.length);
        ServletOutputStream output = null;
        try{
            output = response.getOutputStream();
            output.write(gzipData);
        }finally{
            if(output != null){
                output.flush();
            }
        }
       
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    private byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            output.write(data);
        } catch (IOException e) {
        } finally {
            try {
                if(output != null){
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
            }
        }
        return byteOutput.toByteArray();
    }
}
