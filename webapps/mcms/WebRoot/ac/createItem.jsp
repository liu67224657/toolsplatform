<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
	AdminConsoleFacade facade = new AdminConsoleFacade();
	boolean isSuccess = facade.insertJoymeSpec(request);
	if(isSuccess)
		out.print("ok");
	else 
		out.print("failed");
%>

<a href="./main.jsp?filePath=${param.filePath}">返回</a>