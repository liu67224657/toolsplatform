<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

点击后群刷，不可逆
<form action="cleanaction.jsp" method="post">
	<input type="submit" value="提交">
</form>

