<%@page import="com.enjoyf.crwalwiki.service.CrwalLogService"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
    CrwalLogService service = new CrwalLogService();
	service.insertPagesLocationCrwalLog(request);
%>