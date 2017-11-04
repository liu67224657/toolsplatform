package com.enjoyf.webcache.util;

import com.enjoyf.util.RandomUtil;
import com.enjoyf.util.SystemUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericliu on 16/5/11.
 */
public class IruiUtil {

    private static List<List<String>> dataList = new ArrayList<List<String>>();
    private static SystemUtil su = new SystemUtil();

    public static String getRandomUrl() {
        int dataListNum = RandomUtil.getRandomInt(dataList.size());

        List<String> urlList = dataList.get(dataListNum);
        String url = urlList.get(RandomUtil.getRandomInt(urlList.size()));
        return url;
    }


    public static void loadUrlByFile() throws IOException {
        String fileName = su.getMetaInfFolderPath() + "/irui_url.txt";
        int index = 0;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
            isr = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(isr);
            String line = null;

            List<String> urlList = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                urlList.add(line);
                index++;
                if (index % 300 == 0) {
                    dataList.add(urlList);
                    urlList = new ArrayList<String>();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (isr != null) {
                isr.close();
                isr = null;
            }
            if (br != null) {
                br.close();
                br = null;
            }
        }
    }
}
