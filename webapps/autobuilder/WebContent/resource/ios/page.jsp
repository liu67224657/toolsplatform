<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<a href="${ctx}/resource/ios/uploadpage.do">上传资源包</a> <br/>

可使用的资源
<c:forEach var="resObj" items="${resourceList}">
  <div style="border:1px">
      资源名称：${resObj.resourceName}&nbsp;&nbsp;&nbsp;&nbsp;包名：${resObj.resourceCode}&nbsp;&nbsp;&nbsp;&nbsp;版本：${resObj.resourceVersion}
      <br/>&nbsp;&nbsp;&nbsp;&nbsp;icons地址：${resObj.resourceIcons}
      <br/>&nbsp;&nbsp;&nbsp;&nbsp;images地址：${resObj.resourceImages}
      <br/>&nbsp;&nbsp;&nbsp;&nbsp;profiles地址：${resObj.resourceProfiles}
  </div>
</c:forEach>
</body>
</html>