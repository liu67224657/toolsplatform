<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript">
    $(document).ready(function() {
        $('#form').submit(function() {
            var searchtitle = $('#searchtitle').val();
            if ($.trim(searchtitle) == "") {
                alert("标题不能为空哦");
                $('#searchtitle').focus();
                return false;
            }
        });
    })
</script>
<style type="text/css">
    .td_cent{text-align:center;vertical-align:middle};
    .truncate{
        overflow: hidden;
        text-overflow:ellipsis; /* for IE */
        -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
        white-space: nowrap;
    }
</style>
<div align="center">
    <form id="form" name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/page/searchwikipage.do?pagenum=1">
        wiki:
        <select name="wikikey">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${wiki == entry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;标题:<input type="text" name="searchtitle" id="searchtitle"/>

        <input type="submit" value="查询"/>
    </form>
</div>

<table align="center" cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="90%">
    <tr style="font-weight:bold;color: #000000">
        <td>wiki_key</td>
        <td>个数</td>
        <td>操作</td>
    </tr>

    <c:forEach var="item" items="${list}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr  style="background-color:#CCE8CF;">
        </c:if>
            <td>${item.wikiKey}</td>
            <td>${item.pageId}</td>
            <td><a href="${pageContext.request.contextPath}/wiki/ac/page/export.do?wikikey=${item.wikiKey}">导出</a>|<a href="${pageContext.request.contextPath}/wiki/ac/page/keywikilist.do?wikikey=${item.wikiKey}">列表</a></td>
        </tr>
    </c:forEach>
</table>
</html>