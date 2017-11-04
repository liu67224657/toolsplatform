<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.webcache.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>url规则配置</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#form_submit').on('submit', function () {
                var desRule = $('#input_des_rule').val();
                var srcRule = $('#input_src_rule').val();
                var jumpUrl = $('#input_jump_url').val();
                if (srcRule == desRule) {
                    alert('源站url不能和输出url相同');
                    return false;
                }
                if (jumpUrl == desRule) {
                    alert('手机跳转的url不能和输出url相同');
                    return false;
                }
            })
        })
    </script>
</head>
<body style="overflow: scroll">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td colspan="20">
            <a href="<%=PropertiesContainer.getInstance().getToolsLoginOutURL()%>" style="float:left;">退出</a>
        </td>
    </tr>
    <tr>
        <td colspan="20">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                </tr>
            </table>
            <form action="/ac/urlrule/create.do" method="post" id="form_submit">
                <table width="80%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="100">输出URL:</td>
                        <td colspan="2">
                            <input type="text" name="desrule" value="" size="100" id="input_des_rule"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">源站URL:</td>
                        <td colspan="2">
                            <input type="text" name="srcrule" value="" size="100" id="input_src_rule"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">来源:</td>
                        <td colspan="2">
                            <select name="srctype">
                                <c:forEach items="${srcTypeList}" var="srcType">
                                    <option value="${srcType.code}">${srcType.desc}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">wikiKey:</td>
                        <td colspan="2">
                            <input type="text" name="pagekey" value="" size="100"/>如：op、dtcq，cms不用填
                        </td>
                    </tr>
                    <tr>
                        <td width="100">状态:</td>
                        <td colspan="2">
                            <select name="status">
                                <c:forEach items="${statusList}" var="status">
                                    <option value="${status.code}">${status.desc}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">CDN:</td>
                        <td colspan="2">
                            <select name="cdntype">
                                <c:forEach items="${cdnList}" var="cdn">
                                    <option value="${cdn.code}">${cdn.desc}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">统计script:</td>
                        <td colspan="2">
                            <textarea name="pvsetting" cols="20" rows="5"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">手机跳m站的URL</td>
                        <td colspan="2">
                            <input type="text" name="tabsetting" value="" size="100" id="input_jump_url"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="100">客户端:</td>
                        <td colspan="2">
                            <select name="clienttype">
                                <c:forEach items="${clientTypeList}" var="clientType">
                                    <option value="${clientType.code}">${clientType.desc}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input type="submit" name="button" value="提交"/>
                            <input name="Reset" type="button" value="返回" onclick="javascipt:window.history.go(-1);">
                        </td>
                    </tr>
                </table>
            </form>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
