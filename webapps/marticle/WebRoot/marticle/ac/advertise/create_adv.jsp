<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    if (session.getAttribute("ac-user") == null) {
        response.sendRedirect("./login.jsp");
        return;
    }

%>
<form name="form2" action="${pageContext.request.contextPath}/marticle/ac/channel/createaction.do" method="post">
    <input type="hidden" name="specId" value="${joymeSpec.specId}"/>
    <input type="hidden" name="filePath" value="${filePath}"/>
    <table>
        <c:forEach items="${channelMap}" var="entry">
            <tr>
                <td>${entry.key}-下载地址：</td>
                <td><input type="text" size="80" name="${entry.key}_specDownloadUrl"
                           value="${specChannel.downUrlMap[entry.key]}"></td>
            </tr>
            <tr>
                <td>${entry.key}-广告语:</td>
                <td><input type="text" size="80" name="${entry.key}_specAdvertise"
                           value='${specChannel.advertiesMap[entry.key]}'></td>
            </tr>
        </c:forEach>
        <tr>
            <td><input type="submit" value="提交"></td>
            <td></td>
        </tr>
    </table>
</form>


