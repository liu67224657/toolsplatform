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

<form action="${pageContext.request.contextPath}/wiki/ac/createItemAction.do" name="form1" method="post">
  <input type="hidden" name="wikitype"  value="${wikitype}"/>
  <input type="hidden" name="joymeItemId"  value="-1"/>
<table>
	<tr>
		<td width="200"></td>
		<td width="200">属于哪个wiki</td>
		<td><select name="wiki">
				<c:forEach items="${wikiMap}" var="entry">
					<option value="<c:out value="${entry}"/>"><c:out value="${entry}" /></option>
				</c:forEach>
		</select></td>
	</tr>

	<tr>
		<td></td>
		<td>属于哪个渠道</td>
		<td><select name="channel">
				<c:forEach items="${channelMap}" var="entry">
					<option <c:if test="${entry.key == 'default'}">selected="selected"</c:if> value="<c:out value="${entry.key}"/>"><c:out value="${entry.key}" /></option>
				</c:forEach>
		</select></td>
	</tr>

	<tr>
		<td></td>
		<td>首页 or 子页</td>
		<td><select name="isIndex">
				<option value="0">两者</option>
				<option value="1">首页</option>
				<option value="2">子页</option>
		</select></td>
	</tr>

	<tr>
		<td><input type="radio" name="isDefaultKey" value="1"
			onclick="changeKey()" checked="checked" />默认key</td>
		<td>默认item的key</td>
		<td><select id="defaultIndexKey" name="defaultIndexKey">
				<option value="headimage">头图</option>
				<option value="advertise">广告</option>
				<option value="left_1">左侧栏1</option>
				<option value="left_2">左侧栏2</option>
				<option value="left_3">左侧栏3</option>
				<option value="right_1">右侧栏1</option>
				<option value="right_2">右侧栏2</option>
				<option value="right_3">右侧栏3</option>
		</select>
		头图-headimage &nbsp;广告-advertise &nbsp;左侧栏1-left_1 &nbsp;左侧栏2-left_2 &nbsp;左侧栏3-left_3 &nbsp;右侧栏1-right_1 &nbsp;右侧栏2-right_2 &nbsp;右侧栏3-right_3 &nbsp;
		</td>
	</tr>

	<tr>
		<td><input type="radio" name="isDefaultKey" value="0" onclick="changeKey()" />自定义key</td>
		<td>自定义item的key</td>
		<td><input type="text" id="customIndexKey" name="customIndexKey" disabled="disabled"></td>
	</tr>
	
	<tr>
		<td></td>
		<td>key的描述</td>
		<td><input type="text" name="itemDescription"></td>
	</tr>

	<tr>
		<td></td>
		<td>它是一个什么样的类型</td>
		<td><select id="itemType" name="itemType" onchange="changeDiv(this)">
				<option value="no">===请选择====</option>
				<option value="image">图片</option>
				<option value="textlink">文字链</option>
				<option value="flash">flash</option>
				<option value="iframe">iframe</option>
				<option value="HTML">HTML代码</option>
		</select></td>
	</tr>
</table>

<!-- 图片层 -->
<div id="imageDiv" style="display: none">
	<table>
		<tr>
			<td width="200"></td>
			<td width="200">图片地址（必填）</td>
			<td><input type="text" name="imageUrl"/></td>
		</tr>
		<tr>
			<td></td>
			<td>图片外链</td>
			<td><input type="text" name="imageLinkUrl"/></td>
		</tr>
		<tr>
			<td></td>
			<td>是否链接打开一个新的窗口</td>
			<td><select name="imageIsBlank">
				<option value="0">否</option>
				<option value="1">是</option>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>图片alt信息</td>
			<td><input type="text" name="imageAlt"/></td>
		</tr>
		<tr>
			<td></td>
			<td>图片宽度（必填）</td>
			<td><input type="text" name="imageWidth"/></td>
		</tr>
		<tr>
			<td></td>
			<td>图片高度（必填）</td>
			<td><input type="text" name="imageHeight"/></td>
		</tr>
		<tr>
			<td></td>
			<td>id</td>
			<td><input type="text" name="imageId"/></td>
		</tr>
		<tr>
			<td></td>
			<td>class</td>
			<td><input type="text" name="imageClass"/></td>
		</tr>
	</table>
</div>

<!-- 文字链层 -->
<div id="textlinkDiv" style="display: none">
	<table>
		<tr>
			<td width="200"></td>
			<td width="200">文字链链接地址（必填）</td>
			<td><input type="text" name="texturl"/></td>
		</tr>
		<tr>
			<td></td>
			<td>文本信息（必填）</td>
			<td><input type="text" name="textInfo"/></td>
		</tr>
		<tr>
			<td></td>
			<td>是否链接打开一个新的窗口</td>
			<td><select name="textIsBlank">
				<option value="0">否</option>
				<option value="1">是</option>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>id</td>
			<td><input type="text" name="textId"/></td>
		</tr>
		<tr>
			<td></td>
			<td>class</td>
			<td><input type="text" name="textClass"/></td>
		</tr>
	</table>
</div>

<!-- flash层 -->
<div id="flashDiv" style="display: none">
	<table>
		<tr>
			<td width="200"></td>
			<td width="200">flash地址（必填）</td>
			<td><input type="text" name="flashUrl"/> 例如：http://player.youku.com/player.php/Type/Folder/Fid/20034817/Ob/1/sid/XNjEwMzA4MzUy/v.swf</td>
		</tr>
		<tr>
			<td></td>
			<td>flash宽度（必填）</td>
			<td><input type="text" name="flashWidth"/></td>
		</tr>
		<tr>
			<td></td>
			<td>flash高度（必填）</td>
			<td><input type="text" name="flashHeight"/></td>
		</tr>
	</table>
</div>

<!-- iframe层 -->
<div id="iframeDiv" style="display: none">
	<table>
		<tr>
			<td width="200"></td>
			<td width="200">iframe地址（必填）</td>
			<td><input type="text" name="iframeUrl"/></td>
		</tr>
		<tr>
			<td></td>
			<td>iframe宽度（必填）</td>
			<td><input type="text" name="iframeWidth"/></td>
		</tr>
		<tr>
			<td></td>
			<td>iframe高度（必填）</td>
			<td><input type="text" name="iframeHeight"/></td>
		</tr>
		<tr>
			<td></td>
			<td>id</td>
			<td><input type="text" name="iframeId"/></td>
		</tr>
		<tr>
			<td></td>
			<td>class</td>
			<td><input type="text" name="iframeClass"/></td>
		</tr>
	</table>
</div>

<!-- HTML层 -->
<div id="HTMLDiv" style="display: none">
	<table>
		<tr>
			<td width="200"></td>
			<td width="200">HTML</td>
			<td><textarea name="htmlContext" cols="120" rows="40"></textarea></td>
		</tr>
	</table>
</div>

<input type="submit" value="提交"/>
</form>
</html>