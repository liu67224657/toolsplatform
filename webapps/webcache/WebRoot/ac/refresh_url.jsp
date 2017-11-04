<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>url规则配置</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script>
    </script>
</head>
<body style="overflow: scroll">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
    </tr>
    <tr>
        <td colspan="20" height="1" style="color: #0000ff;">定时刷新列表</td>
    </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="20">
            <table border="0" cellpadding="0" cellspacing="0">
                <form action="/ac/refreshtimer/addurl.do" method="post">
                    <tr>
                        <td>
                            添加url：<input type="text" name="refreshurl" value=""/>
                            <select name="refreshtype">
                                <option value="0">子文件</option>
                                <option value="1">子文件及子文件夹</option>
                            </select>
                            <input type="submit" value="添加" style="color: #0000ff"/>
                            <br/>*填写url的地址，必须http://开头，如：http://www.joyme.com/news/。子文件表示该栏目下的index.html及列表页等集合页的.html文件。子文件夹则会刷新该栏目下的全部的文章缓存。
                        </td>
                    </tr>
                </form>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellpadding="2" cellspacing="2">
                <tr bgcolor="#a9a9a9">
                    <td nowrap>URL</td>
                    <td nowrap>刷新方式</td>
                    <td nowrap>操作</td>
                </tr>
                <c:choose>
                    <c:when test="${list != null && list.size() > 0}">
                        <c:forEach items="${list}" varStatus="st" var="refreshUrl">
                            <tr <c:if test="${st.index % 2 == 1}">style="background-color: #CCE8CF"</c:if>>
                                <td nowrap>${refreshUrl.url}</td>
                                <td nowrap><c:if test="${refreshUrl.type == 0}">子文件</c:if><c:if test="${refreshUrl.type == 1}">子文件及子文件夹</c:if></td>
                                <td nowrap>
                                    <a href="/ac/refreshtimer/delurl.do?urlid=${refreshUrl.urlId}">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="20">
                                <span style="color: #ff0000">没有数据</span>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
