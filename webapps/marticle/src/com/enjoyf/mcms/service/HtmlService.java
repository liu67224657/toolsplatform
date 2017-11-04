package com.enjoyf.mcms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymePoint;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.bean.temp.ArchiveBodyBean;
import com.enjoyf.mcms.bean.temp.PointArchiveListBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.mcms.filter.URLFilter;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.mcms.util.ParseHtml;
import com.enjoyf.util.FileUtil;
import com.enjoyf.util.StringUtil;

public class HtmlService {
    public final static String BEGIN_GAME_LOOP_FIELD = "{joyme:game-loop}";
    public final static String END_GAME_LOOP_FIELD = "{/joyme:game-loop}";
    public final static String BEGIN_ARTICLE_LOOP_FIELD = "{joyme:article-loop}";
    public final static String END_ARTICLE_LOOP_FIELD = "{/joyme:article-loop}";
    public final static String BEGIN_CHECK_DOWNLOAD_LOOP_FIELD = "{joyme:check-download}";
    public final static String END_CHECK_DOWNLOAD_LOOP_FIELD = "{/joyme:check-download}";
    public final static String BEGIN_GAME_POINT = "{joyme:game-point}";
    public final static String END_GANE_POINT = "{/joyme:game-point}";

    public final static String POINT_NAME = "\\{joyme:point-name /\\}";
    public final static String POINT_MORE = "\\{joyme:point-more /\\}";

    public final static String ARTICLE_INFO = "\\{joyme:artcile-info /\\}";

    public final static String CONTENT_PATH = "\\{joyme:contextPath/\\}";
    public final static String ARTICLE_TITLE = "\\{joyme:article-title/\\}";
    public final static String ARTICLE_PUBLISH_USER = "\\{joyme:article-publishuser/\\}";
    public final static String ARTICLE_PUBLISH_DATE = "\\{joyme:article-publishtime/\\}";
    public final static String ARTICLE_BODY = "\\{joyme:article-body/\\}";

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

    private final static String DEFAULT_FOLDER = "default";
    private final static String DEFAULT_TIME = "2013年1月1日";

    public String makeSpecHtml(JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception {
        // 获得渠道
        String htmlTemplate = this.getGameHtmlTemplate(channel);
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
        // for (Iterator iterator = tempSpecList.iterator();
        // iterator.hasNext();) {
        // PointArchiveListBean bean = (PointArchiveListBean) iterator.next();
        //            
        // }

        // for (Iterator iterator = specList.iterator(); iterator.hasNext();) {
        // PointArchiveListBean bean = (PointArchiveListBean) iterator.next();
        // JoymePoint point = bean.getPoint();
        // htmlFile = point.getHtmlFile();
        // List archiveList = bean.getArchiveList();
        //
        // String pointHtml = this.getPointHtml(loopField, point, archiveList);
        // sb.append(pointHtml);
        // }

        String[] headEnd = StringUtil.getHeadAndEndString(htmlTemplate, BEGIN_GAME_LOOP_FIELD, END_GAME_LOOP_FIELD);
        String retString = this.getReplaceString(headEnd, sb.toString());
        // System.out.println(retString);

        String path = getGameCachePath(htmlFile, channel);
        FileUtil.writeFile(path, retString);
        return retString;
    }

    private String getHtmlFile(List specList, String htmlFile) {
        if (specList.size() > 0) {
            PointArchiveListBean bean = (PointArchiveListBean) specList.get(0);
            htmlFile = bean.getPoint().getHtmlFile();
        }
        return htmlFile;
    }

    private StringBuffer getGamePointHtml(List specList, String loopField) {
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

    /**
     * 获得专题缓存的地址
     * 
     * @param htmlFile
     * @return
     * @throws IOException
     */
    public String getGameCachePath(String htmlFile, HttpServletRequest request) throws Exception {
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

    /**
     * 获得一个节点的html
     * 
     * @param loopField
     * @param point
     * @param archiveList
     * @return
     */
    private String getPointHtml(String loopField, JoymePoint point, List archiveList) {
        // 修改节点的名称
        String pointHtml = loopField;

        pointHtml = pointHtml.replaceAll(POINT_NAME, point.getSpecPointName());
        String moreLink = "../" + URLFilter.ARTICLE_LIST_FLAG + "/" + point.getSpecPointId() + "_1.html";
        pointHtml = pointHtml.replaceAll(POINT_MORE, "<a href=\"" + moreLink + "\">更多</a>");

        // 循环出文章列表
        String articleLoopFiled = StringUtil.getStringBetweenFlag(pointHtml, BEGIN_ARTICLE_LOOP_FIELD, END_ARTICLE_LOOP_FIELD);
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = archiveList.iterator(); iterator.hasNext();) {
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
    private String getReplaceString(String[] headEnd, String str) {
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
    private String getArchiveListString(String articleLoopFiled, JoymePointArchive archive) {
        StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"../").append(archive.getHtmlPath()).append("/").append(archive.getHtmlFile());
        sb.append(" \">");
        sb.append(archive.getTitle());
        sb.append("</a>");
        articleLoopFiled = articleLoopFiled.replaceAll(ARTICLE_INFO, sb.toString());
        return articleLoopFiled;
    }

    private String getGameHtmlTemplate(String channel) {
        String template = ConfigContainer.getGameTemplate(channel);
        return (String) TemplateContainer.gameTemplateMap.get(template);
    }

    public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
            String localPath) throws Exception {
        String htmlTemplate = getArchiveTemplate(channel);
        htmlTemplate = htmlTemplate.replaceAll(CONTENT_PATH, localPath);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_TITLE, dedeArchives == null ? "正文" : dedeArchives.getTitle());

        String datestr = dedeArchives == null ? DEFAULT_TIME : DateUtil.convert2String(Long.parseLong(dedeArchives.getPubdate() + "000"),
                "yyyy年MM月dd日");
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_PUBLISH_DATE, datestr);
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_PUBLISH_USER, "着迷网");
        htmlTemplate = htmlTemplate.replaceAll(ARTICLE_BODY, ParseHtml.getNoStyleHtml(dedeAddonarticle.getBody()));

        // 保存html
        String path = getArchiveCachePath(filePath, fileName, channel);
        FileUtil.writeFile(path, htmlTemplate);

        return htmlTemplate;
    }

