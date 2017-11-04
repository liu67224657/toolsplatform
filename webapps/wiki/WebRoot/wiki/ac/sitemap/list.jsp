<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

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
