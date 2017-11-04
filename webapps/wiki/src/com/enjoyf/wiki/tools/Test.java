package com.enjoyf.wiki.tools;

import java.util.regex.Pattern;

public class Test {
    private static final Pattern link = Pattern.compile("<a([^>]+)>");
    private static final Pattern ahref = Pattern.compile("href=\"([^\"]+)\"");

    public static void main(String[] args) {
    	System.out.println(System.getProperty("java.io.tmpdir"));
//        URLService service = new URLService();
//        String html = service.getHTML("http://ma.joyme.com");
//
//        Matcher linkMatcher = link.matcher(html);
//        StringBuffer sb = new StringBuffer(); 
//        while (linkMatcher.find()) {
//            String propsString = linkMatcher.group(1);
//
//            Matcher ahrefMatcher = ahref.matcher(propsString);
//           
//            if (ahrefMatcher.find()) {
//                String href = ahrefMatcher.group(1);
//                propsString = propsString.replaceAll(href, "123456");
//            }
//            
//            linkMatcher.appendReplacement(sb, propsString);
//        }
//        linkMatcher.appendTail(sb); 
//
//        System.out.println(sb.toString());
//        System.out.println(URLDecoder.decode("%E3%80%8A%E5%9B%9B%E5%9B%BD%E6%88%98%E8%AE%B0%E3%80%8B%E4%B8%8A%E7%BA%BF%E5%AE%98%E6%96%B9%E6%B4%BB%E5%8A%A8%E5%BC%80%E5%90%AF"));
    }
}
