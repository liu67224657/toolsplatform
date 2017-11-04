<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.webcache.container.PropertiesContainer" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
                var input_sitemapurl = $('#input_sitemapurl').val();
                if(input_sitemapurl.length == 0){
                    alert('sitemap访问地址');
                    return false;
                }


                var input_mappingurl = $('#input_mappingurl').val();
                if(input_mappingurl.length == 0){
                    alert('源文件');
                    return false;
                }
            });
        });
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <a href="<%=PropertiesContainer.getInstance().getToolsLoginOutURL()%>" style="float:right;">退出</a>
        </td>
    </tr>
    <tr>
        <td>
            <form action="/ac/sitemap/modify.do" method="post" id="form_submit">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <input type="hidden" name="qdkey" value="${qdkey}"/>
                    <input type="hidden" name="qstatus" value="${qstatus}"/>
                    <input type="hidden" name="sid" value="${sitemap.sitemapId}"/>
                    <tr>
                        <td>域名</td>
                        <td><input id="input_domainkey" type="text" name="domainkey" value="${sitemap.domainKey}" size="55"/>（填写如：www | m | wiki）</td>
                    </tr>
                    <tr>
                        <td>sitemap访问地址</td>
                        <td><input id="input_sitemapurl" type="text" name="sitemapurl" value="${sitemap.sitemapUrl}" size="55"/>（对外访问地址）</td>
                    </tr>
                    <tr>
                        <td>源文件</td>
                        <td><input id="input_mappingurl" type="text" name="mappingurl" value="${sitemap.mappingUrl}" size="55"/><br>（如article生成的源sitemap地址，如http://www.joyme.com/vr/sitemap.xml 源文件为：http://article.joyme.com/article/pc/vr/sitemap.xml 其他只需要修改vr位置的栏
                            目名称即可）<br><br></td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td><textarea rows="3" cols="57" name="expdesc">${sitemap.expDesc}</textarea></td>
                    </tr>
                    <tr>
                        <td>有效状态</td>
                        <td>
                            <select name="status">
                                <c:forEach items="${statusList}" var="status">
                                    <option value="${status.code}"
                                            <c:if test="${sitemap.removeStatus.code == status.code}">selected="selected"</c:if>>${status.desc}</option>
                                </c:forEach>
                            </select>
                        </td>
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
