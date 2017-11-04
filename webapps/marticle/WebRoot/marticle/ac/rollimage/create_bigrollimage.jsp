<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <fmt:setBundle basename="i18n.def" var="def"/>
大端轮播图列表
<c:forEach items="${imageList}" var="image">
    <form action="${pageContext.request.contextPath}/marticle/ac/rollimage/bigmodifyaction.do" method="post">
    <input type="hidden" name="specId" value="${joymeSpec.specId}"/>
    <input type="hidden" name="filePath" value="${filePath}"/>

     <input type="hidden" name="imgId" value="${image.imageId}"/>
    <table>
        <tr>
            <td>标&nbsp;&nbsp;&nbsp;&nbsp;题<input type="text" name="title" size="40" value="${image.title}"></td> </tr>
           <tr> <td>链&nbsp;&nbsp;&nbsp;&nbsp;接<input type="text" name="link" size="40" value="${image.link}"></td> </tr>
            <tr><td>图片地址<input type="text" name="pic" size="40" value="${image.pic}"></td> </tr>
           <tr> <td>
               排&nbsp;&nbsp;&nbsp;&nbsp;序<select name="displayorder">
                <option value="1" <c:if test="${image.displayorder==1}">selected</c:if>>1</option>
                <option value="2" <c:if test="${image.displayorder==2}">selected</c:if>>2</option>
                <option value="3" <c:if test="${image.displayorder==3}">selected</c:if>>3</option>
                <option value="4" <c:if test="${image.displayorder==4}">selected</c:if>>4</option>
                <option value="5" <c:if test="${image.displayorder==5}">selected</c:if>>5</option>
            </select>
            </td> </tr>
            <tr><td>跳转类型<select name="redirectType">
                <c:forEach items="${redirectTypes}" var="type">
                <option value="${type.code}" <c:if test="${type.code==image.redirectType}">selected</c:if>><fmt:message key="redirect.type.${type.code}" bundle="${def}"/></option>
                </c:forEach>
            </select>
            </td></tr>
            <tr><td><input type="submit" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/marticle/ac/rollimage/deleteaction.do?specId=${joymeSpec.specId}&filePath=${filePath}&imgId=${image.imageId}">删除</a></td> </tr>
    </table>
    </form>
    <br/>
</c:forEach>
<br/>
<hr/>
<br/>
<form name="form2" action="${pageContext.request.contextPath}/marticle/ac/rollimage/bigcreateaction.do" method="post">
    <input type="hidden" name="specId" value="${joymeSpec.specId}"/>
    <input type="hidden" name="filePath" value="${filePath}"/>
    添加轮播图
    <table>
        <tr>
            <td>标&nbsp;&nbsp;&nbsp;&nbsp;题<input type="text" name="title" size="40"></td>
        </tr>
        <tr>
            <td>链&nbsp;&nbsp;&nbsp;&nbsp;接<input type="text" name="link" size="40"></td>
        </tr>
        <tr>
            <td>图片地址<input type="text" name="pic" size="40"></td>
        </tr>
        <tr>
            <td>排&nbsp;&nbsp;&nbsp;&nbsp;序<select name="displayorder">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select></td>
        </tr>
        <tr>
            <td>跳转类型<select name="redirectType">
                <option>请选择</option>
                <c:forEach items="${redirectTypes}" var="type">
                <option value="${type.code}"><fmt:message key="redirect.type.${type.code}" bundle="${def}"/></option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>

