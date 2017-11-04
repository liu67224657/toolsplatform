<%@ page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%--
  Created by IntelliJ IDEA.
  User: zhimingli
  Date: 14-1-9
  Time: 下午7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>common</title></head>
<style>
    .hr0 {
        height: 1px;
        border: none;
        border-top: 1px dashed #0066CC;
    }


</style>
<body>

<table>
    <tr>
        <td><a href="<%=PropertiesContainer.getInstance().getWikiLogoutAction()%>" style="float:right;">退出</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/createindex.jsp">刷新首页</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/refreshpagecache.jsp">清除单页</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/refreshwikicache.jsp">清除所有页</a></td>
        <%--<td>|</td>--%>
        <%--<td><a href="${pageContext.request.contextPath}/wiki/ac/refresitemapcache.jsp">清除网站地图</a></td>--%>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/itempage.do">标签操作</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/templatepage.do">模板操作</a></td>
        <td>|</td>
        <%--<td style="display: none;"><a href="${pageContext.request.contextPath}/wiki/ac/share/page.do">MWIKI分享操作</a></td>--%>
        <%--<td>|</td>--%>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/st/page.do">wiki网站地图</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/sitemap/wiki.jsp">java_查看wiki</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/page/wikipagelist.do">wiki数量统计</a></td>
         <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/toolsOpinionList.do">wiki玩家反馈</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/page/addwikipage.do">wiki手动新增地址</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/joymewikilist.do">joyme_wiki列表</a></td>
        <td>|</td>
        <td><a href="${pageContext.request.contextPath}/wiki/ac/rank/page.do">排行榜</a></td>
    </tr>
</table>
<hr class="hr0"/>
</body>
</html>