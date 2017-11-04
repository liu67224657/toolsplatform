<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.util.Set" %>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
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
    <script type="text/javascript" src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js"></script>
    <title>生成一个首页</title>
</head>
<body>
<script language="javascript">
    function check() {
        if (document.form1.wikiPageId.value == '') {
            alert('请输入数字');
            document.form1.wikiPageId.focus();
            return false;
        }
    }
    $(document).ready(function(){
        $('span[name=span_key]').click(function(){
            var key= $(this).attr('data-key');
            $('#text_key').val(key);
        });
    });
</script>

<form action="./refreshpagecache_action.jsp" name="form1" method="post" onsubmit="return check()">
    <input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>
    <input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>
    选择KEY：<br/>
    <%
        Map<String, List<String>> map = PropertiesContainer.getInstance().getWikKeySetByFisrstLetter();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String firstLetter = entry.getKey();
    %>
    <div style="clear: both;margin-top: 10px;"><div class=".spanfirstletter" style="display:inline;float: left;"><B><%=firstLetter%>
    </B>：</div><div style="display: inline;clear: both;">
        <%
            List<String> list=entry.getValue();
            for(String key:list){ %>
        <span name="span_key" data-key="<%=key%>" style="width:40px"><%=key%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%  } %>

    </div></div>
    <%}%>
    <table>
        <tr>
            <td>key</td>
            <td>
                    <input id="text_key" type="text"  name="key" value="" />
            </td>
            <td></td>
        </tr>
        <tr>
            <td>pageId</td>
            <td><input type="text" name="wikiPageId"/></td>
            <td>比如 http://www.joyme.com/wiki/mt/853.shtml 填如853</td>
        </tr>
        <tr>
            <td>手机版还是PC版本</td>
            <td>
                <select name="wikitype">
                    <option value="wiki">PC版</option>
                    <option value="mwiki">手机版</option>
                </select>
            </td>
            <td></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>