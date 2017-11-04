<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <a href="${ctx}/batch/android/template/uploadpage.do">上传模板包</a> <br/>
    已经可使用的模板
    <table class="table table-striped table-bordered table-hover">
        <caption>...</caption>
        <thead>
        <tr class="success">
            <th>版本号</th>
            <th>类型</th>
            <th>地址</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="template" items="${list}">
            <tr>
                <td>${template.templateCode}</td>
                <td>${template.templateVersion}</td>
                <td> ${template.templateUrl} </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>