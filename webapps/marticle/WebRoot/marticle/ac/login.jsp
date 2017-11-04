<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.enjoyf.mcms.facade.ArchiveFacade" %>
<%@ page import="com.enjoyf.mcms.util.StringUtil" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="com.enjoyf.util.MD5Util" %>
<%@ page import="com.enjoyf.util.SystemUtil" %>
<%@ page import="org.apache.commons.collections.bag.SynchronizedSortedBag" %>
<%@ page import="com.sun.org.apache.xpath.internal.SourceTree" %>
<%@ page import="com.enjoyf.mcms.container.ConfigContainer" %>
<%@ page import="com.enjoyf.util.CookieUtil" %>
<%@ page import="com.enjoyf.util.ToolsLoginUtil" %>
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

    session.setAttribute("ac-user", "ac-user");
    String href = "";
    String messgae = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_MESSAGE);
    String encrypt = CookieUtil.getCookieValue(request, ToolsLoginUtil.TOOLS_COOKIEKEY_ENCRYPT);
    if (messgae == null || encrypt == null || StringUtil.isEmpty(messgae) || StringUtil.isEmpty(encrypt)) {
        href = ConfigContainer.getToolsLoginURL();
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
                if (roidSet.contains(1) || roidSet.contains(103)) {
                    href = ConfigContainer.getMarticleMainJsp();
                } else {
                    href = null;
                }
            } else {
                href = ConfigContainer.getToolsLoginURL();
            }
        }
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
%>

<div style="text-align:center;font-size: 24px;">
    <div style="color: #ff0000;display: none;" id="marticle">Tools无marticle组权限，请从OA申请相关权限。</div>
    <a href="<%=href%>" id="login">点击跳转至tools登录marticle</a>
</div>


<script>
    var href = "<%=href%>";
    if (href == "null") {
        document.getElementById("marticle").style.display = "block";
        document.getElementById("login").href="<%=ConfigContainer.getToolsLoginURL()%>";
    }
</script>
</body>
</html>
