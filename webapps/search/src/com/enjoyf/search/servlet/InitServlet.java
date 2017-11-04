package com.enjoyf.search.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.enjoyf.search.util.ConfigContainer;
import com.enjoyf.util.SystemUtil;

public class InitServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5352229787670744390L;

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
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        try {
            SystemUtil su = new SystemUtil();
            ConfigContainer.init();

        } catch (Exception e) {
            System.out.println("==========Error when init=========");
            e.printStackTrace();
        }
        System.out.println("==========INIT SUCCESS=========");
    }

}
