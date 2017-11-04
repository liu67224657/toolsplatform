<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.service.TemplateService"%>
<%@ page import="com.enjoyf.util.SystemUtil" %>
<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
      SystemUtil su = new SystemUtil();
	new TemplateService().reloadTemplate(su.getWebRootPath());
%>
ok