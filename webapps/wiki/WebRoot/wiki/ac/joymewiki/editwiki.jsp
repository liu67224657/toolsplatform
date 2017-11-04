<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script language="javascript">
	function changeKey() {
		var obj = document.getElementsByName("isDefaultKey");
		var customIndexKey = document.getElementById("customIndexKey");
		var defaultIndexKey = document.getElementById("defaultIndexKey");
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked == true) {
				if (obj[i].value == 1) {
					defaultIndexKey.disabled = false;
					customIndexKey.disabled = true;
				} else {
					defaultIndexKey.disabled = true;
					customIndexKey.disabled = false;
				}
			}
		}
	}

	function changeDiv(obj) {
		var divs = document.getElementsByTagName("div");
		for(var i = 0 ; i < divs.length; i++){
			divs[i].style.display = 'none';
		}
		
		if (obj.value == "image") {
			var imageDiv = document.getElementById("imageDiv");
			imageDiv.style.display = 'block';
		}
		
		if (obj.value == "textlink") {
			var imageDiv = document.getElementById("textlinkDiv");
			imageDiv.style.display = 'block';
		}
		if (obj.value == "flash") {
			var imageDiv = document.getElementById("flashDiv");
			imageDiv.style.display = 'block';
		}
		if (obj.value == "iframe") {
			var imageDiv = document.getElementById("iframeDiv");
			imageDiv.style.display = 'block';
		}
		if (obj.value == "HTML") {
			var imageDiv = document.getElementById("HTMLDiv");
			imageDiv.style.display = 'block';
		}
	}
</script>

<form action="${pageContext.request.contextPath}/wiki/ac/updatejoymewiki.do" name="form1" method="post">
 <input type="hidden" name="joymeWikiId"  value="${joymewiki.joymeWikiId}"/>
<table>
	<tr>
		<td>wiki_key</td>
		<td><input type="text" name="joymeWikiKey" value="${joymewiki.joymeWikiKey}" size="40"></td>
	</tr>
	<tr>
		<td>wiki类型</td>
		<td><input type="text" name="contextPath" value="${joymewiki.contextPath}" size="40"></td>
	</tr>
	<tr>
		<td>wiki_domain</td>
		<td><input type="text" name="joymeWikiDomain" value="${joymewiki.joymeWikiDomain}" size="40"></td>
	</tr>
	<tr>
		<td>wiki名称</td>
		<td><input type="text" name="joymeWikiName" value="${joymewiki.joymeWikiName}" size="40">*若需要区分pc和m，请以"_pc"和"_m"来结尾，若不需要区分，请不要在游戏的名字后面加其他的描述</td>
	</tr>
	<tr>
		<td>二级域名</td>
		<td>
			<select name="supportSubDomain">
				<c:if test="${joymewiki.supportSubDomain==false}">
					<option value="false" selected>否</option>
					<option value="true">是</option>
				</c:if>
				<c:if test="${joymewiki.supportSubDomain==true}">
					<option value="false">否</option>
					<option value="true" selected>是</option>
				</c:if>
			</select>
		</td>
	</tr>
	<tr>
		<td>pc是否保留JSCSS</td>
		<td>
			<select name="pcKeepJscss">
			<c:if test="${joymewiki.pcKeepJscss==0}">
				<option value="0" selected>不保留</option>
				<option value="1">保留</option>
			</c:if>
			<c:if test="${joymewiki.pcKeepJscss==1}">
				<option value="0">不保留</option>
				<option value="1" selected>保留</option>
			</c:if>
			</select>
		</td>
	</tr>
	<tr>
		<td>m是否保留JSCSS</td>
		<td>
			<select name="mKeepJscss">
			<c:if test="${joymewiki.mKeepJscss==0}">
				<option value="0" selected>不保留</option>
				<option value="1">保留</option>
			</c:if>
			<c:if test="${joymewiki.mKeepJscss==1}">
				<option value="0">不保留</option>
				<option value="1" selected>保留</option>
			</c:if>
				</select>
		</td>
	</tr>
</table>

<input type="submit" value="提交"/>
</form>
</html>