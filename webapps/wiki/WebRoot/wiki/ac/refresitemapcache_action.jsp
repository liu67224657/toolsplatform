<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="com.enjoyf.wiki.service.CacheService" %>
<jsp:include page="common.jsp" flush="false"/>
<%
//    if (session.getAttribute("adminUser") == null) {
//        return;
//    }
    String key = request.getParameter("key");
    CacheService service = new CacheService();
    boolean isSuccess = service.refresitemapcache(key);
    if (isSuccess)
        out.print("OK");
    else
        out.print("failed");
%>
<a href="${pageContext.request.contextPath}/wiki/ac/main.jsp">返回</a>