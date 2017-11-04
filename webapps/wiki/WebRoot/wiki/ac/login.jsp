<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.enjoyf.util.CookieUtil" %>
<%@ page import="com.enjoyf.util.ToolsLoginUtil" %>
<%@ page import="com.enjoyf.util.StringUtil" %>
<%@ page import="com.enjoyf.util.MD5Util" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<style type="text/css">
    body {
        font-size: 12px;
        font-family: tahoma;
    }

    div {
        margin-top: 300px;
        height: 25px;
        line-height: 25px;
        /**
        border:1px solid #FF0099;
        background-color:#FFCCFF;**/
    }
</style>
<html>
<%

    String href = "";
    String messgae = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_MESSAGE);
    String encrypt = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_ENCRYPT);
    if (messgae == null || encrypt == null || StringUtil.isEmpty(messgae) || StringUtil.isEmpty(encrypt)) {
        href = PropertiesContainer.getInstance().getWikiLoginAction();
    }

    String utfMesage = null;
    try {
        if (messgae != null) {
            utfMesage = java.net.URLDecoder.decode(messgae, "utf-8");
            String marticleEncrypt = MD5Util.Md5(ToolsLoginUtil.TOOLS_COOKIEKEY_SECRET_KEY + utfMesage);
            if (encrypt.equals(marticleEncrypt)) {
                String roid = utfMesage.split("\\|")[0];
                String roidArr[] = roid.split(",");
                Set<Integer> roidSet = new HashSet<Integer>();
                for (String id : roidArr) {
                    roidSet.add(Integer.valueOf(id));
                }
                if (roidSet.contains(1) || roidSet.contains(102)) {
                    href = PropertiesContainer.getInstance().getWikiMainPage();
                } else {
                    href = null;
                }
            } else {
                href = PropertiesContainer.getInstance().getWikiLoginAction();
            }
        }
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
%>
<body>
<%--<form method="post" action="${pageContext.request.contextPath}/wiki/ac/login.do">--%>
<%--用户名：<input type="text"  name="userName" value=""><br>--%>
<%--密码：<input type="password"  name="password" value=""><br/>--%>
<%--<input type="submit" value="登录"/>--%>
<%--</form>--%>
<%--</body>--%>
<%--<c:if test="${failed == true}">--%>
<%--用户名密码错误--%>
<%--</c:if>--%>
<div style="text-align:center;font-size: 24px;">
    <div style="color: #ff0000;display: none;" id="wikipage">无数字站权限，请从OA申请相关权限或联系管理员。</div>
    <a href="<%=href%>" id="login">点击跳转至tools登录数字站</a>
</div>



<script>
    var href ="<%=href%>";
    if(href=="null"){
        document.getElementById("wikipage").style.display="block";
       document.getElementById("login").href="<%=PropertiesContainer.getInstance().getWikiLoginAction()%>";
    }
</script>
</body>
</html>
