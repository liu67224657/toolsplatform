<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>

<%
	AdminConsoleFacade facade = new AdminConsoleFacade();
	boolean isLogin = facade.login(request);
	if(!isLogin){
		out.println("登录失败");
		return;
	} else {
		response.sendRedirect("./main.jsp");
	}
%>