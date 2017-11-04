<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.lang.Boolean" %>
<%@page import="com.enjoyf.wiki.service.CacheService" %>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
//    if (session.getAttribute("adminUser") == null) {
//        return;
//    }

    CacheService service = new CacheService();
    if (request.getParameter("type").equals("1")) {
        String key = request.getParameter("key").trim();
        String domain = request.getParameter("domain").trim();
        String url = request.getParameter("url").trim();
        String path = request.getParameter("path").trim();
        String wikiName = request.getParameter("wikiName").trim();
        String wikiType = request.getParameter("wikitype").trim();

        String pcKeepJscss = request.getParameter("pcKeepJscss");
        String mKeepJscss = request.getParameter("mKeepJscss");

        boolean  supportDomain=Boolean.parseBoolean(request.getParameter("supportDomain").trim());

        boolean refreshCss = true;

        String androidPath = null;
        if (request.getParameter("androidPath") != null) {
            androidPath = request.getParameter("androidPath").trim();
        }
        String iosPath = null;
        if (request.getParameter("iosPath") != null) {
            iosPath = request.getParameter("iosPath").trim();
        }
//        wikiName = new String(wikiName.getBytes("ISO-8859-1"), "UTF-8");


        boolean isSuccess = service.initCreatingIndex(wikiName, key, domain, path, url, androidPath, iosPath, refreshCss, wikiType, supportDomain,pcKeepJscss,mKeepJscss);
        if (isSuccess)
            out.print("OK");
        else
            out.print("failed");
    } else if (request.getParameter("type").equals("2")) {
        String key = request.getParameter("key");
        String wikiType = request.getParameter("wikitype").trim();

        PropertiesContainer container=PropertiesContainer.getInstance();

        String domain = container.getDomain(key, wikiType);
        String path = container.getPath(key, wikiType);


        boolean refreshCss = false;
        if (request.getParameter("refreshCss") != null) {
            refreshCss = Boolean.parseBoolean(request.getParameter("refreshCss").trim());
        }

        String url = domain + path + "%E9%A6%96%E9%A1%B5";
        boolean isSuccess = service.creatingIndex( key, domain, path, url, refreshCss, wikiType);
        if (isSuccess)
            out.print("OK");
        else
            out.print("failed");
    }
%>
<a href="${pageContext.request.contextPath}/wiki/ac/main.jsp">返回</a>