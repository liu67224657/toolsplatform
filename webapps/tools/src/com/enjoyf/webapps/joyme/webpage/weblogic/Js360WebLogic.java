package com.enjoyf.webapps.joyme.webpage.weblogic;

import com.enjoyf.util.JsonBinder;
import com.enjoyf.util.MD5Util;
import com.enjoyf.webapps.joyme.webpage.dto.Js360Obj;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-16 上午11:49
 * Description:
 */
@Service(value = "js360WebLogic")
public class Js360WebLogic {

    public String genJsObjectByExcel(Map<String, Js360Obj> map) {
        return "var g_soft_info =" + JsonBinder.buildNormalBinder().toJson(map) + ";";
    }

    public String genHtmlStringByExcel(Map<String, Js360Obj> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"app-list\" id=\"appdownloadpage-1\"><ul>");
        int i = 0;
        int pageIdx = 2;
        for (Map.Entry<String, Js360Obj> entry : map.entrySet()) {
            sb.append("<li><div class=\"img\">").
                    append("<img src=\"").append(entry.getValue().getIconUrl()).append("\"></div>")
                    .append("<div class=\"text\">")
                    .append("<p>").append(entry.getValue().getSoftname()).append("</p>")
                    .append("<a href=\"javascript:void(0);\" class=\"download state-1\" id=\"").append(entry.getKey()).append("_a\" onclick=\"try{a({'id':'").append(entry.getKey()).append("'});}catch(e){}\"></a>")
                    .append("</div>")
                    .append("</li>");
            if (i % 10 == 9 && (i != map.entrySet().size() - 1)) {
                sb.append("</ul></div><div class=\"app-list\" style=\"display:none\" id=\"appdownloadpage-").append(pageIdx).append("\"><ul>");
                pageIdx++;
            }
            i++;
        }
        sb.append("</ul></div>");

        return sb.toString();
    }

    public String calFileMd5(File file) throws IOException, NoSuchAlgorithmException, URISyntaxException {
        return MD5Util.getFileMD5String(file).toUpperCase();
    }

    public Map<String, Js360Obj> praseByExcel(File excelFile) {
        Map<String, Js360Obj> returnMap = new LinkedHashMap<String, Js360Obj>();
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> keyRowIterator = sheet.rowIterator();

            int i = 1;
            int rowIndx = 0;
            while (keyRowIterator.hasNext()) {
                Row row = keyRowIterator.next();
                if (rowIndx > 0) {
                    Js360Obj js360Obj = new Js360Obj();

                    Cell nameCell = row.getCell(0);
                    Cell dowloadCell = row.getCell(1);
                    Cell cateCell = row.getCell(2);
                    Cell channelCell = row.getCell(3);
                    Cell iconCell = row.getCell(4);
                    Cell packageCell = row.getCell(5);
                    Cell sizeCell = row.getCell(6);
                    Cell versionCell = row.getCell(7);
                    Cell md5Cell = row.getCell(8);

                    js360Obj.setSoftname(nameCell.getStringCellValue());
                    js360Obj.setUrl(dowloadCell.getStringCellValue());
                    js360Obj.setApkCate(cateCell.getStringCellValue());
                    js360Obj.setApkMd5(md5Cell.getStringCellValue());

                    js360Obj.setChannelName(channelCell.getStringCellValue());
                    js360Obj.setIconUrl(iconCell.getStringCellValue());
                    js360Obj.setPackageName(packageCell.getStringCellValue());

                    HttpURLConnection urlcon = null;
                    try {
                        URL url = new URL(dowloadCell.getStringCellValue());
                        urlcon = (HttpURLConnection) url.openConnection();
                         js360Obj.setSize(String.valueOf(urlcon.getContentLength()));
                    } catch (IOException e1) {
                        System.out.println("get apk size error.name:"+nameCell.getStringCellValue());
                        e1.printStackTrace();
                        throw e1;
                    } finally {
                        if (urlcon != null) {
                            urlcon.disconnect();
                        }
                    }
                    if (js360Obj.getSize() == null || js360Obj.getSize().equals("")) {
                        try {
                            js360Obj.setSize(sizeCell.getStringCellValue());
                        } catch (Exception e) {
                            js360Obj.setSize(String.valueOf((int)sizeCell.getNumericCellValue()));
                        }
                    }
                    try {
                        js360Obj.setVersion(versionCell.getStringCellValue());
                    } catch (Exception e) {
                        js360Obj.setVersion(String.valueOf((int)versionCell.getNumericCellValue()));
                    }

                    returnMap.put(String.valueOf(i++), js360Obj);
                }
                rowIndx++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    public static void main(String[] args) throws URISyntaxException, IOException, NoSuchAlgorithmException {

        URL url = new URL("http://shouji.360tpcdn.com/360sj/dev/20130807/com.joyme.app.android.mt_15_094656.apk");
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        System.out.println(urlcon.getContentLength());
    }

}
