<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<form action="${pageContext.request.contextPath}/wiki/ac/page/doaddwiki.do" name="form1" method="post">
    <input type="text" name="wiki_key"  value="wiki"/>
    <input type="text" name="wiki_url"  value=""/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>