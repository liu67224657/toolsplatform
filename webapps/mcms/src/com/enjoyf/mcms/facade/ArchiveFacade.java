package com.enjoyf.mcms.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.bean.DedePoint;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymePoint;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.bean.temp.NavigateBean;
import com.enjoyf.mcms.bean.temp.PageBean;
import com.enjoyf.mcms.bean.temp.PointArchiveListBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.container.MemCachedContainer;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.service.ChannelService;
import com.enjoyf.mcms.service.DedeAddonarticleService;
import com.enjoyf.mcms.service.DedeArchivesService;
import com.enjoyf.mcms.service.DedeArctypeService;
import com.enjoyf.mcms.service.JoymeChannelContentService;
import com.enjoyf.mcms.service.JoymePointArchiveService;
import com.enjoyf.mcms.service.JoymePointService;
import com.enjoyf.mcms.service.JoymeSpecService;
import com.enjoyf.mcms.service.URLService;
import com.enjoyf.mcms.util.DateUtil;

public class ArchiveFacade {
    private static DedeArchivesService dedeArchivesService = new DedeArchivesService();
    private static JoymePointService joymePointService = new JoymePointService();
    private static JoymePointArchiveService joymePointArchiveService = new JoymePointArchiveService();
    private static DedeArctypeService dedeArctypeService = new DedeArctypeService();
    private static DedeAddonarticleService dedeAddonarticleService = new DedeAddonarticleService();
    private static JoymeSpecService joymeSpecService = new JoymeSpecService();
    private static JoymeChannelContentService joymeChannelContentService = new JoymeChannelContentService();
    private static URLService urlService = new URLService();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
//    private static HtmlowService htmlService = new HtmlService();
    private final static int PAGE_COUNT = 15;
    private final static long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    public final static String ARTICLE_LIST_FLAG = "articleList";
    private final static String REDIRECT_URL_FLAG = "http://www.joyme.com/article";
    private final static String SUBSTRING_URL_FLAG = "http://www.joyme.com";

