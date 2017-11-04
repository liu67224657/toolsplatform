<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%@page import="com.enjoyf.mcms.container.ConfigContainer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>
<%
	AdminConsoleFacade facade = new AdminConsoleFacade();
	facade.createCleanAllArchiveList();
%>

<a href="./main.jsp">返回</a>