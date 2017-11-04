package com.enjoyf.activity.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by zhimingli on 2016/1/14 0014.
 */
public class ReadTxt {
    public static void main(String[] args) {
        BufferedReader br = null;
        InputStreamReader isr = null;
        List<String> list = new ArrayList<String>();
        try {
            isr = new InputStreamReader(new FileInputStream(new File("E:\\H5礼包\\战鹰宝藏 YST_GC_34_12_2016-10-31 18-14-22.txt")), "UTF-8");
            br = new BufferedReader(isr);
            String line = "";
            StringBuffer str = new StringBuffer();
//            countryPointMap.put(5, "yizhou");//雷熊
//            countryPointMap.put(10, "yuzhou");//霜狼
//            countryPointMap.put(15, "jingzhou");//飞狮
//            countryPointMap.put(20, "qingzhou");//魔牛
//            countryPointMap.put(26, "yangzhou");//影蛇
//            countryPointMap.put(36, "end");//战鹰
            String city = "_activity_powerglory_country_end";
            while ((line = br.readLine()) != null) {
                System.out.println("redis-cli -p 6380 lpush " + city + " " + line + ";");
            }
        } catch (Exception e) {

        }
    }
}
