<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.def" var="def"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device.width, initial-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="http://reswiki1.joyme.com/css/wiki/util.css" />
	<title>错误提示</title>
</head>
 <body>
 <fmt:message key="${error}" bundle="${def}"/>
 </body>
</html>