<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.util.Set" %>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@page import="java.util.Iterator" %>
<jsp:include page="common.jsp" flush="false"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--<%--%>
    <%--if (session.getAttribute("adminUser") == null) {--%>
        <%--return;--%>
    <%--}--%>
<%--%>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>清除网站地图</title>
</head>
<body>

<form action="refresitemapcache_action.jsp" name="form1" method="post">
    <input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>
    <input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>
    <table>
        <tr>
            <td>key</td>
            <td>
                <select name="key">
                    <%
                        Set keySet = PropertiesContainer.getInstance().getJoymeWikiKetSet();;
                        for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
                            String key = iter.next().toString();
                    %>
                    <option value="<%=key%>"><%=key%>
                    </option>
                    <%}%>
                </select>

            </td>
            <td></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>