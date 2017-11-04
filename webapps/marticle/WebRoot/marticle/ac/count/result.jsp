<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询结果</title>
</head>
<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

<body>
	<table border=1>
		<tr>
			<td>日期</td>
			<td>渠道</td>
			<td>PV</td>
			<td>UV</td>
		</tr>
		<c:forEach var="item" items="${countList}">
			<tr>
				<td>${item.date}</td>
				<td>${item.channel}</td>
				<td>${item.pvcount}</td>
				<td>${item.uvcount}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>