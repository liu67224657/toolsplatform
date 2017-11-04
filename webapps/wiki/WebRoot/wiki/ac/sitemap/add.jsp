<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var i = 0;
    var delStr = "";
    function addTr() {
        var trHtml = '<tr id="add' + i + '"><td><input type="text" size="80" name="loc' + i + '" id="loc' + i + '" />'
                + '</td><td><input type="text" size="10" name="priority' + i + '" id="priority' + i + '" /></td><td>'
                + '<a href="javascript:delTr(1,' + i + ');">删除</a></td></tr>';
        i++;
        $("#table").append(trHtml);
    }
    function delTr(type, i) {
        var gnl = confirm("你真的确定要删除吗?");
        if (gnl == false) {
            return false;
        }
        $("#add" + i).remove();
        if (type != 1) {
            if (delStr == "") {
                delStr = i;
            } else {
                delStr = delStr + "___" + i;
            }
        }
    }
    function add() {
        var addStr = "";
        var url = "${pageContext.request.contextPath}/wiki/ac/st/add.do";
        for (var size = 0; size < i; size++) {
            var loc = $("#loc" + size).val();
            var priority = $("#priority" + size).val();

            if (loc == undefined || priority == undefined) {
                continue;
            }

            if ($.trim(loc) == "" || $.trim(priority) == "") {
                alert("请检查是否存在空值。");
                return false;
            }
            if (addStr == "") {
                addStr = loc + "___" + priority
            } else {
                addStr = addStr + "--" + loc + "___" + priority
            }
        }

        url = url + "?wiki=${wiki}&addStr=" + addStr + "&delStr=" + delStr;
        $.ajax({
                    url : url,
                    type : "post",
                    async:false,
                    success : function(req) {
                        window.location.reload();
                    },
                    error:function() {
                        alert('获取失败，请刷新');
                    }
                });


    }
</script>
<div align="center">
    查询条件:
    <form name="form1" method="post" action="${pageContext.request.contextPath}/wiki/ac/st/sitemaplist.do">
        wiki:
        <select name="wiki">
            <option value="">所有</option>
            <c:forEach items="${wikiMap}" var="entry">
                <option
                        <c:if test="${param.wiki == eentry}">selected="selected"</c:if>
                        value="<c:out value="${entry}"/>"><c:out value="${entry}"/></option>
            </c:forEach>
        </select>
        <input type="submit" value="查询"/>
    </form>
</div>
<div align="center"><span>当前选择wiki：${wiki}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:addTr()">增加一行</a></div>
<div align="center">
    <form action="${pageContext.request.contextPath}/wiki/ac/st/uploadxml.do" name="" method="post" id="form"
          name="form"
          enctype="multipart/form-data">
        <input type="hidden" name="wiki" value="${wiki}"/>
        <input type="file" name="xmlFile" id="xmlFile"/>
        <input type="submit" id="submit" value="上传XML">
    </form>
</div>
<table border=1 width=800 align="center" id="table" style="BORDER-COLLAPSE: collapse">
    <tr>
        <td>地址</td>
        <td>权重</td>
        <td width="100px">操作</td>
    </tr>

    <c:forEach var="item" items="${list}">
        <tr id="add${item.sitemapid}">
            <td>${item.loc}</td>
            <td>${item.priority}</td>
            <td><a href="javascript:delTr(0,'${item.sitemapid}')">删除</a></td>
        </tr>
    </c:forEach>

</table>

<div align="center">
    <a href="javascript:add()">保存</a>
</div>
</html>
