<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%
	if(request.getParameter("flag") == null || !request.getParameter("flag") .equals("a8xdv7fslf")){
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