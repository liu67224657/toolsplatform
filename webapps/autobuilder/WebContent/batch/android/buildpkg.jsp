<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
选择编译的包名:
<form action="${ctx}/batch/android/pkg/build.do" method="post">
    <table>
        <tr>
            <td>应用名称：</td>
            <td><input name="name" value="${appname}" size="32"/></td>
            <td></td>
        </tr>
        <tr>
            <td>APP的title：</td>
            <td><input name="title" value="${title}" size="32"/></td>
            <td></td>
        </tr>
        <tr>
            <td>专区code：</td>
            <td><input name="code" value="${code}" size="32"/></td>
            <td>http://marticle.joyme.com/marticle/<b style="color:red">zhiwudazhanjiangshi2</b>.html</td>
        </tr>
        <tr>
            <td>选择模板：</td>
            <td><select name="templateid">
                <option>请选择模板</option>
                <c:forEach var="template" items="${list}">
                    <option value="${template.templateId}">${template.templateCode}</option>
                </c:forEach>
            </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择ICON：</td>
            <td>
                <c:forEach var="icon" items="${iconList}">
                    <input type="radio" name="iconid" value="${icon.id}"/><img src="http://www.autopkg.test${icon.picS}" width="24"
                                                                               height="24"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择loading：</td>
            <td>
                <c:forEach var="load" items="${loadingList}">
                    <input type="radio" name="loadid" value="${load.id}"/><img src="http://www.autopkg.test${load.picM}" width="80"
                                                                               height="128"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择背景图</td>
            <td>
                <c:forEach var="background" items="${backgroundList}">
                    <input type="radio" name="backgroundid" value="${background.id}"/><img src="http://www.autopkg.test${background.picM}"
                                                                                           width="80" height="128"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>VersionCode：</td>
            <td><input name="vcode" value="" size="32"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Version：</td>
            <td><input name="version" value="" size="32"/></td>
            <td></td>
        </tr>
        <tr>
            <td>分享内容：</td>
            <td><textarea name="share"></textarea></td>
            <td></td>
        </tr>
        <tr>
            <td>微信key：</td>
            <td><input type="input" name="wxkey" size="32"/></td>
            <td></td>
        </tr>

        <tr>
            <td>是否打包全部</td>
            <td>
                <input type="checkbox" name="isall" value="true">打包全部
            </td>
            <td></td>
        </tr>

        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
</body>
</html>