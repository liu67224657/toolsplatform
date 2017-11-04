package com.enjoyf.search.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-8-5
 * Time: 上午12:11
 * To change this template use File | Settings | File Templates.
 */
public class HtmlUtil {

    public static void writeJsonHtml(HttpServletResponse response,String html){
        response.setContentType("text/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(html);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
