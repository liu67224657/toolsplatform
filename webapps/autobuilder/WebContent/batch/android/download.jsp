<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<c:forEach var="appPath" items="${apkPathList}">
    <a href="${ctx}/batch/android/pkg/download.do?name=${appPath}&logid=${lid}">${appPath}</a> </br></br>
</c:forEach>
</body>
</html>