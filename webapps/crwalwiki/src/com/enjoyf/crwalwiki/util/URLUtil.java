package com.enjoyf.crwalwiki.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class URLUtil {
    private final static String DEFAULT_ENCODE = "UTF-8";

    public StringBuffer openURL(String path, String encoding) throws Exception {
        URL url = new URL(path);
        InputStream input = null;
        BufferedReader br = null;
        try {
            input = url.openStream();
            br = new BufferedReader(new InputStreamReader(input, encoding));
            String data;
            StringBuffer sb = new StringBuffer();
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null)
                br.close();
            if (input != null)
                input.close();
        }
    }

    public StringBuffer openURL(String path) throws Exception {
        return this.openURL(path, DEFAULT_ENCODE);
    }
    
    public String openURLWithTimeOut(String pageURL) throws IOException {
        StringBuilder pageHTML = new StringBuilder();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(pageURL);
            connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            is = connection.getInputStream();
            GZIPInputStream gis = new GZIPInputStream(is);
            br = new BufferedReader(new InputStreamReader(gis, "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
                pageHTML.append("\r\n");
            }
           
        } catch (Exception e) {
            System.out.println("open link fail:" + pageURL);
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
                connection = null;
            }
            if(br != null){
                br.close();
                br = null;
            }
            if(is != null){
                is.close();
                is = null;
            }
        }
        return pageHTML.toString();
    }
}
