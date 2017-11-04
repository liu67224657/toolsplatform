package com.enjoyf.wiki.facade;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.util.URLUtil;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.WikiRecommend;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeWikiService;
import com.enjoyf.wiki.service.WikiPageService;
import com.enjoyf.wiki.service.WikiRankService;
import com.enjoyf.wiki.service.WikiRecommendService;
import com.enjoyf.wiki.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-22
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class WikiLastModifiedFacade implements Job {
    private static JoymeWikiService joymeWikiService = new JoymeWikiService();

    private static WikiRankService rankService=new WikiRankService();

    public void job() {

        List<JoymeWiki> joymeWikiList = null;
        try {
            joymeWikiList = joymeWikiService.queryJoymeWikiList(null);
            for (JoymeWiki joymeWiki : joymeWikiList) {
                //最新
                rankService.fetchLastModifyList(joymeWiki.getJoymeWikiKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long start = System.currentTimeMillis();
        LogService.infoSystemLog("===WikiLastModifiedFacade===job start=============");
        job();
        long end = System.currentTimeMillis();
        LogService.infoSystemLog("====WikiLastModifiedFacade ==job end=============" + (end - start) / 1000);

    }
}
