package com.enjoyf.mcms.util;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.temp.ArchivesBean;
import com.enjoyf.mcms.service.DedeAddonarticleService;
import com.enjoyf.mcms.service.DedeArchivesService;
import com.enjoyf.mcms.service.DedeChanneltypeService;
import com.enjoyf.util.StringUtil;

import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-1-4 下午2:45
 * Description:
 */
public class ArchiveUtil {
	private final static String REDIRECT_URL_FLAG = "http://www.joyme.com/";
	private DedeChanneltypeService dedeChanneltypeService = new DedeChanneltypeService();
	private DedeAddonarticleService dedeAddonarticleService = new DedeAddonarticleService();
	private DedeArchivesService dedeArchivesService = new DedeArchivesService();
	private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();

    private static final Pattern ARCHIVE_NUMBER_SUFFIX=Pattern.compile("([/\\d]+)\\.html");

	private static final Pattern pattern = Pattern.compile(REDIRECT_URL_FLAG + "([a-zA-Z0-9_]*wiki)/");

	private static final String KEY_APPWIKI = "appwiki";

	public ArchivesBean getArchivesByRedirectUrl(Connection conn, int aid, int archiveChannel) throws Exception {

		String tableName = dedeChanneltypeService.queryDedeChanneltypebyId(conn, archiveChannel);
		DedeAddonarticle dedeAddonarticle = dedeAddonarticleService.queryDedeAddonarticlebyId(conn, tableName, aid);

		if (dedeAddonarticle == null || dedeAddonarticle.getRedirecturl() == null || "".equals(dedeAddonarticle.getRedirecturl()))
			return null;
		else {
			String url = dedeAddonarticle.getRedirecturl();
			if (!url.startsWith(REDIRECT_URL_FLAG))
				return null;
			else if (pattern.matcher(url).find()) {
				//外站链接
				Matcher match = pattern.matcher(url);
				match.find();

				String link = url.replaceAll(match.group(1), KEY_APPWIKI);


				return new ArchivesBean(null, link, dedeAddonarticle);
			} else {
				int archiveId = this.getArchiveId(url);
				if (archiveId == 0) {
					return null;
				} //解析跳转连接失败

				DedeArchives dedeArchives = dedeArchivesService.queryDedeArchivesbyId(conn, archiveId);
				return new ArchivesBean(dedeArchives, url, dedeAddonarticle);
			}
		}

	}

	public ArchivesBean getArchivesByRedirectUrlSyhb(Connection conn, int aid, int archiveChannel) throws JoymeDBException {
		DedeAddonarticle dedeAddonarticle = null;
		try {
			String tableName = dedeChanneltypeService.queryDedeChanneltypebyId(conn, archiveChannel);
			dedeAddonarticle = dedeAddonarticleService.queryDedeAddonarticlebyId(conn, tableName, aid);
		} catch (JoymeServiceException e) {
			e.printStackTrace();
		}
		return new ArchivesBean(null, dedeAddonarticle.getRedirecturl(), dedeAddonarticle);
	}


	public int getArchiveId(String url) {
		String[] urls = url.split("/");
		int archiveId = 0;
		String number = "";
		for (int i = 0; i < urls.length; i++) {
			String item = urls[i];
			if (item.endsWith(".html")) {
				item = item.replaceAll(".html", "");
				int position = item.indexOf("_");
				if (position >= 0) {
					item = item.substring(0, position);
				}
			}

			if (StringUtil.isNumeric(item)) {
				number += item;
			}
		}

		if (number.length() > 8 && number.startsWith("20")) {
			archiveId = Integer.parseInt(number.substring(8, number.length()));
		}

		return archiveId;
	}


    public String formatUrl(String url) {
//        String s="http://localhost:8080/syhb2/pingguoduan/2/0141/0/315/64/05.html";
        Matcher matcher = ARCHIVE_NUMBER_SUFFIX.matcher(url);

        StringBuffer sb=new StringBuffer();
        if(matcher.find()){
            String numberString=matcher.group(1);

            numberString=numberString.replace("/","");
            String id= String.valueOf(getArchiveId(numberString));

            int position=numberString.lastIndexOf(id);
            String prefix=numberString.substring(0,position);

            String urlSuffix="/"+prefix+"/"+id+".html";

            matcher.appendReplacement(sb,urlSuffix);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

}
