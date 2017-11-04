<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.ArchiveFacade"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
  
  <body>
  	<form method="post" action="${pageContext.request.contextPath}/ac/loginaction.jsp"> 
  		用户名：<input type="text"  name="userName" value=""><br>
  		密码：<input type="password"  name="password" value=""><br/>
  		<input type="submit" value="登录"/>
  	</form>
  </body>
</html>
