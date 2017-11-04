<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<a href="${ctx}/src/ios/uploadpage.do">上传SRC包</a> <br/>

已经可使用的源码
<c:forEach var="srcObj" items="${srcList}">
  <div>
      版本号：${srcObj.srcVersion}&nbsp;&nbsp;&nbsp;&nbsp;类型：${srcObj.srcType}&nbsp;&nbsp;&nbsp;&nbsp;地址：${srcObj.srcUrl}
  </div>
</c:forEach>
</body>
</html>