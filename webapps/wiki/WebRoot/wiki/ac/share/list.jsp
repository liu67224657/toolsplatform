<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%@ include file="../common.jsp" %>

<div align="center">
    查询条件:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <form name="form1" method="post" action="${ctx}/wiki/ac/share/page.do">
        wiki:
        <select name="wiki">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${wikiKey == entry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="查询"/>
    </form>
    <a  href="${ctx}/wiki/ac/share/createPage.do">创建分享信息</a>
</div>
<table cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="99%">
    <tr style="font-weight:bold;color: #000000">
        <td>wiki</td>
        <td>内容</td>
        <td>图片</td>
        <td>操作</td>
    </tr>

    <c:forEach var="share" items="${list}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr style="background-color:#CCE8CF;">
        </c:if>
            <td>${share.joymeWikiKey}</td>
            <td>${share.joymeShareContent}</td>
            <td>${share.joymeSharePic}</td>
            <td><a  href="${ctx}/wiki/ac/share/modifyPage.do?id=${share.joymeShareId}">编辑</a>|<a href="${ctx}/wiki/ac/share/delete.do?id=${share.joymeShareId}&wiki=${share.joymeWikiKey}">删除</a>
            </td>

        </tr>
    </c:forEach>
</table>