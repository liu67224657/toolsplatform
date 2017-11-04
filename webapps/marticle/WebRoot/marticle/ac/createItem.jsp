<%@page import="com.enjoyf.mcms.service.JoymeChannelContentService"%>
<%@page import="com.enjoyf.mcms.container.ConfigContainer"%>
<%@page import="com.enjoyf.mcms.bean.temp.CreateItemBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.enjoyf.mcms.facade.AdminConsoleFacade"%>

<%
	if(session.getAttribute("ac-user") == null){
		response.sendRedirect("./login.jsp");
		return;
	}
	AdminConsoleFacade facade = new AdminConsoleFacade();
	String specName = request.getParameter("specName");
	String specType = request.getParameter("specType");
	String specLanguage = request.getParameter("specLanguage");
	String specSize = request.getParameter("specSize");
	String specVersion = request.getParameter("specVersion");
	String specPicUrl = request.getParameter("specPicUrl");
	String filePath = request.getParameter("filePath");
	String specId = request.getParameter("specId");
	String isCompressImages = request.getParameter("isCompressImages");
	String localPath = ConfigContainer.getLocalPath(request);
	JoymeChannelContentService service = new JoymeChannelContentService();
//	Map channelMap = service.getChannelMap(request);
	
	CreateItemBean bean = new CreateItemBean();
	bean.setSpecName(specName);
	bean.setSpecType(specType);
	bean.setSpecLanguage(specLanguage);
	bean.setSpecSize(specSize);
	bean.setSpecVersion(specVersion);
	bean.setSpecPicUrl(specPicUrl);
	bean.setFilePath(filePath);
	bean.setSpecId(specId);
	bean.setLocalPath(localPath);
//	bean.setChannelMap(channelMap);
	bean.setIsCompressImages(isCompressImages);
	
	boolean isSuccess = facade.createInsertJoymeSpec(bean);
	
	if(isSuccess)
		out.print("ok");
	else 
		out.print("failed");
%>

<a href="./main.jsp?filePath=${param.filePath}">返回</a>