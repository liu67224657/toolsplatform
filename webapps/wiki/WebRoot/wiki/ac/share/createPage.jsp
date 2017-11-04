<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%@ include file="../common.jsp" %>

<table>
    <form action="${ctx}/wiki/ac/share/create.do" name="" method="post" id="form" name="form">
        <tr>
            <td width="200"></td>
            <td width="200">属于哪个wiki</td>
            <td><select name="wiki" id="wiki">
                <c:forEach items="${wikiMap}" var="entry">
                    <option value="<c:out value="${entry}"/>"><c:out
                            value="${entry}"/></option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td></td>
            <td>分享内容</td>
            <td><textarea name="content" id="content" style="width:600px;height:140px"></textarea>
                </br>
                <span id="error_content" style="color:red"></span></td>
        </tr>
        <tr>
            <td></td>
            <td>分享的图片地址</td>
            <td><input type="text"  name="picurl" value="" size="50"/>
                </br>
                <span id="error_picurl" style="color:red"></span></td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <input type="submit" id="submit" value="保存">
            </td>
        </tr>
    </form>
</table>