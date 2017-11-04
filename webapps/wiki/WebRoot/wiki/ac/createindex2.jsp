<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.Set"%>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<title>生成一个首页</title>
</head>
<%--<%if(session.getAttribute("adminUser") == null){--%>
	<%--return;--%>
<%--}%>--%>
<script type="text/javascript">
	function check(){
		if(document.form1.key.value == ''){
			alert('key不能为空');
			document.form1.key.focus();
			return false;
		}
		if(document.form1.domain.value == ''){
			alert('domain不能为空');
			document.form1.domain.focus();
			return false;
		}
		if(document.form1.url.value == ''){
			alert('url不能为空');
			document.form1.url.focus();
			return false;
		}
	}

	$(document).ready(function(){
		$("#key").bind("input propertychange",function(){
			var key = $(this).val().trim();
			if(key==""){
				return;
			}
			var domain ="http://"+key+".joyme.com";
			var url ="http://"+key+".joyme.com/wiki/%E9%A6%96%E9%A1%B5";
			$("#domain").val(domain);
			$("#url").val(url);
		});
	});
</script>


<body>
<b>这个功能用于第一次生成首页</b>
	<form action="./createindex_action.jsp" name="form1" method="post" onsubmit="return check()">
	<input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>
	<input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>
	<input type="hidden" name="type" value="1"/>
	<table>
        <tr>
			<td>WIKI的中文名称</td>
			<td><input type="text" name="wikiName" size="50"/></td>
			<td>WIKI的中文名称，比填项</td>
		</tr>
		<tr>
			<td>key</td>
			<td><input type="text" name="key" size="50" value="" id="key"/></td>
			<td>比如mt mkhx mjh等，生成后为http://www.joyme.com/mt 后面那个mt</td>
		</tr>
		<tr>
			<td>域名</td>
			<td><input type="text" name="domain" size="50" value="http://mt.joyme.com" id="domain"/></td>
			<td>比如 http://mt.joyme.com 为原始的mt wiki的域名 ,最后不能有/</td>
		</tr>
        <tr>
         <td>手机版还是PC版本</td>
			<td>
				<select name="wikitype">
					<option value="wiki">PC版</option>
                    <option value="mwiki">手机版</option>
				</select>
			</td>
        </tr>
		<tr>
			<td>首页地址</td>
			<td><input type="text" name="url" size="50" value="http://mt.joyme.com/wiki/%E9%A6%96%E9%A1%B5" id="url"/></td>
			<td>比如 http://mt.joyme.com/wiki/%E9%A6%96%E9%A1%B5</td>
		</tr>
        <tr>
         <td>支持二级域名</td>
			<td>
				<select name="supportDomain">
					<option value="false">否</option>
                    <option value="true">是</option>
				</select>
			</td>
        </tr>
		<tr>
			<td>路径</td>
			<td><input type="text" name="path" value="/wiki/" size="50"></td>
			<td>访问子页面的中间的路径比如 http://mt.joyme.com/wiki/ 除非有特殊情况才改动。</td>
		</tr>
				
		<tr>
			<td>android APK地址</td>
			<td><input type="text" name="androidPath" value="" size="50"></td>
			<td>android下载包的地址，如果有用户通过移动端的seo访问的时候，会在第5个页面弹出下载框</td>
		</tr>
		
		<tr>
			<td>IOS appstore地址</td>
			<td><input type="text" name="iosPath" value="" size="50"></td>
			<td>appstore的地址，如果有用户通过移动端的seo访问的时候，会在第5个页面弹出下载框</td>
		</tr>


		<tr>
			<td>PC_是否保留JSCSS</td>
			<td>
				<select name="pcKeepJscss">
					<option value="0" selected>否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>

		<tr>
			<td>M_是否保留JSCSS</td>
			<td>
				<select name="mKeepJscss">
					<option value="0" selected>否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		
	</table>
	<input type="submit" value="提交"> 		 
	</form>
	
<br><br><br><br><br><br>
<b>日常更新工作，每日更新等等，请点下面这个，不需要重复输入了.....</b>
<br>
<b>这个功能用于第二次-第N次刷新首页，不需要重复输入上面的信息，提交即可</b>

	<%--<form action="./createindex_action.jsp" method="post">--%>
	<%--<input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>--%>
	<%--<input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>--%>
	<%--<input type="hidden" name="type" value="2"/>--%>
	<%--<table>--%>
		<%--<tr>--%>
			<%--<td>key</td>--%>
			<%--<td>--%>
				<%--<select name="key">--%>
				<%--<%--%>
					<%--Set keySet = PropertiesContainer.joymeWikiMap.keySet();--%>
					<%--for(Iterator iter = keySet.iterator() ; iter.hasNext(); ){--%>
						<%--String key = iter.next().toString();--%>
				<%--%>--%>
					<%--<option value="<%=key%>"><%=key%></option>					--%>
				<%--<%}%>--%>
				<%--</select>--%>

			<%--</td>--%>
			<%--<td></td>--%>
		<%--</tr>--%>
	<%--</table>--%>
	<%--<input type="submit" value="提交"> 		 --%>
	<%--</form>--%>
</body>
</html>