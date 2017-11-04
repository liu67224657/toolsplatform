<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<style type="text/css">
        .td_cent{text-align:center;vertical-align:middle};
        .truncate{
            overflow: hidden;
            text-overflow:ellipsis; /* for IE */
            -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
            white-space: nowrap;
        }
 </style>
<div align="center">
    查询条件:
    <form name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/toolsOpinionList.do?pageNum=1">
        wiki:
        <select name="wiki">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${wiki == entry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;是否删除:
        <select name="remove_status">
            <option value="">全部</option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
        <input type="submit" value="查询"/>
    </form>
</div>


<table cellspacing="0" bordercolordark='#D3D8E0' bordercolorlight='#4F7FC9' cellpadding="3" border="1" width="99%">
    <tr style="font-weight:bold;color: #000000">
        <td class="td_cent">id</td>
        <td class="td_cent">wiki</td>
        <td class="td_cent">标题</td>
        <td class="td_cent">项目</td>
        <td class="td_cent">数字</td>
        <td class="td_cent">昵称</td>
        <td class="td_cent">联系方式</td>
        <td class="td_cent">状态</td>
        <td class="td_cent">创建时间</td>
        <td class="td_cent">操作</td>
    </tr>
    <c:forEach var="item" items="${result.retList}" varStatus="status">
        <c:if test="${status.index%2==0}">
            <tr>
        </c:if>
        <c:if test="${status.index%2!=0}">
            <tr  style="background-color:#CCE8CF;">
        </c:if>
             <td class="td_cent">${item.opinionId}</td>
            <td class="td_cent">${item.wiki}</td>
            <td class="td_cent">
                    <a href="${item.wikiSource}" target="_blank">${item.title}</a>
                </td>
            <td class="td_cent truncate" style="width: 220px;text-align: left;">${item.opinionKey}</td>
            <td class="td_cent truncate" style="width: 220px;text-align: left;">
                <c:if test="${item.opinionKey=='特殊:修改图片'}">
                   <a href="${item.opinionValue}" target="_blank"><img src="${item.opinionValue}" width="200px" height="100px"></a>
                </c:if>
                <c:if test="${item.opinionKey!='特殊:修改图片'}">
                    ${item.opinionValue}
                </c:if>
            </td>
            <td class="td_cent truncate" style="width: 150px;text-align: left;">${item.nickName}</td>
            <td class="td_cent truncate" style="width: 150px;text-align: left;">${item.contacts}</td>
             <td class="td_cent">
                 <c:if test="${item.removeState==0}">
                     未处理
                 </c:if>
                  <c:if test="${item.removeState==1}">
                      已删除
                 </c:if>
             </td>
            <td class="td_cent"><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
            <td class="td_cent">
                <c:if test="${item.removeState==0}">
                <a href="${pageContext.request.contextPath}/wiki/ac/toolsOpinionDelete.do?pageNum=${param.pageNum}&id=${item.opinionId}&removeState=1">删除</a></td>
                </c:if>
                <c:if test="${item.removeState==1}">
                <a href="${pageContext.request.contextPath}/wiki/ac/toolsOpinionDelete.do?pageNum=${param.pageNum}&id=${item.opinionId}&removeState=0">恢复</a></td>
                </c:if>
        </tr>
    </c:forEach>
</table>

<div align="center">
    <c:if test="${param.pageNum > 1}">
        <a href="${pageContext.request.contextPath}/wiki/ac/toolsOpinionList.do?pageNum=${param.pageNum-1}"><span>上一页</span></a>
    </c:if>
    <c:if test="${result.hasNextPage==true}">
        <a href="${pageContext.request.contextPath}/wiki/ac/toolsOpinionList.do?pageNum=${param.pageNum+1}"><span>下一页</span></a>
    </c:if>
</div>