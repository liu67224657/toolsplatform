package com.enjoyf.framework.source;

import java.io.File;
import java.util.Properties;

import com.enjoyf.framework.log.LogService;

public class FromSource {

    public static String getValue(String name) {
        Properties prop = new Properties();
        try {
            prop.load(FromSource.class.getResourceAsStream("/from_source.properties"));
        } catch (Exception e) {
            LogService.errorSystemLog("read from_source.properties error!!!", e);
        }
        String propValue = (String) prop.get(name);
        LogService.infoSystemLog(propValue, false);

        if (propValue == null || propValue.trim().length() == 0) {
            LogService.errorSystemLog("getFilePath url not empty!");
        }
        return propValue;
    }

    public static void main(String[] args) {
        System.out.println(getValue("4"));
        File file = new File("");
        System.out.println(file.getAbsolutePath());
    }
}
