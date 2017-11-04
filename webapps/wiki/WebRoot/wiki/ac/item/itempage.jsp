<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script language="javascript">
    $(document).ready(function() {
        $('#link_create').click(function() {
            window.location.href = '${pageContext.request.contextPath}/wiki/ac/createItem.do?wikitype=' + $('#select_wikitype').val();
        });

        $('#link_list').click(function() {
            window.location.href = '${pageContext.request.contextPath}/wiki/ac/itemList.do?pageNum=1&wikitype=' + $('#select_wikitype').val();
        });
    })
</script>
<body>
    <select id="select_wikitype" name="wikitype">
        <option value="wiki">PC版</option>
        <option value="mwiki">手机版</option>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a id="link_create" href="#" >创建标签</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a id="link_list" href="#"/>标签列表</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</body>
</html>