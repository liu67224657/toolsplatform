<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/views/jsp/common/jstl.jsp" %>
<html>
<head><title>360工具</title></head>
<body>
<form action="${ctx}/js360/calmd5" method="post" enctype="multipart/form-data">
计算文件的MD5： <input type="file" name="tmppath" >
    <input type="submit" value="提交"/>
</form>
MD5值：<input type="text"  value="${md5}" readonly="true"/>
<br/><br/>
<hr/>
<form action="${ctx}/js360/uploadexcel" method="post" enctype="multipart/form-data">
上传360格式的EXCEL文件：<input type="file" name="excelfile" ><input type="submit" value="提交"/>
</form>
<br/>
Excel文件地址：${excelPath}&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${excelDate!=null}">
Excel修改时间：<fmt:formatDate value="${excelDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
</c:if>
<br/><br/>
<hr/>
<form action="${ctx}/js360/genjsobj" method="post">
 <input type="submit" value="生成360的JS对象"/>
</form>
JS对象结果：<br/>
<c:if test="${fn:length(error)>0}">错误信息：<span style="color:red;">${error}</span></c:if>
<textarea readonly="true" style="margin: 2px; width: 907px; height: 246px;">${objString}</textarea>
<br/><br/>
<hr/>
HTML结果：<br/>
<textarea readonly="true" style="margin: 2px; width: 907px; height: 246px;">${htmlString}</textarea>
</body>
</html>