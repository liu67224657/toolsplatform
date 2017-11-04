<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<a href="${ctx}/resource/android/uploadpage.do">上传资源包</a> <br/>

可使用的资源

<table class="table table-striped table-bordered table-hover">
    <caption>...</caption>
    <thead>
    <tr class="success">
        <th>资源名称</th>
        <th>包名</th>
        <th>版本</th>
        <th>assets地址</th>
        <th>res地址</th>

    </tr>
    </thead>
    <tbody>

    <c:forEach var="resObj" items="${resourceList}">

        <tr>
            <td>${resObj.resourceName}</td>
            <td>${resObj.resourceCode}</td>
            <td>${resObj.resourceVersion}</td>
            <td>${resObj.resourceAssets}</td>
            <td>${resObj.resourceRes}</td>
        </tr>

    </c:forEach>

    </tbody>
</table>


</body>
</html>