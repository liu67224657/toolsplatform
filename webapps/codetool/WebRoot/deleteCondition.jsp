<%@page import="com.mobcent.codetool.daoimpl.GenerateDaoImpl"%>
<%@page import="com.mobcent.codetool.web.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<script language="javascript">
   function check(){
	   if(document.form1.table.value == ''){
		   alert('请输入表名');
	   	   return false;
	   }
   }
</script>
<body>
	<form name="form1" action="deleteCondition.jsp?action=do" method="POST" onsubmit="return check()">
		表名:<input type="text" name="table" value="<%=request.getParameter("table") != null ? request.getParameter("table") : ""%>"> 比如：sdk_topic<br>
		条件:<input type="text" name="whereClause" value="<%=request.getParameter("whereClause") != null ? request.getParameter("whereClause") : ""%>">比如： where topic_id = ? and ( user_id in ($list1) or forum_id > ? )<br>
		 <input type="submit" value="提交">
	</form>
	
	<%
    if(request.getParameter("action") != null ){
        String table = request.getParameter("table");
        String whereClause = request.getParameter("whereClause");
        if(table != null){
  	%>
  	
  	 <!-- dao实现 -->
    <%
    		GenerateDeleteConditionDaoImpl cf=new GenerateDeleteConditionDaoImpl();
            String path=cf.makeJavaFile(table , whereClause);
    %>

	<a href="<%=path%>">dao实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	
  	
  	<!-- dao接口-->
    <%
   			GenerateDeleteConditionInterface inter = new GenerateDeleteConditionInterface();
            inter.setDao(true);
    		String interPath=inter.makeJavaFile(table , whereClause);
    %>
  	<a href="<%=interPath%>">dao接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	
  	
  	<!-- bean接口 -->
	 <%
	 		GenerateCreatePOJOFile pojo=new GenerateCreatePOJOFile();
            String pojoPath=pojo.makeJavaFile(table, null);
     %>

	<a href="<%=pojoPath%>">bean</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
	 <!-- service实现 -->
	<%
			GenerateDeleteConditionService service = new GenerateDeleteConditionService();
            String servicePath = service.makeJavaFile(table, whereClause);
            	
     %>

	<a href="<%=servicePath%>">service实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
  	
  	<!-- service dao -->
	<%
			GenerateDeleteConditionInterface inter1 = new GenerateDeleteConditionInterface();
		    inter1.setDao(false);
			String interPath1=inter1.makeJavaFile(table , whereClause);
            	
     %>
     
     	<a href="<%=interPath1%>">service接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
  	
  	<%
  	 GenerateDaoImpl web = new GenerateDaoImpl();
    String queryString = web.makeDeleteConditionWeb(table, whereClause, true);
  	%>
  	<br><br>
  	//<%=table%>删除DAO层
	<p style="color:red"><%=queryString%></p>
	<br>
	<br>
	//<%=table%> POJO语句
	<% String pojoString = web.makePOJOWeb(table);%>
	<p style="color:green"><%=pojoString%></p>
	<br>
  	
  	<%}}%>
</body>

</html>