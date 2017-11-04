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

<form method="post" action="new_action.jsp">
	请输入要发布的key:
	<select name="key">
		<c:forEach  var="item" items="${keyList}">
			<option value="${item}">${item}</option>
		</c:forEach>
	</select>
	<input type="submit" value="提交"/>
</form>
