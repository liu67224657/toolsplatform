<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
	if(request.getParameter("flag") == null || !request.getParameter("flag").equals("d2f8jlfl0x7")){
		response.sendRedirect("./login.jsp");
		return;
	}
%>
<%
	AdminConsoleFacade facade = new AdminConsoleFacade();
	facade.createCleanAllCache();
%>

<a href="./main.jsp">返回</a>