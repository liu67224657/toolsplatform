<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@page import="java.util.Set" %>
<%@page import="com.enjoyf.wiki.container.PropertiesContainer" %>
<%@page import="java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>生成一个首页</title>
</head>
<%--<%if(session.getAttribute("adminUser") == null){--%>
<%--return;--%>
<%--}%>--%>
<script type="text/javascript">
    function check() {
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
</script>


<body>
<br><br><br><br><br><br>
<b>日常更新工作，每日更新等等，请点下面这个，不需要重复输入了.....</b>
<br>
<b>这个功能用所于第二次-第N次刷新所有首页，不需要重复输入上面的信息，</b>

<form action="./createallindex_action.jsp" method="post">
    <input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>
    <input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>
    <input type="hidden" name="refreshPage" value="false"/>
    <input type="hidden" name="type" value="2"/>
    <table>
        <tr>
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

        </tr>
    </table>
    <input type="submit" value="提交">
</form>



<br><br><br>
<b>慎重使用，会清除目录下所有文件</b>
<form action="./createallindex_action.jsp" method="post">
    <input type="hidden" name="userName" value="<%=request.getParameter("userName")%>"/>
    <input type="hidden" name="password" value="<%=request.getParameter("password")%>"/>
    <input type="hidden" name="type" value="2"/>
    <input type="hidden" name="refreshCss" value="false"/>
    <input type="hidden" name="wikitype" value="wiki"/>
    <table>
            <td>清除所有页面</td>
            <td>
                <select name="refreshPage">
                    <option value="true">是</option>
                </select>
            </td>
            <td></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>