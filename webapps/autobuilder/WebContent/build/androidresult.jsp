<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<c:choose>
    <c:when test="${success}">
        build成功可以开始打包<br/>
        <a href="${ctx}/build/android/packageone.do?pkgname=${pkgname}">打包一个</a><br/>
        <a href="${ctx}/build/android/package.do?pkgname=${pkgname}">打包全部渠道</a>
    </c:when>
    <c:otherwise>
        build失败
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${fn:length(error)>0}">
       <span style="color:red">${error}</span>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
</body>
</html>