<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<% 
	String flag = request.getParameter("flag");
	if(flag == null || !flag.equals("xdfgb33GTDx")){
	    return ;
	}
%>
<body>
	<a href="new.jsp">我要抓取一个整站</a>
	<br>
	<a href="page.jsp">我要抓取一些页面</a>
	<br>
	<a href="query.jsp">我要查询，看看完成了没</a>
</body>
</html>