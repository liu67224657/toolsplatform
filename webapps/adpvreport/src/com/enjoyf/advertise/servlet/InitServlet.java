package com.enjoyf.advertise.servlet;

import com.enjoyf.advertise.props.Properties;
import com.enjoyf.util.SystemUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InitServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 2413378459563837687L;

    @Override
    public void init() throws ServletException {
        System.out.println("===begin to init====");
        //读配置文件
        try {
            loadProperties();

//            System.out.println(IPSeeker.getInstance().getAddress("106.3.78.243"));
//             System.out.println(IPSeeker.getInstance().getArea("106.3.78.243"));
//             System.out.println(IPSeeker.getInstance().getCountry("106.3.78.243"));
//            System.out.println(IPSeeker.getInstance().getIPEntries("106.3.78.243"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.init();
    }


    private void loadProperties() throws IOException, FileNotFoundException {
        SystemUtil su = new SystemUtil();
        String metaInfoPath = su.getMetaInfFolderPath() + "/config.properties";
        Properties.prop.load(new FileInputStream(new File(metaInfoPath)));
    }
}
