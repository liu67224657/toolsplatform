package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.DedeCategory;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.ICategoryFactory;
import com.enjoyf.mcms.util.ArchiveUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/6/20 17:41
 * Description:
 */
public class AbstractCategoryFactory implements ICategoryFactory {

    protected final static String TYPE_DIR_REPLACE_FLAG = "\\{typedir\\}";
    protected final static String YEAR_REPLACE_FLAG = "\\{Y\\}";
    protected final static String MONTH_REPLACE_FLAG = "\\{M\\}";
    protected final static String DATE_REPLACE_FLAG = "\\{D\\}";
    protected final static String AID_REPLACE_FLAG = "\\{aid\\}";

    protected ArchiveUtil archiveUtil=new ArchiveUtil();

    public String getArticleListPath(String channel, int platform, int categoryId, int pageNum) throws Exception {
        return ConfigContainer.getCacheFolder() + "/categoryarticles/" + channel + "/" + platform + "_" + categoryId + "_" + pageNum + ".html";
    }

    @Override
    public String makeArticleListHtml(DedeArchivePageBean pageBean, DedeCategory category, int platform, int categoryId, int pageNum, String cachePath, String channel) throws Exception {
        String retString = praseArticleListHtml(pageBean, category, channel);

        String cacheFile = this.getArticleListPath(channel, platform, categoryId, pageNum);
        FileUtils.writeStringToFile(new File(cacheFile), retString, "utf-8");
        return retString;
    }

    protected String praseArticleListHtml(DedeArchivePageBean pageBean, DedeCategory category, String channel) {
        return null;
    }

    public String getCategoryTagsPath(String channel) throws Exception {
        return ConfigContainer.getCacheFolder() + "/categorytags/" + channel + "/index.html";
    }

    public String makeCategoryTagsHtml(List<DedeCategory> list, String channel, String path) throws Exception {

        String retString = praseHtml(list, channel);

        String cacheFile = this.getCategoryTagsPath(channel);
        FileUtils.writeStringToFile(new File(cacheFile), retString, "utf-8");
        return retString;
    }

    protected String praseHtml(List<DedeCategory> list, String channel) {
        return null;
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
        int aid = archive.getId();

        namerule = namerule.replaceAll(TYPE_DIR_REPLACE_FLAG, typedir);
        namerule = namerule.replaceAll(YEAR_REPLACE_FLAG, year + "");
        namerule = namerule.replaceAll(MONTH_REPLACE_FLAG, (month <= 9) ? "0" + month : month + "");
        namerule = namerule.replaceAll(DATE_REPLACE_FLAG, (day <= 9) ? "0" + day : day + "");
        namerule = namerule.replaceAll(AID_REPLACE_FLAG, aid + "");

        return namerule;
    }
}
