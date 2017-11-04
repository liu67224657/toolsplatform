<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

<div align="center">
    <form name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/joymewikilist.do">
        wiki:
        <select name="wikikey">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${param.wiki == wikikey}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        <input type="submit" value="查询"/>
    </form>
</div>

<table cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="99%">
    <tr style="font-weight:bold;color: #000000">
        <td>ID</td>
        <td>wiki_key</td>
        <td>wiki类型</td>
        <td>wiki_domain</td>
        <td>wiki名称</td>
        <td>二级域名</td>
        <td>pc是否保留JSCSS</td>
        <td>m是否保留JSCSS</td>
        <td>操作</td>
    </tr>



    <c:forEach var="entry" items="${wikilist}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr  style="background-color:#CCE8CF;">
        </c:if>
            <td>${entry.joymeWikiId}</td>
            <td>${entry.joymeWikiKey}</td>
            <td>${entry.contextPath}</td>
            <td>${entry.joymeWikiDomain}</td>
            <td>${entry.joymeWikiName}</td>
            <td>
                <c:if test="${entry.supportSubDomain==false}">不是</c:if>
                <c:if test="${entry.supportSubDomain==true}">是</c:if>
            </td>
            <td>
                <c:if test="${entry.pcKeepJscss==0}">不保留</c:if>
                <c:if test="${entry.pcKeepJscss==1}">保留</c:if>
            </td>
            <td>
                <c:if test="${entry.mKeepJscss==0}">不保留</c:if>
                <c:if test="${entry.mKeepJscss==1}">保留</c:if>
            </td>
            <td><a href="${pageContext.request.contextPath}/wiki/ac/editjoymewiki.do?joymewikiid=${entry.joymeWikiId}">编辑</a></td>
        </tr>
    </c:forEach>
</table>

