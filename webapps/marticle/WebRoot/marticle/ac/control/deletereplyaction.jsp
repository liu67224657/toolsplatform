<%@page import="java.sql.ResultSet"%>
<%@page import="com.enjoyf.framework.jdbc.bean.DataBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.mysql.jdbc.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("../login.jsp");
		return;
	}

    String name = request.getParameter("name");
    String domain = request.getParameter("domain");
    String able = request.getParameter("able");
    System.out.println(able);
    if ((name == null || name.length() == 0) && (domain == null || domain.length() == 0)) {
        return;
    }

    Connection conn = null;
    int i = -1;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://192.168.30.151:3311/VIEWLINE?user=groupadmin&password=groupadmin";
        //String url = "jdbc:mysql://db001.dev:3306/VIEWLINE?user=root&password=654321";
        conn = DriverManager.getConnection(url);
		conn.setAutoCommit(false);
        
        BaseJDBCDAOImpl dao = new BaseJDBCDAOImpl();
        String sql = "SELECT UNO FROM PROFILE.PROFILE_BLOG WHERE 1=1 ";
        List list = new ArrayList();
        if(name != null && !"".equals(name.trim())){
            sql += "  AND SCREENNAME = ?";
            list.add(name);
        }
        if(domain != null && !"".equals(domain.trim())){
            sql += "  AND DOMAINNAME = ?";
            list.add(domain);
        }
        
        String uno = "";
        
        Object[] objects = list.toArray();
        DataBean dbean = dao.executeBindingQuery(conn, sql, objects);
        ResultSet rs = dbean.getRs();
        
        if(rs.next()){
            uno = rs.getString(1);
        } else {
            dao.cleanup(dbean);
            return;
        }
        dbean.getStmt().close();
        
        String tableSeq = "";
    	System.out.println(able);
    	System.out.println(uno);
        for(i = 0 ; i < 100; i++){
            if(i < 10){
                tableSeq = "0" + i;
            } else {
                tableSeq = i+"";
            }
            sql = "UPDATE CONTENT.CONTENT_INTERACTION_"+tableSeq+" SET REMOVESTATUS = ? WHERE INTERACTIONUNO = ?";
            objects = new Object[]{able, uno};
        	dao.executeBindingUpdate(conn, sql, objects, true, false);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
%>

OK<a href="./deletereply.jsp">返回</a>