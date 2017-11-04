<%@page import="com.enjoyf.crwalwiki.service.CrwalLogService"%>
<%@page import="java.util.List"%>
<%@page import="com.enjoyf.crwalwiki.service.CacheService"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	CrwalLogService service = new CrwalLogService();
	service.queryLastCrwalLog(request);
%>

<table>
	<tr>
		<td>id</td>
		<td>key</td>
		<td>执行时间 </td>
		<td>是否开始</td>
		<td>是否结束</td>
		<td>下载</td>
	</tr>
	<c:forEach  var="item" items="${lastLogList}">
		<tr>
		<td>${item.id}</td>
		<td>${item.crwalKey}</td>
		<td>${item.createTime}</td>
		<td>
			<c:if test="${item.isRunning == 1}">是</c:if>
			<c:if test="${item.isRunning == 0}">否</c:if>
		</td>
		<td>
			<c:if test="${item.isFinish == 1}">是</c:if>
			<c:if test="${item.isFinish == 0}">否</c:if>
		</td>
		<td>
			<c:if test="${item.downloadUrl == null}">未完成，不能下载</c:if>
			<c:if test="${item.isFinish == 0 && item.isRunning == 0}">&nbsp;<a href="delete.jsp?id=${item.id}">删除</a></c:if>
			<c:if test="${item.downloadUrl != null}"><a href="./download.jsp?id=${item.id}">下载</a></c:if>
		</td>
	</tr>
	</c:forEach>
</table>