package com.enjoyf.wiki.tools;

import com.enjoyf.util.HttpURLUtils;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.bean.temp.WikiParamBean;
import com.enjoyf.wiki.container.PropertiesContainer;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-25 下午4:17
 * Description:
 */
public class WikiUtil {
    public static final String PC_WIKI = "wiki";
    public static final String MOBILE_WIKI = "mwiki";

    private final static String WWW_FLAG = "www.";
    private final static String RESWIKI_FLAG = "reswiki.";
    private final static String WIKI_FLAG = "/wiki";
    private final static String M_WIKI_FLAG = "/mwiki";

    private final static String M_WIKI_HOST_FLAG = ".m.";

    public final static String WIKI_KEY_ILLEGEL_CODE = "%";


    /**
     * 优先获取header或者parameter里的wikitype
     * <p/>
     * header为空判断host如果包含wikihost是PC的如果不包含是m的
     * <p/>
     * 其他二级域名认为是wiki
     *
     * @param url
     * @param request
     * @return
     * @throws MalformedURLException
     */
    public static String getWikiTypeByUrl(URL url, HttpServletRequest request) {
        String wikiType = HttpURLUtils.getValueByRequest(request, "wikitype");
        if (wikiType != null && wikiType.equalsIgnoreCase(MOBILE_WIKI)) {
            return MOBILE_WIKI;
        } else if (wikiType != null && wikiType.equalsIgnoreCase(PC_WIKI)) {
            return PC_WIKI;
        }

        String host = url.getHost();
        if (PropertiesContainer.getInstance().getWikiHost().contains(host)) {
            return PC_WIKI;
        } else if (PropertiesContainer.getInstance().getWikiMobileHost().contains(host)) {
            return MOBILE_WIKI;
        } else if (!isWWWdomain(url)) {
            if (host.contains(M_WIKI_HOST_FLAG)) {
                return MOBILE_WIKI;
            } else {
                return PC_WIKI;
            }
        }

        return PC_WIKI;
    }


    public static WikiParamBean getWikiParamBean(URL url, HttpServletRequest request) throws MalformedURLException {
        WikiParamBean paramBean = null;


        String path = url.getPath();
        String host = url.getHost().replace("http://", "");
        String[] urlpath = path.split("/");
        String wikitype = getWikiTypeByUrl(url, request);
        //www开头
        // http://www.joyme.com/wiki/mkhx/index.shtml
        //http://www.joyme.com/wxwiki/mkhx/index.shtml
        if (host.contains("www.")) {
            if (urlpath.length == 4) {
                paramBean = new WikiParamBean(urlpath[3], urlpath[2]);
                return paramBean;
            }
            //wiki.joyme.com m.wiki.joyme.com 开头
        } else if (host.startsWith("wiki") || host.startsWith("m.wiki")) {

            //http://wiki.joyme.com/index.shtml
            if (urlpath.length == 2) {
                //wiki.joyme.com/index(sitemap).shtml
                if (urlpath[1].lastIndexOf(".") > 0) {
                    paramBean = new WikiParamBean(urlpath[1], "wiki");
                } else {
                    paramBean = new WikiParamBean("index.shtml", urlpath[1]);
                }
                return paramBean;
            }

            //http://wiki.joyme.com/qjnn/366283.shtml
            //http://m.wiki.joyme.com/qjnn/350270.shtml
            if (urlpath.length == 3) {
                paramBean = new WikiParamBean(urlpath[2], urlpath[1]);
            } else if (urlpath.length == 4) {//http://www.joyme.com/wxwiki/mt/index.shtml
                paramBean = new WikiParamBean(urlpath[3], urlpath[2]);
            }

        } else {//二级域名
            String[] hosts = host.split("\\.");
            String key = hosts[0].replace("http://", "");
            if (urlpath.length == 2) {//http://zzzdy.joyme.com/index.shtml
                paramBean = new WikiParamBean(urlpath[1], key);
            } else if (urlpath.length == 3) {//http://qjnn.joyme.com/youkuwiki/index.shtml
                paramBean = new WikiParamBean(urlpath[2], key);
            }
        }

        return paramBean;
    }

//    public static WikiParamBean getWikiParamBean(URL url) throws MalformedURLException {
//        WikiParamBean paramBean = null;
//
//        if (url.getPath().contains("/wiki/wiki")) {
//            if (url.getPath().endsWith("/index.shtml")) {
//                paramBean = new WikiParamBean("index.shtml", "wiki");
//            } else {
//                paramBean = new WikiParamBean(url.getPath().replaceAll("\\/", "").replaceAll("wiki", ""), "wiki");
//            }
//
//            return paramBean;
//        }
//
//        //www wiki m.wiki
//        if (isWWWdomain(url)) {
//
//            String[] urlpath = getWikiPath(url).split("/");
//
//            LogService.infoSystemLog("################getWikiParamBean urlpath length:is =" + urlpath.length, true);
//
//
//            if (urlpath.length == 2) {
//                //wiki.joyme.com/index(sitemap).shtml
//                if (urlpath[1].lastIndexOf(".") > 0) {
//                    paramBean = new WikiParamBean(urlpath[1], "wiki");
//                } else {
//                    //如果是www.joyme.com/wiki/lt  wiki.joyme.com/lt
//                    paramBean = new WikiParamBean("index.shtml", urlpath[1]);
//                }
//                return paramBean;
//            }
//
//            //wiki.joyme.com
//            if (urlpath.length < 2) {
//                paramBean = new WikiParamBean("index.shtml", "wiki");
//                return paramBean;
//            }
//
//            //TODO 多级URL可以访问http://wiki.joyme.com/qjnn/a/a/393119.shtml
//            //上面URL应该是无法访问，返回404
//            if (urlpath.length > 3) {
//                LogService.infoSystemLog("wikiutil===,length=" + urlpath.length + ",urlpath=" + ArrayUtils.toString(urlpath));
//                return null;
//            }
//
//
//            //其他
//            String key = urlpath[1];
//            paramBean = new WikiParamBean(urlpath[urlpath.length - 1], key);
//        } else {
//            //http://ttkp.joyme.com/wxwiki/
//            String[] hosts = url.getHost().split("\\.");
//            String key = hosts[0].replace("http://", "");
//
//            String[] urlpath = getWikiPath(url).split("/");
//            if (urlpath.length == 2) {
//                paramBean = new WikiParamBean(urlpath[1], key);
//            }
//
//            if (urlpath.length == 3) {
//                paramBean = new WikiParamBean(urlpath[2], key);
//            }
//            return paramBean;
//
//        }
//
//        return paramBean;
//    }


