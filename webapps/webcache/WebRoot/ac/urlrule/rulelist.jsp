<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.webcache.container.PropertiesContainer" %>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="20">
            <a href="<%=PropertiesContainer.getInstance().getToolsLoginOutURL()%>" style="float:left;">退出</a>
        </td>
    </tr>
    <tr>
        <td colspan="20">
            <a href="/ac/main.jsp" style="float:left;">返回</a>
        </td>
    </tr>
    <tr>
        <td colspan="20">
            <table width="50%" border="0" cellpadding="0" cellspacing="0">
                <form action="/ac/urlrule/list.do" method="post" id="form_submit">
                    <tr>
                        <td>来源:<select name="qsrc">
                            <c:forEach items="${srcTypeList}" var="srcType">
                                <option value="${srcType.code}"
                                        <c:if test="${qsrc == srcType.code}">selected="selected"</c:if>>${srcType.desc}</option>
                            </c:forEach>
                        </select></td>
                        <td>有效状态:<select name="qstatus">
                            <c:forEach items="${statusList}" var="status">
                                <option value="${status.code}"
                                        <c:if test="${status.code == qstatus}">selected="selected"</c:if>>${status.desc}</option>
                            </c:forEach>
                        </select>
                        </td>
                        <td>
                            <input type="submit" name="button" value="查询" style="color: #0000ff"/>
                        </td>
                    </tr>
                </form>
            </table>
            <table width="10%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                        <a href="/ac/urlrule/createpage.do">
                            <input type="button" value="添加" style="color: #0000ff"/>
                        </a>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellpadding="2" cellspacing="2">
                <tr bgcolor="#a9a9a9">
                    <%--<td nowrap>主键</td>--%>
                    <td nowrap>输出域名</td>
                    <td nowrap>输出URL</td>
                    <td nowrap>源站URL</td>
                    <td nowrap>来源</td>
                    <td nowrap>状态</td>
                    <td nowrap>操作</td>
                </tr>
                <c:choose>
                    <c:when test="${list != null && list.size() > 0}">
                        <c:forEach items="${list}" varStatus="st" var="rule">
                            <tr <c:if test="${st.index % 2 == 1}">style="background-color: #CCE8CF"</c:if>>
                                <%--<td nowrap>${rule.ruleId}</td>--%>
                                <td nowrap>${rule.desHost}</td>
                                <td nowrap>${rule.desRule}</td>
                                <td nowrap>${rule.srcRule}</td>
                                <td nowrap>${rule.srcType.desc}</td>
                                <td nowrap>${rule.status.desc}</td>
                                <td nowrap>
                                    <a href="/ac/urlrule/modifypage.do?ruleid=${rule.ruleId}">修改</a>
                                    <a href="/ac/urlrule/delete.do?ruleid=${rule.ruleId}&olddesrule=${rule.desRule}&oldsrcrule=${rule.srcRule}">删除</a>
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
                <%--<c:if test="${page.maxPage > 1}">--%>
                <%--<tr>--%>
                <%--<td colspan="20">--%>
                <%--<c:if test="${page.curPage>1}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.curPage-1}&qdkey=${qdkey}&qstatus=${qstatus}"--%>
                <%--class="prepage"><span>上一页</span></a>--%>
                <%--</c:if>--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage<=4}">--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.maxPage<=4}">--%>
                <%--<c:forEach var="curPage" begin="1" end="${page.maxPage}">--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage!=curPage}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${curPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${curPage}</span></a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                <%--<b>${curPage}</b>--%>
                <%--</c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:forEach>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                <%--<c:forEach var="curPage" begin="1" end="4">--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage!=curPage}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${curPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${curPage}</span></a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                <%--<b>${curPage}</b>--%>
                <%--</c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:forEach>--%>
                <%--<c:if test="${page.curPage>=3}">--%>
                <%--<c:if test="${page.curPage>3}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.curPage+1}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${page.curPage+1}</span></a>--%>
                <%--</c:if>--%>
                <%--<c:if test="${page.maxPage>=page.curPage+2}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.curPage+2}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${page.curPage+2}</span></a>--%>
                <%--</c:if>--%>
                <%--</c:if>--%>
                <%--<c:if test="${page.maxPage>page.curPage+4}">--%>
                <%--<em>...</em>--%>
                <%--</c:if>--%>
                <%--<c:if test="${page.curPage<3||(page.curPage>=3 && page.maxPage>page.curPage+3)}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.maxPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${page.maxPage}</span></a>--%>
                <%--</c:if>--%>
                <%--</c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:when>--%>
                <%--<c:when test="${page.curPage>4}">--%>
                <%--<a href="/ac/sitemap/list.do?p=1&qdkey=${qdkey}&qstatus=${qstatus}"><span>1</span></a>--%>
                <%--<em>...</em>--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage<page.maxPage-2}">--%>
                <%--<c:forEach var="curPage" begin="${page.curPage-2}"--%>
                <%--end="${page.curPage+2}">--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage!=curPage}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${curPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${curPage}</span></a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise><b>${curPage}</b></c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:forEach>--%>
                <%--<em>...</em>--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.maxPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${page.maxPage}</span></a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                <%--<c:forEach var="curPage" begin="${page.curPage-2}"--%>
                <%--end="${page.maxPage}">--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage!=curPage}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${curPage}&qdkey=${qdkey}&qstatus=${qstatus}"><span>${curPage}</span></a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise><b>${curPage}</b></c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:forEach>--%>
                <%--</c:otherwise>--%>
                <%--</c:choose>--%>
                <%--</c:when>--%>
                <%--</c:choose>--%>
                <%--<c:choose>--%>
                <%--<c:when test="${page.curPage<page.maxPage}">--%>
                <%--<a href="/ac/sitemap/list.do?p=${page.curPage+1}&qdkey=${qdkey}&qstatus=${qstatus}"--%>
                <%--class="nextpage"><span>下一页</span></a>--%>
                <%--</c:when>--%>
                <%--</c:choose>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--</c:if>--%>
            </table>
        </td>
    </tr>
</table>
</body>
</html>