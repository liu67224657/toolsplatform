package com.enjoyf.advertise.weblogic;

import com.enjoyf.advertise.bean.*;
import com.enjoyf.advertise.service.AdvertiseServiceSngl;
import com.enjoyf.advertise.utils.CookieUtil;
import com.enjoyf.advertise.utils.HttpUtil;
import com.enjoyf.advertise.utils.IPSeeker;
import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-3-14 上午11:39
 * Description:
 */
public class ReportWebLogic {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void report(HttpServletRequest request) {
        String url = request.getParameter("url");
        String host = null;
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String referer = request.getParameter("ref");
        String publishId = request.getParameter("pid");
        String sessionId = request.getParameter("sid");
        String globalId = request.getParameter("gid");
        String ip = HttpUtil.getRemoteAddr(request);
        Date date = new Date();
        String statDate = dateFormat.format(date);

        String inTime = request.getParameter("int");
        //pv
        reportPageView(host,url, referer, publishId, sessionId, globalId, ip, date, statDate);
        //uv
        reportUserView(host,url, referer, publishId, globalId, ip, date, statDate);

        //访问停留时间
        if (!StringUtil.isEmpty(inTime) && !StringUtil.isEmpty(referer)) {
            try {
                reportReferReadTimes(host,referer, publishId, sessionId, globalId, ip, statDate, date, Long.parseLong(inTime));
            } catch (NumberFormatException e) {
            }
        }

        //访问深度
        if (!StringUtil.isEmpty(referer) && !referer.equals(url)) {
            reportPageViewCount(publishId, sessionId, ip, date, statDate);
        }
    }

    private void reportPageView(String host,String url, String referer, String publishId, String sessionId, String globalId, String ip, Date date, String dateStr) {
        AdvertisePageView advertisePageView = new AdvertisePageView();
        advertisePageView.setHost(host);
        advertisePageView.setGlobalId(globalId);
        advertisePageView.setCreateTime(date);
        advertisePageView.setPublishId(publishId);
        advertisePageView.setUrl(url);
        advertisePageView.setRefer(referer);
        advertisePageView.setCreateIp(ip);
        advertisePageView.setStatDate(dateStr);
        advertisePageView.setSessionId(sessionId);
        advertisePageView.setArea(IPSeeker.getInstance().getCountry(ip));
        AdvertiseServiceSngl.getInstance().reportAdvertisePageView(advertisePageView);
    }


    private void reportUserView(String host,String url, String referer, String publishId, String globalId, String ip, Date date, String dateStr) {
        AdvertiseUserView advertiseUserView = new AdvertiseUserView();
        advertiseUserView.setGlobalId(globalId);
        advertiseUserView.setCreateTime(date);
        advertiseUserView.setPublishId(publishId);
        advertiseUserView.setUrl(url);
        advertiseUserView.setRefer(referer);
        advertiseUserView.setCreateIp(ip);
        advertiseUserView.setStatDate(dateStr);
        advertiseUserView.setArea(IPSeeker.getInstance().getCountry(ip));
        advertiseUserView.setHost(host);
        AdvertiseServiceSngl.getInstance().reportAdvertiseUserView(advertiseUserView);
    }


    private void reportPageViewCount(String publishId, String sessionId, String ip, Date date, String dateStr) {
        AdvertisePageViewCount pvCount = new AdvertisePageViewCount();
        pvCount.setSessionId(sessionId);
        pvCount.setPublishId(publishId);
        pvCount.setStatDate(dateStr);
        pvCount.setCreateTime(date);
        pvCount.setCreateIp(ip);
        pvCount.setCount(2);
        pvCount.setArea(IPSeeker.getInstance().getCountry(ip));
        AdvertiseServiceSngl.getInstance().increasePvCount(pvCount);
    }


    private void reportReferReadTimes(String host,String refer, String publishId, String sessionId, String globalId, String ip, String statDate, Date now, long before) {
        AdvertiseReferReadTimes advertiseReferReadTimes = new AdvertiseReferReadTimes();
        advertiseReferReadTimes.setUrl(refer);
        advertiseReferReadTimes.setReadTimes((now.getTime() - before) / 1000);
        advertiseReferReadTimes.setPublishId(publishId);
        advertiseReferReadTimes.setSessionId(sessionId);
        advertiseReferReadTimes.setGlobalId(globalId);
        advertiseReferReadTimes.setCreateTime(now);
        advertiseReferReadTimes.setCreateIp(ip);
        advertiseReferReadTimes.setStatDate(statDate);
        advertiseReferReadTimes.setArea(IPSeeker.getInstance().getCountry(ip));
        advertiseReferReadTimes.setHost(host);
        AdvertiseServiceSngl.getInstance().reportAdvertiseReferReadTimes(advertiseReferReadTimes);
    }

    public void bounceRate(HttpServletRequest request) {
        String url = request.getParameter("url");
        String publishId = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_KEY_ADVPID);
        String sessionId = CookieUtil.getCookieValue(request, CookieUtil.COOKI_KEY_JSESSIONID);
        String globalId = CookieUtil.getCookieValue(request, CookieUtil.COOKIE_KEY_PV_gbid);
        String ip = HttpUtil.getRemoteAddr(request);
        Date date = new Date();
        String statDate = dateFormat.format(date);

        reportBounceRate(url, publishId, sessionId, globalId, ip, statDate, date);
    }

    private void reportBounceRate(String url, String publishId, String sessionId, String globalId, String ip, String statDate, Date date) {
        AdvertiseBounceRate advertiseBounceRate = new AdvertiseBounceRate();
        advertiseBounceRate.setCreateIp(ip);
        advertiseBounceRate.setCreateTime(date);
        advertiseBounceRate.setGlobalId(globalId);
        advertiseBounceRate.setPublishId(publishId);
        advertiseBounceRate.setSessionId(sessionId);
        advertiseBounceRate.setStatDate(statDate);
        advertiseBounceRate.setUrl(url);
        advertiseBounceRate.setArea(IPSeeker.getInstance().getCountry(ip));
        AdvertiseServiceSngl.getInstance().reportAdvertiseBounceRate(advertiseBounceRate);
    }


}
