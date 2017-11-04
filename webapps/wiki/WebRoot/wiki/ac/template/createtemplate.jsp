<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript">
    $(document).ready(function() {
        $('#form').submit(function() {
            var template = $('#templateName').val();
            if (template.length == 0) {
                $('#error_templateName').html('模板名称不能为空');
                return false;
            }

            var templateContext = $('#templateContext').val();
            if (templateContext.length == 0) {
                $('#error_templateContext').html('模板内容不能为空');
                return false;
            }

             var css = $('#css').val();
            if (css.length == 0) {
                $('#error_css').html('请上传CSS');
                return false;
            }
        });

        $('#preview').click(function() {
            $('#p_key').val($('#wiki').val());
            $('#p_channel').val($('#channel').val());
            $('#p_isIndex').val($('#isIndex').val());
            $('#p_praseFactory').val($('#praseFactory').val());
             $('#p_templateContext').val($('#templateContext').val());

            $('#form_preivew').submit();
        })

    })
</script>
    <form action="${ctx}/wiki/ac/templatepreview.do" name="" method="post" id="form_preivew">
        <input type="hidden" name="key" id="p_key"/>
        <input type="hidden" name="channel" id="p_channel"/>
        <input type="hidden" name="isIndex" id="p_isIndex"/>
        <input type="hidden" name="praseFactory" id="p_praseFactory"/>
        <input type="hidden" name="templateContext" id="p_templateContext"/>

    </form>
    <table>
        <form action="${ctx}/wiki/ac/createtemplateAction.do" name="" method="post" id="form" name="form" enctype="multipart/form-data">
          <input type="hidden" name="csspath"  value=""/>
            <input type="hidden" name="wikitype"  value="${wikitype}"/>
            <input type="hidden" name="templateid"  value="-1"/>
            <tr>
            <td width="200"></td>
            <td width="200">模板名称</td>
            <td><input id="templateName" type="text" name="templateName" size="100"/>
                <span id="error_templateName" style="color:red"></span>
            </td>
        </tr>

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
            <td>属于哪个渠道</td>
            <td><select name="channel" id="channel">
                <c:forEach items="${channelMap}" var="entry">
                    <option  <c:if test="${entry.key == 'default'}">selected</c:if>  value="<c:out value="${entry.key}"/>"><c:out value="${entry.key}"/></option>
                </c:forEach>
            </select></td>
        </tr>

        <tr>
            <td></td>
            <td>首页 or 子页</td>
            <td><select name="isIndex" id="isIndex">
                <option value="0">两者</option>
                <option value="1">首页</option>
                <option value="2">子页</option>
                <option value="3">工具页</option>
            </select></td>
        </tr>
        <tr>
            <td></td>
            <td>解析类</td>
            <td><select name="praseFactory" id="praseFactory">
                <option value="default">默认</option>
            </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>模板内容</td>
            <td><textarea name="templateContext" id="templateContext" style="width:600px;height:400px"></textarea>
                </br>
                <span id="error_templateContext" style="color:red"></span></td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <input type="button" id="preview" value="预览">
                <input type="submit" id="submit" value="保存">
            </td>
        </tr>
        </form>
    </table>

</html>