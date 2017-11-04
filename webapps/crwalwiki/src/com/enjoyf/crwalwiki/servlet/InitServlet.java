package com.enjoyf.crwalwiki.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.enjoyf.crwalwiki.container.ConfigContainer;
import com.enjoyf.crwalwiki.service.SyncCrwalService;
import com.enjoyf.util.SystemUtil;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        SystemUtil su = new SystemUtil();
        String path = su.getMetaInfFolderPath();
        loadProperties(path);

        // 启动抓取的线程
        new SyncCrwalService().start();
    }

    private void loadProperties(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(path + "/config.properties"));
            Properties prop = new Properties();
            prop.load(is);
            ConfigContainer.prop = prop;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
