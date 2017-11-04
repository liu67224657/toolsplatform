<%@ page import="com.enjoyf.util.AppUtil" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (!AppUtil.checkIsAndroid(request) && !AppUtil.checkIsIOS(request)) {
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>500 - ${jmh_title}</title>
    <link href="http://www.joyme.com/static/theme/default/css/core.css?${version}" rel="stylesheet" type="text/css"/>
    <link href="http://www.joyme.com/static/theme/default/css/global.css?${version}" rel="stylesheet" type="text/css"/>
    <link href="http://www.joyme.com/static/theme/default/css/common.css?${version}" rel="stylesheet" type="text/css"/>
    <script src="http://www.joyme.com/static/js/common/seajs.js"
            data-main="http://www.joyme.com/static/js/init/common-init"></script>
</head>
<body>
<!--头部开始-->
<c:import url="./header.jsp"/>
<!--头部结束-->
<!--中间部分开始-->
<div class="content clearfix">
    <div class="page_error">
        <div class="page_error_con">
            <p> 囧~</p>

            <h3>对不起！此页面不存在</h3>
        </div>
        <div class="page_error_area">
            <a href="${URL_WWW}/home">返回个人中心</a>
            <a href="${URL_WWW}" class="see_auto">去首页</a>
        </div>
    </div>
</div>
<!--中间部分结束-->
<!--页尾开始-->
<%@include file="./footer.jsp" %>
<!--页尾结束-->
</body>
</html>
<% } else {%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>404</title>
    <link href="http://www.joyme.com/static/theme/wap/css/errorpage.css" rel="stylesheet" type="text/css">
<body>
<div id="wrapper">
    <div class="no-net">
        <b><img src="http://www.joyme.com/static/theme/wap/images/no-net.jpg" alt=""></b>

        <p>页面加载失败</p>

        <p>你访问的页面走丢了</p>
    </div>
</div>
</body>
</html>
<%}%>
