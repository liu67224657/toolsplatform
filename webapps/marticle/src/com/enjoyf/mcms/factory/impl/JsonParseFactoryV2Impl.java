package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.*;
import com.enjoyf.mcms.bean.temp.ArchiveBodyBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.service.GameDownloadService;
import com.enjoyf.mcms.service.JoymeImageService;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.mcms.util.MarticleUtil;
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

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-25
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class JsonParseFactoryV2Impl extends AbstractParseFactoryImpl implements IHtmlParseFactory {

	private final static int TEXT_TYPE = 1;
	private final static int IMAGE_TYPE = 2;
	private final static int VEDIO_TYPE = 3;
	private final static int GAME_DOWNLOAD_TYPE = 4;

	private final static int TEXT_TYPE_FIRST = 5;//导语
	private final static int TEXT_TYPE_LAST = 6;//结束语
	private final static int IMAGE_TYPE_SIMPLE = 7;//图片简单文字
	private final static int IMAGE_TYPE_DIFF = 8;//图片复杂文字
	private final static int TEXT_TYPE_REFERENCE = 9;//段落引用样式
	private final static int TEXT_TYPE_WEB_HTML = 10;//html5
	private final static int TEXT_TYPE_CENG_JI = 11;//段落层级


	private JoymeImageService joymeImageService = new JoymeImageService();

	@Override
	public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, CmsAddress cmsAddress, String filePath, String fileName, String channel, String localPath, String plat, String uri) throws Exception {
		StringBuffer sb = new StringBuffer();

		JSONObject object = new JSONObject();


		object.put("rs", 1);
		String title = dedeArchives == null ? "正文" : dedeArchives.getTitle();
		String specName = getSpecName(dedeArchives);
		object.put("spec_name", !StringUtil.isEmpty(specName) ? specName : "游戏攻略");

		// 标题
		object.put("title", title);
		// 日期
		object.put("date", dedeArchives == null ? DEFAULT_TIME : DateUtil.convert2String(Long.parseLong(dedeArchives.getPubdate() + "000"), "yyyy年MM月dd日"));
		//long日期 默认20130101
		object.put("time", dedeArchives == null ? DEFAULT_TIME_LONG : Long.parseLong(dedeArchives.getPubdate() + "000"));
		// 作者
		object.put("p_user", StringUtil.isEmpty(dedeArchives.getWriter()) ? "着迷网" : dedeArchives.getWriter());

		List noHtmlStyleBodyList = this.getNoHtmlStyleList(dedeAddonarticle.getBody(), dedeArchives.getId(), channel);
		object.put("body", this.getBodyArray(noHtmlStyleBodyList));

		//
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

		if (cmsAddress != null) {
			object.put("address", cmsAddress.getAddress());
			object.put("location", cmsAddress.getLocation());
		}

		object.put("relatednews", dedeArchives == null ? "" : this.getRelatedNews(dedeArchives.getRelatedNewsList()));
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
			if (bean.getType() == TEXT_TYPE || bean.getType() == IMAGE_TYPE || bean.getType() == VEDIO_TYPE ||
					bean.getType() == TEXT_TYPE_FIRST || bean.getType() == TEXT_TYPE_LAST || bean.getType() == TEXT_TYPE_REFERENCE
					|| bean.getType() == TEXT_TYPE_WEB_HTML || bean.getType() == TEXT_TYPE_CENG_JI) {
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
					if (!StringUtil.isEmpty(bean.getPictype())) {
						object.put("pictype", bean.getPictype());
					}
					if (!StringUtil.isEmpty(bean.getPicalt())) {
						object.put("picalt", bean.getPicalt());
					}
					if (!StringUtil.isEmpty(bean.getPicalttype()) && bean.getPicalttype().equals(MarticleUtil.IMG_ALT_TYPE_SIMPLE)) {
						object.put("picalttype", IMAGE_TYPE_SIMPLE);
					} else if (!StringUtil.isEmpty(bean.getPicalttype()) && bean.getPicalttype().equals(MarticleUtil.IMG_ALT_TYPE_DIFF)) {
						object.put("picalttype", IMAGE_TYPE_DIFF);
					}
				}
			} else if (bean.getType() == GAME_DOWNLOAD_TYPE) {
				// 处理下载区域
				object.put("type", bean.getType());
				object.put("gameName", bean.getGameName());
				object.put("gameIcon", bean.getGameIcon());
				object.put("androidUrl", bean.getAndroidDownloadLink());
				object.put("ipadUrl", bean.getIpadDownloadLink());
				object.put("iphoneUrl", bean.getIphoneDownloadLink());
				object.put("androidSize", bean.getAndroidDownloadSize());
				object.put("iphoneSize", bean.getIphoneDownloadSize());
				object.put("ipadSize", bean.getIpadDownloadSize());
				object.put("wikiUrl", bean.getWikiUrl());
				object.put("cmsUrl", bean.getCmsUrl());
				object.put("rate", bean.getRate());
			}

			array.add(object.toString());
		}
		return array;
	}

	private JSONArray getRelatedNews(List<DedeAddonrelevance> relatedNewsList) throws JSONException {
		JSONArray array = new JSONArray();
		for (int i = 0; i < relatedNewsList.size(); i++) {
			DedeAddonrelevance bean = relatedNewsList.get(i);
			JSONObject object = new JSONObject();
			object.put("title", bean.getTitle());
//			object.put("url", bean.getUrl());
			object.put("url", archiveUtil.formatUrl(bean.getUrl()));

			object.put("type", bean.getType());
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
			// 处理下载
			ArchiveBodyBean archiveBodyBean = new ArchiveBodyBean();
			if (isGameDownload(element)) {
				String id = element.attr("id");
				if (id != null) {
					archiveBodyBean = getGameDownload(archiveBodyBean, id, channel);
					if (archiveBodyBean == null)
						continue;

					archiveBodyList.add(archiveBodyBean);
				}
				continue;
			}

			// 处理p为文字类型
			if (isTextNode(element)) {
				// 删除子div
				removeChildDivElement(element);

				String id = element.attr("id");

				archiveBodyBean = new ArchiveBodyBean();
				String text = "";
				if (!StringUtil.isEmpty(id)) {
					text = MarticleUtil.replaceStylecss(element.html());
				} else {
					text = removeHtmlSymbol(element.text());
					text = MarticleUtil.replaceStylecss(text);
				}

				if (!StringUtil.isEmpty(id) && id.equals(MarticleUtil.ID_JOYME_DAOYU)) {
					archiveBodyBean.setContent(text);
					archiveBodyBean.setType(TEXT_TYPE_FIRST);
					archiveBodyList.add(archiveBodyBean);
				} else if (!StringUtil.isEmpty(id) && id.equals(MarticleUtil.ID_JOYME_JIESUYU)) {
					archiveBodyBean.setContent(text);
					archiveBodyBean.setType(TEXT_TYPE_LAST);
					archiveBodyList.add(archiveBodyBean);
				} else if (!StringUtil.isEmpty(id) && id.equals(MarticleUtil.ID_JOYME_DUANLUO)) {
					archiveBodyBean.setContent(text);
					archiveBodyBean.setType(TEXT_TYPE_REFERENCE);
					archiveBodyList.add(archiveBodyBean);
				} else if (!StringUtil.isEmpty(id) && id.equals(MarticleUtil.ID_JOYME_HTML5)) {
					archiveBodyBean.setContent(text);
					archiveBodyBean.setType(TEXT_TYPE_WEB_HTML);
					archiveBodyList.add(archiveBodyBean);
				} else if (!StringUtil.isEmpty(id) && id.equals(MarticleUtil.ID_JOYME_CENGJI)) {
					archiveBodyBean.setContent(text);
					archiveBodyBean.setType(TEXT_TYPE_CENG_JI);
					archiveBodyList.add(archiveBodyBean);
				} else {
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

	private boolean isGameDownload(Element element) {
		if (element.nodeName().equals("div") && element.attr("name").equals("joyme-download"))
			return true;
		return false;
	}

	/**
	 * 配置下载的模块
	 *
	 * @param archiveBodyBean
	 * @param id
	 * @throws IOException
	 */
	private ArchiveBodyBean getGameDownload(ArchiveBodyBean archiveBodyBean, String id, String channel) throws IOException {
		GameBean bean = getGameBean(id);
		if (bean == null)
			return null;

		archiveBodyBean.setAndroidDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.ANDROID, channel));
		archiveBodyBean.setIphoneDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.IPHONE, channel));
		archiveBodyBean.setIpadDownloadLink(gameService.getDownloadLink(bean, GameDownloadService.IPAD, channel));
		archiveBodyBean.setAndroidDownloadSize(bean.getAndroidGameSize());
		archiveBodyBean.setIphoneDownloadSize(bean.getIphoneGameSize());
		archiveBodyBean.setIpadDownloadSize(bean.getIpadGameSize());

		archiveBodyBean.setGameIcon(bean.getGameIcon());
		archiveBodyBean.setGameName(bean.getGameName());
		archiveBodyBean.setWikiUrl(bean.getClientWikiUrl());
		archiveBodyBean.setCmsUrl(bean.getClientCmsUrl());
		archiveBodyBean.setRate(bean.getRate());
		archiveBodyBean.setType(GAME_DOWNLOAD_TYPE);
		return archiveBodyBean;
	}

	private boolean isTextNode(Element element) {
		if (element.nodeName().equals(LINE_NODE))
			return true;

		if (element.nodeName().equals(DIV_NODE))
			return true;

		return false;
	}

	private void removeChildDivElement(Element element) {
		List childElements = element.children();
		for (Iterator iterator2 = childElements.iterator(); iterator2.hasNext(); ) {
			Element childElement = (Element) iterator2.next();
			if (childElement.nodeName().equals("div"))
				childElement.remove();
		}
	}

	private static String removeHtmlSymbol(String html) {
		html = HtmlUtils.htmlUnescape(html);
		// html = UnicodeUtil.UnicodeToString(html);
		return html;
	}

	private void setImgElement(Element element, ArchiveBodyBean bean, int aid) {
		bean.setContent(getImgPath(element.attr("src")));
//        bean.setContent(getDomain() + element.attr("src"));
		bean.setType(IMAGE_TYPE);

		bean.setPictype(element.attr("pictype"));
		bean.setPicalt(element.attr("title"));
		bean.setPicalttype(element.attr("alttype"));

		bean.setHeight(getHeightOfImage(element));
		bean.setWidth(getWidthOfImage(element));
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

}
