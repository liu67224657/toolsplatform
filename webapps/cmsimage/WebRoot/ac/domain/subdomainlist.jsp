<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
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
    <title>域名管理</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script>

    </script>
</head>
<body>
<div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="20">
                <a href="<%=PropertiesContainer.getToolsLoginOutURL()%>" style="float:right;">退出</a>
            </td>
        </tr>
        <tr>
            <td colspan="20">
                <table width="50%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>
                            <a href="/ac/joymedomain/list.do"><input style="color: blue" type="button"
                                                                     value="<< 返回主域名列表"/></a>
                        </td>
                    </tr>
                </table>
                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>主域名:</td>
                        <td>${joymeDomain.domainName}</td>
                        <td>有效期:</td>
                        <td>${joymeDomain.expireDateStr}</td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                    </tr>
                </table>
                <br/>
                <br/>
                <table width="50%" border="0" cellpadding="0" cellspacing="0">
                    <form action="/ac/joymedomain/subdomain/list.do" method="post" id="form_submit">
                        <tr>
                            <td>二级域名</td>
                            <td>
                                <input type="hidden" name="maindomain" value="${joymeDomain.domainName}"/>
                                <input type="text" name="domainname" value="${domainName}"/>(如:dtcq)
                            </td>
                            <td>排序</td>
                            <td>
                                <select name="orderby">
                                    <option value="modifydate"
                                            <c:if test="${orderBy == 'modifydate'}">selected="selected"</c:if>>最后修改时间
                                    </option>
                                    <option value="domainname"
                                            <c:if test="${orderBy == 'domainname'}">selected="selected"</c:if>>域名
                                    </option>
                                    <option value="department"
                                            <c:if test="${orderBy == 'department'}">selected="selected"</c:if>>部门
                                    </option>
                                </select>
                            </td>
                            <td>
                                <select name="ordertype">
                                    <option value="DESC" <c:if test="${orderType == 'DESC'}">selected="selected"</c:if>>
                                        降序
                                    </option>
                                    <option value="ASC" <c:if test="${orderType == 'ASC'}">selected="selected"</c:if>>
                                        升序
                                    </option>
                                </select>
                            </td>
                            <td>
                                <input style="color: blue" type="submit" name="button" value="查询"/>
                            </td>
                        </tr>
                    </form>
                </table>
                <table width="50%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>
                            <a href="/ac/joymedomain/subdomain/createpage.do?maindomain=${joymeDomain.domainName}">
                                <input style="color: blue" type="button" value="添加"/></a>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td colspan="20" height="1" style="background-color: #BEBEBE;color: #000000;"></td>
                    </tr>
                </table>
                <table style="width:100%;border:1px;border-color: inherit">
                    <tr bgcolor="#a9a9a9">
                        <td>域名</td>
                        <td>首页</td>
                        <td>部门</td>
                        <%--<td>SEO备注</td>--%>
                        <td>备注</td>
                        <td>状态</td>
                        <td>最后修改时间</td>
                        <td>操作</td>
                    </tr>
                    <c:choose>
                        <c:when test="${list != null && list.size() > 0}">
                            <c:forEach items="${list}" varStatus="st" var="subDomain">
                                <tr <c:if test="${st.index % 2 == 1}">style="background-color: #CCE8CF" </c:if>>
                                    <td align="center">
                                            ${subDomain.domainName}.${subDomain.mainDomain}
                                    </td>
                                    <td align="center">
                                            ${subDomain.indexUrl}
                                    </td>
                                    <td align="center">
                                            ${subDomain.useDept}
                                    </td>
                                        <%--<td align="center">--%>
                                        <%--<textarea cols="20" rows="3">${subDomain.seoDesc}</textarea>--%>
                                        <%--</td>--%>
                                    <td align="center">
                                        <textarea cols="20" rows="3">${subDomain.domainDesc}</textarea>
                                    </td>
                                    <td
                                            <c:if test="${subDomain.removeStatus == 0}">style="color: #ff0000" </c:if>
                                            align="center">
                                        <c:if test="${subDomain.removeStatus == 0}">无效</c:if>
                                        <c:if test="${subDomain.removeStatus == 1}">有效</c:if>
                                    </td>
                                    <td align="center">
                                            ${subDomain.modifyDateStr}
                                    </td align="center">
                                    <td align="center">
                                        <a href="/ac/joymedomain/subdomain/modifypage.do?maindomain=${subDomain.mainDomain}&domainname=${subDomain.domainName}">修改</a>
                                        <a href="/ac/joymedomain/subdomain/delete.do?maindomain=${subDomain.mainDomain}&domainname=${subDomain.domainName}">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="20">
                                    <span style="color: #ff0000">没有数据</span>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${page.maxPage > 1}">
                        <tr>
                            <td colspan="20">
                                <c:if test="${page.curPage>1}">
                                    <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.curPage-1}"
                                       class="prepage"><span>上一页</span></a>
                                </c:if>
                                <c:choose>
                                    <c:when test="${page.curPage<=4}">
                                        <c:choose>
                                            <c:when test="${page.maxPage<=4}">
                                                <c:forEach var="curPage" begin="1" end="${page.maxPage}">
                                                    <c:choose>
                                                        <c:when test="${page.curPage!=curPage}">
                                                            <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${curPage}"><span>${curPage}</span></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <b>${curPage}</b>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="curPage" begin="1" end="4">
                                                    <c:choose>
                                                        <c:when test="${page.curPage!=curPage}">
                                                            <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${curPage}"><span>${curPage}</span></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <b>${curPage}</b>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                <c:if test="${page.curPage>=3}">
                                                    <c:if test="${page.curPage>3}">
                                                        <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.curPage+1}"><span>${page.curPage+1}</span></a>
                                                    </c:if>
                                                    <c:if test="${page.maxPage>=page.curPage+2}">
                                                        <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.curPage+2}"><span>${page.curPage+2}</span></a>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${page.maxPage>page.curPage+4}">
                                                    <em>...</em>
                                                </c:if>
                                                <c:if test="${page.curPage<3||(page.curPage>=3 && page.maxPage>page.curPage+3)}">
                                                    <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.maxPage}"><span>${page.maxPage}</span></a>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${page.curPage>4}">
                                        <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=1"><span>1</span></a>
                                        <em>...</em>
                                        <c:choose>
                                            <c:when test="${page.curPage<page.maxPage-2}">
                                                <c:forEach var="curPage" begin="${page.curPage-2}"
                                                           end="${page.curPage+2}">
                                                    <c:choose>
                                                        <c:when test="${page.curPage!=curPage}">
                                                            <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${curPage}"><span>${curPage}</span></a>
                                                        </c:when>
                                                        <c:otherwise><b>${curPage}</b></c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                <em>...</em>
                                                <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.maxPage}"><span>${page.maxPage}</span></a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="curPage" begin="${page.curPage-2}"
                                                           end="${page.maxPage}">
                                                    <c:choose>
                                                        <c:when test="${page.curPage!=curPage}">
                                                            <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${curPage}"><span>${curPage}</span></a>
                                                        </c:when>
                                                        <c:otherwise><b>${curPage}</b></c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${page.curPage<page.maxPage}">
                                        <a href="/ac/joymedomain/subdomain/list.do?domainname=${domainName}&maindomain=${joymeDomain.domainName}&orderby=${orderBy}&ordertype=${orderType}&p=${page.curPage+1}"
                                           class="nextpage"><span>下一页</span></a>
                                    </c:when>

                                </c:choose>
                            </td>
                        </tr>
                    </c:if>
                    <tr>

                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
