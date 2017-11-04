<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%--
  Created by IntelliJ IDEA.
  User: ericliu
  Date: 13-9-24
  Time: 上午11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>删文章</title></head>
<body>
<%
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    if ((username == null || username.length() == 0) && (pwd == null || pwd.length() == 0)) {
        return;
    } else {
%>
<form action="<%=request.getContextPath()%>/ac/delete.jsp" method="post">
    填写名称：<input name="name" value="<%=request.getParameter("name")!=null?request.getParameter("name"):""%>"/>
    填写域名:<input name="domain" value="<%=request.getParameter("domain")!=null?request.getParameter("domain"):""%>"/>
    <input name="username" type="hidden" value="<%=request.getParameter("username")%>"/>
    <input name="pwd" type="hidden" value="<%=request.getParameter("pwd")%>"/>
    <input type="submit" value="删除"/>
</form>
<%
    }
%>

<%
    String name = request.getParameter("name");
    String domain = request.getParameter("domain");
    if ((name == null || name.length() == 0) && (domain == null || domain.length() == 0)) {
        return;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    int i = -1;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://192.168.30.151:3311/VIEWLINE?user=groupadmin&password=groupadmin";
        con = DriverManager.getConnection(url);

        String param = name;
        String sql = "UPDATE VIEWLINE.VIEWLINE_LINEITEM t0 INNER JOIN PROFILE.PROFILE_BLOG t1 ON t1.UNO=t0.DIRECTUNO " +
                "SET t0.VALIDSTATUS='removed' WHERE t1.SCREENNAME = ?";

        if (domain != null && domain.length() > 0) {
            sql = "UPDATE VIEWLINE.VIEWLINE_LINEITEM t0 INNER JOIN PROFILE.PROFILE_BLOG t1 ON t1.UNO=t0.DIRECTUNO " +
                    "SET t0.VALIDSTATUS='removed' WHERE t1.DOMAINNAME = ?";
            param = domain;
        }

        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, param);

        i = pstmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
%>
<%
    if (i == -1) {

    } else {
%>删除<%=i%>条<%
    }
%>
</body>
</html>