<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.min.js"></script>
<style>
    .tr_class_colo{
        background-color:#DFDFDF;
    }
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改专区列表排列方式</title>
    <script type="text/javascript">
        function checkSpecName() {
            if (document.formsp.htmlfile.value == '') {
                alert('请输入名称');
                document.formsp.htmlfile.focus();
                return false;
            }
            return true;
        }

        function update(specPointId) {
            var htmlfile = $("#htmlfile").val();
            var isrank = $("#isrank_" + specPointId).val();
            window.location.href = "${pageContext.request.contextPath}/marticle/ac/updatePointList.do?htmlfile=" + htmlfile + "&isrank=" + isrank + "&specPointId=" + specPointId;
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
搜索:http://www.joyme.com/article/game/<span style="color:red;">koudaimenghuan</span>.html 放入红色的部分
<form name="formsp" method="post" action="${pageContext.request.contextPath}/marticle/ac/queryJoymePointList.do"
      onsubmit="return checkSpecName()">
    <input type="text" size="40" name="htmlfile" value="${htmlfile}" id="htmlfile">
    <input type="submit" value="提交"/>
</form>

<table border=1 style="BORDER-COLLAPSE: collapse" width="800">
    <tr class="tr_class_colo">
        <td width="100px" align="center">列表名称</td>
        <td width="100px" align="center">排列方式</td>
        <td width="50px" align="center">操作</td>
    </tr>
    <c:forEach var="item" items="${list}">
        <tr>
            <td align="center">${item.specPointName}</td>
            <td align="center">
                <select name="isrank_${item.specPointId}" id="isrank_${item.specPointId}" style="width:200px;">
                    <option value="0" <c:if test="${item.isrank==0}">selected</c:if>>竖排</option>
                    <option value="1" <c:if test="${item.isrank==1}">selected</c:if>>横排</option>
                </select>
            </td>
            <td align="center">
                <a href='javascript:update(${item.specPointId})'>保存</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>