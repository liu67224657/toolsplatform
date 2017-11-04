package com.enjoyf.autobuilder.servlet;

import com.enjoyf.autobuilder.service.SystemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-28 下午5:07
 * Description:
 */
public class InitServlet extends HttpServlet {
    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws javax.servlet.ServletException if an error occurs
     */
    public void init() throws ServletException {
        SystemService service = new SystemService();

        try {
            service.loadProperties();

        }  catch (Exception e) {
            System.out.print("==========Error when init==============");
            e.printStackTrace();
        }


    }
}
