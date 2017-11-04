<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询结果</title>
    <script type="text/javascript">
        function checkSpecName() {
            /**
             if (document.formsp.specname.value == '') {
             alert('请输入名字');
             document.formsp.specname.focus();
             return false;
             }
             **/
            return true;
        }
    </script>
</head>
<%
    if (session.getAttribute("ac-user") == null) {
        response.sendRedirect("./login.jsp");
        return;
    }
%>
<body>
<a href="/marticle/ac/main.jsp" style="color:red;">返回首页</a>
</br>
输入中文查询游戏包名，如查询"植物大战僵尸"：输入 "植、植物、植物大"可匹配，输入”大战、僵尸“不匹配
<form name="formsp" method="post" action="${pageContext.request.contextPath}/marticle/ac/queryListSpec.do"
      onsubmit="return checkSpecName()">
    <input type="text" size="40" name="specname">
    <input type="submit" value="提交"/>
</form>
<p style="color:blue;">查询关键字：${specname} &nbsp;&nbsp;返回条数：${size}</p>
<table border=1 style="BORDER-COLLAPSE: collapse" width="800">
    <tr>
        <td width="200px">游戏名称</td>
        <td width="200px">英文</td>
        <td width="200px">包名</td>
        <td width="50px">操作</td>
    </tr>
    <c:forEach var="item" items="${listJoymeSpec}">
        <tr>
            <td>${item.specName}</td>
            <td>${item.filePath}</td>
            <td>${item.packageName}</td>
            <td><a href="${pageContext.request.contextPath}/marticle/ac/querybyid.do?specid=${item.specId}">编辑</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>