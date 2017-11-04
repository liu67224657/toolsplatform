<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title></title>

</head>
<body>
选择编译的包名:
<table class="table table-striped table-bordered table-hover">
    <caption>...</caption>
    <thead>
    <tr class="success">
        <th>包名</th>
        <th>操作</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="pkg" items="${pkgSet}">
        <tr>
            <td>${pkg}</td>
            <td>
                <input class="btn btn-default" type="button" onclick="pkg('${pkg}')" value="打包">
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>


<form action="${ctx}/build/android/page.do" method="post">
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