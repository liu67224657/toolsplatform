<%@page import="com.enjoyf.crwalwiki.service.CrwalLogService"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	CrwalLogService service = new CrwalLogService();
	service.deleteCrwalLog(null, id);
%>

<a href="query.jsp">返回</a>