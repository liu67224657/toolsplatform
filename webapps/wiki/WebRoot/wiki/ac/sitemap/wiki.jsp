<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<%@page import="java.lang.*" %>
<%@page import="java.util.Set" %>
<%@page import="com.enjoyf.wiki.container.TemplateContainer" %>
<%@page import="com.enjoyf.wiki.bean.JoymeTemplate" %>
<%@page import="java.util.Iterator" %>
<%@ page import="com.enjoyf.wiki.bean.JoymeTemplate" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<table align="center" cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="60%">
    <tr style="font-weight:bold;color: #000000">
        <td>wiki</td>
        <td>模板名称</td>
    </tr>
    <script>
        var arr = new Array();
    </script>
    <%
        int i = 0;
        for (Map.Entry<String, JoymeTemplate> entry : TemplateContainer.templateMap.entrySet()) {
            String string = entry.getKey();
            JoymeTemplate joymeTemplate = entry.getValue();

    %>
    <script>
        var obj = new Object();
        obj.name = "<%=string%>";
        obj.value = "<%=joymeTemplate.getTemplateName()%>";
        arr[<%=i%>] = obj;
    </script>
    <%

    %>
   
    <%
            i++;
        }
    %>

    <script>

        arr = arr.sort(function(a, b) {
            return a.name > b.name ? 1 : -1;
        });
        var html = "";
        for (i = 0; i < arr.length; i++) {
            if (i == 0) {
                html = "<tr><td>" + arr[i].name + "</td><td>" + arr[i].value + "</td></tr>";
            } else {
                if(i%2!=0){
                    html = html + "<tr style='background-color:#CCE8CF;'><td>" + arr[i].name + "</td><td>" + arr[i].value + "</td></tr>";
                }else{
                    html = html + "<tr><td>" + arr[i].name + "</td><td>" + arr[i].value + "</td></tr>";
                }
            }
        }
        document.write(html)
    </script>


</table>


</html>
