<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
	AdminConsoleFacade facade = new AdminConsoleFacade();
	boolean isSuccess = facade.refresh(request);
	String filePath = request.getParameter("filePath");
	if(isSuccess)
		out.print("ok");
	else 
		out.print("failed");
%>

<a href="./main.jsp?filePath=${param.filePath}">返回</a>