    /**
     * 生成临时数据
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public int generateTempData(String fileName) throws Exception {
        Connection conn = null;

        Object[] objects = dedeArchivesService.getArchivesPointList(conn, fileName);
        // dedeSpecArticleId是dede的专区的articleId
        if (objects == null) {
            return 0;
        }

        int dedeSpecArticleId = (Integer) objects[0];
        List list = (List) objects[1];

        List joymePointList = new ArrayList();

        int seqId = dedeArchivesService.getMaxSeqId(conn, fileName) + 1;
        // 如果以后有专区排序的需求，在joyme_point中增加order字段 然后每次清0 产生的时候根据list的顺序带入1 2 3即可

        try {
            conn = dao.getConnection();
            // 生成之前需要先把point的active的is_active全部置0，避免改名字的情况出现后多出游戏专区来
            // 并且把displayorder都置0
            joymePointService.updateJoymePointIsActiveByHtmlFile(conn, fileName);

            int a = 1;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DedePoint point = (DedePoint) iterator.next();
                String pointName = point.getName();
                JoymePoint bean = new JoymePoint();
                bean.setArchiveId(dedeSpecArticleId);
                bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
                bean.setDisplayRowNum(Integer.parseInt(point.getRownum()));
                bean.setIsAble(1);
                bean.setKeywords(point.getKeywords());
                bean.setSpecPointName(pointName);
                bean.setTypeid(point.getTypeid());
                bean.setHtmlPath("game");
                bean.setHtmlFile(fileName);
                bean.setIsActive(1);
                bean.setDisplayOrder(a);
                a++;

                // 插入到整体的表中
                long pointId = joymePointService.insertJoymePointByName(conn, dedeSpecArticleId, bean);
                bean.setSpecPointId(pointId);

                // 把typeid200条一查后，放到结果表中，形成临时表。
                insertJoymePointArchive(conn, list, point, pointId, seqId, dedeSpecArticleId, pointName, fileName);

                // 分批次的放入arichive
                conn.commit();
                joymePointList.add(bean);
            }
            joymePointArchiveService.updateJoymePointArchiveStatus(conn, 1, seqId);

            // 删除老数据
            joymePointArchiveService.deleteOldJoymePointArchive(conn, seqId, dedeSpecArticleId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.closeConnection(conn);
        }

        return 1;
    }

    // private void makeHtml(String fileName, Map htmlMap) {
    // File file = new File();
    //        
    // }

    /**
     * 通过htmlFile生成专区页
     * 
     * @param conn
     * @param htmlFile
     * @return html代码
     */
    public String generateSpecHtml(Connection conn, String htmlFile, HttpServletRequest request, String[] channels) {
        try {
            List joymePointList = joymePointService.queryJoymePointByHtmlFile(conn, htmlFile);
            JoymeSpec spec = joymeSpecService.queryJoymeSpecByFilePath(conn, htmlFile);

            // 没有数据，新生成
            if (joymePointList.size() == 0) {
                int status = this.generateTempData(htmlFile);
                if (status == 0)
                    throw new Exception("generate temp data failed, which filename is :" + htmlFile);

                joymePointList = joymePointService.queryJoymePointByHtmlFile(null, htmlFile);
            }

            int seqId = dedeArchivesService.getMaxSeqId(null, htmlFile);
            String localPath = ConfigContainer.getLocalPath(request);
            return generateSpecHtml(null, spec, joymePointList, seqId, channels, localPath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成子页的html
     * 
     * @param request
     * @param archiveId
     * @param filePath
     * @param fileName
     * @return
     * @throws Exception
     */
    public String generateArticleHtml(HttpServletRequest request, HttpServletResponse response, int archiveId, String filePath, String fileName)
            throws Exception {
        Connection conn = null;
        try {
            conn = dao.getConnection();
            DedeArchives dedeArchives = dedeArchivesService.queryDedeArchivesbyId(conn, archiveId);
            DedeAddonarticle dedeAddonarticle = dedeAddonarticleService.queryDedeAddonarticlebyId(conn, archiveId);
            this.parseDedeAddonarticle(dedeAddonarticle);

            String localPath = ConfigContainer.getLocalPath(request);
            String channel = ChannelService.getChannel(request);

            IHtmlParseFactory factory = ConfigContainer.getHtmlParseFactory(channel);
            return factory.makeArchiveHtml(dedeArchives, dedeAddonarticle, filePath, fileName, channel, localPath);
            
//            if (!channel.equals("json")) {
//                return htmlService.makeArchiveHtml(dedeArchives, dedeAddonarticle, filePath, fileName, channel, localPath);
//            } else {
//                return htmlService.makeJsonArchiveHtml(dedeArchives, dedeAddonarticle, filePath, fileName, channel, localPath);
//            }
        } finally {
            dao.closeConnection(conn);
        }
    }

    private DedeAddonarticle parseDedeAddonarticle(DedeAddonarticle dedeAddonarticle) throws JoymeDBException, JoymeServiceException {
        if (dedeAddonarticle.getRedirecturl() == null || "".equals(dedeAddonarticle.getRedirecturl()))
            return dedeAddonarticle;
        else {
            String url = dedeAddonarticle.getRedirecturl();
            if (!url.startsWith(REDIRECT_URL_FLAG))
                return dedeAddonarticle;
            else {
                url = url.replaceAll(SUBSTRING_URL_FLAG, "");
                int position = url.lastIndexOf("/");
                String fileName = "";
                if (position > 0) {
                    fileName = urlService.getArchiveFileName(url, position);
                }

                int archiveId = urlService.getArchiveId(url, fileName);
                DedeAddonarticle dedeAddonarticleTemp = dedeAddonarticleService.queryDedeAddonarticlebyId(null, archiveId);
                dedeAddonarticle.setBody(dedeAddonarticleTemp.getBody());
            }
        }
        return dedeAddonarticle;
    }

    /**
     * 更多页面的查询逻辑
     * 
     * @param request
     * @throws Exception
     */
    public static void getArchiveList(HttpServletRequest request) throws Exception {
        int pageNum = 1;
        if (request.getParameter("pageNum") != null) {
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        }

        int pointId = Integer.parseInt(request.getParameter("pointId"));

        // 得到archive page list
        String key = getAritcleKey(pageNum, pointId);

        PageBean bean = null;
        Object object = MemCachedContainer.mcc.get(key);
        if (object == null) {
            bean = joymePointArchiveService.getJoymePointArchivePageList(null, pointId, pageNum, PAGE_COUNT);
            // 放置面包线
            putNavigate(bean, pointId);
            MemCachedContainer.mcc.set(key, bean, new Date(EXPIRE_TIME));
        } else {
            bean = (PageBean) object;
        }
        request.setAttribute("pageBean", bean);

    }

    /**
     * 根据节点的list表和seqId生成文件
     * 
     * @param conn
     * @param joymePointList
     * @param seqId
     * @throws Exception
     */
    private String generateSpecHtml(Connection conn, JoymeSpec spec, List joymePointList, int seqId, String[] channels, String localPath)
            throws Exception {
        List list = new ArrayList();

        // 封装专区的文章列表，由多个节点组成
        for (Iterator iterator = joymePointList.iterator(); iterator.hasNext();) {
            JoymePoint point = (JoymePoint) iterator.next();
            long pointId = point.getSpecPointId();
            int rownum = point.getDisplayRowNum();

            List archiveList = joymePointArchiveService.queryJoymePointArchiveByPointId(conn, pointId, 0, rownum, seqId);

            PointArchiveListBean bean = new PointArchiveListBean();
            bean.setArchiveList(archiveList);
            bean.setPoint(point);
            list.add(bean);
        }

        // 获得专区的渠道下载地址和广告语
        String html = "";
        for (int i = 0; i < channels.length; i++) {
            IHtmlParseFactory factory = ConfigContainer.getHtmlParseFactory(channels[i]);
            JoymeChannelContent content = spec == null ? null : joymeChannelContentService.queryJoymeChannelContentbySpecChannel(conn, spec.getSpecId(), channels[i]);

            // 生成html
            html = factory.makeSpecHtml(spec, list, content, channels[i], localPath);
        }
        return html;
    }

    /**
     * 插入临时表的数据，先查栏目里面所有的，在根据keywords是否符合查出来
     * 
     * @param conn
     * @param list
     * @param point
     * @param pointId
     * @param seqId
     * @param aid
     * @param pointName
     * @param specFilePath
     * @throws JoymeDBException
     * @throws JoymeServiceException
     * @throws SQLException
     */
    private void insertJoymePointArchive(Connection conn, List list, DedePoint point, long pointId, int seqId, int aid, String pointName,
            String specFilePath) throws JoymeDBException, JoymeServiceException, SQLException {
        int maxId = 0;
        List list1 = this.parseTypeId(point.getTypeid());

        for (int n = 0;; n++) {

            // 获得200条记录
            List archiveList = dedeArchivesService.queryDedeArchivesByTypeid(conn, 0, list1, maxId);
            if (archiveList == null || archiveList.size() == 0) {
                break;
            }

            // 获得最大的seq

            for (int i = 0; i < archiveList.size(); i++) {
                DedeArchives dedearchive = (DedeArchives) archiveList.get(i);
                if (dedearchive.getId() > maxId)
                    maxId = dedearchive.getId();

                String keywords = dedearchive.getKeywords();
                keywords = keywords.replaceAll("，", ",");
                String[] keys = keywords.split(",");
                boolean isEnter = false;
                for (int j = 0; j < keys.length; j++) {
                    // 判断关键字是否符合
                    if (isContainKeywords(point, keys[j])) {
                        isEnter = true;
                        JoymePointArchive bean = new JoymePointArchive();
                        bean.setArchiveId((long) dedearchive.getId());
                        bean.setTypeId(dedearchive.getTypeid());
                        bean.setSpecFilePath(specFilePath);
                        bean.setSpecArchiveId((long) aid);
                        long time = Long.parseLong(dedearchive.getPubdate() + "000");
                        bean.setArchivePublishTime(time);
                        bean.setIsActive(0);
                        bean.setPointId(pointId);
                        bean.setSeqId(seqId);
                        bean.setTitle(dedearchive.getTitle());
                        bean.setPointName(pointName);

                        if (dedearchive.getTypeid() > 0) {
                            DedeArctype dedeArctype = dedeArctypeService.queryDedeArctype(conn, dedearchive.getTypeid());

                            String[] paths = getHtmlPath(dedeArctype, time, dedearchive.getId());
                            bean.setHtmlPath(paths[0]);
                            bean.setHtmlFile(paths[1]);
                            joymePointArchiveService.insertJoymePointArchive(conn, bean);
                        }
                    }
                }

                if (isEnter)
                    conn.commit();
            }
        }
    }

    /**
     * @param point
     *            目标
     * @param keys
     *            当前条目的
     * @return
     */
    private boolean isContainKeywords(DedePoint point, String keys) {
        String[] targetKeys = point.getKeywords().split(",");
        for (int i = 0; i < targetKeys.length; i++) {
            if (keys.contains(targetKeys[i]))
                return true;
        }
        return false;
    }

    private String[] getHtmlPath(DedeArctype dedeArctype, long time, int archiveId) {
        String typedir = dedeArctype.getTypedir().replaceAll("\\{cmspath\\}/", "");
        String namerule = dedeArctype.getNamerule();
        // {typedir}/{Y}{M}{D}/{aid}.html
        namerule = namerule.replaceAll("\\{typedir\\}", typedir);
        String date = DateUtil.convert2String(time);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        namerule = namerule.replaceAll("\\{Y\\}", year);
        namerule = namerule.replaceAll("\\{M\\}", month);
        namerule = namerule.replaceAll("\\{D\\}", day);
        namerule = namerule.replaceAll("\\{aid\\}", archiveId + "");

        int position = namerule.lastIndexOf("/");
        String[] paths = new String[2];
        if (position > 0) {
            paths[0] = namerule.substring(0, position);
            paths[1] = namerule.substring(position + 1, namerule.length());
        }

        return paths;
        // namerule = namerule.replaceAll(regex, replacement)
    }

    private List parseTypeId(String typeid) {
        List list = new ArrayList();
        String[] typeids = typeid.split(",");
        for (int i = 0; i < typeids.length; i++) {
            list.add(typeids[i]);
        }
        return list;
    }

    /**
     * 放置面包线
     * 
     * @param bean
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    private static void putNavigate(PageBean bean, int pointId) throws JoymeDBException, JoymeServiceException {
        Connection conn = null;
        try {
            conn = dao.getConnection();
            JoymePoint point = joymePointService.queryJoymePointbyId(conn, pointId);
            if (point != null) {
                int archiveId = point.getArchiveId();

                JoymeSpec spec = joymeSpecService.queryJoymeSpecByArchiveId(conn, archiveId);
                NavigateBean nbean = new NavigateBean();
                if (spec != null) {
                    nbean.setLevel1Str(spec.getSpecName());
                    nbean.setLevel1Add("../game/" + spec.getFilePath() + ".html");
                }

                nbean.setLevel2Str(point.getSpecPointName());
                bean.setNavigate(nbean);
            }
        } finally {
            dao.closeConnection(conn);
        }

    }

    private static String getAritcleKey(int pageNum, int pointId) {
        return ARTICLE_LIST_FLAG + "_" + pointId + "_" + pageNum;
    }
}
