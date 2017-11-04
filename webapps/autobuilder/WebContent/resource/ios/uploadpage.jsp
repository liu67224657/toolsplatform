<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<form action="${ctx}/resource/ios/upload.do" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>填写名称：</td>
            <td><input name="name" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td>填写包名：</td>
            <td><input name="code" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td>上传文件：</td>
            <td><input type="file" name="resourcezip"/></td>
            <td></td>
        </tr>
        <tr>
            <td>填写版本：</td>
            <td><input name="version" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
</body>
</html>