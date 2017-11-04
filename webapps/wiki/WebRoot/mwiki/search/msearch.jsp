<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.enjoyf.wiki.search.Search"%>
<%@page import="com.enjoyf.wiki.bean.PageBean"%>
<%
    Search search = new Search();
    search.search(request, response);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>搜索</title>
    <meta charset="utf-8">
    <meta content="width=device.width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title></title>
    <link rel="stylesheet" href="http://reswiki1.joyme.com/css/wap_search/css/wap_styles.css" />
    <link rel="icon" href="http://lib.joyme.com/static/img/favicon.ico" type="image/x-icon" />
    <script type="text/javascript">
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {return false}, true)
        }, true);
    </script>
</head>
<body>
<div id="wrap">
    <div class="wap-tit">搜索</div>
        <div class="wap-search">
                <div><input type="text" placeholder="请输入要搜索的内容" name="search" id="search"></div>
                <a class="icon-search"  href="javascript:void(0)" onclick="msearch()">搜索</a>
                <input type="hidden" name="key" value="${param.key}" id="key">
                <input type="hidden" name="pageNum" value="1" id="pageNum">
                <input type="hidden" name="pageCount" value="10" id="pageCount">
        </div>
    <!--wap-search-list-->
    <div class="wap-search-list">
        <div></div>
        <ul>
            <c:forEach  var="item" items="${pageBean.retList}">
                <li><a href="${item.httpUrl}">${item.wikiUrl}</a></li>
            </c:forEach>
        </ul>
    </div>
    <!--wap-search-list==end-->
    <!--wap-search-page-->
    <div class="wap-search-page">
        <ul>
            <c:if test="${param.pageNum > 1}">
                <li><a href="./msearch.jsp?search=<%=URLEncoder.encode(request.getParameter("search") , "utf-8")%>&pageCount=${param.pageCount}&pageNum=${param.pageNum - 1}&key=${param.key}" class="nextpage"><span>上一页</span></a></li>
            </c:if>
            <c:if test="${pageBean.hasNextPage==true}">
                <li><a href="./msearch.jsp?search=<%=URLEncoder.encode(request.getParameter("search") , "utf-8")%>&pageCount=${param.pageCount}&pageNum=${param.pageNum + 1}&key=${param.key}" class="nextpage"><span>下一页</span></a></li>
            </c:if>
        </ul>
    </div>
    <!--wap-search-page==end-->
</div>
</body>
<script>
    function msearch(){
        var search = document.getElementById("search").value;
        if(search==null || search==""){
            alert("请输入要搜索的内容！");
            return;
        }
        var key = document.getElementById("key").value;
        var pageNum = document.getElementById("pageNum").value;
        var pageCount = document.getElementById("pageCount").value;
       //window.location.href="http://172.16.76.151:8081/mwiki/search/msearch.jsp?search="+search+"&key="+key+"&pageNum="+pageNum+"&pageCount="+pageCount;
        window.location.href="http://www.joyme.com/mwiki/search/msearch.jsp?search="+search+"&key="+key+"&pageNum="+pageNum+"&pageCount="+pageCount;
    }
</script>
</html>