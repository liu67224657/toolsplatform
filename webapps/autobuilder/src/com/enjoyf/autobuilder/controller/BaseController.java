package com.enjoyf.autobuilder.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-17 下午3:59
 * Description:
 */
public class BaseController {
        protected static void runProcess(String cmd) throws IOException {
        Process p = null;
        InputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            fis = p.getInputStream();
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(cmd + ":->" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
            if (fis != null) {
                fis.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
}
