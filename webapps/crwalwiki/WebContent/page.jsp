<%@page import="java.util.List"%>
<%@page import="com.enjoyf.crwalwiki.service.CacheService"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%
CacheService cs = new CacheService(); 
cs.getKeyList(request);
%>

<form method="post" action="page_action.jsp">
	请输入要抓取的链接，中间用;隔开，英文的分开，不能是中文的<br>
	<textarea rows="30" cols="120" name="urls"></textarea>
	<input type="submit" value="提交"/>
</form>
