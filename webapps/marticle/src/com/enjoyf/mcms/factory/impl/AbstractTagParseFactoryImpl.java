package com.enjoyf.mcms.factory.impl;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedeTagArchive;
import com.enjoyf.mcms.bean.temp.ArchiveListBean;
import com.enjoyf.mcms.bean.temp.ArchivesBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.mcms.factory.ITagParseFactory;
import com.enjoyf.mcms.service.DedeTaglistService;
import com.enjoyf.mcms.util.ArchiveUtil;
import com.enjoyf.mcms.util.MarticleUtil;
import com.enjoyf.util.StringUtil;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTagParseFactoryImpl implements ITagParseFactory {
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    private static DedeTaglistService dedeTaglistService = new DedeTaglistService();
    protected final static String TAG_LIST_REPLACE_STRING = "\\{joyme:taglist-title/\\}";
    protected final static String BEGIN_GAME_LOOP_FIELD = "{joyme:taglist-loop}";
    protected final static String END_GAME_LOOP_FIELD = "{/joyme:taglist-loop}";
    protected final static String TAG_ARCHIVE_LINK_REPLACE_STRING = "\\{joyme:tagarchive-link/\\}";
    protected final static String TAG_ARCHIVE_TITLE_REPLACE_STRING = "\\{joyme:tagarchive-title/\\}";
    protected final static String TAG_LIST_LAST_PAGE_REPLACE_STRING = "\\{joyme:tag-list-last-page/\\}";
    protected final static String TAG_LIST_NEXT_PAGE_REPLACE_STRING = "\\{joyme:tag-list-next-page/\\}";

    protected final static String CMS_PATH_REPLACE_FLAG = "\\{cmspath\\}";
    protected final static String TYPE_DIR_REPLACE_FLAG = "\\{typedir\\}";
    protected final static String YEAR_REPLACE_FLAG = "\\{Y\\}";
    protected final static String MONTH_REPLACE_FLAG = "\\{M\\}";
    protected final static String DATE_REPLACE_FLAG = "\\{D\\}";
    protected final static String AID_REPLACE_FLAG = "\\{aid\\}";

    protected ArchiveUtil archiveUtil = new ArchiveUtil();


    @Override
    public String parseTagList(int tagId, int pageNum, String channel,String plat) throws Exception {
        Connection conn = dao.getConnection();
        try {
            ArchiveListBean archiveListBean = this.getTagArchiveList(conn, tagId, pageNum,plat);

            String tagName = dedeTaglistService.queryDedeTagName(conn, tagId);
            String retString = parseTagList(archiveListBean, tagId, tagName, channel, pageNum);
            String cacheFile = this.getTagCachePath(tagId, pageNum, channel, plat);
            FileUtils.writeStringToFile(new File(cacheFile), retString, "utf-8");
            return retString;
        } finally {
            dao.closeConnection(conn);
        }
    }

    protected String parseTagList(ArchiveListBean archiveListBean, int tagId, String tagName, String channel, int pageNum) {
        String tagTemplate = TemplateContainer.getTagTemplate(channel);
        tagTemplate = MarticleUtil.addRobotsMetaHead(tagTemplate);

        tagTemplate = tagTemplate.replaceAll(TAG_LIST_REPLACE_STRING, tagName);

        //
        String loopField = StringUtil.getStringBetweenFlag(tagTemplate, BEGIN_GAME_LOOP_FIELD, END_GAME_LOOP_FIELD);

        StringBuffer sb = getTagListBody(archiveListBean, loopField);

        String[] headEnd = StringUtil.getHeadAndEndString(tagTemplate, BEGIN_GAME_LOOP_FIELD, END_GAME_LOOP_FIELD);
        String retString = StringUtil.getReplaceString(headEnd, sb.toString());
        //

        String lastPage = (pageNum >= 2) ? getTurePageLink(tagId, pageNum, false) : "";
        retString = retString.replaceAll(TAG_LIST_LAST_PAGE_REPLACE_STRING, lastPage);

        String nextPage = archiveListBean.isHasNextPage() ? getTurePageLink(tagId, pageNum, true) : "";
        retString = retString.replaceAll(TAG_LIST_NEXT_PAGE_REPLACE_STRING, nextPage);
        return retString;
    }

    private String getTurePageLink(int tagId, int pageNum, boolean isNextPage) {
        String href = "./" + tagId + "_" + (isNextPage ? (pageNum + 1) : (pageNum - 1)) + ".html";
        String title = isNextPage ? "下一页" : "上一页";
        String retString = "<a href=\"" + href + "\">" + title + "</a>";
        return retString;
    }

    private StringBuffer getTagListBody(ArchiveListBean archiveListBean, String loopField) {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = archiveListBean.getArchiveList().iterator(); iterator.hasNext(); ) {
            DedeTagArchive tagArchive = (DedeTagArchive) iterator.next();
//            String link = "../" +
//            String typedir = tagArchive.getTypedir();
//            typedir = typedir.replaceAll("\\{cmspath\\}", "..");
//            String namerule = tagArchive.getNamerule();
//
//            Date date = new Date(tagArchive.getPublishdate() * 1000);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH) + 1;
//            int day = calendar.get(Calendar.DATE);
//            int aid = tagArchive.getId();
//
//            namerule = namerule.replaceAll(TYPE_DIR_REPLACE_FLAG, typedir);
//            namerule = namerule.replaceAll(YEAR_REPLACE_FLAG, year + "");
//            namerule = namerule.replaceAll(MONTH_REPLACE_FLAG, (month < 9) ? "0" + (month + 1) : (month + 1) + "");
//            namerule = namerule.replaceAll(DATE_REPLACE_FLAG, day + "");
//            namerule = namerule.replaceAll(AID_REPLACE_FLAG, aid + "");
            String namerule = getTagUrlByRule(tagArchive, "..");
            if (namerule == null)
                continue;

            String temp = loopField.replaceAll(TAG_ARCHIVE_LINK_REPLACE_STRING, namerule);
            temp = temp.replaceAll(TAG_ARCHIVE_TITLE_REPLACE_STRING, tagArchive.getTitle());
            sb.append(temp);
        }
        return sb;
    }

    protected String getTagUrlByRule(DedeTagArchive tagArchive, String cmsPathReplaceStr) {
        String typedir = tagArchive.getTypedir();
        if (typedir == null)
            return null;

        typedir = typedir.replaceAll("\\{cmspath\\}", cmsPathReplaceStr);

        String namerule = tagArchive.getNamerule();

        Date date = new Date(tagArchive.getPublishdate() * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int aid = tagArchive.getId();

        namerule = namerule.replaceAll(TYPE_DIR_REPLACE_FLAG, typedir);
        namerule = namerule.replaceAll(YEAR_REPLACE_FLAG, year + "");
        namerule = namerule.replaceAll(MONTH_REPLACE_FLAG, (month < 9) ? "0" + (month + 1) : (month + 1) + "");
        namerule = namerule.replaceAll(DATE_REPLACE_FLAG, (day < 10) ? "0" + String.valueOf(day) : String.valueOf(day));
        namerule = namerule.replaceAll(AID_REPLACE_FLAG, aid + "");

        return namerule;
    }

    private ArchiveListBean getTagArchiveList(Connection conn, int tid, int pageNum,String plat) throws JoymeDBException, JoymeServiceException {
        PageQueryBean bean = dedeTaglistService.queryDedeTaglist(conn, tid, pageNum,plat);
        List tagArchiveList = new ArrayList();
        if(bean.getResultList().size()>0){
            tagArchiveList = dedeTaglistService.queryTagArchiveList(conn, bean.getResultList());
    
            for (Object object : tagArchiveList) {
                DedeTagArchive tagArchive = (DedeTagArchive) object;
                //判断标签列表中的文章litpic是否为空如果为空 通过archive的channel找到文章对应的表看他的redirectUrl找到对应的文章重新set到list中
                if (StringUtil.isEmpty(tagArchive.getLitpic())) {
                    try {
                        ArchivesBean archivesBean = archiveUtil.getArchivesByRedirectUrl(null, tagArchive.getId(), tagArchive.getChannel());
                        if(archivesBean!=null && archivesBean.getArchives()!=null){
                            DedeArchives archives=archivesBean.getArchives();
                            tagArchive.setId(archives.getId());
                            tagArchive.setTitle(archives.getTitle());
                            tagArchive.setWriter(archives.getWriter());
                            tagArchive.setLitpic(archives.getLitpic());
                            tagArchive.setPublishdate(Long.parseLong(String.valueOf(archives.getPubdate())));
                            tagArchive.setDescription(archives.getDescription());
                            tagArchive.setChannel(archives.getChannel());
                        }
                    } catch (Exception e) {
                        LogService.errorSystemLog("get dedeAddonarticle put litpic error.");
                        continue;
                    }
                }
            }

        }

        ArchiveListBean archiveListBean = new ArchiveListBean();
        archiveListBean.setArchiveList(tagArchiveList);
        archiveListBean.setHasNextPage(bean.isHasNextPage());
        return archiveListBean;

    }

    @Override
    public String getTagCachePath(int tagId, int pageNum, String channel, String plat) throws Exception {
        String path = ConfigContainer.getCacheFolder() + "/tag/"+plat+"/" + channel + "/" + tagId + "_" + pageNum + ".html";
        return path;
    }
}
