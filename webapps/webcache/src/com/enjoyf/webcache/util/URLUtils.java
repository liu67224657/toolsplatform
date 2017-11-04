package com.enjoyf.webcache.util;

/**
 * Created by zhimingli on 2015/8/10.
 */
public class URLUtils {

//    /**
//     * //根据外网访问地址，获取文件存储路径
//     *
//     * @param \http://hs.joyme.com
//     * @return d:/cmsimage-cache/hs/vip/hs/ or /opt/cmsimage-cache/hs/vip/hs
//     */
//    @Deprecated
//    public static String getPath(String urlType, String channel, String url) {
//
//
//        IHtmlParser htmlPraser = HtmlParserFactory.getPraser(urlType);
//
//        String key = htmlPraser.getUrlKeyByUrl(url, channel);
//
//        return htmlPraser.getCacheFile(urlType, key, channel, url);
//    }


    public static String getFullURL(String path) {
        String[] paths = path.split("/");
        String tempPath = "";
        if (paths.length > 3) {
            String lastPath = paths[paths.length - 1];
            if (!lastPath.endsWith("/")) {
                for (int i = 0; i < paths.length; i++) {
                    if (i != paths.length - 1) {
                        tempPath += paths[i] + "/";
                    } else {
                        tempPath += lastPath;
                    }
                }
            } else {
                tempPath = path;
            }
            return tempPath;
        } else if (paths.length == 3) { //只有域名的情况
            return path;
        } else {
            return null;
        }
    }

}
