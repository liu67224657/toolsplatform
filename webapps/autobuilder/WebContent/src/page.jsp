<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet" media="screen">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <a href="${ctx}/src/android/uploadpage.do">上传SRC包</a> <br/>

    已经可使用的源码


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

        <c:forEach var="srcObj" items="${srcList}">

            <tr>
                <td>${srcObj.srcVersion}</td>


                <td>${srcObj.srcType}</td>

                <td> ${srcObj.srcUrl} </td>

            </tr>

        </c:forEach>

        </tbody>
    </table>

</div>
</body>
</html>