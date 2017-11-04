<%@page import="com.enjoyf.crwalwiki.bean.CrwalLog"%>
<%@page import="com.enjoyf.crwalwiki.service.CrwalLogService"%>
<%@page import="com.enjoyf.crwalwiki.util.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	CrwalLogService service = new CrwalLogService();
	int id = Integer.parseInt(request.getParameter("id"));
	CrwalLog log = service.queryCrwalLogbyId(null, id);
	String fileName = log.getDownloadUrl();
	FileUtil util = new FileUtil();
	util.download(fileName, response);
	
	out.clear();  
	out = pageContext.pushBody();  
%>