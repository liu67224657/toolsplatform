<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade" %>
<%@ page import="com.enjoyf.mcms.container.ConfigContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    if (session.getAttribute("ac-user") == null) {
        response.sendRedirect("./login.jsp");
        return;
    }
%>

<script type="text/javascript">
    function check() {
        if (document.form1.filePath.value == '') {
            alert('请输入专区名字');
            document.form1.filePath.focus();
            return false;
        }
        if (document.form1.filePath.value.indexOf("http://") >= 0) {
            alert('请输入红色字体的部分，即专区名称，不是整个http地址');
            document.form1.filePath.focus();
            return false;
        }
    }
    function checkSpecName() {
        /**
         if (document.formsp.specname.value == '') {
         alert('请输入名字');
         document.formsp.specname.focus();
         return false;
         }
         **/
        return true;
    }
</script>
<div><a href="<%=ConfigContainer.getToolsLoginOutURL()%>" style="float:right;">退出</a></div>
搜索:http://www.joyme.com/article/game/<font color="red">koudaimenghuan</font>.html 放入红色的部分
<form name="form1" method="post" action="./main.jsp" onsubmit="return check()">
    <input type="text" size=40 name="filePath">
    <input type="submit" value="提交"/>
</form>

<%
    if (request.getParameter("filePath") != null) {
        AdminConsoleFacade facade = new AdminConsoleFacade();
        facade.getJoymeSpecByFilePath(request);
%>
<c:if test="${joymeSpec == null}">
    ${param.filePath}还没有创建
</c:if>

<script type="text/javascript">
    function refresh() {
        document.form2.action = "${pageContext.request.contextPath}/marticle/ac/refresh.jsp"
        document.form2.submit();
    }
</script>

<form name="form2" action="${pageContext.request.contextPath}/marticle/ac/createItem.jsp" method="post">
    <input type="hidden" name="specId" value="${joymeSpec.specId}"/>
    <input type="hidden" name="filePath" value="${param.filePath}"/>
    <table>
        <tr>
            <td>专区名：</td>
            <td><input type="text" size="80" name="specName" value="${joymeSpec.specName}"></td>
        </tr>
        <tr>
            <td>类型：</td>
            <td><input type="text" size="80" name="specType" value="${joymeSpec.specType}"></td>
        </tr>
        <tr>
            <td>语言：</td>
            <td><input type="text" size="80" name="specLanguage" value="${joymeSpec.specLanguage}"></td>
        </tr>
        <tr>
            <td>大小：</td>
            <td><input type="text" size="80" name="specSize" value="${joymeSpec.specSize}"></td>
        </tr>
        <tr>
            <td>版本：</td>
            <td><input type="text" size="80" name="specVersion" value="${joymeSpec.specVersion}"></td>
        </tr>
        <tr>
            <td>图片链接：</td>
            <td><input type="text" size="80" name="specPicUrl" value="${joymeSpec.specPicUrl}"></td>
        </tr>
        <tr>
            <td>是否压缩图片：</td>
            <td>
                <select name="isCompressImages">
                    <option value="1"
                            <c:if test="${joymeSpec.isCompressImages == 1}">selected="selected"</c:if>  >是
                    </option>
                    <option value="0"
                            <c:if test="${joymeSpec.isCompressImages == 0}">selected="selected"</c:if>  >否
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
            <td>
                <c:if test="${joymeSpec != null}">
                    <a href="${pageContext.request.contextPath}/marticle/ac/channel/createpage.do?specId=${joymeSpec.specId}&filePath=${param.filePath}">设置广告语</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath}/marticle/ac/rollimage/bigpage.do?specId=${joymeSpec.specId}&filePath=${param.filePath}">设置轮播图</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${pageContext.request.contextPath}/marticle/ac/rollimage/smallpage.do?specId=${joymeSpec.specId}&filePath=${param.filePath}">设置小端轮播图</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </c:if>
            </td>
            <td><c:if test="${joymeSpec != null}"><input type="button" value="清理缓存"
                                                         onclick="javascript:refresh()"></c:if></td>
        </tr>
    </table>
</form>
<%}%>

<br/>
<br/>
<br/>
<a href="checkmp4.jsp">检查视频是否有MP4格式</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="clean.jsp">群刷(刷新数据和文件！慎用！)</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="./cleanall.jsp">清空所有缓存（刷新文件）</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="./cleantag.jsp">清空所有标签列表缓存</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="./cleanarchivetype.jsp">清空所有文章二级列表缓存(更多等...)</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="./cleanarchivelist.jsp">清空所有文章列表缓存</a>
<br>
<a href="./cleansinglepage.jsp">清空单页面</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/marticle/ac/rollimage/smallpage.do?specId=${joymeSpec.specId}&filePath=${param.filePath}">设置小端默认轮播图</a>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="./cleanshareaction.jsp">加载小端的分享信息</a>


</br><a href="./count/count.jsp">统计</a>
</br><a href="./categorytags.jsp">清空所有手游画报标签</a>
</br><a href="./categoryarchivelist.jsp">清空所有手游画报标签列表页</a>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
输入中文查询游戏包名，如查询"植物大战僵尸"：输入 "植、植物、植物大"可匹配，输入”大战、僵尸“不匹配
<form name="formsp" method="post" action="${pageContext.request.contextPath}/marticle/ac/queryListSpec.do"
      onsubmit="return checkSpecName()">
    <input type="text" size="40" name="specname">
    <input type="submit" value="提交"/>
</form>