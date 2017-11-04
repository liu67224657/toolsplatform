<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<c:choose>
    <c:when test="${fn:length(error)>0}">
       <span style="color:red">${error}</span>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
</body>
</html>