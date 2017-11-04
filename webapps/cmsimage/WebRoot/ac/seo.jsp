<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table align="center" border="1">
	<tr align="center">
		<td width="150">id</td>
		<td width="250">转化后的URL</td>
		<td width="250">转化前的URL</td>
		<td width="120">操作</td>
	</tr>
	<c:forEach var="item" items="${seoList}">
		<form action="/ac/updateSeoConfig.do" method="post">
			<tr>
				<td>${item.seoId}<input type="hidden" name="seoId" value="${item.seoId}"/></td>
				<td><input type="text" name="seoTransferPath" value="${item.seoTransferPath}" size="40"/></td>
				<td><input type="text" name="seoOriginalPath" value="${item.seoOriginalPath}" size="40"/></td>
				<td><input type="submit" value="更改">&nbsp;&nbsp;&nbsp;<a href="/ac/deleteSeoConfig.do?seoId=${item.seoId}">删除</a></td>
			</tr>
		</form>
	</c:forEach>
	<form action="/ac/insertSeoConfig.do" method="post">
		<tr>
			<td width="150"></td>
			<td width="250"><input type="text" name="seoTransferPath" size="40"></td>
			<td width="250"><input type="text" name="seoOriginalPath" size="40"></td>
			<td width="120"><input type="submit" value="增加"></td>
		</tr>
	</form>
</table>
