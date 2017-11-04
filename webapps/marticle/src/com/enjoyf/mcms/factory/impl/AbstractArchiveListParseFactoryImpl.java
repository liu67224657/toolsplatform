package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.temp.NavigateBean;
import com.enjoyf.mcms.bean.temp.PageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.mcms.factory.IArchiveListFactory;
import com.enjoyf.mcms.util.MarticleUtil;
import com.enjoyf.util.StringUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Iterator;

public abstract class AbstractArchiveListParseFactoryImpl implements IArchiveListFactory {
    protected final static String ARCHIVELIST_TITLE_REPLACE_STRING = "\\{joyme:seo-title/\\}";
    protected final static String BEGIN_ARCHIVELIST_LOOP_FIELD = "{joyme:archivelist-loop}";
    protected final static String END_ARCHIVELIST_LOOP_FIELD = "{/joyme:archivelist-loop}";
    protected final static String TAG_ARCHIVE_LINK_REPLACE_STRING = "\\{joyme:archivelist-link/\\}";
    protected final static String TAG_ARCHIVE_TITLE_REPLACE_STRING = "\\{joyme:archivelist-title/\\}";
    protected final static String TAG_LIST_LAST_PAGE_REPLACE_STRING = "\\{joyme:archivelist-last-page/\\}";
    protected final static String TAG_LIST_NEXT_PAGE_REPLACE_STRING = "\\{joyme:archivelist-next-page/\\}";
    protected final static String TAG_ARCHIVE_NAV_REPLACE_STRING = "\\{joyme:archivelist-nav/\\}";


    @Override
    public String getPath(String channel, int archivelistId, int pageNum) throws Exception {
        String path = ConfigContainer.getCacheFolder() + "/archivelist/" + channel + "/" + archivelistId + "_" + pageNum + ".html";
        return path;
    }

    @Override
    public String makeArchiveListHtml(PageBean pageBean, int archiveListId, int pageNum, String cachePath, String channel) throws Exception {
        String retString = makeHtml(pageBean, archiveListId, pageNum, channel);
        FileUtils.writeStringToFile(new File(cachePath), retString, "utf-8");
        return retString;
    }

    private String makeHtml(PageBean pageBean, int archiveListId, int pageNum, String channel) {
        String templateKey = ConfigContainer.getArchiveListTemplate(channel);
        String template = (String) TemplateContainer.archiveListTemplateMap.get(templateKey);
        template = MarticleUtil.addRobotsMetaHead(template);

        template = template.replaceAll(ARCHIVELIST_TITLE_REPLACE_STRING, makeTitle(pageBean.getNavigate()));

        template=template.replaceAll(TAG_ARCHIVE_NAV_REPLACE_STRING,getNav(pageBean.getNavigate()));

        String loopField = StringUtil.getStringBetweenFlag(template, BEGIN_ARCHIVELIST_LOOP_FIELD, END_ARCHIVELIST_LOOP_FIELD);

        String articleList = makeList(pageBean, loopField);
        String[] headEnd = StringUtil.getHeadAndEndString(template, BEGIN_ARCHIVELIST_LOOP_FIELD, END_ARCHIVELIST_LOOP_FIELD);
        String retString = StringUtil.getReplaceString(headEnd, articleList);
        //

        String lastPage = (pageNum >= 2) ? getTurePageLink(archiveListId, pageNum, false) : "";
        retString = retString.replaceAll(TAG_LIST_LAST_PAGE_REPLACE_STRING, lastPage);

        String nextPage = pageBean.isHasNextPage() ? getTurePageLink(archiveListId, pageNum, true) : "";
        retString = retString.replaceAll(TAG_LIST_NEXT_PAGE_REPLACE_STRING, nextPage);

        return retString;
    }

    private String makeTitle(NavigateBean bean) {
        StringBuffer sb = new StringBuffer();

        if (!StringUtil.isEmpty(bean.getLevel1Str()) || !StringUtil.isEmpty(bean.getLevel2Str())) {
            if (!StringUtil.isEmpty(bean.getLevel1Str())) {
                sb.append(bean.getLevel1Str() + "-");
            }
            if (!StringUtil.isEmpty(bean.getLevel2Str())) {
                sb.append(bean.getLevel2Str());
            }
        } else {
            sb.append("更多");
        }
        return sb.toString();
    }

    private String makeList(PageBean bean, String loopField) {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = bean.getRetList().iterator(); iterator.hasNext(); ) {
            JoymePointArchive pointArchive = (JoymePointArchive) iterator.next();
            String namerule = getLink(pointArchive, "..");

            String temp = loopField.replaceAll(TAG_ARCHIVE_LINK_REPLACE_STRING, namerule);
            temp = temp.replaceAll(TAG_ARCHIVE_TITLE_REPLACE_STRING, pointArchive.getTitle());
            sb.append(temp);
        }
        return sb.toString();
    }

    protected String getLink(JoymePointArchive item, String prefix) {
        return prefix + "/" + item.getHtmlPath() + "/" + item.getHtmlFile();
    }

    private String getTurePageLink(int archiveListId, int pageNum, boolean isNextPage) {
        String href = "./" + archiveListId + "_" + (isNextPage ? (pageNum + 1) : (pageNum - 1)) + ".html";
        String title = isNextPage ? "下一页" : "上一页";
        String retString = "<a href=\"" + href + "\">" + title + "</a>";
        return retString;
    }

    private String getNav(NavigateBean bean) {
        StringBuffer sb = new StringBuffer();
        if (!StringUtil.isEmpty(bean.getLevel1Str()) || !StringUtil.isEmpty(bean.getLevel2Str())) {
            if (!StringUtil.isEmpty(bean.getLevel1Str())) {
                sb.append(" <a href=\"" + bean.getLevel1Add() + "\">" + bean.getLevel1Str() + "</a>&gt;");
            }
            if (!StringUtil.isEmpty(bean.getLevel2Str())) {
                sb.append(bean.getLevel2Str());
            }
        } else {
            sb.append("");
        }
        return sb.toString();
    }


}
