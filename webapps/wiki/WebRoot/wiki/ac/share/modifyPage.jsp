<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%@ include file="../common.jsp" %>

<table>
    <form action="${ctx}/wiki/ac/share/modify.do" name="" method="post" id="form" name="form">

          <input type="hidden" value="${wikishare.joymeShareId}" name="id"/>
        <tr>
            <td width="200"></td>
            <td width="200">属于哪个wiki</td>
            <td>${wikishare.joymeWikiKey}</td>
            <input type="hidden" value="${wikishare.joymeWikiKey}" name="wiki"/>
        </tr>
        <tr>
            <td></td>
            <td>分享内容</td>
            <td><textarea name="content" id="content" style="width:600px;height:400px">${wikishare.joymeShareContent}</textarea>
                </br>
                <span id="error_content" style="color:red"></span></td>
        </tr>
        <tr>
            <td></td>
            <td>分享的图片地址</td>
            <td>
                <input type="text"  name="picurl" value="${wikishare.joymeSharePic}" size="50"/>
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