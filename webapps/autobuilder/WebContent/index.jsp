<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/jstllibs.jsp" %>
<html>
<head><title>自动打包工具</title></head>
<body>
<B>安卓打包工具</B>
<a href="${ctx}/src/android/page.do">安卓源码SRC管理</a>
<a href="${ctx}/resource/android/page.do">安卓资源配置管理</a>
<a href="${ctx}/build/android/selectcode.do">安卓打包操作</a>
<br/>
<br/>
<hr/>
<br/>
<br/>
<B>IOS打包工具</B>
<a href="${ctx}/src/ios/page.do">IOS源码SRC管理</a>
<a href="${ctx}/resource/ios/page.do">IOS资源配置管理</a>
<a href="${ctx}/build/ios/selectcode.do">IOS打包操作</a>
<hr/>
<br/>
<br/>
<B>安卓小端批量生成工具</B>
<a href="${ctx}/batch/android/template/page.do">安卓模板管理</a>
<a href="${ctx}/batch/android/pkg/buildpage.do">安卓打包操作</a>
<br/>
<br/>
<B>IOS小端批量生成工具</B>
<a href="${ctx}/batch/ios/template/page.do">IOS模板管理</a>
<a href="${ctx}/batch/ios/pkg/buildpage.do">IOS打包操作</a>
<br/>
<br/>
<B>默认背景图管理</B>
<a href="${ctx}/batch/image/uploadpage.do">背景图上传</a>
</body>
</html>