    /**
     * 输出子页的json串
     * 
     * @param dedeArchives
     * @param dedeAddonarticle
     * @param filePath
     * @param fileName
     * @param channel
     * @param localPath
     * @return
     * @throws JSONException
     */
    public String makeJsonArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
            String localPath) throws Exception {
        StringBuffer sb = new StringBuffer();
//        sb.append("<meta charset=\"utf-8\" />");
        
        JSONObject object = new JSONObject();

        object.put("rs", 1);
        // 标题
        object.put("title", dedeArchives == null ? "正文" : dedeArchives.getTitle());
        //日期
        object.put("date", dedeArchives == null ? DEFAULT_TIME : DateUtil.convert2String(Long.parseLong(dedeArchives.getPubdate() + "000"), "yyyy年MM月dd日"));
        //作者
        object.put("p_user", "着迷网");
        
        List noHtmlStyleBodyList = ParseHtml.getNoHtmlStyleList(dedeAddonarticle.getBody());
        object.put("body", this.getBodyArray(noHtmlStyleBodyList));
        
        // 保存html
        sb.append(object.toString());
        String path = getArchiveCachePath(filePath, fileName, channel);
        FileUtil.writeFile(path, sb.toString());
        
        return sb.toString();
    }
    
    private JSONArray getBodyArray(List noHtmlStyleBodyList) throws JSONException{
        JSONArray array = new JSONArray();
        for (Iterator iterator = noHtmlStyleBodyList.iterator(); iterator.hasNext();) {
            ArchiveBodyBean bean = (ArchiveBodyBean)iterator.next();
            JSONObject object = new JSONObject();
            object.put("type", bean.getType());
            object.put("content", bean.getContent());
            if(bean.getLink() != null){
                object.put("link", bean.getLink());
            }
            
            //图片类型放入高和宽
            if(bean.getType() == 2){
                object.put("width", bean.getWidth());
                object.put("height", bean.getHeight());
            }
            
            array.add(object.toString());
        }
        return array;
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
        String path = getArchiveCachePath(filePath, fileName, channel);
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
    public String getArchiveCachePath(String filePath, String fileName, String channel) throws Exception {
        String cacheFolder = ConfigContainer.getCacheFolder();
        String path = null;
        if (fileName.endsWith(".html")) {
            path = cacheFolder + "/archive/" + filePath + "/" + channel + "/" + fileName;
        } else {
            path = cacheFolder + "/archive/" + filePath + "/" + channel + "/" + fileName + ".html";
        }
        return path;
    }

    private String getArchiveTemplate(String channel) {
        String template = ConfigContainer.getArchiveTemplate(channel);
        return (String) TemplateContainer.archiveTemplateMap.get(template);
    }
}
