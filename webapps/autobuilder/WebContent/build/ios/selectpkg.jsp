<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
选择编译的包名:
<form action="${ctx}/build/ios/page.do" method="post">
    <select name="code">
        <option>请选择包名</option>
        <c:forEach var="pkg" items="${pkgSet}">
            <option value="${pkg}">${pkg}</option>
        </c:forEach>
    </select>
    <input type="submit" value="选择">
</form>
<br/>
<hr/>
<br/>
<br/>
</body>
</html>