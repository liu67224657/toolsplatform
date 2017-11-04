<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/borderStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui-1.8.11.custom.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.11.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>
<script type="text/javascript">
$(function() {
	$("#date").datepicker().datepicker("option", "dateFormat", "yy-mm-dd");
});
</script>

<form action="${pageContext.request.contextPath}/marticle/ac/querycount.do" method="post">
	<input type="hidden" name="action" value="query">
	日期：格式 2013-09-25
	<input type="text" name="date" id="date" />
	<input type="submit" value="提交">
</form>