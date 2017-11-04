<%@ page import="com.enjoyf.autobuilder.bean.BuildLog" %>
<%@ page import="com.enjoyf.autobuilder.service.BuildLogService" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.enjoyf.autobuilder.util.FileUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id = Integer.parseInt(request.getParameter("lid"));
    String name=request.getParameter("name");
    BuildLogService buildLogService = new BuildLogService();

    BuildLog buildLog = buildLogService.queryBuildLogbyId(null, id);


    JSONObject object = JSONObject.fromObject(buildLog.getJsonstring());
    String apkpath = object.getString("apkpath");

    response = FileUtil.download(apkpath+"/"+name, response);


    out.clear();
    out = pageContext.pushBody();
%>