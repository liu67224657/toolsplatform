<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.util.VideoTools"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

如果链接是这样的
http://v.youku.com/v_show/id_<font color="red">XNTIzNzk4NjUy </font>.html
，那么id填入：XNTIzNzk4NjUy 
<br>
如果链接是这样的
http://player.youku.com/player.php/sid/<font color="red">XNTg5MjI0MjE2</font>/v.swf
，那么id填入：XNTg5MjI0MjE2 

<form method="post" action="./checkmp4.jsp">
	id:<input type="text" name="id">
	<input type="submit" value="提交">
</form>

<br/>

<%
	if(request.getParameter("id") != null){
		VideoTools tools = new VideoTools();
		String id = request.getParameter("id");
		String url = tools.getMP4ViedoUrl(id);
		if(url != null)
			out.print("有MP4格式");
		else 
			out.print("没有MP4格式");
}%>