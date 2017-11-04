<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

<div align="center">
    <c:forEach items="${keySet}" var="key" >
        <a href="/wiki/ac/rank/query.do?wikikey=${key}">${key}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
</div>
