package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeTemplate;
import com.enjoyf.wiki.bean.PageBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.container.TemplateContainer;
import com.enjoyf.wiki.dao.JoymeTemplateDao;
import com.enjoyf.wiki.template.TemplateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

public class JoymeTemplateService {

    private static JoymeTemplateDao subDao = new JoymeTemplateDao();

    /**
     * date 2013-09-17 17:47:37
     *
     * @param joymeTemplateId
     * @return JoymeTemplate
     * @author shenqiv0.1
     */
    public JoymeTemplate queryJoymeTemplatebyId(Connection conn, Integer joymeTemplateId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeTemplatebyId(conn, joymeTemplateId);
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
     * date 2013-09-17 17:47:37
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeTemplate(Connection conn, JoymeTemplate bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeTemplate(conn, bean);
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
     * date 2013-09-17 17:47:37
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeTemplate(Connection conn, JoymeTemplate bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeTemplate(conn, bean);
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
     * date 2013-09-17 17:47:37
     *
     * @param joymeTemplateId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeTemplate(Connection conn, Integer joymeTemplateId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeTemplate(conn, joymeTemplateId);
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
     * date 2013-09-17 18:32:25
     *
     * @param channel
     * @param wiki
     * @param isIndex
     * @return List <joymeTemplate>
     * @author shenqiv0.1
     */
    public JoymeTemplate getJoymeTemplate(Connection conn, String channel, String wiki, Integer isIndex, String wikiType) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getJoymeTemplate(conn, channel, wiki, isIndex, wikiType);
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
     * date 2013-09-17 18:49:23
     *
     * @return List <joymeTemplate>
     * @author shenqiv0.1
     */
    public List queryJoymeTemplate(Connection conn, String wikiType) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeTemplate(conn, wikiType);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryJoymeTemplate(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeTemplate(conn);
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
    public PageBean queryJoymeTemplateByCondition(Connection conn, String wiki, String channel, Integer isIndex, String itemKey, String wikiType, int pageNum) throws Exception {
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

    public String getTemplate(String key, String channel, int isIndex, String wikiType) throws JoymeServiceException, IOException, JoymeDBException {
        JoymeTemplate joymeTemplate = TemplateContainer.getTemplate(key, channel, isIndex, wikiType);
        if (joymeTemplate == null) {
            return null;
        }

        //工具类的默认用2的CSS
        if(isIndex>2){
            isIndex=2;
        }

        String templateCss = TemplateUtil.processCss(key, channel, isIndex, wikiType);
        if (templateCss != null && templateCss.length() > 0) {
            String s = String.valueOf(joymeTemplate.getTemplateContext());
            Document document = Jsoup.parse(s);
            Elements elements = document.getElementsByTag("link");
            if (elements != null) {

                for (Element element : elements) {
                    String rel = element.attr("rel");
                    String href = element.attr("href");
                    if (rel == null || href == null || !rel.equals("stylesheet") || href.equals("wiki.css")) {
                        element.remove();
                    }
                }
            }
            document.getElementsByTag("head").get(0).append("<link rel=\"stylesheet\" href=\"" + templateCss + "\" />");
            return document.html();
        }

        return null;
    }


}