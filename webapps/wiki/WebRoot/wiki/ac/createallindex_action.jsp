<%@page import="com.enjoyf.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.lang.Boolean" %>
<%@page import="com.enjoyf.wiki.service.CacheService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="common.jsp" flush="false"/>
<%
//    if (session.getAttribute("adminUser") == null) {
//        return;
//    }

        CacheService service = new CacheService();
        String wikiType = request.getParameter("wikitype").trim();
        boolean refreshCss = Boolean.parseBoolean(request.getParameter("refreshCss").trim());;
        boolean refreshPage = Boolean.parseBoolean(request.getParameter("refreshPage").trim());;

        boolean isSuccess = service.beginCreatingAllIndex(refreshCss, wikiType,refreshPage);
        if (isSuccess)
            out.print("OK");
        else
            out.print("failed");
%>
<a href="${pageContext.request.contextPath}/wiki/ac/main.jsp">返回</a>