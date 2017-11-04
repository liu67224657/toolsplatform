<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="content-language" content="zh-CN" />
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>网站地图管理</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(document).ready(function(){
            $('#form_submit').bind('submit', function(){
                var key = $('#input_domainkey').val();
                if(key.length == 0){
                    alert('请填写域名key');
                    return false;
                }
//                var context = $('#input_contextpath').val();
//                if(context.length == 0){
//                    alert('请填写路径');
//                    return false;
//                }
            });
        });

    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <a href="<%=PropertiesContainer.getToolsLoginOutURL()%>" style="float:right;">退出</a>
        </td>
    </tr>
    <tr>
        <td>
            <form action="/ac/sitemap/create.do" method="post" id="form_submit">
                <input type="hidden" name="qdkey" value="${qdkey}"/>
                <input type="hidden" name="qstatus" value="${qstatus}"/>
                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>域名</td>
                        <td><input id="input_domainkey" type="text" name="domainkey" value="" size="5"/>.${evnDomain}</td>
                    </tr>
                    <tr>
                        <td>路径</td>
                        <td><input id="input_contextpath" type="text" name="contextpath" value=""/>(域名后面的路径，如：news/official/)</td>
                    </tr>
                    <tr>
                        <td>规则</td>
                        <td>
                            <select name="rulecode">
                                <c:forEach items="${ruleList}" var="rule">
                                    <option value="${rule.code}">${rule.desc}-${rule.rule}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td><textarea rows="3" cols="20" name="expdesc"></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="button" value="提交"/>
                            <input name="Reset" type="button"  value="返回" onclick="javascipt:window.history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
