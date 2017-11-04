package com.enjoyf.wiki.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.wiki.container.PropertiesContainer;
import org.springframework.web.util.HtmlUtils;

import net.sf.json.JSONObject;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.wiki.bean.JoymeItem;
import com.enjoyf.wiki.bean.JoymeSubItemBean;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.dao.JoymeItemDao;

public class JoymeItemService {

    private static JoymeItemDao subDao = new JoymeItemDao();

    /**
     * date 2013-09-17 12:32:21
     *
     * @param joymeItemId
     * @return JoymeItem
     * @author shenqiv0.1
     */
    public JoymeItem queryJoymeItembyId(Connection conn, Integer joymeItemId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeItembyId(conn, joymeItemId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-09-17 12:32:21
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeItem(Connection conn, JoymeItem bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeItem(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-09-17 12:32:21
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeItem(Connection conn, JoymeItem bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeItem(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-09-17 12:32:21
     *
     * @param joymeItemId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeItem(Connection conn, Integer joymeItemId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeItem(conn, joymeItemId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * @param isIndex
     * @param bean
     * @return
     */
    public int insertOrUpdate(int isIndex, JoymeItem bean, String wikiType) {
        Connection conn = null;
        try {
            conn = subDao.getConnection();

            if (isIndex == 0 || isIndex == 1) {
                insertJoymeItem(bean, conn, 1, wikiType);
            }

            if (isIndex == 0 || isIndex == 2) {
                insertJoymeItem(bean, conn, 2, wikiType);
            }
            return 1;
        } catch (Exception e) {
            LogService.errorSystemLog("Error when insert or update item", e);
            return 0;
        } finally {
            if (conn != null) {
                subDao.closeConnection(conn);
            }
        }
    }

    private void insertJoymeItem(JoymeItem bean, Connection conn, int index, String wikiType) throws JoymeDBException, JoymeServiceException {
        JoymeItem bean1 = this.queryJoymeItem(conn, bean.getWiki(), bean.getChannel(), index, bean.getItemKey(), wikiType);
        bean.setIsIndex(index);
        int joymeItemId = bean.getJoymeItemId();
        if (joymeItemId == -1) {
            this.insertJoymeItem(conn, bean);
        } else {
            bean.setJoymeItemId(joymeItemId);
            this.updateJoymeItem(conn, bean);
        }
    }

    public JoymeItem queryJoymeItem(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, String wikiType) throws JoymeDBException,
            JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeItem(conn, wiki, channel, isIndex, itemKey, wikiType);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 查找key，如果没有，channel换为default查找。
     *
     * @param wiki
     * @param channel
     * @param isIndex
     * @param itemKey
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymeItem queryItemWithDefault(String wiki, String channel, Integer isIndex, String itemKey, String wikiType) throws JoymeDBException, JoymeServiceException {
        Connection conn = subDao.getConnection();
        try {
            JoymeItem item = this.queryJoymeItem(conn, wiki, channel, isIndex, itemKey, wikiType);
            if (item == null)
                return this.queryJoymeItem(conn, wiki, "default", isIndex, itemKey, wikiType);
            return item;
        } finally {
            subDao.closeConnection(conn);
        }
    }

    /**
     * 根据条件
     *
     * @param conn
     * @param wiki
     * @param channel
     * @param isIndex
     * @param itemKey
     * @param pageNum
     * @return
     * @throws Exception
     */
    public PageBean queryJoymeItemByCondition(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, String wikiType, int pageNum) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeItemByCondition(conn, wiki, channel, isIndex, itemKey, pageNum, wikiType);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 封装放入数据库的image的json串
     *
     * @param request
     * @return
     */
    public String getImageItemProperties(HttpServletRequest request) {
        String itemProperites = "";
        String imageUrl = request.getParameter("imageUrl");
        String imageAlt = request.getParameter("imageAlt");
        String imageWidth = request.getParameter("imageWidth");
        String imageHeight = request.getParameter("imageHeight");
        String imageLinkUrl = request.getParameter("imageLinkUrl");
        String imageIsBlank = request.getParameter("imageIsBlank");
        String imageClass = request.getParameter("imageClass");
        String imageId = request.getParameter("imageId");

        JSONObject object = new JSONObject();
        object.put("imageUrl", imageUrl);
        object.put("imageAlt", imageAlt);
        object.put("imageWidth", imageWidth);
        object.put("imageHeight", imageHeight);
        object.put("imageLinkUrl", imageLinkUrl);
        object.put("imageIsBlank", imageIsBlank);
        object.put("imageClass", imageClass);
        object.put("imageId", imageId);
        itemProperites = object.toString();
        return itemProperites;
    }

    /**
     * 封装image的html
     *
     * @param request
     * @return
     */
    public String getImageContext(HttpServletRequest request) {
        String imageUrl = request.getParameter("imageUrl");
        String imageAlt = request.getParameter("imageAlt");
        String imageWidth = request.getParameter("imageWidth");
        String imageHeight = request.getParameter("imageHeight");
        String imageLinkUrl = request.getParameter("imageLinkUrl");
        String imageIsBlank = request.getParameter("imageIsBlank");
        String imageClass = request.getParameter("imageClass");
        String imageId = request.getParameter("imageId");

        StringBuffer sb = new StringBuffer();

        if (imageLinkUrl != null && !imageLinkUrl.equals(""))
            sb.append("<a href=\"" + imageLinkUrl + "\" " + (imageIsBlank.equals("1") ? "target=\"_blank\"" : "") + ">");

        sb.append("<img src=\"" + imageUrl + "\"");

        if (imageId != null && !imageId.equals(""))
            sb.append(" id=\"" + imageId + "\"");
        if (imageClass != null && !imageClass.equals(""))
            sb.append(" class=\"" + imageClass + "\"");
        sb.append(" width=\"" + imageWidth + "\" height=\"" + imageHeight + "\" alt=\"" + imageAlt + "\">");

        if (imageLinkUrl != null && !imageLinkUrl.equals(""))
            sb.append("</a>");

        return sb.toString();
    }

    public void setImageLinkSubItemBean(JSONObject object, JoymeSubItemBean subItemBean) {
        subItemBean.setImageUrl(object.get("imageUrl") != null ? object.getString("imageUrl") : null);
        subItemBean.setImageAlt(object.get("imageAlt") != null ? object.getString("imageAlt") : null);
        subItemBean.setWidth(object.get("imageWidth") != null ? object.getString("imageWidth") : null);
        subItemBean.setHeight(object.get("imageHeight") != null ? object.getString("imageHeight") : null);
        subItemBean.setLinkUrl(object.get("imageLinkUrl") != null ? object.getString("imageLinkUrl") : null);
        subItemBean.setIsBlank(object.get("imageIsBlank") != null ? object.getInt("imageIsBlank") : 0);
        subItemBean.setClazz(object.get("imageClass") != null ? object.getString("imageClass") : null);
        subItemBean.setId(object.get("imageId") != null ? object.getString("imageId") : null);
    }

    /**
     * 封装放入数据库的textlink的json串
     *
     * @param request
     * @return
     */
    public String getTextlinkProperties(HttpServletRequest request) {
        String texturl = request.getParameter("texturl");
        String textInfo = request.getParameter("textInfo");
        String textId = request.getParameter("textId");
        String textClass = request.getParameter("textClass");
        String textIsBlank = request.getParameter("textIsBlank");

        JSONObject object = new JSONObject();
        object.put("texturl", texturl);
        object.put("textInfo", textInfo);
        object.put("textId", textId);
        object.put("textClass", textClass);
        object.put("textIsBlank", textIsBlank);
        return object.toString();
    }

    /**
     * 封装textlink的html
     *
     * @param request
     * @return
     */
    public String getTextlinkContext(HttpServletRequest request) {
        String texturl = request.getParameter("texturl");
        String textInfo = request.getParameter("textInfo");
        String textId = request.getParameter("textId");
        String textClass = request.getParameter("textClass");
        String textIsBlank = request.getParameter("textIsBlank");

        StringBuffer sb = new StringBuffer();
        sb.append("<a");
        if (textId != null && !textId.equals(""))
            sb.append(" id=\"" + textId + "\"");
        if (textClass != null && !textClass.equals(""))
            sb.append(" class=\"" + textClass + "\"");
        if (textIsBlank != null && !textIsBlank.equals(""))
            sb.append(" target=\"_blank\"");
        sb.append(" href=\"" + texturl + "\">" + textInfo + "</a>");

        return sb.toString();
    }

    /**
     * 封装textlink在页面上的元素
     *
     * @param object
     * @param subItemBean
     */
    public void setTextLinkSubItemBean(JSONObject object, JoymeSubItemBean subItemBean) {
        subItemBean.setLinkUrl(object.get("texturl") != null ? object.getString("texturl") : null);
        subItemBean.setText(object.get("textInfo") != null ? object.getString("textInfo") : null);
        subItemBean.setId(object.get("textId") != null ? object.getString("textId") : null);
        subItemBean.setClazz(object.get("textClass") != null ? object.getString("textClass") : null);
        subItemBean.setIsBlank(object.get("textIsBlank") != null ? object.getInt("textIsBlank") : 0);
    }

    /**
     * 获得保存到数据库中的数据
     *
     * @param request
     * @return
     */
    public String getFlashProperties(HttpServletRequest request) {
        String flashUrl = request.getParameter("flashUrl");
        String flashWidth = request.getParameter("flashWidth");
        String flashHeight = request.getParameter("flashHeight");

        JSONObject object = new JSONObject();
        object.put("flashUrl", flashUrl);
        object.put("flashWidth", flashWidth);
        object.put("flashHeight", flashHeight);
        return object.toString();
    }

    /**
     * 获得flash的html的代码
     *
     * @param request
     * @return
     */
    public String getFlashContext(HttpServletRequest request) {
        String flashUrl = request.getParameter("flashUrl");
        String flashWidth = request.getParameter("flashWidth");
        String flashHeight = request.getParameter("flashHeight");

        StringBuffer sb = new StringBuffer();
        sb.append("<object width=\"" + flashWidth + "\" height=\"" + flashHeight + "\">").append("\r\n");
        sb.append("<param name=\"movie\" value=\"" + flashUrl + "\"></param>  ").append("\r\n");
        sb.append("<param name=\"flashvars\"></param>").append("\r\n");
        sb.append("<param name=\"allowFullScreen\" value=\"true\"></param> ").append("\r\n");
        sb.append("<param name=\"allowscriptaccess\" value=\"always\"></param>").append("\r\n");
        sb.append("<embed src=\"" + flashUrl + "\" type=\"application/x-shockwave-flash\"").append("\r\n");
        sb.append("allowscriptaccess=\"always\" allowfullscreen=\"true\"> </embed>").append("\r\n");
        sb.append("</object>").append("\r\n");
        return sb.toString();
    }

    public void setFlashSubItemBean(JSONObject object, JoymeSubItemBean subItemBean) {
        subItemBean.setLinkUrl(object.get("flashUrl") != null ? object.getString("flashUrl") : null);
        subItemBean.setWidth(object.get("flashWidth") != null ? object.getString("flashWidth") : null);
        subItemBean.setHeight(object.get("flashHeight") != null ? object.getString("flashHeight") : null);
    }

    /**
     * 生成保存在数据库中的iframe的数据
     *
     * @param request
     * @return
     */
    public String getIframeProperties(HttpServletRequest request) {
        String iframeUrl = request.getParameter("iframeUrl");
        String iframeWidth = request.getParameter("iframeWidth");
        String iframeHeight = request.getParameter("iframeHeight");
        String iframeId = request.getParameter("iframeId");
        String iframeClass = request.getParameter("iframeClass");

        JSONObject object = new JSONObject();
        object.put("iframeUrl", iframeUrl);
        object.put("iframeWidth", iframeWidth);
        object.put("iframeHeight", iframeHeight);
        object.put("iframeId", iframeId);
        object.put("iframeClass", iframeClass);
        return object.toString();
    }

    public String getIframeContext(HttpServletRequest request) {
        String iframeUrl = request.getParameter("iframeUrl");
        String iframeWidth = request.getParameter("iframeWidth");
        String iframeHeight = request.getParameter("iframeHeight");
        String iframeId = request.getParameter("iframeId");
        String iframeClass = request.getParameter("iframeClass");

        StringBuffer sb = new StringBuffer();
        sb.append("<iframe ");
        if (iframeId != null && !iframeId.equals(""))
            sb.append(" id=\"" + iframeId + "\"");
        if (iframeId != null && !iframeId.equals(""))
            sb.append(" class=\"" + iframeClass + "\"");
        sb.append(" src=\"" + iframeUrl + "\" width=\"" + iframeWidth + "\" height=\"" + iframeHeight + "\"/>");


        return sb.toString();
    }

    public void setIframeSubItemBean(JSONObject object, JoymeSubItemBean subItemBean) {
        subItemBean.setLinkUrl(object.get("iframeUrl") != null ? object.getString("iframeUrl") : null);
        subItemBean.setWidth(object.get("iframeWidth") != null ? object.getString("iframeWidth") : null);
        subItemBean.setId(object.get("iframeId") != null ? object.getString("iframeId") : null);
        subItemBean.setHeight(object.get("iframeHeight") != null ? object.getString("iframeHeight") : null);
        subItemBean.setClazz(object.get("iframeClass") != null ? object.getString("iframeClass") : null);
    }

    /**
     * 获得html保存的数据库的，需要转义
     *
     * @param request
     * @return
     */
    public String getHTMLProperties(HttpServletRequest request) {
        String htmlContext = request.getParameter("htmlContext");
        String escapeString = HtmlUtils.htmlEscape(htmlContext);
        JSONObject object = new JSONObject();
        object.put("htmlContext", escapeString);
        return object.toString();
    }

    /**
     * 获得HTML的输出，直接输出即可
     *
     * @param request
     * @return
     */
    public String getHTMLContext(HttpServletRequest request) {
        return request.getParameter("htmlContext");
    }

    public void setHTMLSubItemBean(JSONObject object, JoymeSubItemBean subItemBean) {
        subItemBean.setText(object.get("htmlContext") != null ? object.getString("htmlContext") : null);
    }

}