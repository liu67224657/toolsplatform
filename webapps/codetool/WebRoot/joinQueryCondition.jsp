<%@page import="com.mobcent.codetool.web.*"%>
<%@page import="com.mobcent.codetool.daoimpl.GenerateDaoImpl"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<script language="javascript">
   function check(){
	   if(document.form1.sql.value == ''){
		   alert('请输入sql语句');
	   	   return false;
	   }
	   if(document.form1.logicName.value == ''){
	   	   alert('请输入逻辑名');
	   	   return false;
	   }
   }
</script>
<body>
	<form name="form1" action="joinQueryCondition.jsp?action=do" method="POST" onsubmit="return check()">
		sql语句:<input type="text" name="sql" value="<%=request.getParameter("sql") != null ? request.getParameter("sql") : ""%>"> 比如：SELECT * FROM sdk_user t1 INNER JOIN sdk_forum_user t2 ON t1.user_id = t2.user_id WHERE t1.registed_forum_id > ? AND t2.reply_posts_num = ?<br>
		<br>传入的参数请用?<br>
		逻辑名：<input type="text" name="logicName" value="<%=request.getParameter("logicName") != null ? request.getParameter("logicName") : ""%>"> 逻辑名是指这个sql语句实际的逻辑用途，比如forumUserRole等等<br>
		 <input type="submit" value="提交">
	</form>
	
	<%
		    if(request.getParameter("action") != null ){
		        String sql = request.getParameter("sql");
		        String logicName = request.getParameter("logicName");
		        if(sql != null && logicName != null){
		   			
		%>
		
	<!-- InputBean -->	
	<%
		GenerateJoinQueryInputBean input = new GenerateJoinQueryInputBean();
		String inputStr=input.makeJavaFile(logicName , sql);
	%>
		<a href="<%=inputStr%>">InputBean</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
	<!-- OutputBean -->	
	<%
		GenerateJoinQueryOutputBean output = new GenerateJoinQueryOutputBean();
		String outputStr=output.makeJavaFile(logicName , sql);
	%>
		<a href="<%=outputStr%>">OutputBean</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
	 <!-- dao实现 -->
    <%
   		GenerateJoinQueryDaoImpl cf=new GenerateJoinQueryDaoImpl();
        String path=cf.makeJavaFile(logicName , sql);
    %>

	<a href="<%=path%>">dao实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	 <!-- dao接口-->
    <%
   		GenerateJoinQueryInterface inter=new GenerateJoinQueryInterface();
    	inter.setDao(true);
        String interStr=inter.makeJavaFile(logicName , sql);
    %>

	<a href="<%=interStr%>">dao接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
	 <!-- service实现-->
    <%
   		GenerateJoinQueryService service=new GenerateJoinQueryService();
        String serviceStr=service.makeJavaFile(logicName , sql);
    %>

	<a href="<%=serviceStr%>">service实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- service接口-->
    <%
   		GenerateJoinQueryInterface inter1 = new GenerateJoinQueryInterface();
  		inter1.setDao(false);
        String interStr1=inter1.makeJavaFile(logicName , sql);
    %>

	<a href="<%=interStr1%>">service接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		
	<!-- 显示在页面 -->	
	<br><br>	
	<%
		GenerateDaoImpl web = new GenerateDaoImpl();
		String inputBean = web.makeJoinQueryInputBean(logicName, sql ,true);
	%>	
  	
  	//<%=logicName%>InputBean
	<p style="color:red"><%=inputBean%></p>
	<br>
	<%String outputBean = web.makeJoinQueryOutputBean(logicName, sql ,true);%>
	//<%=logicName%>OutputBean
	<p style="color:blue"><%=outputBean%></p>
	<br>
	
	<%String daoImpl = web.makeJoinQueryWeb(logicName, sql ,true);%>
	//<%=logicName%>查询
	<p style="color:green"><%=daoImpl%></p>
	<br>
	
  	<%}}%>
</body>

</html>