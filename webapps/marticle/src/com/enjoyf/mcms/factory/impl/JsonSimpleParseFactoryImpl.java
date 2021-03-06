package com.enjoyf.mcms.factory.impl;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.json.JoymeImageJsonBean;
import com.enjoyf.mcms.bean.json.JoymePointArchiveJsonBean;
import com.enjoyf.mcms.bean.json.JoymePointJsonBean;
import com.enjoyf.mcms.bean.json.JoymeSpecJsonBean;
import com.enjoyf.mcms.bean.temp.ArchiveBodyBean;
import com.enjoyf.mcms.bean.temp.JoymeImageType;
import com.enjoyf.mcms.bean.temp.PointArchiveListBean;
import com.enjoyf.mcms.bean.temp.PointArchiveListJsonBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.filter.URLFilter;
import com.enjoyf.mcms.service.GameDownloadService;
import com.enjoyf.mcms.service.JoymeImageService;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.util.FileUtil;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonSimpleParseFactoryImpl extends AbstractParseFactoryImpl implements IHtmlParseFactory {
	private final static int TEXT_TYPE = 1;
	private final static int IMAGE_TYPE = 2;
	private final static int VEDIO_TYPE = 3;
	private final static int GAME_DOWNLOAD_TYPE = 4;

	private JoymeImageService joymeImageService = new JoymeImageService();

	@Override
	public String makeSpecHtml(Connection conn, JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception {

		String returnObj = generatorJson(spec, specList, channel);

		String htmlFile = "";
		htmlFile = getHtmlFile(specList, htmlFile);
		String path = getGameCachePath(htmlFile, channel);
		FileUtil.writeFile(path, returnObj);

		return returnObj;
	}

	protected String generatorJson(JoymeSpec spec, List specList, String channel) throws Exception {
		JSONObject object = new JSONObject();

		object.put("rs", 1);
		object.put("spec", makeJsonSpec(spec));
		object.put("image", makeImage(spec));

		List<PointArchiveListJsonBean> jsonPointList = new ArrayList<PointArchiveListJsonBean>();
		for (Object listBean : specList) {
			PointArchiveListBean bean = (PointArchiveListBean) listBean;
			jsonPointList.add(makeJsonPointArchiveList(bean, channel));
		}

		object.put("speclist", jsonPointList);

		return object.toString();
	}

	private JoymeSpecJsonBean makeJsonSpec(JoymeSpec spec) {

		JoymeSpecJsonBean returnObj = new JoymeSpecJsonBean();
		if (spec == null) {
			return returnObj;
		}

		returnObj.setType(spec.getSpecType());
		// returnObj.setAdvertise(spec.getSpecAdvertise());
		returnObj.setDownload(spec.getSpecDownloadUrl());
		returnObj.setCode(spec.getFilePath());
		returnObj.setLanguage(spec.getSpecLanguage());
		returnObj.setId(spec.getSpecId());
		returnObj.setName(spec.getSpecName());
		returnObj.setPic(spec.getSpecPicUrl());
		returnObj.setSize(spec.getSpecSize());
		returnObj.setVersion(spec.getSpecVersion());
		return returnObj;
	}

	private List<JoymeImageJsonBean> makeImage(JoymeSpec spec) throws JoymeServiceException, JoymeDBException {
		List<JoymeImageJsonBean> returnObj = new ArrayList<JoymeImageJsonBean>();
		if (spec == null) {
			return returnObj;
		}

		List<JoymeImage> imageList = joymeImageService.queryJoymeImage(null, spec.getSpecId(), JoymeImageType.TYPE_SMALL_CLIENT);
		if (imageList == null || imageList.size() == 0) {
			imageList = joymeImageService.queryJoymeImage(null, 0, JoymeImageType.TYPE_SMALL_CLIENT);
		}

		for (JoymeImage image : imageList) {
			JoymeImageJsonBean jsonBean = new JoymeImageJsonBean();
			jsonBean.setTitle(image.getTitle());
			jsonBean.setPicurl(image.getPic());
			jsonBean.setType(image.getRedirectType());
			jsonBean.setValue(image.getLink());

			returnObj.add(jsonBean);
		}
		return returnObj;
	}

	private PointArchiveListJsonBean makeJsonPointArchiveList(PointArchiveListBean bean, String channel) {
		PointArchiveListJsonBean returnObj = new PointArchiveListJsonBean();

		JoymePointJsonBean joymePointJsonBean = makeJsonJoymePoint(bean.getPoint(), channel);
		returnObj.setModule(joymePointJsonBean);
		returnObj.setList(makeJsonPointArchiveList(bean.getArchiveList(), channel));

		return returnObj;
	}

	private List<JoymePointArchiveJsonBean> makeJsonPointArchiveList(List<JoymePointArchive> archiveList, String channel) {
		List<JoymePointArchiveJsonBean> returnList = new ArrayList<JoymePointArchiveJsonBean>();
		int i = 0;
		for (JoymePointArchive archive : archiveList) {
			if (i >= 3) {
				continue;
			}
			JoymePointArchiveJsonBean jsonBean = new JoymePointArchiveJsonBean();

			jsonBean.setAid(archive.getArchiveId());
			jsonBean.setTitle(archive.getTitle());
			jsonBean.setDate(archive.getArchivePublishTime());
			jsonBean.setPicurl(archive.getImageUrl());
			jsonBean.setDesc(archive.getShortContent());
			jsonBean.setUrl("http://marticle.joyme.com/" + channel + "/" + archive.getHtmlPath() + "/" + archive.getHtmlFile());

			returnList.add(jsonBean);
			i++;
		}

		return returnList;
	}

	private JoymePointJsonBean makeJsonJoymePoint(JoymePoint point, String channel) {
		JoymePointJsonBean returnObj = new JoymePointJsonBean();

		returnObj.setId(point.getArchiveId());
		returnObj.setKeywords(point.getKeywords());
		returnObj.setName(point.getSpecPointName());

		String moreLink = ConfigContainer.getMarticleDomain() + "/" + channel + "/" + URLFilter.ARTICLE_LIST_FLAG + "/" + point.getSpecPointId()
				+ "_1.html";
		returnObj.setLink(moreLink);
		return returnObj;
	}

	public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
								  String localPath, String plat) throws Exception {
		StringBuffer sb = new StringBuffer();

		JSONObject object = new JSONObject();

		object.put("rs", 1);

		String specName = getSpecName(dedeArchives);
		object.put("spec_name", !StringUtil.isEmpty(specName) ? specName : "游戏攻略");
		// 标题
		object.put("title", dedeArchives == null ? "正文" : dedeArchives.getTitle());
		// 日期
		object.put("date", dedeArchives == null ? DEFAULT_TIME : DateUtil.convert2String(Long.parseLong(dedeArchives.getPubdate() + "000"), "yyyy年MM月dd日"));

		//long日期 默认20130101
		object.put("time", dedeArchives == null ? DEFAULT_TIME_LONG : Long.parseLong(dedeArchives.getPubdate() + "000"));
		// 作者
		object.put("p_user", "着迷网");

		List noHtmlStyleBodyList = this.getNoHtmlStyleList(dedeAddonarticle.getBody(), dedeArchives.getId(), channel);
		object.put("body", this.getBodyArray(noHtmlStyleBodyList));

		String clientpic = dedeArchives.getClientpic();
		if (StringUtil.isEmpty(clientpic) || !clientpic.startsWith("http://")) {
			clientpic = StringUtil.isEmpty(clientpic) ? "" : ConfigContainer.getCMSDomain() + clientpic;
		}
		object.put("clientpic", clientpic);
		object.put("clientnote", StringUtil.isEmpty(dedeArchives.getClientnote()) ? "" : dedeArchives.getClientnote());
		object.put("typename", StringUtil.isEmpty(dedeArchives.getTypeName()) ? "" : dedeArchives.getTypeName());
		object.put("typecolor", StringUtil.isEmpty(dedeArchives.getTypeColor()) ? "" : dedeArchives.getTypeColor());

		String litpic = dedeArchives.getLitpic();
		if (StringUtil.isEmpty(litpic) || !litpic.startsWith("http://")) {
			litpic = StringUtil.isEmpty(litpic) ? "" : ConfigContainer.getCMSDomain() + clientpic;
		}
		object.put("pic", litpic);
		object.put("desc", StringUtil.isEmpty(dedeArchives.getDescription()) ? "" : dedeArchives.getDescription());
		// 保存html
		sb.append(object.toString());
		String path = getArchiveCachePath(filePath, fileName, channel, plat);
		FileUtil.writeFile(path, sb.toString());

		return sb.toString();
	}

	private JSONArray getBodyArray(List noHtmlStyleBodyList) throws JSONException {
		JSONArray array = new JSONArray();
		for (Iterator iterator = noHtmlStyleBodyList.iterator(); iterator.hasNext(); ) {
			ArchiveBodyBean bean = (ArchiveBodyBean) iterator.next();
			JSONObject object = new JSONObject();
			if (bean.getType() == TEXT_TYPE || bean.getType() == IMAGE_TYPE || bean.getType() == VEDIO_TYPE) {
				object.put("type", bean.getType());
				object.put("content", bean.getContent());
				if (bean.getLink() != null) {
					object.put("link", bean.getLink());
					object.put("extLink", bean.getExtLink());
				}

				// 图片类型放入高和宽
				if (bean.getType() == IMAGE_TYPE) {
					object.put("width", bean.getWidth());
					object.put("height", bean.getHeight());
				}
			} else if (bean.getType() == GAME_DOWNLOAD_TYPE) {
				// 处理下载区域
				object.put("type", bean.getType());
				object.put("gameName", bean.getGameName());
				object.put("gameIcon", bean.getGameIcon());
				object.put("androidUrl", bean.getAndroidDownloadLink());
				object.put("ipadUrl", bean.getIpadDownloadLink());
				object.put("iphoneUrl", bean.getIphoneDownloadLink());
				object.put("wikiUrl", bean.getWikiUrl());
				object.put("cmsUrl", bean.getCmsUrl());
			}

			array.add(object.toString());
		}
		return array;
	}

	private List getNoHtmlStyleList(String html, int aid, String channel) throws Exception {
		// System.out.println(HtmlUtils.htmlUnescape(html));
		Document document = Jsoup.parse(html);
		Elements elements = document.getAllElements();
		List archiveBodyList = new ArrayList();
		for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
			Element element = (Element) iterator.next();
			ArchiveBodyBean archiveBodyBean = new ArchiveBodyBean();
			// 处理下载
			if (isGameDownload(element)) {
				String id = element.attr("id");
				if (id != null) {
					getGameDownload(archiveBodyBean, id, channel);
					archiveBodyList.add(archiveBodyBean);
				}
				continue;
			}

			// 处理p为文字类型
			if (isTextNode(element)) {
				// 如果是div，不能div中包div
				boolean canDisplay = isDivChild(element);

				if (canDisplay) {
					archiveBodyBean = new ArchiveBodyBean();
					String text = removeHtmlSymbol(element.text());
					if (text != null && !text.trim().equals("")) {
						archiveBodyBean.setContent(text);
						archiveBodyBean.setType(TEXT_TYPE);
						archiveBodyList.add(archiveBodyBean);
					}
				}
			}

			// 处理img为图片类型
			if (element.nodeName().equals(IMAGE_NODE)) {
				setImgElement(element, archiveBodyBean, aid);
				archiveBodyList.add(archiveBodyBean);
			}

			// 处理视频
			if (element.nodeName().equals(EMBED_NODE)) {
				archiveBodyBean.setContent("不能观看请点这里");
				// bean.setLink(getMobileId(element.attr("src")));
				archiveBodyBean.setLink(this.getM3U8(getMobileId(element.attr("src"))));
				archiveBodyBean.setExtLink(this.getYoukuExtLink(getMobileId(element.attr("src"))));
				archiveBodyBean.setType(VEDIO_TYPE);
				archiveBodyList.add(archiveBodyBean);
			}
		}

		return archiveBodyList;
	}

	/**
	 * 配置下载的模块
	 *
	 * @param archiveBodyBean
	 * @param id
	 * @throws java.io.IOException
	 */
	private void getGameDownload(ArchiveBodyBean archiveBodyBean, String id, String channel) throws IOException {
		GameBean bean = getGameBean(id);
		archiveBodyBean.setAndroidDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.ANDROID, channel));
		archiveBodyBean.setIphoneDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.IPHONE, channel));
		archiveBodyBean.setIpadDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.IPAD, channel));

		archiveBodyBean.setGameIcon(bean.getGameIcon());
		archiveBodyBean.setGameName(bean.getGameName());
		archiveBodyBean.setWikiUrl(bean.getWikiUrl());
		archiveBodyBean.setCmsUrl(bean.getCmsUrl());
		archiveBodyBean.setType(GAME_DOWNLOAD_TYPE);
	}

	private boolean isGameDownload(Element element) {
		if (element.nodeName().equals("div") && element.attr("name").equals("joyme-download"))
			return true;
		return false;
	}

	private boolean isDivChild(Element element) {
		if (element.nodeName().equals("div")) {
			List children = element.children();
			if (children != null && children.size() > 0)
				return false;
			else
				return true;
		} else {
			return true;
		}
	}

	private void setImgElement(Element element, ArchiveBodyBean bean, int aid) {
		bean.setContent(getImgPath(element.attr("src")));
		bean.setType(IMAGE_TYPE);
		bean.setHeight(getHeightOfImage(element));
		bean.setWidth(getWidthOfImage(element));
	}

	private boolean isTextNode(Element element) {
		if (element.nodeName().equals(LINE_NODE))
			return true;

		if (element.nodeName().equals(DIV_NODE))
			return true;

		return false;
	}

	/**
	 * 得到m3u8的地址
	 *
	 * @param mobileId
	 * @return
	 */
	private String getM3U8(String mobileId) {
		String link = "http://v.youku.com/player/getRealM3U8/vid/" + mobileId + "/type/mp4/v.m3u8";
		return link;
	}

	/**
	 * 获得优酷外跳地址
	 *
	 * @param mobileId
	 * @return
	 */
	private String getYoukuExtLink(String mobileId) {
		String link = "http://v.youku.com/v_show/id_" + mobileId + ".html";
		return link;
	}

	private static String removeHtmlSymbol(String html) {
		html = HtmlUtils.htmlUnescape(html);
		// html = UnicodeUtil.UnicodeToString(html);
		return html;
	}


}
