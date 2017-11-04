<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.mcms.facade.AdminConsoleFacade" %>

<%
    if (session.getAttribute("ac-user") == null) {
        response.sendRedirect("./login.jsp");
        return;
    }
%>
<%
    AdminConsoleFacade facade = new AdminConsoleFacade();
    facade.createCleanShare();%>
<a href="./main.jsp">返回</a>

