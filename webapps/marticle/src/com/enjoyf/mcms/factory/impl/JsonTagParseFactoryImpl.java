package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.DedeTagArchive;
import com.enjoyf.mcms.bean.DedeTaglist;
import com.enjoyf.mcms.bean.json.DedeArchiveJsonBean;
import com.enjoyf.mcms.bean.json.JsonTagBean;
import com.enjoyf.mcms.bean.temp.ArchiveListBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.ITagParseFactory;
import com.enjoyf.mcms.service.DedeTaglistService;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonTagParseFactoryImpl extends AbstractTagParseFactoryImpl implements ITagParseFactory {

	private DedeTaglistService taglistService = new DedeTaglistService();


	@Override
	protected String parseTagList(ArchiveListBean archiveListBean, int tagId, String tagName, String channel, int pageNum) {

		JSONObject object = new JSONObject();

		object.put("rs", 1);
		object.put("tagid", tagId);
		object.put("tag", tagName);

		object.put("list", makeJsonObj((List<DedeTagArchive>) archiveListBean.getArchiveList(), channel));
		object.put("hasnextpage", archiveListBean.isHasNextPage());

		return object.toString();
	}

	private List<DedeArchiveJsonBean> makeJsonObj(List<DedeTagArchive> archiveList, String channel) {
		List<DedeArchiveJsonBean> returnObj = new ArrayList<DedeArchiveJsonBean>();

		for (DedeTagArchive tagArchive : archiveList) {
			String tagUrl = getTagUrlByRule(tagArchive, ConfigContainer.getMarticleDomain() + "/" + channel);

			DedeArchiveJsonBean tagBean = new DedeArchiveJsonBean();
			tagBean.setAid(tagArchive.getId());
			if (tagArchive.getLitpic().startsWith("http://")) {
				tagBean.setPicurl(tagArchive.getLitpic());
			} else {
				tagBean.setPicurl(ConfigContainer.getCMSDomain() + tagArchive.getLitpic());
			}
			tagBean.setDate(tagArchive.getPublishdate() * 1000L);
			tagBean.setTitle(tagArchive.getTitle());
			tagBean.setUrl(tagUrl);
			tagBean.setUrl(archiveUtil.formatUrl(tagUrl));
			tagBean.setWriter(tagArchive.getWriter());
			tagBean.setDesc(tagArchive.getDescription());

			try {
				List<DedeTaglist> tagBeanList = taglistService.queryDedeTagByAid(null, tagArchive.getId());
				List<JsonTagBean> jsonTagBeanList = new ArrayList<JsonTagBean>();
				for (DedeTaglist dedeTag : tagBeanList) {
					JsonTagBean jsonTagBean = new JsonTagBean();
					jsonTagBean.setTid(dedeTag.getTid());
					jsonTagBean.setTname(dedeTag.getTag());
					jsonTagBeanList.add(jsonTagBean);
				}
				tagBean.setTags(jsonTagBeanList);
			} catch (Exception e) {
				e.printStackTrace();
			}

			returnObj.add(tagBean);
		}

		return returnObj;
	}


}
