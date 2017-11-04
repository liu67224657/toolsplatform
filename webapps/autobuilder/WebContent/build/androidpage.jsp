<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%--<html>
<head><title>Simple jsp page</title></head>
<body>--%>
<form action="${ctx}/build/android/build.do" method="post">
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
            <td>应用名称缩写：</td>
            <td><input name="appshortname" value=""/></td>
            <td></td>
        </tr>
        <tr>
            <td>环境：</td>
            <td>
                正式：
                <input type="radio" checked="checked" name="host" value="api"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                alpha：
                <input type="radio" name="host" value="alpha"/>
            </td>
        </tr>


        <tr>
            <td>APPKEY：</td>
            <td><input name="appkey" value="${appkey}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Umeng_key：</td>
            <td><input name="umengkey" value="${umengkey}"/></td>
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
            <td>选择assests和res：</td>
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
            <td>VersionCode：</td>
            <td><input name="vcode" value="${vcode}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Version：</td>
            <td><input name="version" value="${version}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>顶部标题：</td>
            <td><input name="toptitle" value="${toptitle}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>关于标题：</td>
            <td><input name="abouttitle" value="${abouttitle}"/></td>
            <td></td>
        </tr>


        <tr>
            <td>关于内容的前缀：</td>
            <td><input name="aboutcontent" value="${aboutcontent}"/></td>
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
            <td>notifyurl：</td>
            <td><input name="notifyurl" value="${notifyurl}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>一级导航默认颜色：</td>
            <td><input name="mcolorn" class="form-control" value="${mcolorn}" id="cp1"/></td>
            <td></td>
        </tr>
        <tr>
            <td>一级导航点击颜色：</td>
            <td><input name="mcolora" value="${mcolora}" id="cp2"/></td>
            <td></td>
        </tr>
        <tr>

            <td>WIKI域名：</td>
            <td><input name="wikihost" value="${wikihost}"/></td>
            <td>*只打包WIKI时候填写，必须填写</td>
        </tr>


        <td>APP_NAME_FLAG：</td>
        <td><input name="app_name_flag" /></td>

        </tr>
        <td>IMAGE_URL_HOST：</td>
        <td><input name="image_url_host"  /></td>

        </tr>


        <tr>
            <td colspane="3"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>

<%--
</body>
</html>--%>
