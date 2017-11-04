<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="content-language" content="zh-CN"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>域名管理</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script>

    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <a href="<%=PropertiesContainer.getToolsLoginOutURL()%>" style="float:right;">退出</a>
        </td>
    </tr>
    <tr>
        <td>
            <form action="/ac/joymedomain/subdomain/modify.do" method="post" id="form_submit">
                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <input type="hidden" name="maindomain" value="${subDomain.mainDomain}"/>
                    <tr>
                        <td><input type="hidden" name="domainname" value="${subDomain.domainName}"/></td>
                    </tr>
                    <tr>
                        <td>域名</td>
                        <td>${subDomain.domainName}.${subDomain.mainDomain}</td>
                    </tr>
                    <tr>
                        <td>首页</td>
                        <td><input type="text" name="indexurl" value="${subDomain.indexUrl}"/></td>
                    </tr>
                    <tr>
                        <td>部门</td>
                        <td>
                            <input type="text" name="usedept" value="${subDomain.useDept}"/>
                        </td>
                    </tr>
                    <%--<tr>--%>
                        <%--<td>SEO备注</td>--%>
                        <%--<td>--%>
                            <%--<textarea cols="20" rows="3" name="seodesc">${subDomain.seoDesc}</textarea>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td>备注</td>
                        <td>
                            <textarea cols="20" rows="3" name="domaindesc">${subDomain.domainDesc}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>状态</td>
                        <td>
                            <select name="removestatus">
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="button" value="提交"/>
                            <input name="Reset" type="button"  value="返回" onclick="javascipt:window.history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
