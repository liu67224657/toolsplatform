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
        <td>序号</td>
        <td>key</td>
        <td>pageid</td>
        <td>标题</td>
    </tr>

    <c:forEach var="item" items="${list}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr  style="background-color:#CCE8CF;">
        </c:if>
        <td>${status.index+1}</td>
        <td>${item.wikiKey}</td>
            <td>${item.pageId}</td>
            <td>${item.wikiUrl}</td>
        </tr>
    </c:forEach>



</table>
<%--<div align="center">--%>
    <%--<c:if test="${hasNextPage==true}">--%>
        <%--<a href="${pageContext.request.contextPath}/wiki/ac/page/searchwikipage.do?pagenum=${pagenum}&searchtitle=${searchtitle}&wikikey=${wikikey}"><span>下一页</span></a>--%>
    <%--</c:if>--%>
    <%--<c:if test="${hasNextPage==false}">--%>
        <%--<a href="${pageContext.request.contextPath}/wiki/ac/page/searchwikipage.do?pagenum=${pagenum-1}&searchtitle=${searchtitle}&wikikey=${wikikey}"><span>上一页</span></a>--%>
    <%--</c:if>--%>
<%--</div>--%>
</html>