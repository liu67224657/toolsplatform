<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.util.Set" %>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ include file="/common/jstllibs.jsp" %>
<jsp:include page="common.jsp" flush="false"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js"></script>
    <title>生成一个首页</title>
</head>
<%--<%if(session.getAttribute("adminUser") == null){--%>
<%--return;--%>
<%--}%>--%>
<script type="text/javascript">
    function check() {
        if (document.form1.key.value == '') {
            alert('key不能为空');
            document.form1.key.focus();
            return false;
        }
        if (document.form1.domain.value == '') {
            alert('domain不能为空');
            document.form1.domain.focus();
            return false;
        }
        if (document.form1.url.value == '') {
            alert('url不能为空');
            document.form1.url.focus();
            return false;
        }
    }

    $(document).ready(function () {
        $('span[name=span_key]').click(function () {
            var key = $(this).attr('data-key');
            $('#text_key').val(key);
        });
    });

</script>


<body>

<b>日常更新工作，每日更新等等，请点下面这个，不需要重复输入了.....</b>
<br>
<b>这个功能用于第二次-第N次刷新首页，不需要重复输入上面的信息，提交即可</b>

<form action="./createindex_action.jsp" method="post">
    <input type="hidden" name="type" value="2"/>
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
                <%--<select name="key">--%>
                <%--<%--%>
                <%--Set keySet = PropertiesContainer.getInstance().getJoymeWikiKetSet();--%>
                <%--for(Iterator iter = keySet.iterator() ; iter.hasNext(); ){--%>
                <%--String key = iter.next().toString();--%>
                <%--%>--%>
                <%--<option value="<%=key%>"><%=key%></option>					--%>
                <%--<%}%>--%>
                <%--</select>--%>
                <input id="text_key" type="text" name="key" value=""/>
            </td>
            <td>是否更新css</td>
            <td>
                <select name="refreshCss">
                    <option value="false">否</option>
                    <option value="true">是</option>
                </select>

            </td>
            <td>手机版还是PC版本</td>
            <td>
                <select name="wikitype">
                    <option value="wiki">PC版</option>
                    <option value="mwiki">手机版</option>
                </select>

            </td>
            <td> <input type="submit" value="提交"></td>
        </tr>
    </table>

</form>
</body>
</html>