package com.enjoyf.mcms.factory;

import com.enjoyf.mcms.bean.temp.NavigateBean;
import com.enjoyf.mcms.bean.temp.PageBean;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-28 下午5:33
 * Description:
 */
public interface IArchiveListFactory {

    String getPath(String channel, int archivelistId, int pageNum) throws Exception;

    String makeArchiveListHtml(PageBean pageBean, int archiveId, int pageNum, String cachePath, String channel) throws Exception;
}
