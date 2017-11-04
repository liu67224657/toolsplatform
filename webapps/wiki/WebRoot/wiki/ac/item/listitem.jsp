<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

<div align="center">
    查询条件:
    <form name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/itemList.do?pageNum=1">
        wiki:
        <select name="wiki">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${param.wiki == eentry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        手机还是PC版本:
        <select name="wikitype">
            <option value="">所有</option>
            <option value="wiki" <c:if test="${param.wikitype == 'wiki'}">selected="selected"</c:if>>PC</option>
            <option value="mwiki" <c:if test="${param.wikitype == 'mwiki'}">selected="selected"</c:if>>手机</option>
        </select>
        &nbsp;&nbsp;&nbsp;channel:
        <select name="channel">
            <option value="">所有</option>
            <c:forEach items="${channelMap}" var="entry">
                <option
                        <c:if test="${param.channel == entry.key}">selected="selected"</c:if>
                        value="<c:out value="${entry.key}"/>"><c:out value="${entry.key}"/></option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;key:
        <input type="text" name="itemkey" value="${param.itemkey}"/>
        &nbsp;&nbsp;&nbsp;是否是首页:
        <select name="isIndex">
            <option value="0">两者</option>
            <option value="1">首页</option>
            <option value="2">子页</option>
        </select>
        <input type="submit" value="查询"/>
    </form>
</div>

<table cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="99%">
    <tr style="font-weight:bold;color: #000000">
        <td>wiki</td>
        <td>渠道</td>
        <td>key</td>
        <td>是否首页</td>
        <td>类型</td>
        <td>操作</td>
    </tr>

    <c:forEach var="item" items="${result.retList}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr  style="background-color:#CCE8CF;">
        </c:if>
            <td>${item.wiki}</td>
            <td>${item.channel}</td>
            <td>${item.itemKey}</td>
            <td>
                <c:if test="${item.isIndex==1}">首页</c:if>
                <c:if test="${item.isIndex==2}">子页</c:if>
            </td>
            <td>${item.itemType}</td>
            <td><a href="${pageContext.request.contextPath}/wiki/ac/editItem.do?joymeItemId=${item.joymeItemId}&wikitype=${item.contextPath}">编辑</a></td>
        </tr>
    </c:forEach>
</table>

<div align="center">
    <c:if test="${param.pageNum > 1}">
        <a href="${pageContext.request.contextPath}/wiki/ac/itemList.do?pageNum=${param.pageNum-1}&wiki=${param.wiki}&channel=${param.channel}&itemkey=${param.itemkey}&isIndex=${param.isIndex}&wikitype=${param.wikitype}"><span>上一页</span></a>
    </c:if>
    <c:if test="${result.hasNextPage==true}">
        <a href="${pageContext.request.contextPath}/wiki/ac/itemList.do?pageNum=${param.pageNum+1}&wiki=${param.wiki}&channel=${param.channel}&itemkey=${param.itemkey}&isIndex=${param.isIndex}&wikitype=${param.wikitype}"><span>下一页</span></a>
    </c:if>
</div>