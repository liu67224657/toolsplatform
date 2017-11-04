<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
%>

<script type="text/javascript">
function check(){
	if(document.form1.filePath.value == ''){
		alert('请输入专区名字');
		document.form1.filePath.focus();
		return false;
	}
	if(document.form1.filePath.value.indexOf("http://") >= 0){
		alert('请输入红色字体的部分，即专区名称，不是整个http地址');
		document.form1.filePath.focus();
		return false;
	}
}
</script>

搜索:http://www.joyme.com/article/game/<font color="red">koudaimenghuan</font>.html 放入红色的部分
<form name="form1" method="post" action="./main.jsp" onsubmit="return check()">
	<input type="text" size=40 name="filePath">
	<input type="submit" value="提交"/>
</form>

<%
	if(request.getParameter("filePath") != null){
		AdminConsoleFacade facade = new AdminConsoleFacade();
		facade.getJoymeSpecByFilePath(request);
%>
	<c:if test="${joymeSpec == null}">
		${param.filePath}还没有创建
	</c:if>

	<script type="text/javascript">
		function refresh(){
			document.form2.action="${pageContext.request.contextPath}/ac/refresh.jsp"
			document.form2.submit();	
		}
	</script>

	<form name="form2" action="${pageContext.request.contextPath}/ac/createItem.jsp" method="post">
		<input type="hidden" name="specId" value="${joymeSpec.specId}"/>
		<input type="hidden" name="filePath" value="${param.filePath}"/>
		<table>
			<tr>
				<td>专区名：</td>
				<td><input type="text" size="80" name="specName" value="${joymeSpec.specName}"></td>
			</tr>
			<tr>
				<td>类型：</td>
				<td><input type="text" size="80" name="specType" value="${joymeSpec.specType}"></td>
			</tr>
			<tr>
				<td>语言：</td>
				<td><input type="text" size="80" name="specLanguage" value="${joymeSpec.specLanguage}"></td>
			</tr>
			<tr>
				<td>大小：</td>
				<td><input type="text" size="80" name="specSize" value="${joymeSpec.specSize}"></td>
			</tr>
			<tr>
				<td>版本：</td>
				<td><input type="text" size="80" name="specVersion" value="${joymeSpec.specVersion}"></td>
			</tr>
			<tr>
				<td>图片链接：</td>
				<td><input type="text" size="80" name="specPicUrl" value="${joymeSpec.specPicUrl}"></td>
			</tr>
			
		<c:forEach items="${channelMap}" var="entry">
			<tr>
				<td>${entry.key}-下载地址：</td>
				<td><input type="text" size="80" name="${entry.key}_specDownloadUrl" value="${specChannel.downUrlMap[entry.key]}"></td>
			</tr>
			<tr>
				<td>${entry.key}-广告语:</td>
				<td><input type="text" size="80" name="${entry.key}_specAdvertise" value='${specChannel.advertiesMap[entry.key]}'></td>
			</tr>
		</c:forEach>	
			
			
			<tr>
				<td><input type="submit" value="提交"></td>
				<td><c:if test="${joymeSpec != null}"><input type="button" value="清理缓存" onclick="javascript:refresh()"></c:if></td>
			</tr>
		</table>
	</form>
	
	
<%}%>

<br/>
<br/>
<br/>
<a href="checkmp4.jsp">检查视频是否有MP4格式</a>
