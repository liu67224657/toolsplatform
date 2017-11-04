package com.enjoyf.mcms.factory;

import com.enjoyf.mcms.bean.DedeCategory;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;

import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/6/20 17:38
 * Description:
 */
public interface ICategoryFactory {

    String getArticleListPath(String channel, int platform, int categoryId, int pageNum) throws Exception;

    String makeArticleListHtml(DedeArchivePageBean pageBean, DedeCategory category, int platform, int categoryId, int pageNum, String cachePath, String channel) throws Exception;

    String getCategoryTagsPath(String channel) throws Exception;

    String makeCategoryTagsHtml(List<DedeCategory> list, String channel, String path) throws Exception;
}