    public static String appendIndex(String url) {
        if (url.endsWith("/")) {
            url += "index.shtml";
        }
        return url;
    }

    /**
     * 访问的url是否来自于www。
     *
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static boolean isWWWdomain(URL url) {
        String host = url.getHost().replace("http://", "");
        return host.contains(WWW_FLAG)
                || host.contains("localhost") || host.startsWith("172.16")
                || host.startsWith("192.168") || host.startsWith("127.0") || host.startsWith("123.") || host.startsWith("10.")
                || host.startsWith("wiki") || host.startsWith("m.wiki");
    }

    public static boolean isResWIki(URL url) throws MalformedURLException {
        return url.getHost().replace("http://", "").contains(RESWIKI_FLAG);
    }

    public static String getWikiPath(URL url) throws MalformedURLException {
        String path = url.getPath();
        if (isWWWdomain(url)) {
            path = path.replaceAll(WIKI_FLAG, "");
            path = path.replaceAll(M_WIKI_FLAG, "");
        }
        return path;
    }

    /**
     * 2级域名，不是默认渠道http://xxx.joyme.com/<channel>wiki/<pageid>.shtml
     * 2级域名，默认渠道http://xxx.joyme.com/<pageid>.shtml
     * 2级域名，默认渠道mwiki  http://xxx.joyme.com/mwiki/<pageid>.shtml
     * <p/>
     * 非2级域名，mobile 默认渠道  http://m.wiki.joyme.com/<pageid>.shtml
     * 非2级域名，pc 默认渠道  http://wiki.joyme.com/<pageid>.shtml
     * 非2级域名，pc 其他渠道  http://wiki.joyme.com/wxwiki/<pageid>.shtml
     */
    public static String getWikiUrl(String key, String wikiType, String wikiPageId, String channel) throws Exception {
        JoymeWiki joymeWiki = PropertiesContainer.getInstance().getJoymeWiki(key, wikiType);

        String url = "";
        if (joymeWiki == null) {
            return url;
        }

        if (joymeWiki.getSupportSubDomain()) {
            url = "http://" + key + "." + PropertiesContainer.getInstance().getSubDomain() + "/";
            if (!channel.equals("default")) {
                url += channel + "wiki" + "/";
            } else {
                if (wikiType.equals(WikiUtil.MOBILE_WIKI)) {
                    url += "mwiki" + "/";
                }
            }
            url += wikiPageId + ".shtml";

        } else {
            String host = wikiType.equals(WikiUtil.MOBILE_WIKI) ? PropertiesContainer.getInstance().getWikiMobileHost() : PropertiesContainer.getInstance().getWikiHost();
            url = host + "/" + key + "/" + wikiPageId + ".shtml";
            if (!channel.equals("default")) {
                if (wikiType.equals(WikiUtil.PC_WIKI)) {
                    url = host + "/" + channel + "wiki/" + key + "/" + wikiPageId + ".shtml";
                }
            }
        }
        return url;
    }


    public static void main(String[] args) throws MalformedURLException {

        String host = "http://ttkp.joyme.beta/wxwiki/";


//        if ( "http://www.joyme.com".contains(host)) {
//            System.out.println(PC_WIKI);
//        } else if ( "http://m.wiki.joyme.com".contains(host)) {
//            System.out.println(MOBILE_WIKI);
//        } else if (!isWWWdomain(new URL(host))) {
//            if (host.contains(M_WIKI_HOST_FLAG)) {
//                System.out.println(MOBILE_WIKI);
//            } else {
//                System.out.println(PC_WIKI);
//            }
//        }
        //System.out.println(getWikiParamBean(new URL("http://qjnn.joyme.beta/youkuwiki/346487.shtml")));
        //System.out.println(getWikiParamBean(new URL("http://www.joyme.beta/wxwiki/dtcq/")));
        // System.out.println(getWikiParamBean(new URL("http://www.joyme.beta/wxwiki/dtcq/index.shtml")));
        //  System.out.println(getWikiParamBean(new URL("http://www.joyme.beta/wxwiki/dtcq/67153.shtml")));
    }


}
