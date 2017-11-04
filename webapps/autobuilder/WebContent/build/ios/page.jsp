<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<form action="${ctx}/build/ios/build.do" method="post">

    <table>
        <tr>
            <td>包名：</td>
            <td><input name="pkgname" value="${pkgname}" readonly="true"/></td>
            <td></td>
        </tr>
        <tr>
            <td>应用名称：</td>
            <td><input name="appname" value="${appname}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>APPKEY：</td>
            <td><input name="appkey" value="${appkey}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>ITUNSURL：</td>
            <td><input name="itunesurl" value="${itunesurl}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Umeng_key：</td>
            <td><input name="umengkey" value="${umengkey}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>苹果APPID：</td>
            <td><input name="appid" value="${appid}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>应用类型：</td>
            <td>
                <select name="apptype">
                    <option value="wiki" <c:if test="${fn:contains(apptype,'wiki')}">selected </c:if> >wiki</option>
                    <option value="cms" <c:if test="${fn:contains(apptype,'cms')}">selected</c:if>>cms</option>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>Debug:</td>
            <td>
                <label><input name="debug" type="checkbox" id="debug" <c:if test="${debug}">checked</c:if> />Debug</label>
            </td>
        </tr>
        <tr>
            <td>环境选择：</td>
            <td>
                <label><input name="env" type="checkbox" id="env" value="alpha" <c:if test="${fn:contains(envs,'alpha')}">checked</c:if> />alpha</label>
                <label><input name="env" type="checkbox" id="env" value="beta" <c:if test="${fn:contains(envs,'beta')}">checked</c:if> />beta</label>
                <label><input name="env" type="checkbox" id="env" value="dev" <c:if test="${fn:contains(envs,'dev')}">checked</c:if> />dev</label>
                <label><input name="env" type="checkbox" id="env" value="com" <c:if test="${fn:contains(envs,'com')}">checked</c:if> />com</label>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>渠道选择：</td>
            <td>
                <label><input name="channel" type="checkbox" id="channel" value="91"  <c:if test="${fn:contains(channels,'91')}">checked</c:if> />91市场</label>
                <label><input name="channel" type="checkbox" id="channel" value="tongbutui" <c:if test="${fn:contains(channels,'tongbutui')}">checked</c:if>  />同步推</label>
                <label><input name="channel" type="checkbox" id="channel" value="joyme" <c:if test="${fn:contains(channels,'joyme')}">checked</c:if>  />JOYME</label>
                <label><input name="channel" type="checkbox" id="channel" value="pp" <c:if test="${fn:contains(channels,'pp')}">checked</c:if>  />PP助手</label>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择SRC：</td>
            <td><select name="srcid">
                <c:forEach var="srcObj" items="${srcList}">
                    <option value="${srcObj.srcId}"
                            <c:if test="${srcid==srcObj.srcId}">selected</c:if> >${srcObj.srcVersion}-${srcObj.srcType}</option>
                </c:forEach>
            </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>选择资源包：</td>
            <td><select name="resid">
                <c:forEach var="resObj" items="${resourceList}">
                    <option value="${resObj.resourceId}"
                            <c:if test="${resid==resObj.resourceId}">selected</c:if>  >${resObj.resourceName}-${resObj.resourceCode}-${resObj.resourceVersion}</option>
                </c:forEach>
            </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td>Version：</td>
            <td><input name="version" value="${version}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>关于标题：</td>
            <td><input name="abouttitle" value="${abouttitle}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>接口的域名：</td>
            <td><input name="onlinedomain" value="${onlinedomain}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>测试接口的域名：</td>
            <td><input name="betadomain" value="${betadomain}"/></td>
            <td></td>
        </tr>

        <tr>
            <td>webviewhost：</td>
            <td><input name="webviewhost" value="${webviewhost}"/></td>
            <td>*只打包WIKI时候填写，必须填写</td>
        </tr>
        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>

</body>
</html>