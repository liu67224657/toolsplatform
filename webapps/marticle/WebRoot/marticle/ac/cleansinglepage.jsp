<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

<script language="javascript">
function check(){
	var value = document.form1.pageurl.value;
	if(value == ''){
		alert('输入的url不正确');
		return false;
	}
//	if(value.indexOf('http://marticle.joyme.com/') < 0){
//		alert('输入的url不正确');
//		return false;
//	}
}
</script>

<form action="./cleansinglepageaction.jsp" name="form1" method="post" onsubmit="return check()">
	请输入要刷新的单页面的地址，例如：http://marticle.joyme.com/marticle/rdgl/201308072006.html
	<br><input type="name" name="pageurl" value=""/>
	<input type="submit" value="提交">
</form>


