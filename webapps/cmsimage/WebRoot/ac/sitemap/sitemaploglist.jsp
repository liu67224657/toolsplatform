<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
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
    <title>网站地图管理</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script>

    </script>
</head>
<body style="overflow: scroll">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="20">
            <a href="<%=PropertiesContainer.getToolsLoginOutURL()%>" style="float:left;">退出</a>
        </td>
    </tr>
    <tr>
        <td colspan="20">
            <table width="10%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <a href="/ac/sitemap/list.do">
                            <input type="button" value="返回" style="color: #0000ff"/>
                        </a>
                    </td>
                </tr>
            </table>
            <table width="30%" border="0" cellpadding="0" cellspacing="0">
                <form action="/ac/sitemap/loglist.do" method="post" id="form_submit">
                    <tr>
                        <td>域名:<input type="text" name="qdkey" value="${qdkey}" size="5"/>.${evnDomain}</td>
                        <td>
                            <input type="submit" name="button" value="查询" style="color: #0000ff"/>
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
                    <td nowrap>域名</td>
                    <td nowrap>URL</td>
                    <td nowrap>描述</td>
                    <td nowrap>时间</td>
                </tr>
                <c:choose>
                    <c:when test="${list != null && list.size() > 0}">
                        <c:forEach items="${list}" varStatus="st" var="sitemaplog">
                            <tr <c:if test="${st.index % 2 == 1}">style="background-color: #CCE8CF" </c:if>>
                                <td nowrap>
                                        ${sitemaplog.domainKey}
                                </td>
                                <td nowrap>
                                        <a href="https://www.baidu.com/s?wd=${sitemaplog.pageUrl}" target="_blank">${sitemaplog.pageUrl}</a>
                                </td>
                                <td nowrap>
                                        ${sitemaplog.statusDesc}
                                </td>
                                <td nowrap>
                                        ${sitemaplog.pushDateStr}
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
                <c:if test="${page.maxPage > 1}">
                    <tr>
                        <td colspan="20">
                            <c:if test="${page.curPage>1}">
                                <a href="/ac/sitemap/loglist.do?p=${page.curPage-1}&qdkey=${qdkey}"
                                   class="prepage"><span>上一页</span></a>
                            </c:if>
                            <c:choose>
                                <c:when test="${page.curPage<=4}">
                                    <c:choose>
                                        <c:when test="${page.maxPage<=4}">
                                            <c:forEach var="curPage" begin="1" end="${page.maxPage}">
                                                <c:choose>
                                                    <c:when test="${page.curPage!=curPage}">
                                                        <a href="/ac/sitemap/loglist.do?p=${curPage}&qdkey=${qdkey}"><span>${curPage}</span></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <b>${curPage}</b>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="curPage" begin="1" end="4">
                                                <c:choose>
                                                    <c:when test="${page.curPage!=curPage}">
                                                        <a href="/ac/sitemap/loglist.do?p=${curPage}&qdkey=${qdkey}"><span>${curPage}</span></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <b>${curPage}</b>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${page.curPage>=3}">
                                                <c:if test="${page.curPage>3}">
                                                    <a href="/ac/sitemap/loglist.do?p=${page.curPage+1}&qdkey=${qdkey}"><span>${page.curPage+1}</span></a>
                                                </c:if>
                                                <c:if test="${page.maxPage>=page.curPage+2}">
                                                    <a href="/ac/sitemap/loglist.do?p=${page.curPage+2}&qdkey=${qdkey}"><span>${page.curPage+2}</span></a>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${page.maxPage>page.curPage+4}">
                                                <em>...</em>
                                            </c:if>
                                            <c:if test="${page.curPage<3||(page.curPage>=3 && page.maxPage>page.curPage+3)}">
                                                <a href="/ac/sitemap/loglist.do?p=${page.maxPage}&qdkey=${qdkey}"><span>${page.maxPage}</span></a>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${page.curPage>4}">
                                    <a href="/ac/sitemap/loglist.do?p=1&qdkey=${qdkey}"><span>1</span></a>
                                    <em>...</em>
                                    <c:choose>
                                        <c:when test="${page.curPage<page.maxPage-2}">
                                            <c:forEach var="curPage" begin="${page.curPage-2}"
                                                       end="${page.curPage+2}">
                                                <c:choose>
                                                    <c:when test="${page.curPage!=curPage}">
                                                        <a href="/ac/sitemap/loglist.do?p=${curPage}&qdkey=${qdkey}"><span>${curPage}</span></a>
                                                    </c:when>
                                                    <c:otherwise><b>${curPage}</b></c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <em>...</em>
                                            <a href="/ac/sitemap/loglist.do?p=${page.maxPage}&qdkey=${qdkey}"><span>${page.maxPage}</span></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="curPage" begin="${page.curPage-2}"
                                                       end="${page.maxPage}">
                                                <c:choose>
                                                    <c:when test="${page.curPage!=curPage}">
                                                        <a href="/ac/sitemap/loglist.do?p=${curPage}&qdkey=${qdkey}"><span>${curPage}</span></a>
                                                    </c:when>
                                                    <c:otherwise><b>${curPage}</b></c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${page.curPage<page.maxPage}">
                                    <a href="/ac/sitemap/loglist.do?p=${page.curPage+1}&qdkey=${qdkey}"
                                       class="nextpage"><span>下一页</span></a>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
