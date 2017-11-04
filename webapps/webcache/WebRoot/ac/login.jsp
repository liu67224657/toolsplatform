<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.util.CookieUtil" %>
<%@ page import="com.enjoyf.util.ToolsLoginUtil" %>
<%@ page import="com.enjoyf.util.StringUtil" %>
<%@ page import="com.enjoyf.util.MD5Util" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="com.enjoyf.webcache.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
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
<body>
<%

    String href = "";
    //是否有cmsimage组权限
    String messgae = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_MESSAGE);
    String encrypt = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_ENCRYPT);
    if (messgae == null || encrypt == null || StringUtil.isEmpty(messgae) || StringUtil.isEmpty(encrypt)) {
        href = PropertiesContainer.getInstance().getToolsLoginURL();
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
                if (roidSet.contains(1) || roidSet.contains(104)) {
                    href = PropertiesContainer.getInstance().getCmsimageMainJsp();
                } else {
                    href = null;
                }
            } else {
                href = PropertiesContainer.getInstance().getToolsLoginURL();
            }
        }
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
%>
<%--<form method="post" action="${pageContext.request.contextPath}/ac/login.do"> --%>
<%--用户名：<input type="text"  name="userName" value=""><br>--%>
<%--密码：<input type="password"  name="password" value=""><br/>--%>
<%--<input type="submit" value="登录"/>--%>
<%--</form>--%>



<div style="text-align:center;font-size: 24px;">
      <div style="color: #ff0000;display: none;" id="cmsimage">无CMSIMAGE权限，请从OA申请相关权限或联系管理员。</div>
      <a href="<%=href%>" id="login">点击跳转至tools登录cmsimage</a>
</div>
<script>
    var href ="<%=href%>";
    if(href=="null"){
        document.getElementById("cmsimage").style.display="block";
        document.getElementById("login").href="<%=PropertiesContainer.getInstance().getToolsLoginURL()%>";
    }
</script>
</body>
</html>
