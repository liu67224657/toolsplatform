<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>
<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

点击群刷后，不可逆
<form action="cleanarchivetypeaction.jsp" method="post">
	<input type="submit" value="提交">
</form>
