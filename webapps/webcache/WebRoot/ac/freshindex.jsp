<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.webcache.container.PropertiesContainer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script language="javascript">
    function sure() {
        if (confirm("是否清空")) {
            this.location.href = "/ac/cleanCache.do";
        }
    }

    function submit() {
        var url = document.getElementById('refreshcdn_url').value;
        if (url == null || url.length == 0) {
            alert('请填写URL地址！！！！！！！！！');
            return false;
        }
    }

    $(document).ready(function () {
        $("#clearpage_button").click(function(){
            var clearpage = $.trim($("#clearpage").val());
            if(clearpage==""){
                alert('请填写URL地址！！！！！！！！！');
                $("#clearpage").focus();
                return false;
            }
            $.ajax({
                url: "/ac/clearindexpage.do?clearpage="+clearpage,
                type: "get",
                success: function(req) {
                    $("#clearpage").val("");
                    alert(req);
                }
            });
        });
    });

</script>
<div><a href="<%=PropertiesContainer.getInstance().getToolsLoginOutURL()%>" style="float:right;">退出</a></div>
<br/>
<a href="#" onclick="sure()" style="display: none;">清除缓存</a>
<br/>
<br/>
<a href="/ac/urlrule/list.do">URL规则配置</a>
<br/>
<br/>

<form action="/ac/refreshcdn.do" id="refreshcdnform" method="post" onsubmit="" style="display: none;">
    地址：<input type="text" value="" name="url" id="refreshcdn_url" size="64"/>*必填项填写地址，例如:http://v.joyme.com
    或者http://v.joyme.com/xxx.html
    <br/>
    <input type="submit" value="提交">
</form>


刷新CDN,单页/目录<a href="http://wiki.enjoyf.com/wiki/Joyme_toolsplatform_cmsimage_ac" target="_blank" style="color: #ff0000;">后台刷新规则填写</a>：<br/>
地址：<input type="text" value="" name="clearpage" id="clearpage" size="64"/>*必填项填写地址，例如:http://v.joyme.com 或者http://v.joyme.com/xxx.html
<br/>
<input type="button" value="提交" id="clearpage_button">
