<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/views/jsp/common/jstl.jsp" %>
<html>
<head><title>360工具</title></head>
<body>
<form action="${ctx}/lscs/uploadexcel" method="post" enctype="multipart/form-data">
上传LSCS卡牌格式的EXCEL文件：<input type="file" name="excelfile" ><input type="submit" value="提交"/>
</form>
<br/>
Excel文件地址：${excelPath}&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${excelDate!=null}">
Excel修改时间：<fmt:formatDate value="${excelDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
</c:if>
<br/><br/>
<hr/>

<form action="${ctx}/lscs/uploadcategory" method="post" enctype="multipart/form-data">
上传LSCS职业格式的EXCEL文件：<input type="file" name="excelfile" ><input type="submit" value="提交"/>
</form>
<br/>
Excel文件地址：${excelCatePath}&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${excelCateDate!=null}">
Excel修改时间：<fmt:formatDate value="${excelCateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
</c:if>
<br/><br/>
<hr/>

<form action="${ctx}/lscs/genjsobj" method="post">
 <input type="submit" value="生成LSCS卡牌JS对象"/>
</form>
卡牌对象结果：<br/>
<c:if test="${fn:length(error)>0}">错误信息：<span style="color:red;">${error}</span></c:if>
<textarea readonly="true" style="margin: 2px; width: 907px; height: 246px;">${objString}</textarea>
<br/><br/>
<hr/>
职业对象结果：<br/>
<c:if test="${fn:length(error)>0}">错误信息：<span style="color:red;">${error}</span></c:if>
<textarea readonly="true" style="margin: 2px; width: 907px; height: 246px;">${categoryString}</textarea>
<br/><br/>
<hr/>
</body>
</html>