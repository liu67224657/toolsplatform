<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="content-language" content="zh-CN" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>域名管理</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
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
            <form action="/ac/joymedomain/create.do" method="post" id="form_submit">
                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>域名</td>
                        <td><input type="text" name="domainname" value=""/></td>
                    </tr>
                    <tr>
                        <td>购买时间</td>
                        <td><input type="text" name="buydate" onClick="WdatePicker({autoPickDate:true,dateFmt:'yyyy-MM-dd'})" readonly/></td>
                    </tr>
                    <tr>
                        <td>到期时间</td>
                        <td><input type="text" name="expiredate" onClick="WdatePicker({autoPickDate:true,dateFmt:'yyyy-MM-dd'})" readonly/></td>
                    </tr>
                    <tr>
                        <td>购买商家</td>
                        <td><input type="text" name="buymerchant" value=""/></td>
                    </tr>
                    <tr>
                        <td>购买人</td>
                        <td><input type="text" name="buyuser" value=""/></td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td><textarea rows="3" cols="20" name="domaindesc"></textarea></td>
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
