<%@page import="java.sql.ResultSet"%>
<%@page import="com.enjoyf.framework.jdbc.bean.DataBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%--
  Created by IntelliJ IDEA.
  User: ericliu
  Date: 13-9-24
  Time: 上午11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>删文章</title></head>
<body>

<%
if(session.getAttribute("ac-user") == null){
	response.sendRedirect("../login.jsp");
	return;
}
%>

<form action="<%=request.getContextPath()%>/marticle/ac/control/deletereplyaction.jsp" method="post">
    填写名称：<input name="name" value="<%=request.getParameter("name")!=null?request.getParameter("name"):""%>"/>
    填写域名:<input name="domain" value="<%=request.getParameter("domain")!=null?request.getParameter("domain"):""%>"/>
  操作:<select name="able">
  		<option value="y">关闭</option>	
  		<option value="n">打开</option>
  </select>   
    
    <input name="username" type="hidden" value="<%=request.getParameter("username")%>"/>
    <input name="pwd" type="hidden" value="<%=request.getParameter("pwd")%>"/>
    <input type="submit" value="确定"/>
</form>
</body>
</html>