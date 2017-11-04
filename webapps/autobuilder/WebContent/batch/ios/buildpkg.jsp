<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
IOS打包操作
<form action="${ctx}/batch/ios/pkg/build.do" method="post">
    <table>
        <tr>
            <td>应用名称：</td>
            <td><input name="name" value="${appname}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>APP的title：</td>
            <td><input name="title" value="${title}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>专区code：</td>
            <td><input name="code" value="${code}"/></td>
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
                    <input type="radio" name="iconid" value="${icon.id}"/><img src="http://172.16.77.99${icon.picXB}"  width="24" height="24"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择loading：</td>
            <td>
                <c:forEach var="load" items="${loadingList}">
                    <input type="radio" name="loadid" value="${load.id}"/><img src="http://172.16.77.99${load.picM}" width="80" height="128"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择背景图</td>
            <td>
                <c:forEach var="background" items="${backgroundList}">
                    <input type="radio" name="backgroundid" value="${background.id}"/><img src="http://172.16.77.99${background.picM}"
                                                                                           width="80" height="128"/>
                </c:forEach>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>Version：</td>
            <td><input name="version" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td>分享内容：</td>
            <td><textarea name="share"></textarea></td>
            <td></td>
        </tr>
        <tr>
            <td>渠道选择：</td>
            <td>
                <label><input name="channel" type="checkbox"  value="91" />91市场</label>
                <label><input name="channel" type="checkbox"  value="tongbutui"  />同步推</label>
                <label><input name="channel" type="checkbox"  value="joyme"/>JOYME</label>
                <label><input name="channel" type="checkbox"  value="pp"  />PP助手</label>
            </td>
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