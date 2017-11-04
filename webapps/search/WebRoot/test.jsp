<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>
<body>
<form action="/search/query.do" method="post">
    core:<input  name="c" type="text" value="users"/> <br/>
    query:<input  name="q" type="text" value="(searchtext:熊)"/><br/>
    sort:<input  name="sort" type="text" value="age:desc"/><br/>
    page:<input  name="p" type="text" value="1"/> <br/>
    size:<input  name="ps" type="text" value="20"/> <br/>
    <input type="submit" value="搜索">
</form>

<%--test version git--%>
</body>
