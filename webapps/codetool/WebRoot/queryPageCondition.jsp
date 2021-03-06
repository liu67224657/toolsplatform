<%@page import="com.mobcent.codetool.web.*"%>
<%@page import="com.mobcent.codetool.daoimpl.GenerateDaoImpl"%>
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
	<form name="form1" action="queryPageCondition.jsp?action=do" method="POST" onsubmit="return check()">
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
            GenerateQueryPageDaoImpl cf=new GenerateQueryPageDaoImpl();
            String path=cf.makeJavaFile(table , whereClause);
    %>

	<a href="<%=path%>">dao实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	
  	<!-- dao接口-->
    <%
   			GenerateQueryPageInterface inter = new GenerateQueryPageInterface();
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
			GenerateQueryPageService service = new GenerateQueryPageService();
            String servicePath = service.makeJavaFile(table, whereClause);
     %>

	<a href="<%=servicePath%>">service实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- service接口 -->
	<%
			GenerateQueryPageInterface inter1 = new GenerateQueryPageInterface();
		    inter1.setDao(false);
			String interPath1=inter1.makeJavaFile(table , whereClause);
            	
     %>

	<a href="<%=interPath1%>">service接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
  	
  	<!-- 页面上的显示 -->
  	<br><br>
  	<%
	  	GenerateDaoImpl web = new GenerateDaoImpl();
	    String countString = web.makeQueryCountConditionWeb(table, whereClause ,true);
  	%>
  	
  	//<%=table%>查询总数的DAO层
	<p style="color:red"><%=countString%></p>
	<br>
	
	<%String pageString = web.makeQueryPageConditionWeb(table, whereClause ,true);%>
	//<%=table%>分页查询DAO层
	<p style="color:blue"><%=pageString%></p>
	<br>
	
	
	//<%=table%> POJO语句
	<% String pojoString = web.makePOJOWeb(table);%>
	<p style="color:green"><%=pojoString%></p>
	<br>
  	
  	<%}}%>
</body>

</html>