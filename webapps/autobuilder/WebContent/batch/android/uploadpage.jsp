<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title>


</head>
<body>
<form action="${ctx}/batch/android/template/upload.do" method="post" enctype="multipart/form-data" id="myfrom">
    <table>
        <tr>
            <td>上传文件：</td>
            <td><input type="file" name="zip"/></td>
            <td></td>
        </tr>
        <tr>
            <td>填写模板编码：</td>
            <td><input name="code" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td>填写版本：</td>
            <td><input name="version" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交" /></td>
        </tr>
    </table>
</form>
</body>
</html>