<%@page import="com.mobcent.codetool.web.*"%>
<%@page import="com.mobcent.codetool.daoimpl.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<script language="javascript">
   function check(){
	   if(document.form1.table.value == ''){
		   alert('请输入表名');
	   	   return false;
	   }
   }
</script>

	<form name="form1" action="CRUD.jsp?action=do" method="POST" onsubmit="return check()">
		表名:<input type="text" name="table" value="<%=request.getParameter("table") != null ? request.getParameter("table") : ""%>"> 比如：sdk_topic<br>
		
		<br>
		<input type="submit" value="提交">
	</form>


	<%
    if(request.getParameter("action") != null ){
        String table = request.getParameter("table");
    %>
    
    <!-- dao实现 -->
    <%
            GenerateCreateCRUDDaoImpl cf=new GenerateCreateCRUDDaoImpl();
            String path=cf.makeJavaFile(table , null);
    %>

	<a href="<%=path%>">dao实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- dao接口 -->
	 <%
	     GenerateCreateCRUDInterface dao=new GenerateCreateCRUDInterface();
	     dao.setDao(true);
	     String daoPath=dao.makeJavaFile(table , null);
	 %>

	<a href="<%=daoPath%>">dao接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- bean接口 -->
	 <%
            GenerateCreateFile pojo=new GenerateCreatePOJOFile();
            String pojoPath=pojo.makeJavaFile(table, null);
            	
     %>

	<a href="<%=pojoPath%>">bean</a>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- service实现 -->
	<%
            GenerateCreateCRUDService service = new GenerateCreateCRUDService();
            String servicePath = service.makeJavaFile(table, null);
            	
     %>

	<a href="<%=servicePath%>">service实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
	
	<!-- serviceinterface -->
	 <%
			GenerateCreateCRUDInterface dao1=new GenerateCreateCRUDInterface();
            dao1.setDao(false);
            String siPath=dao1.makeJavaFile(table, null);
            	
     %>

	<a href="<%=siPath%>">service接口</a>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<!-- 屏幕上的代码 -->
	<br><br><br>
	
	
	
	
	
	<%
					    GenerateDaoImpl web = new GenerateDaoImpl();
					        String queryString = web.makeQueryPkWeb(table, true);
					%>
	//<%=table%>查询DAO层
	<p style="color:red"><%=queryString%></p>
	<br>
	<br>
	<% 
		String insertString = web.makeInsertWeb(table,true);
	%>
	//<%=table%>插入DAO层
	<p style="color:blue"><%=insertString%></p>
	<br>
	<% 
		String updateString = web.makeUpdateByIdWeb(table,true);
	%>
	
	//<%=table%>修改DAO层
	<p style="color:red"><%=updateString%></p>
	<br>
	//<%=table%>删除语句
	<% 
		String deleteString = web.makeDeleteWeb(table,true);
	%>
	<p style="color:blue"><%=deleteString%></p>
	<br>
	//<%=table%> POJO语句
	<% String pojoString = web.makePOJOWeb(table);%>
	<p style="color:green"><%=pojoString%></p>
	<br>
	
	<%
    }
  	%>


</body>

</html>
