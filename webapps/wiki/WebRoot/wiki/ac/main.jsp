<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="common.jsp" %>
<html>

<%--<%--%>
    <%--if (session.getAttribute("adminUser") == null) {--%>
        <%--return;--%>
    <%--}--%>
<%--%>--%>
<%--当前用户:${adminUser.userName} <a href="${pageContext.request.contextPath}/wiki/ac/updatePassword.jsp">修改密码</a>--%>
<%--<div><a href="<%=PropertiesContainer.getInstance().getWikiLogoutAction()%>" style="float:right;">退出</a></div>--%>
<table>
    <tr>
        <td>清除，刷新选项</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/createindex.jsp">刷新首页</a></td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/refreshpagecache.jsp">清除单页</a></td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/refreshwikicache.jsp">清除所有页</a></td>
        <%--<td><a href="${pageContext.request.contextPath}/wiki/ac/refresitemapcache.jsp">清除网站地图</a></td>--%>
    </tr>
    <tr>
        <td>标签选项</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/itempage.do">标签操作</a></td>
    </tr>
    <tr>
        <td>模板选项</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/templatepage.do">模板操作</a></td>

    </tr>
    <%--<tr>--%>
        <%--<td>分享操作</td>--%>
        <%--<td><a href="${pageContext.request.contextPath}/wiki/ac/share/page.do">MWIKI分享操作</a></td>--%>

    <%--<tr>--%>
        <%--<td>wiki网站地图</td>--%>
        <%--<td><a href="${pageContext.request.contextPath}/wiki/ac/st/list.do">wiki网站地图</a></td>--%>

    <%--</tr>--%>
</table>
</html>
