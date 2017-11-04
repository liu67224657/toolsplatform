package com.enjoyf.mcms.factory.impl;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.temp.PointArchiveListBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.filter.URLFilter;
import com.enjoyf.mcms.service.*;
import com.enjoyf.mcms.util.*;
import com.enjoyf.mcms.util.AppUtil;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.util.*;
import com.enjoyf.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractParseFactoryImpl implements IHtmlParseFactory {


    public final static String BEGIN_GAME_LOOP_FIELD = "{joyme:game-loop}";
    public final static String END_GAME_LOOP_FIELD = "{/joyme:game-loop}";
    public final static String BEGIN_ARTICLE_LOOP_FIELD = "{joyme:article-loop}";
    public final static String END_ARTICLE_LOOP_FIELD = "{/joyme:article-loop}";
    public final static String BEGIN_CHECK_DOWNLOAD_LOOP_FIELD = "{joyme:check-download}";
    public final static String END_CHECK_DOWNLOAD_LOOP_FIELD = "{/joyme:check-download}";
    public final static String BEGIN_GAME_POINT = "{joyme:game-point}";
    public final static String END_GANE_POINT = "{/joyme:game-point}";
    public final static String ARTICLE_BODY = "{joyme:article-body/}";

    public final static String POINT_NAME = "\\{joyme:point-name /\\}";
    public final static String POINT_MORE = "\\{joyme:point-more /\\}";

    public final static String ARTICLE_INFO = "\\{joyme:artcile-info /\\}";

    public final static String CONTENT_PATH = "\\{joyme:contextPath/\\}";
    public final static String ARTICLE_TITLE = "\\{joyme:article-title/\\}";
    public final static String ARTICLE_DESCRIPTION = "\\{joyme:article-description/\\}";
    public final static String ARTICLE_CLIENTPIC = "\\{joyme:article-clientpic/\\}";
    public final static String ARTICLE_HEAD = "\\{joyme:article-head/\\}";
    public final static String ARTICLE_PUBLISH_USER = "\\{joyme:article-publishuser/\\}";
    public final static String ARTICLE_PUBLISH_DATE = "\\{joyme:article-publishtime/\\}";
    public final static String ARTICLE_BODY_FLAG = "\\{joyme:article-body/\\}";
    public final static String ARTICLE_RELATEDNEWS = "\\{joyme:relatednews/\\}";
    public final static String ARTICLE_RELATEDNEWS_SYHB4 = "\\{joyme:relatednews-syhb4/\\}";
    public final static String ARTICLE_RELATEDNEWS_SYHB4_PICTORIAL = "\\{joyme:relatednews-syhb4-pictorial/\\}";
    public final static String ARTICLE_RELATEDNEWS_WANBA210 = "\\{joyme:relatednews-wanba210/\\}";
    public final static String MARTICLE_HOST = "\\{joyme:marticle_host/\\}";

    public final static String SPEC_NAME = "\\{joyme:spec-name/\\}";
    public final static String SPEC_TYPE = "\\{joyme:spec-type/\\}";
    public final static String SPEC_LANGUAGE = "\\{joyme:spec-language/\\}";
    public final static String SPEC_SIZE = "\\{joyme:spec-size/\\}";
    public final static String SPEC_VERSION = "\\{joyme:spec-version/\\}";
    public final static String SPEC_IMG_URL = "\\{joyme:spec-img-url/\\}";
    public final static String SPEC_ADVERTISE = "\\{joyme:spec-advertise/\\}";
    public final static String SPEC_DOWNLOAD_URL = "\\{joyme:spec-download-url/\\}";
    public final static String BEGIN_CHECK_DOWNLOAD_LOOP_FIELD_FLAG = "\\{joyme:check-download\\}";
    public final static String END_CHECK_DOWNLOAD_LOOP_FIELD_FLAG = "\\{/joyme:check-download\\}";

    public final static String PREVIOUS_PAGE = "\\{joyme:previous-page/\\}";
    public final static String NEXT_PAGE = "\\{joyme:next-page/\\}";
    // articleType
    protected final static String LIST_NAME_REPLACE_STRING = "\\{joyme:atlist-title/\\}";
    protected final static String BEGIN_ARTICLE_TYPE_LOOP_FIELD = "{joyme:atlist-loop}";
    protected final static String END_ARTICLE_TYPE_LOOP_FIELD = "{/joyme:atlist-loop}";
    protected final static String ARCHIVE_LINK_REPLACE_STRING = "\\{joyme:archive-link/\\}";
    protected final static String ARCHIVE_TITLE_REPLACE_STRING = "\\{joyme:archive-title/\\}";
    protected final static String BEGIN_ARTICLE_TYPE_LASTPAGE_FIELD = "\\{joyme:at-list-last-page/\\}";
    protected final static String END_ARTICLE_TYPE_NEXTPAGE_FIELD = "\\{joyme:at-list-next-page/\\}";
    protected final static String TYPE_DIR_REPLACE_FLAG = "\\{typedir\\}";
    protected final static String YEAR_REPLACE_FLAG = "\\{Y\\}";
    protected final static String MONTH_REPLACE_FLAG = "\\{M\\}";
    protected final static String DATE_REPLACE_FLAG = "\\{D\\}";
    protected final static String AID_REPLACE_FLAG = "\\{aid\\}";


    public final static String ARTICLE_UNIKEY = "\\{joyme:article-unikey/\\}";
    public final static String ARTICLE_URI = "\\{joyme:article-uri/\\}";
    public final static String ARTICLE_SHARE_URI = "\\{joyme:article-share-uri/\\}";


    public final static String DEFAULT_FOLDER = "default";
    public final static String DEFAULT_TIME = "2013年1月1日";
    public final static Long DEFAULT_TIME_LONG = 1356969600052L;//01/01/2013 00:00:00 long

    protected final static String NEXT_LINE = "\r\n";
    protected final static String LINE_NODE = "p";
    protected final static String DIV_NODE = "div";
    protected final static String IMAGE_NODE = "img";
    protected final static String EMBED_NODE = "embed";
    protected static String domain = null;

    private static URLUtil urlUtil = new URLUtil();
    protected static ImageUtil imageUtil = new ImageUtil();
    protected static SystemUtil systemUtil = new SystemUtil();
    protected static GameDownloadService gameService = new GameDownloadService();
    protected static JoymePointArchiveService joymePointArchiveService = new JoymePointArchiveService();
    private JoymeSpecService joymeSpecService = new JoymeSpecService();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();

    protected ArchiveUtil archiveUtil = new ArchiveUtil();

    public static int times = 0;

    private final static String OLD_IMAGE_FLAG = "http://www.joyme.com/article/";
    private final static String NEW_IMAGE_FLAG = "http://article.joyme.com/article/";

    public String makeSpecHtml(Connection conn, JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath)
            throws Exception {
        // 获得渠道
        String htmlTemplate = this.getGameHtmlTemplate(channel);
        htmlTemplate = MarticleUtil.addRobotsMetaHead(htmlTemplate);

        htmlTemplate = htmlTemplate.replaceAll(CONTENT_PATH, localPath);
        htmlTemplate = htmlTemplate.replaceAll(SPEC_TYPE, spec != null ? spec.getSpecType() : "");
        htmlTemplate = htmlTemplate.replaceAll(SPEC_LANGUAGE, spec != null ? spec.getSpecLanguage() : "");
        htmlTemplate = htmlTemplate.replaceAll(SPEC_SIZE, spec != null ? spec.getSpecSize() : "");
        htmlTemplate = htmlTemplate.replaceAll(SPEC_VERSION, spec != null ? spec.getSpecVersion() : "");
        htmlTemplate = htmlTemplate.replaceAll(SPEC_IMG_URL, spec != null ? spec.getSpecPicUrl() : "");
        htmlTemplate = htmlTemplate.replaceAll(SPEC_NAME, spec != null ? spec.getSpecName() : "首页");

        // 处理下载
        if (content == null || content.getDownloadUrl() == null || "".equals(content.getDownloadUrl())) { // 没用的时候，删除
            String[] headEnd = StringUtil.getHeadAndEndString(htmlTemplate, BEGIN_CHECK_DOWNLOAD_LOOP_FIELD, END_CHECK_DOWNLOAD_LOOP_FIELD);
            htmlTemplate = headEnd[0] + headEnd[1];
        } else { // 如果有下载的地址
            htmlTemplate = htmlTemplate.replaceAll(SPEC_DOWNLOAD_URL, content.getDownloadUrl());
            htmlTemplate = htmlTemplate.replaceAll(BEGIN_CHECK_DOWNLOAD_LOOP_FIELD_FLAG, "");
            htmlTemplate = htmlTemplate.replaceAll(END_CHECK_DOWNLOAD_LOOP_FIELD_FLAG, "");
        }

        // 处理广告
        htmlTemplate = htmlTemplate.replaceAll(SPEC_ADVERTISE, content != null ? content.getAdvertiseContent() : "");

        String loopField = StringUtil.getStringBetweenFlag(htmlTemplate, BEGIN_GAME_LOOP_FIELD, END_GAME_LOOP_FIELD);

        // 最后的名称，在point的getHtmlFile()中放着，每个都一样
        String htmlFile = "";
        // 生成专区的信息
        StringBuffer sb = getGamePointHtml(specList, loopField);

        htmlFile = getHtmlFile(specList, htmlFile);

        String[] headEnd = StringUtil.getHeadAndEndString(htmlTemplate, BEGIN_GAME_LOOP_FIELD, END_GAME_LOOP_FIELD);
        String retString = this.getReplaceString(headEnd, sb.toString());

        String path = getGameCachePath(htmlFile, channel);
        FileUtil.writeFile(path, retString);
        return retString;
    }

    protected String getGameHtmlTemplate(String channel) {
        String template = ConfigContainer.getGameTemplate(channel);
        return (String) TemplateContainer.gameTemplateMap.get(template);
    }

    protected String getArticleTypeTemplate(String channel) {
        String template = ConfigContainer.getGameTemplate(channel);
        return (String) TemplateContainer.articleTypeTemplateMap.get(template);
    }

    protected StringBuffer getGamePointHtml(List specList, String loopField) {
        // 循环取出各个节点 point
        StringBuffer sb = new StringBuffer();

        List tempSpecList = new ArrayList();
        tempSpecList.addAll(specList);

        while (true) {
            // 如果没有专区信息了，结束
            if (tempSpecList.size() == 0)
                break;

            boolean firstEnter = true;
            String tempLoopField = "";
            while (true) {
                if (firstEnter) {
                    tempLoopField = new String(loopField);
                    firstEnter = false;
                }

                // 如果没有找到BEGIN_GAME_POINT了，这次循环结束，进行下一个loop的循环。
                if (tempLoopField.indexOf(BEGIN_GAME_POINT) < 0 || tempSpecList.size() == 0)
                    break;

                String pointHtml = StringUtil.getStringBetweenFlag(tempLoopField, BEGIN_GAME_POINT, END_GANE_POINT);
                PointArchiveListBean bean = (PointArchiveListBean) tempSpecList.get(0);
                JoymePoint point = bean.getPoint();
                List archiveList = bean.getArchiveList();
                String pointItemHtml = this.getPointHtml(pointHtml, point, archiveList);

                String[] pointItemHeadEnd = StringUtil.getHeadAndEndString(tempLoopField, BEGIN_GAME_POINT, END_GANE_POINT);
                tempLoopField = pointItemHeadEnd[0] + pointItemHtml + pointItemHeadEnd[1];
                sb.append(pointItemHtml);

                // 封装完成,去掉tempSpecList中当前的节点
                tempSpecList.remove(bean);
            }
            // sb.append(tempLoopField);
        }
        return sb;
    }

    protected String getHtmlFile(List specList, String htmlFile) {
        if (specList.size() > 0) {
            PointArchiveListBean bean = (PointArchiveListBean) specList.get(0);
            htmlFile = bean.getPoint().getHtmlFile();
        }
        return htmlFile;
    }

    /**
     * 获得一个节点的html
     *
     * @param loopField
     * @param point
     * @param archiveList
     * @return
     */
    protected String getPointHtml(String loopField, JoymePoint point, List archiveList) {
        // 修改节点的名称
        String pointHtml = loopField;

        pointHtml = pointHtml.replaceAll(POINT_NAME, point.getSpecPointName());
        String moreLink = "../" + URLFilter.ARTICLE_LIST_FLAG + "/" + point.getSpecPointId() + "_1.html";
        // String moreLink = "../" + URLFilter.ARTICLE_LIST_FLAG +
        // "/more.do?pointId=" + point.getSpecPointId() + "&pageNum=1";
        pointHtml = pointHtml.replaceAll(POINT_MORE, "<a href=\"" + moreLink + "\">更多</a>");

        // 循环出文章列表
        String articleLoopFiled = StringUtil.getStringBetweenFlag(pointHtml, BEGIN_ARTICLE_LOOP_FIELD, END_ARTICLE_LOOP_FIELD);
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = archiveList.iterator(); iterator.hasNext(); ) {
            JoymePointArchive archive = (JoymePointArchive) iterator.next();
            String archiveStr = this.getArchiveListString(articleLoopFiled, archive);
            sb.append(archiveStr);
        }

        String[] headEnd = StringUtil.getHeadAndEndString(pointHtml, BEGIN_ARTICLE_LOOP_FIELD, END_ARTICLE_LOOP_FIELD);
        String retString = this.getReplaceString(headEnd, sb.toString());
        return retString;
    }

    /**
     * 拼接前后的html
     *
     * @param headEnd
     * @param
     * @return
     */
    protected String getReplaceString(String[] headEnd, String str) {
        if (headEnd != null && headEnd.length == 2) {
            return headEnd[0] + str + headEnd[1];
        } else {
            return str;
        }
    }

    /**
     * 获得文章列表的html
     *
     * @param articleLoopFiled
     * @param archive
     * @return
     */
    protected String getArchiveListString(String articleLoopFiled, JoymePointArchive archive) {
        StringBuffer sb = new StringBuffer();
        if (archive.getRedirectUrl() != null) {
            sb.append("<a href=\"").append(archive.getRedirectUrl());
        } else {
            sb.append("<a href=\"../").append(archive.getHtmlPath()).append("/").append(archive.getHtmlFile());
        }

        sb.append(" \">");
        sb.append(archive.getTitle());
        sb.append("</a>");
        articleLoopFiled = articleLoopFiled.replaceAll(ARTICLE_INFO, sb.toString());
        return articleLoopFiled;
    }

    /**
     * 获得专题缓存的地址
     *
     * @param htmlFile
     * @return
     * @throws IOException
     */
    protected String getGameCachePath(String htmlFile, HttpServletRequest request) throws Exception {
        String channel = ChannelService.getChannel(request);
        channel = channel == null ? DEFAULT_FOLDER : channel;
        return this.getGameCachePath(htmlFile, channel);
    }

    /**
     * 获得专题缓存的地址
     *
     * @param htmlFile
     * @param channel
     * @return
     * @throws Exception
     */
    public String getGameCachePath(String htmlFile, String channel) throws Exception {
        return ConfigContainer.getCacheFolder() + "/game/" + channel + "/" + htmlFile + ".html";
    }

    public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, CmsAddress cmsAddress, String filePath, String fileName, String channel,
                                  String localPath, String plat, String uri) throws Exception {
        String htmlTemplate = getArchiveTemplate(channel);
        htmlTemplate = htmlTemplate.replaceAll(CONTENT_PATH, localPath);

        htmlTemplate = MarticleUtil.addRobotsMetaHead(htmlTemplate);

        String title = dedeArchives == null ? "正文" : dedeArchives.getTitle();
        String clientPic = dedeArchives.getClientpic();
        //如果是完整地址则不替换
        if (StringUtil.isEmpty(clientPic) || !clientPic.startsWith("http://")) {
            clientPic = StringUtil.isEmpty(clientPic) ? "http://marticle.joyme.com/img/default_client_pic.png" : ConfigContainer.getCMSDomain() + clientPic;
        }
        if ((dedeArchives == null || StringUtil.isEmpty(dedeArchives.getClientpic()))) {
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_HEAD, "<h1 class=\"article-title\">" + title + "</h1>");
        } else {
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_HEAD, "<div class=\"img-wrapper\"><h1 class=\"headline-title\">" + title + "</h1>" +
                    "<img src=\"" + clientPic + "\" width=\"100%\" ></div>");
        }
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_TITLE, title);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_DESCRIPTION, StringUtil.isEmpty(dedeArchives.getDescription()) ? "" : dedeArchives.getDescription());


        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_CLIENTPIC, clientPic);
        htmlTemplate = htmlTemplate.replaceAll(MARTICLE_HOST, ConfigContainer.getMarticleHost());

        //TODO 手游画报1.4.2添加wap评论
        String archiveid = StringUtil.isEmpty(dedeArchives.getId() + "") ? "" : dedeArchives.getId() + "";
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_UNIKEY, archiveid);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_URI, ConfigContainer.getMarticleDomain() + uri);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_SHARE_URI, ShortUrlUtils.getSinaURL(ConfigContainer.getMarticleDomain() + uri.replaceAll(channel, "pictorial")));


        String datestr = dedeArchives == null ? DEFAULT_TIME : DateUtil.convertArticleString(Long.parseLong(dedeArchives.getPubdate() + "000"));

        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_PUBLISH_DATE, datestr);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_PUBLISH_USER, StringUtil.isEmpty(dedeArchives.getWriter()) ? "着迷网" : dedeArchives.getWriter());
        // htmlTemplate = htmlTemplate.replaceAll(ARTICLE_BODY,
        // this.getNoStyleHtml(dedeAddonarticle.getBody()));

        // 替换正文
        int position = htmlTemplate.indexOf(ARTICLE_BODY);
        if (position > 0) {
            String begin = htmlTemplate.substring(0, position);
            String end = htmlTemplate.substring(position + ARTICLE_BODY.length(), htmlTemplate.length());
            htmlTemplate = begin + this.getNoStyleHtml(dedeArchives.getId(), dedeAddonarticle.getBody(), channel, plat) + end;
        }
        //替换相关文章
        if (!CollectionUtils.isEmpty(dedeArchives.getRelatedNewsList())) {
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS, replaceRelatedArticle(dedeArchives.getRelatedNewsList(), channel));
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_SYHB4, replaceRelatedArticleSyhb(dedeArchives.getRelatedNewsList(), channel));
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_WANBA210, replaceRelatedArticleWanba210(dedeArchives.getRelatedNewsList(), channel));
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_SYHB4_PICTORIAL, replaceRelatedArticleSyhbPictorial(dedeArchives.getRelatedNewsList()));
        } else {
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS, "");
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_SYHB4, "");
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_WANBA210, "");
            htmlTemplate = htmlTemplate.replaceAll(ARTICLE_RELATEDNEWS_SYHB4_PICTORIAL, "");
        }

        //替换上一篇下一篇
        JoymePointArchive joymePointArchive = joymePointArchiveService.getPointArchiveIdByArchive(null, dedeArchives.getId());
        if (joymePointArchive != null) {
            htmlTemplate = this.replaceNextArticle(htmlTemplate, joymePointArchive.getPointId(), joymePointArchive.getPointArchiveId());
        } else {
            htmlTemplate = htmlTemplate.replaceAll(NEXT_PAGE, "");
            htmlTemplate = htmlTemplate.replaceAll(PREVIOUS_PAGE, "");
        }
        // 保存html
        String path = getArchiveCachePath(filePath, fileName, channel, plat);
        FileUtil.writeFile(path, htmlTemplate);

        return htmlTemplate;
    }

    protected String replaceRelatedArticleWanba210(List<DedeAddonrelevance> relatedNewsList, String channel) {
        StringBuffer relate = new StringBuffer("<h1><span>扩展阅读</span></h1>");
        relate.append("<ul>");
        for (DedeAddonrelevance dedeAddonrelevance : relatedNewsList) {

            //  sss
            String url = archiveUtil.formatUrl(dedeAddonrelevance.getUrl().replace("com", ConfigContainer.getMarticleHost()));
            relate.append("<li><a id='jump' jump='" + url.replace("marticle/", channel + "/") + "' href=\"javascript:void(0);\"><cite><img src='" + dedeAddonrelevance.getClientpic() + "?imageView2/0/w/100' ></cite><p><b>" + dedeAddonrelevance.getTitle() + "</b><span>" + StringUtil.truncate(dedeAddonrelevance.getDescription(), 23) + "</span></p></a></li>");
        }
        relate.append("</ul>");
        return relate.toString();
    }

    /**
     * 替换相关文章
     *
     * @param relatedNewsList
     * @return
     */
    private String replaceRelatedArticle(List<DedeAddonrelevance> relatedNewsList, String channel) {
        StringBuffer relate = new StringBuffer("<h2>相关文章</h2>");
        relate.append("<ul>");

        for (DedeAddonrelevance dedeAddonrelevance : relatedNewsList) {
            String url = archiveUtil.formatUrl(dedeAddonrelevance.getUrl());
            if (channel.equals("pictorial")) {
                relate.append("<li><a href=" + url.replace("marticle/", "pictorial/") + ">" + dedeAddonrelevance.getTitle() + " </a></li> ");
            } else {
                relate.append("<li><a href=" + url.replace("marticle/", channel + "/") + ">" + dedeAddonrelevance.getTitle() + " </a></li> ");
            }

        }
        relate.append("</ul>");
        return relate.toString();
    }

    /**
     * 替换相关文章
     *
     * @param relatedNewsList
     * @return
     */
    private String replaceRelatedArticleSyhb(List<DedeAddonrelevance> relatedNewsList, String channel) {
        StringBuffer relate = new StringBuffer("<h1><span>相关文章</span></h1>");
        relate.append("<ul>");
        for (DedeAddonrelevance dedeAddonrelevance : relatedNewsList) {
            String url = archiveUtil.formatUrl(dedeAddonrelevance.getUrl());
            if (channel.equals("pictorial")) {
                relate.append("<li><a jump=\"" + url.replace("marticle/", "pictorial/") + "\" href=\"javascript:void(0);\">" + dedeAddonrelevance.getTitle() + " </a></li> ");
            } else {
                relate.append("<li><a jump=\"" + url.replace("marticle/", channel + "/") + "\" href=\"javascript:void(0);\">" + dedeAddonrelevance.getTitle() + " </a></li> ");
            }

        }
        relate.append("</ul>");
        return relate.toString();
    }

    /**
     * 替换相关文章
     *
     * @param relatedNewsList
     * @return
     */
    private String replaceRelatedArticleSyhbPictorial(List<DedeAddonrelevance> relatedNewsList) {
        StringBuffer relate = new StringBuffer("<h1><span>相关文章</span></h1>");
        relate.append("<ul>");
        for (DedeAddonrelevance dedeAddonrelevance : relatedNewsList) {
            String url = archiveUtil.formatUrl(dedeAddonrelevance.getUrl());
            relate.append("<li><a  href=\"" + url.replace("marticle/", "pictorial/") + "\">" + dedeAddonrelevance.getTitle() + " </a></li> ");
        }
        relate.append("</ul>");
        return relate.toString();
    }

    private String replaceNextArticle(String htmlTemplate, Long pointId, Long pointArchiveId) throws Exception {
        JoymePointArchive joymePointArchive = joymePointArchiveService.getJoymePointArchive(null, pointId, pointArchiveId, "<");
        String link = null;
        if (joymePointArchive != null) {
            link = this.getLink(joymePointArchive);
            htmlTemplate = htmlTemplate.replaceAll(NEXT_PAGE, "<span class=\"nextArticle\">下一篇:</span>" + link);
        } else {
            htmlTemplate = htmlTemplate.replaceAll(NEXT_PAGE, "");
        }

        joymePointArchive = joymePointArchiveService.getJoymePointArchive(null, pointId, pointArchiveId, ">");
        if (joymePointArchive != null) {
            link = this.getLink(joymePointArchive);
            htmlTemplate = htmlTemplate.replaceAll(PREVIOUS_PAGE, "<span class=\"nextArticle\">上一篇:</span>" + link);
        } else {
            htmlTemplate = htmlTemplate.replaceAll(PREVIOUS_PAGE, "");
        }
        return htmlTemplate;
    }

    private String getLink(JoymePointArchive archive) {
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"/marticle/").append(archive.getHtmlPath()).append("/").append(archive.getHtmlFile());
        sb.append(" \">");
        sb.append(archive.getTitle());
        sb.append("</a>");
        return sb.toString();
    }

    protected String getArchiveTemplate(String channel) {
        String template = ConfigContainer.getArchiveTemplate(channel);
        return (String) TemplateContainer.archiveTemplateMap.get(template);
    }

    /**
     * 获得文章子页缓存的页面路径
     *
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public String getArchiveCachePath(String filePath, String fileName, HttpServletRequest request) throws Exception {
        String channel = ChannelService.getChannel(request);
        channel = channel == null ? DEFAULT_FOLDER : channel;
        String plat = AppUtil.getPlatForm(request);
        String path = getArchiveCachePath(filePath, fileName, channel, plat);
        return path;
    }

    /**
     * 获得文章子页缓存的页面路径
     *
     * @param filePath
     * @param fileName
     * @param channel
     * @return
     * @throws Exception
     */
    public String getArchiveCachePath(String filePath, String fileName, String channel, String plat) throws Exception {
        String cacheFolder = ConfigContainer.getCacheFolder();
        String path = null;
        if (fileName.endsWith(".html")) {
            path = cacheFolder + "/archive/" + filePath + "/" + plat + "/" + channel + "/" + fileName;
        } else {
            path = cacheFolder + "/archive/" + filePath + "/" + plat + "/" + channel + "/" + fileName + ".html";
        }
        return path;
    }

    /**
     * 解析html
     *
     * @param html
     * @return
     * @throws Exception
     */
    protected String getNoStyleHtml(int aid, String html, String channel, String plat) throws Exception {
        Document document = Jsoup.parse(html);

        // 去掉style
        removeStyle(document);

        // 修改img
        updateImg(aid, document, channel);

        // 处理视频
        updateEmbed(document);

        // 去掉底脚的查看更多信息
        removeJoymeInfoLink(document);

        // 处理link
        updateLink(document);

        // 封装game-download
        updateGameDownload(document, channel, plat);

        removeBR(document);

        String retHtml = document.getElementsByTag("body").get(0).html();

        // 获得short-content
        // TODO:增加short-content

        return retHtml;
    }

    private void removeBR(Document document) {
        Elements elements = document.getElementsByTag("p");

        if (elements == null || elements.size() > 0) {
            for (Element p : elements) {
                String textP = HtmlUtils.htmlEscape(p.text().trim()).replace("&nbsp;", "");

                Elements element = p.children().select("img");
                if (element != null && element.size() > 0) {
                    continue;
                }

                Elements iframe = p.children().select("iframe");
                if (iframe != null && iframe.size() > 0) {
                    iframe.removeAttr("height");
                    iframe.removeAttr("width");
                    iframe.attr("style", "height:270px;width:100%");
                    continue;
                }

                if (textP == null || textP.length() == 0) {
                    p.remove();
                }
            }

        }

    }

    protected void updateGameDownload(Document doc, String channel, String plat) throws IOException {
        List elements = doc.getElementsByTag("div");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();

            if (element.attr("name").equals("joyme-download")) {
                GameBean bean = this.getGameBean(element.attr("id"));
                if (bean != null) {
                    StringBuffer sb = makeGameDownload(channel, bean, plat);
                    element.html(sb.toString());
                }
            }
        }
    }

    protected StringBuffer makeGameDownload(String channel, GameBean bean, String plat) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"game-download\">").append(NEXT_LINE);
        sb.append(" <div class=\"game-download-box clearfix\">").append(NEXT_LINE);
        sb.append("     <h2>" + bean.getGameName() + "</h2>").append(NEXT_LINE);
        sb.append("     <img src=\"" + bean.getGameIcon() + "\" width=\"116\" height=\"116\"/>").append(NEXT_LINE);
        sb.append("     <div>").append(NEXT_LINE);
        // 设置下载
        String androidUrl = gameService.getDownloadLink(bean, GameDownloadService.ANDROID, channel);
        if (androidUrl != null && plat.equals(AppUtil.ANDROID))
            sb.append("         <a href=\"" + androidUrl + "\">android下载</a>").append(NEXT_LINE);
        String iphoneUrl = gameService.getDownloadLink(bean, GameDownloadService.IPHONE, channel);
        if (iphoneUrl != null && plat.equals(AppUtil.IPHONE))
            sb.append("         <a href=\"" + iphoneUrl + "\">iphone下载</a>").append(NEXT_LINE);
        String ipadUrl = gameService.getDownloadLink(bean, GameDownloadService.IPAD, channel);
        if (ipadUrl != null && plat.equals(AppUtil.IPAD))
            sb.append("         <a href=\"" + ipadUrl + "\">ipad下载</a>").append(NEXT_LINE);

        sb.append("     </div>");
        sb.append(" </div>");
        sb.append("</div>");
        sb.append("<!-- 游戏属性 -->");
        sb.append("<ul class=\"game-meta\">");
        if (!StringUtil.isEmpty(bean.getDisplayPlatform()))
            sb.append(" <li>游戏平台：" + bean.getDisplayPlatform() + "</li>");

        sb.append(" <li>游戏类型：" + bean.getGameType(16) + "</li>");

        if (!StringUtil.isEmpty(bean.getFactoryName()))
            sb.append(" <li>发行厂商：" + bean.getFactoryName(16) + "</li>");

        if (plat.equals(AppUtil.ANDROID) && !StringUtil.isEmpty(bean.getAndroidGameSize()))
            sb.append(" <li>游戏大小：" + bean.getAndroidGameSize() + "</li>");
        if (plat.equals(AppUtil.IPHONE) && !StringUtil.isEmpty(bean.getIphoneGameSize()))
            sb.append(" <li>游戏大小：" + bean.getIphoneGameSize() + "</li>");
        if (plat.equals(AppUtil.IPAD) && !StringUtil.isEmpty(bean.getIpadGameSize()))
            sb.append(" <li>游戏大小：" + bean.getIpadGameSize() + "</li>");

        if (!StringUtil.isEmpty(bean.getGamePublishDate()))
            sb.append(" <li>更新时间：" + bean.getGamePublishDate() + "</li>");
        if (bean.getDisplayWikiCmsUrl() != null)
            sb.append(" <li>游戏专区/wiki：<a href=\"" + bean.getDisplayWikiCmsUrl() + "\">请点击</a></li>");
        sb.append("</ul>");
        return sb;
    }

    protected void updateLink(Document document) {

    }

    /**
     * 去掉style
     *
     * @param document
     */
    protected void removeStyle(Document document) {
        Elements elements = document.getElementsByAttribute("style");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            //如果遇到特殊的class不要删除
            if (element.attr("class").equals("not-remove")) {
                continue;
            }
            element.removeAttr("style");
        }
    }

    /**
     * 更新图片
     *
     * @param document
     * @throws Exception
     */
    protected void updateImg(int aid, Document document, String channel) throws Exception {
        Elements elements = document.getElementsByTag(IMAGE_NODE);
        int i = 0;
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            i++;

            Element element = (Element) iterator.next();
            if (element.hasAttr("width")) {
                try {
                    int width = Integer.parseInt(element.attr("width"));
                    if (width > 240)
                        element.attr("width", "100%");
                } catch (Exception e) {
                    element.attr("width", "100%");
                }
            } else {
                element.attr("width", "100%");
            }

            if (element.hasAttr("height"))
                element.removeAttr("height");

            // 修改src的路径
            // 如果是http://www.joyme.com/article/uploads开头的，需要先去找找upapp里面有没有相同的图片
            String imagePath = getImgPath(element.attr("src"));
            element.attr("src", imagePath);
            if (i == 1) { // 放入第一张图片的地址
                JoymePointArchive bean = new JoymePointArchive();
                bean.setArchiveId((long) aid);
                bean.setImageUrl(imagePath);
                joymePointArchiveService.updateJoymePointArchiveImageUrlByAId(null, bean);
            }

            // 修改onclick
            if (element.hasAttr("onclick")) {
                element.removeAttr("onclick");
            }
        }
    }

    // protected void updateImg(int aid, Document document) throws Exception {
    // Elements elements = document.getElementsByTag(IMAGE_NODE);
    //
    // for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
    // Element element = (Element) iterator.next();
    // // 如果是本站的图，加上域名
    // String targetSrc = getImgPath(element.attr("src"));
    // // 获得文件名
    // String fileName = getFileNameFromSrc(targetSrc);
    //
    // int targetWidth = getImageCache(aid, targetSrc, fileName);
    //
    // String abstractPath = "/marticle/images/" + aid + "/" + fileName;
    // element.attr("src", abstractPath);
    //
    // // 抓取图片，放到本站的位置
    // if (element.hasAttr("width")) {
    // try {
    // if (targetWidth > 240)
    // element.attr("width", "100%");
    // } catch (Exception e) {
    // element.attr("width", "100%");
    // }
    // } else {
    // element.attr("width", "100%");
    // }
    //
    // if (element.hasAttr("height"))
    // element.removeAttr("height");
    //
    // // 更改onclick
    // if (element.hasAttr("onclick")) {
    // element.removeAttr("onclick");
    // }
    // }
    // }

    @Deprecated
    protected int getImageCache(int aid, String targetSrc, String fileName) throws Exception, IOException {
        File tempFile = new File(ConfigContainer.getCacheFolder() + "/tmpImages/" + fileName);
        if (!tempFile.getParentFile().exists())
            tempFile.getParentFile().mkdirs();

        if (!tempFile.exists())
            urlUtil.saveFileFromNet(targetSrc, tempFile);

        // 目标的保存路径
        String targetPath = systemUtil.getWebRootPath() + "images/" + aid + "/" + fileName;
        File targetFile = new File(targetPath);
        int targetWidth = imageUtil.getWidth(tempFile);
        if (tempFile.exists() && !targetFile.exists()) {
            if (!targetFile.getParentFile().exists())
                targetFile.getParentFile().mkdirs();

            if (joymePointArchiveService.isCompressImages(null, aid)) {
                if (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg")) {
                    if (targetWidth > 360) {
                        imageUtil.ChangeJPG(tempFile, targetFile, 360, 0.75f);
                        targetWidth = 360;
                    } else {
                        imageUtil.ChangeJPG(tempFile, targetFile, targetWidth, 0.75f);
                    }
                } else if (fileName.toLowerCase().endsWith("png")) {
                    imageUtil.compressPNG(tempFile, targetFile);
                } else {
                    FileUtils.copyFile(tempFile, targetFile);
                }
            } else {
                FileUtils.copyFile(tempFile, targetFile);
            }
            tempFile.delete();
        }

        times++;
        if (times % 10 == 0)
            System.gc();
        return targetWidth;
    }

    protected String getFileNameFromSrc(String targetSrc) throws MalformedURLException {
        URL url = new URL(targetSrc);
        String path = url.getPath();
        int position = path.lastIndexOf("/");
        String fileName = path;
        if (position > 0) {
            fileName = path.substring(position + 1, path.length());
        }
        return fileName;
    }

    /**
     * 更新视频
     *
     * @param document
     */
    protected void updateEmbed(Document document) {
        Elements elements = document.getElementsByTag("embed");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            // if (element.hasAttr("width"))
            element.attr("width", "100%");
            if (element.hasAttr("height"))
                element.removeAttr("height");

            if (element.hasAttr("src")) {
                String value = element.attr("src");
                String id = this.getMobileId(value);

                // Element element2 = element.appendElement("video");
                String transferURL = this.getMovieUrl(id);
                if (transferURL != null) {
                    element.tagName("video");
                    element.attr("src", transferURL);
                    element.attr("width", "100%");
                    element.attr("controls", "controls");
                }
                // element.remove();

                // element.appendElement("br");
                // Element element2 = element.appendElement("a");
                // element2.attr("href", getMobile(value));
                // element2.attr("target", "_blank");
                // element2.text("不能观看请点击这里");
            }
        }
    }

    /**
     * 通过接口访问，每次都通过
     *
     * @param id
     * @return
     */
    protected String getMovieUrl(String id) {
        return "/marticle/movie/getmovie.do?id=" + id;
    }

    // protected String getImgPath(String link) {
    // String retPath = "";
    // String compressPath = "";
    // if (link.startsWith("/article")) {
    // if (link.startsWith("/article/uploads")) {
    // String temp = link.replaceAll("/article/uploads", "");
    // compressPath = getDomain() + "/article/upapp" + temp;
    // if (URLUtil.isURLValid(compressPath, 5000)) {
    // retPath = compressPath;
    // } else {
    // retPath = getDomain() + link;
    // }
    // } else {
    // retPath = getDomain() + link;
    // }
    // } else {
    // retPath = link;
    // }
    //
    // if (link.startsWith(OLD_IMAGE_FLAG)) {
    // retPath = link.replaceAll(OLD_IMAGE_FLAG, NEW_IMAGE_FLAG);
    // }
    //
    // return retPath;
    // // return link.startsWith("/article") ? getDomain() + link : link;
    // }

    protected String getImgPath(String link) {
        if (link.startsWith("http://www.joyme.com")) {
            return link.replace("http://www.joyme.com", "http://article.joyme.com");
        }
        if (link.startsWith("/article")) {
            return getDomain() + link;
        } else {
            return link;
        }
    }

    /**
     * 获得域名
     *
     * @return
     */
    protected String getDomain() {
        if (domain == null) {
            try {
                domain = ConfigContainer.getConfigValue("cms_domain");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return domain;
    }

    /**
     * 删除着迷消息
     *
     * @param document
     */
    protected void removeJoymeInfoLink(Document document) {
        Elements elements = document.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.attr("href").equalsIgnoreCase("http://www.joyme.com/article/yxgl/")) {
                element.remove();
            }
        }
    }

    protected String getMobileId(String value) {
        if (value.indexOf("youku") >= 0 && (value.endsWith(".swf") || value.endsWith(".html"))) {
            if (value.indexOf("v_show") >= 0) {
                return value;
            }

            int position = value.lastIndexOf("/");
            String id = "";
            if (position > 0) {
                id = value.substring(0, position);
                int position1 = id.lastIndexOf("/");
                if (position1 > 0) {
                    id = id.substring(position1 + 1, id.length());
                }

                return id;
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * 得到视频的链接
     *
     * @param value
     * @return
     */
    @Deprecated
    protected String getMobile(String value) {
        if (value.indexOf("youku") >= 0 && (value.endsWith(".swf") || value.endsWith(".html"))) {
            if (value.indexOf("v_show") >= 0) {
                return value;
            }

            int position = value.lastIndexOf("/");
            String id = "";
            if (position > 0) {
                id = value.substring(0, position);
                int position1 = id.lastIndexOf("/");
                if (position1 > 0) {
                    id = id.substring(position1 + 1, id.length());
                }

                String retString = "http://v.youku.com/v_show/id_" + id + ".html";
                return retString;
            }

            return value;
        } else {
            return value;
        }
    }

    /**
     * 获得下载模块的信息
     *
     * @param id
     * @return
     * @throws IOException
     */
    protected GameBean getGameBean(String id) throws IOException {
        String url = ConfigContainer.getGameDownloadUrl();
        url = MessageFormat.format(url, id);

        GameBean bean = gameService.getGame(url);
        return bean;
    }

    protected String getSpecName(DedeArchives dedeArchives) {
        String specName = "";

        Connection conn = null;
        try {
            conn = dao.getConnection();
            JoymePointArchive joymePointArchive = joymePointArchiveService.queryJoymePointArchivebyArchiveId(conn, dedeArchives.getId());
            if (joymePointArchive != null) {
                JoymeSpec spec = joymeSpecService.queryJoymeSpecByFilePath(conn, joymePointArchive.getSpecFilePath());

                if (spec != null) {
                    specName = spec.getSpecName();
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.closeConnection(conn);
        }

        return specName;
    }

    protected int getHeightOfImage(Element element) {
        int height = 0;
        if (element.hasAttr("height")) {
            try {
                height = Integer.parseInt(element.attr("height"));
            } catch (Exception e) {
                LogService.errorSystemLog("Can't get the height of the image", e);
            }
        }
        return height <= 0 ? 320 : height;
    }

    protected int getWidthOfImage(Element element) {
        int width = 0;
        if (element.hasAttr("width")) {
            try {
                width = Integer.parseInt(element.attr("width"));
            } catch (Exception e) {
                LogService.errorSystemLog("Can't get the width of the image", e);
            }
        }
        return width <= 0 ? 480 : width;
    }
}
