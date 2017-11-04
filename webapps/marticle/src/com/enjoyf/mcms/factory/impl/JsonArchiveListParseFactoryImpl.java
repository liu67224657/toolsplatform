package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.json.JoymeArchiveListJsonBean;
import com.enjoyf.mcms.bean.temp.PageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonArchiveListParseFactoryImpl extends AbstractArchiveListParseFactoryImpl {

    @Override
    public String makeArchiveListHtml(PageBean pageBean, int archiveListId, int pageNum, String cachePath, String channel) throws Exception {
        JSONObject object = new JSONObject();

        object.put("rs", 1);
        object.put("archivelistid", archiveListId);
        object.put("navlevel1", pageBean.getNavigate().getLevel1Str());
        object.put("navlevel2", pageBean.getNavigate().getLevel2Str());

        object.put("articlelist", makeJsonObj((List<JoymePointArchive>) pageBean.getRetList(), channel));
        object.put("hasnextpage", pageBean.isHasNextPage());

        FileUtils.writeStringToFile(new File(cachePath), object.toString(), "utf-8");
        return object.toString();

    }

    private List<JoymeArchiveListJsonBean> makeJsonObj(List<JoymePointArchive> archiveList, String channel) {
        List<JoymeArchiveListJsonBean> returnObj = new ArrayList<JoymeArchiveListJsonBean>();


        for (JoymePointArchive archiveBean : archiveList) {
            String link = getLink(archiveBean, ConfigContainer.getMarticleDomain() + "/" + channel);

            JoymeArchiveListJsonBean jsonBean = new JoymeArchiveListJsonBean();
            jsonBean.setId(archiveBean.getArchiveId());
            jsonBean.setDate(archiveBean.getArchivePublishTime());
            jsonBean.setTitle(archiveBean.getTitle());
            jsonBean.setUrl(link);

            returnObj.add(jsonBean);
        }

        return returnObj;
    }
}
