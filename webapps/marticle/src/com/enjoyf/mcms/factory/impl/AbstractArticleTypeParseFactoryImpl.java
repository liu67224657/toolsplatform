package com.enjoyf.mcms.factory.impl;


import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.mcms.factory.IArticleTypeParseFactory;
import com.enjoyf.mcms.util.ArchiveUtil;
import com.enjoyf.mcms.util.MarticleUtil;
import com.enjoyf.util.FileUtil;
import com.enjoyf.util.StringUtil;

import java.sql.Date;
import java.util.Calendar;

public abstract class AbstractArticleTypeParseFactoryImpl implements IArticleTypeParseFactory {

    //articleType
    public final static String CONTENT_PATH = "\\{joyme:contextPath/\\}";
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

    protected ArchiveUtil archiveUtil=new ArchiveUtil();


    protected String getArticleTypeTemplate(String channel) {
        String template = ConfigContainer.getArticleTypeTemplate(channel);
        return (String) TemplateContainer.articleTypeTemplateMap.get(template);
    }

    @Override
    public String getArticleTypePath(String htmlFile, String channel) throws Exception {
        return ConfigContainer.getCacheFolder() + "/articletype/" + channel + "/" + htmlFile + ".html";
    }

    @Override
    public String makeArticleTypeHtml(DedeArchivePageBean dedeArchivePageBean, DedeArctype arctype, String channel, String htmlFile, String localPath,int pageNum) throws Exception {

        // 生成专区的信息
        String retString = getArticleTypeHtml(dedeArchivePageBean, arctype, channel, localPath,pageNum);

        String path = getArticleTypePath(htmlFile, channel);
        FileUtil.writeFile(path, retString);
        return retString;
    }

    protected String getArticleTypeHtml(DedeArchivePageBean articleTypePageBean, DedeArctype arctype, String channel, String localPath, int pageNum) {
        // 获得渠道
        String htmlTemplate = this.getArticleTypeTemplate(channel);

        htmlTemplate = MarticleUtil.addRobotsMetaHead(htmlTemplate);

        htmlTemplate = htmlTemplate.replaceAll(CONTENT_PATH, localPath);
        htmlTemplate = htmlTemplate.replaceAll(LIST_NAME_REPLACE_STRING, arctype.getTypename() != null ? arctype.getTypename() : "首页");

        //
        String loopField = StringUtil.getStringBetweenFlag(htmlTemplate, BEGIN_ARTICLE_TYPE_LOOP_FIELD, END_ARTICLE_TYPE_LOOP_FIELD);

        StringBuffer sb = getArticleTypeBody(articleTypePageBean, arctype, loopField);

        String[] headEnd = StringUtil.getHeadAndEndString(htmlTemplate, BEGIN_ARTICLE_TYPE_LOOP_FIELD, END_ARTICLE_TYPE_LOOP_FIELD);
        String retString = StringUtil.getReplaceString(headEnd, sb.toString());
        //

        String lastPage = (pageNum >= 2) ? getTurePageLink(arctype.getId(), pageNum, false) : "";
        retString = retString.replaceAll(BEGIN_ARTICLE_TYPE_LASTPAGE_FIELD, lastPage);

        String nextPage = articleTypePageBean.isHasNextPage() ? getTurePageLink(arctype.getId(), pageNum, true) : "";
        retString = retString.replaceAll(END_ARTICLE_TYPE_NEXTPAGE_FIELD, nextPage);
        return retString;
    }

    private String getTurePageLink(int arcTypeId, int pageNum, boolean isNextPage) {
        String href = "./list_" + arcTypeId + "_" + (isNextPage ? (pageNum + 1) : (pageNum - 1)) + ".html";
        String title = isNextPage ? "下一页" : "上一页";
        return "<a href=\"" + href + "\">" + title + "</a>";
    }

    protected StringBuffer getArticleTypeBody(DedeArchivePageBean articleTypeBean, DedeArctype arctype, String loopField) {
        StringBuffer sb = new StringBuffer();
        for (Object o : articleTypeBean.getResultList()) {
            DedeArchives archives = (DedeArchives) o;
            String namerule = getUrlByRule(archives, arctype, "..");

            String temp = loopField.replaceAll(ARCHIVE_LINK_REPLACE_STRING, namerule);
            temp = temp.replaceAll(ARCHIVE_TITLE_REPLACE_STRING, archives.getTitle());
            sb.append(temp);
        }
        return sb;
    }

    protected String getUrlByRule(DedeArchives archive, DedeArctype arctype, String cmsPathReplaceStr) {
        String typedir = arctype.getTypedir();
        typedir = typedir.replaceAll("\\{cmspath\\}", cmsPathReplaceStr);

        String namerule = arctype.getNamerule();

        Date date = new Date((long) archive.getPubdate() * 1000l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int aid = arctype.getId();

        namerule = namerule.replaceAll(TYPE_DIR_REPLACE_FLAG, typedir);
        namerule = namerule.replaceAll(YEAR_REPLACE_FLAG, year + "");
        namerule = namerule.replaceAll(MONTH_REPLACE_FLAG, (month < 9) ? "0" + (month + 1) : (month + 1) + "");
        namerule = namerule.replaceAll(DATE_REPLACE_FLAG, String.valueOf(day));
        namerule = namerule.replaceAll(AID_REPLACE_FLAG, aid + "");

        return namerule;
    }


}
