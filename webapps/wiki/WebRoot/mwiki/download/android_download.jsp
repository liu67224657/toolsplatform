<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	String androidPath = session.getAttribute("androidPath") + "";
	String incomeUrl = session.getAttribute("incomeUrl") + "";
%>
<script language="javascript">

</script>

<body>
	<form id="form1" name="form1" action="<%=androidPath%>" method="get">
	</form>
</body>


<script language="javascript">
	if (confirm("本wiki现在推出了攻略移动应用哦！离线省流量，内容保更新！安装一下试试看吧！")) {
		document.form1.submit();
		document.write('5秒钟后返回下一个链接');		
		window.setTimeout(nextUrl,5000); 
	}else{
		this.location.href = '<%=incomeUrl%>';
	}
	
	function nextUrl(){ 
		this.location.href = '<%=incomeUrl%>';
	} 
</script>
