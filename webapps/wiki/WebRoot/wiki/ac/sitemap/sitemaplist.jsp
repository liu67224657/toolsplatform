<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div align="center">
    查询条件:
    <form name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/st/sitemaplist.do">
        wiki:
        <select name="wiki">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${param.wiki == eentry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        <input type="submit" value="查询"/>
    </form>
</div>
<div align="center"><span style="color:red;">当前选择wiki：${wiki} &nbsp;&nbsp;&nbsp;&nbsp;<a
        href="${pageContext.request.contextPath}/wiki/ac/st/edit.do?wiki=${wiki}">编辑${wiki}网站地图</a></span></div>
<table border=1 width=800 align="center" style="BORDER-COLLAPSE: collapse">
    <tr>
        <td>地址</td>
        <td>权重</td>
    </tr>

    <c:forEach var="item" items="${list}">
        <tr id="${item.sitemapid}">
            <td>${item.loc}</td>
            <td>${item.priority}</td>
        </tr>
    </c:forEach>
</table>
</html>