<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

<form action="/wiki/ac/rank/addhot.do">
    <input type="hidden" value="${wikikey}" name="wikikey"/>
    链接地址：<input type="text" value="" name="url" size="64"/> *必须填写,例如：http://wiki.joyme.com/ttkp/5569.shtml<br/>
    点击量：<input type="text" value="" name="score" size="10"/> *必须填写，标示多少点击量，越大越靠前l<br/>
    <input type="submit" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</form>
<a href="/wiki/ac/rank/newsreload.do?wikikey=${wikikey}">重新刷新最新</a>
<br/><br/>
<div align="center">
    <div style="width: 50%;float:left; display:inline; ">

        最新 :<br/>
        <c:forEach var="news" items="${newsList}">
           <a href="${wikihost}/${wikikey}/${news.id}.shtml" target="_blank">${news.text}</a>&nbsp;&nbsp;--&nbsp;&nbsp;${news.id}<br/>
        </c:forEach>
    </div>
    <div style="width: 50%;float:left; display:inline; ">

        最热 :<br/>
        <c:forEach var="hot" items="${hotList}">
        <a href="${wikihost}/${wikikey}/${hot.id}.shtml" target="_blank">${hot.text}</a>&nbsp;&nbsp;--&nbsp;&nbsp;${hot.id}<br/>
        </c:forEach>
    </div>
</div>
