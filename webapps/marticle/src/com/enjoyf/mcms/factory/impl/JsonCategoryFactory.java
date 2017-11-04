package com.enjoyf.mcms.factory.impl;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.DedeCategory;
import com.enjoyf.mcms.bean.json.DedeCategoryArchiveJsonBean;
import com.enjoyf.mcms.bean.json.DedeCategoryJsonBean;
import com.enjoyf.mcms.bean.json.ParamTextJson;
import com.enjoyf.mcms.bean.temp.DedeArchivePageBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.service.DedeArctypeService;
import com.enjoyf.mcms.util.MarticleUtil;
import com.enjoyf.util.PicRgbUtil;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/6/20 17:41
 * Description:
 */
public class JsonCategoryFactory extends AbstractCategoryFactory {

	private DedeArctypeService arctypeService = new DedeArctypeService();

	protected String praseHtml(List<DedeCategory> list, String channel) {
		JSONObject object = new JSONObject();

		object.put("rs", 1);
		object.put("categories", makeCategoryJsonObject(list));

		return object.toString();
	}

	private Object makeCategoryJsonObject(List<DedeCategory> list) {
		List<DedeCategoryJsonBean> jsonBeanList = new ArrayList<DedeCategoryJsonBean>();

		for (DedeCategory category : list) {
			if (category.getTypeArticle() == MarticleUtil.WEB_VIEW) {
				continue;
			}
			DedeCategoryJsonBean bean = new DedeCategoryJsonBean();
			bean.setId(category.getId());
			bean.setName(category.getTypeName());
			bean.setColor(category.getTypeColor());
			jsonBeanList.add(bean);
		}

		return jsonBeanList;
	}

	@Override
	protected String praseArticleListHtml(DedeArchivePageBean pageBean, DedeCategory category, String channel) {
		JSONObject object = new JSONObject();

		object.put("rs", 1);
		object.put("list", makeJsonObj(pageBean.getResultList(), channel));
		object.put("hasnextpage", pageBean.isHasNextPage());

		return object.toString();

	}

	private List<DedeCategoryArchiveJsonBean> makeJsonObj(List<DedeArchives> archiveList, String channel) {
		List<DedeCategoryArchiveJsonBean> returnObj = new ArrayList<DedeCategoryArchiveJsonBean>();

		for (DedeArchives archive : archiveList) {
			try {
				DedeArctype arctype = arctypeService.queryDedeArctype(null, archive.getTypeid());

				//webview跳转,之前的版本不显示
				if (archive.getFlag() != null) {
					String flag = StringUtil.isEmpty(archive.getFlag().toString()) ? "" : archive.getFlag().toString();
					if (flag.contains("j")) {
						continue;
					}
				}

				String archivesLink = getUrlByRule(archive, arctype, ConfigContainer.getMarticleDomain() + "/" + channel);

				DedeCategoryArchiveJsonBean archiveBean = new DedeCategoryArchiveJsonBean();
				archiveBean.setAid(archive.getId());
				if (!StringUtil.isEmpty(archive.getLitpic())) {
					if (archive.getLitpic().startsWith("http://")) {
						archiveBean.setPic(archive.getLitpic());
					} else {
						archiveBean.setPic(ConfigContainer.getCMSDomain() + archive.getLitpic());
					}
				}
				archiveBean.setDate(archive.getPubdate() * 1000L);
				archiveBean.setTitle(archive.getTitle());
				archiveBean.setUrl(archiveUtil.formatUrl(archivesLink));
				archiveBean.setAuthor(archive.getWriter());
				archiveBean.setDesc(archive.getDescription());

				if (!StringUtil.isEmpty(archiveBean.getPic())) {
					ParamTextJson paramTextJson = new ParamTextJson();
					String pic_rgb = PicRgbUtil.getImagePixel(archiveBean.getPic());
					paramTextJson.setPicrgb(pic_rgb);
					archiveBean.setParam(paramTextJson);
				}

				returnObj.add(archiveBean);
			} catch (JoymeDBException e) {
				e.printStackTrace();
			} catch (JoymeServiceException e) {
				e.printStackTrace();
			}
		}

		return returnObj;
	}


}
