<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<title>dtree</title>
<meta name="description" content="dtree">
<meta name="keywords" content="dtree">
  <script src="../../js/jquery-1.9.1.min.js" type="text/javascript"></script>
  <link href="../../js/dtree/dtree.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="../../js/dtree/dtree.js"></script>
</head>
<body>
<div class="dtree">
  <script>
    d = new dTree('d');
    d.add(0,-1,'接口');
    <c:forEach var="tag" items="${list}" varStatus="st">
    d.add(${tag.key},${tag.key}, "${tag.value}");
    </c:forEach>
    document.write(d);
    d.openAll();
    // d.closeAll();
  </script>
</div>
</body>
</html>

