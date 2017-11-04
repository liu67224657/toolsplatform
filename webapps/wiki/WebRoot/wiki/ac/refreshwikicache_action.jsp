<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="com.enjoyf.wiki.service.CacheService" %>
<%@ page import="com.enjoyf.wiki.tools.WikiUtil" %>
<%@ page import="java.net.URL" %>
<jsp:include page="common.jsp" flush="false"/>
<%
//    if (session.getAttribute("adminUser") == null) {
//        return;
//    }
    String key = request.getParameter("key");
    String wikitype = request.getParameter("wikitype");
    CacheService service = new CacheService();
    boolean isSuccess = service.beginRefreshWiki(key, wikitype);
    if (isSuccess)
        out.print("OK");
    else
        out.print("failed");
%>
<a href="${pageContext.request.contextPath}/wiki/ac/main.jsp">返回</a>