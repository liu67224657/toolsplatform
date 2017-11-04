<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
  <script language="javascript">
	function check(){
		if(document.form1.oldPassword.value=='' || document.form1.newPassword.value=='' || document.form1.confirmPassword.value==''){
			alert('没有填完');
			return false;
		}
		if(document.form1.newPassword.value != document.form1.confirmPassword.value){
			alert('两次密码不一样');
			return false;
		}
	}
  </script>
  
  <body>
  	<form name='form1' method="post" action="${pageContext.request.contextPath}/wiki/ac/updatePassword.do" onsubmit="return check()">
  		原密码：<input type="password"  name="oldPassword" value=""><br/>
  		新密码：<input type="password"  name="newPassword" value=""><br/>
  		确认密码：<input type="password"  name="confirmPassword" value=""><br/>
  		<input type="submit" value="确定"/>
  	</form>
  </body>
  <c:if test="${result == true}">
  	 修改成功
  </c:if>
  <c:if test="${result == false}">
  	 修改失败
  </c:if>
  
</html>
