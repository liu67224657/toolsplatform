package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.json.DedeArchiveJsonBean;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.IArticleTypeParseFactory;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonArticleTypeParseFactoryImpl extends AbstractArticleTypeParseFactoryImpl implements IArticleTypeParseFactory {

	@Override
	public String makeArticleTypeHtml(DedeArchivePageBean dedeArchivePageBean, DedeArctype arctype, String channel, String htmlFile, String localPath, int pageNum) throws Exception {
		JSONObject object = new JSONObject();

		object.put("rs", 1);
		object.put("typeid", arctype.getId());
		object.put("name", arctype.getTypename());

		object.put("articlelist", makeJsonObj((List<DedeArchives>) dedeArchivePageBean.getResultList(), arctype, channel));
		object.put("hasnextpage", dedeArchivePageBean.isHasNextPage());

		return object.toString();
	}

	private List<DedeArchiveJsonBean> makeJsonObj(List<DedeArchives> archiveList, DedeArctype arctype, String channel) {
		List<DedeArchiveJsonBean> returnObj = new ArrayList<DedeArchiveJsonBean>();

		for (DedeArchives archive : archiveList) {
			String archivesLink = getUrlByRule(archive, arctype, ConfigContainer.getMarticleDomain() + "/" + channel);

			DedeArchiveJsonBean archiveBean = new DedeArchiveJsonBean();
			archiveBean.setAid(archive.getId());
			if (!StringUtil.isEmpty(archive.getLitpic())) {
				if (archive.getLitpic().startsWith("http://")) {
					archiveBean.setPicurl(archive.getLitpic());
				} else {
					archiveBean.setPicurl(ConfigContainer.getCMSDomain() + archive.getLitpic());
				}
			}
			archiveBean.setDate(archive.getPubdate() * 1000L);
			archiveBean.setTitle(archive.getTitle());
			archiveBean.setUrl(archiveUtil.formatUrl(archivesLink));
			archiveBean.setWriter(archive.getWriter());

			returnObj.add(archiveBean);
		}

		return returnObj;
	}

}
