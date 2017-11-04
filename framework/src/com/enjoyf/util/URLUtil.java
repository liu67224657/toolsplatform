package com.enjoyf.util;

import com.enjoyf.framework.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class URLUtil {
    private final static String DEFAULT_ENCODE = "UTF-8";

    private static Logger logger = LoggerFactory.getLogger(URLUtil.class);

    private static final int TIME_OUT = 6000;

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

    public static String openURLWithTimeOut(String pageURL) throws IOException {
        StringBuilder pageHTML = new StringBuilder();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(pageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            is = connection.getInputStream();
            boolean isGzip = isGzip(connection.getHeaderFields());
            if (isGzip)
                is = new GZIPInputStream(is);

            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
                pageHTML.append("\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("URLUtil open connection error.url:" + pageURL);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
            if (br != null) {
                br.close();
                br = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
        }
        return pageHTML.toString();
    }

    public static Map<String, Object> openURLConnectionWithTimeOut(String pageURL, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = null;
        StringBuilder responseText = new StringBuilder();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(pageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            is = connection.getInputStream();
            boolean isGzip = isGzip(connection.getHeaderFields());
            if (isGzip)
                is = new GZIPInputStream(is);

            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            int responseCode = connection.getResponseCode();
            String line = null;
            while ((line = br.readLine()) != null) {
                responseText.append(line);
                responseText.append("\r\n");
            }
            logger.info("URLUtil open HttpURLConnection,url:"+pageURL+",responseCode:"+responseCode+",bodyLength:"+responseText.length());
            if(responseText.length() < 500){
                return null;
            }
            resultMap = new HashMap<String, Object>();
            resultMap.put("responseCode", responseCode);
            resultMap.put("responseText", responseText.toString());
            response.setStatus(responseCode);
        } catch (Exception e) {
            LogService.errorSystemLog("URLUtil openURLConnectionWithTimeOut occur Exception.e", e);
            return resultMap;
        } finally {
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
            if (br != null) {
                br.close();
                br = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
        }
        return resultMap;
    }

    public void saveFileFromNet(String urlstr, File saveFile) throws Exception {
        URL url = new URL(urlstr);
        // 打开链接
        HttpURLConnection conn = null;
        FileOutputStream outStream = null;
        InputStream inStream = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(TIME_OUT);
            // 通过输入流获取图片数据
            inStream = conn.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            // 创建输出流
            outStream = new FileOutputStream(saveFile);
            outStream.write(data);
        } finally {
            if (conn != null)
                conn.disconnect();
            if (outStream != null) {
                outStream.flush();
                outStream.close();
                outStream = null;
            }
            if (inStream != null) {
                inStream.close();
                inStream = null;
            }
        }
    }

    private byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        try {
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 把outStream里的数据写入内存
            return outStream.toByteArray();
        } finally {
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
        }
    }

    public static boolean isURLValid(String pageURL, int timeout) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(pageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            int state = connection.getResponseCode();
            return state == 200;
        } catch (Exception e) {
            logger.info("URLUtil open connection error.url:"+pageURL);
            return false;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    public static boolean isURLValid(String pageURL) {
        return isURLValid(pageURL, 5000);
    }

    public static int getURLState(String pageURL, int timeout) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(pageURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            return connection.getResponseCode();
        } catch (Exception e) {
            logger.info("URLUtil open connection error.url:"+pageURL);
            return -1;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private static boolean isGzip(Map headerMap) {
        if (headerMap.get("Content-Encoding") != null) {
            List list = (List) headerMap.get("Content-Encoding");
            if (list.contains("gzip"))
                return true;
        }
        return false;
    }

    private static String HTTP_QINIU = "http://joymepic.qiniudn.com";
    private static String HTTPS_QINIU = "https://joymepic.qiniudn.com";

    private static String HTTP_JOYMEPIC = "http://joymepic.joyme.com";
    private static String HTTPS_JOYMEPIC = "https://joymepic.joyme.com";


    public static String getQiniuUrl(String path) {
        if (StringUtil.isEmpty(path)) {
            return path;
        }
        if (path.startsWith("http://") || path.startsWith("https://")) {
            if (path.startsWith(HTTP_JOYMEPIC)) {
                path = path.replaceAll(HTTP_JOYMEPIC, HTTP_QINIU);
            } else if (path.startsWith(HTTPS_JOYMEPIC)) {
                path = path.replaceAll(HTTPS_JOYMEPIC, HTTPS_QINIU);
            }
            return path;
        }

        return "http://joymepic.qiniudn.com" + path;
    }

    public static String getJoymeDnUrl(String path) {
        if (StringUtil.isEmpty(path)) {
            return path;
        }
        if (path.startsWith("http://") || path.startsWith("https://")) {
            if (path.startsWith(HTTP_QINIU)) {
                path = path.replaceAll(HTTP_QINIU, HTTP_JOYMEPIC);
            } else if (path.startsWith(HTTPS_QINIU)) {
                path = path.replaceAll(HTTPS_QINIU, HTTPS_JOYMEPIC);
            }
            return path;
        }
        return "http://joymepic.joyme.com" + path;
    }
}
