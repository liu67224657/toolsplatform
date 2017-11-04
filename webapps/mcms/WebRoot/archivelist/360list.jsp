<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.enjoyf.mcms.facade.ArchiveFacade"%>
<%@ page import="com.enjoyf.mcms.bean.temp.PageBean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="http://lib.joyme.com/static/theme/default/css/core.css" rel="stylesheet" type="text/css" />
<link href="../css/joymemobile.css" rel="stylesheet" type="text/css" />
<title>
360-
<c:if test="${pageBean.navigate.level1Str != null}">${pageBean.navigate.level1Str}-</c:if>
<c:if test="${pageBean.navigate.level2Str != null}">${pageBean.navigate.level2Str}</c:if>
<c:if test="${pageBean.navigate.level1Str == null && pageBean.navigate.level2Str == null}">更多</c:if>
</title>
</head>
<body style="background:#f3f3f3">
	<div class="location"><a href="${pageBean.navigate.level1Add}">${pageBean.navigate.level1Str}</a>&gt;${pageBean.navigate.level2Str}</div>
	<div class="catalogue">
		<ul>
			<c:forEach  var="item" items="${pageBean.retList}">
				<li><a href="../${item.htmlPath}/${item.htmlFile}">${item.title}</a></li>
			</c:forEach>
		</ul>
		<ul>
			<c:if test="${param.pageNum > 1}">
				<a href="./${param.pointId}_${param.pageNum - 1}.shtml"><span>上一页</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${pageBean.hasNextPage==true}">
				<a href="./${param.pointId}_${param.pageNum + 1}.shtml"><span>下一页</span></a>
			</c:if>
		</ul>
	</div>
	<div class="copyright">本专题由着迷网提供，最全手游攻略，就在着迷网 <br>
			www.joyme.com </div>
</body>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F92eeb6ec4fbf62ae81ae476badb608a9' type='text/javascript'%3E%3C/script%3E"));
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F0a10eee10a39c33d4a8a61d3ad35a96f' type='text/javascript'%3E%3C/script%3E"));
</script> 
</html>