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

	<form name="form1" action="insertReturn.jsp?action=do" method="POST" onsubmit="return check()">
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
        GenerateInsertReturnDaoImpl cf = new GenerateInsertReturnDaoImpl();
        String path=cf.makeJavaFile(table , null);
    %>
		<a href="<%=path%>">dao实现</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	 <!-- dao接口-->
    <%
   		GenerateInsertReturnInterface inter=new GenerateInsertReturnInterface();
   		inter.setDao(true);
        String interStr=inter.makeJavaFile(table , null);
    %>
    <a href="<%=interStr%>">dao接口</a>
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
			GenerateInsertReturnService service = new GenerateInsertReturnService();
			String serviceImplPath = service.makeJavaFile(table, null);
	%>
	<a href="<%=serviceImplPath%>">service实现</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	 <!-- service接口-->
    <%
   		GenerateInsertReturnInterface inter1=new GenerateInsertReturnInterface();
   		inter1.setDao(false);
        String interStr1=inter1.makeJavaFile(table , null);
    %>
    <a href="<%=interStr1%>">service接口</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	
	<!-- 屏幕代码 -->
	<br><br><br>
	//<%=table%>插入DAO层
	<% 
		GenerateDaoImpl web = new GenerateDaoImpl();
		String insertString = web.makeInsertReturnWeb(table, true);
	%>
	<p style="color:blue"><%=insertString%></p>
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
