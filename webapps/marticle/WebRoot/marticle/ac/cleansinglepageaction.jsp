<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>
<%
	String path = request.getParameter("pageurl");
	AdminConsoleFacade facade = new AdminConsoleFacade();
	facade.createCleanSinglePage(path);
%>

OK