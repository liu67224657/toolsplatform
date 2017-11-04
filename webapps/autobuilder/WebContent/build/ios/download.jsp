<%@ page import="java.net.InetAddress" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>IOS 下载</title></head>

<body>
<c:forEach var="appPath" items="${apkPathList}">
    <a href="${ctx}/build/ios/download.do?name=${appPath}&logid=${lid}">${appPath}</a>
</c:forEach>
</body>

</br>

</br>
请访问以下链接执行在线安装
<a href="${wirelessURL}">在线安装</a>
</html>