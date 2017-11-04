<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
	var err = '${errorMsg}';
	if(err != ''){
		alert(err);
	}
	this.location.href="/ac/seo.do";
</script>