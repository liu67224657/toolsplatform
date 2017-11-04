<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.enjoyf.cms.container.PropertiesContainer" %>
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
                url: "/ac/clearpage.do?clearpage="+clearpage,
                type: "get",
                success: function(req) {
                    $("#clearpage").val("");
                    alert("刷新成功");
                }
            });
        });
    });

</script>
<div><a href="<%=PropertiesContainer.getToolsLoginOutURL()%>" style="float:right;">退出</a></div>
<a href="/ac/seo.do">配置跳转规则</a>
<br>
<a href="#" onclick="sure()" style="display: none;">清除缓存</a>

<br/>
<br/>
刷新CDN：
<form action="/ac/refreshcdn.do" id="refreshcdnform" method="post" onsubmit="">
    地址：<input type="text" value="" name="url" id="refreshcdn_url" size="64"/>*必填项填写地址，例如:http://v.joyme.com
    或者http://v.joyme.com/xxx.html
    <%--刷新类型:<select name="type">--%>
    <%--<option value="File">文件</option>--%>
    <%--<option value="Directory">目录</option>--%>
    <%--</select>   *如果是http://v.joyme.com/gamecenter/ 选择目录，如果是http://v.joyme.com/xxx.html选择文件（有后缀名）--%>
    <br/>
    <input type="submit" value="提交">
</form>


刷新单页或者目录<a href="http://wiki.enjoyf.com/wiki/Joyme_toolsplatform_cmsimage_ac" target="_blank" style="color: #ff0000;">后台刷新规则填写</a>：<br/>
地址：<input type="text" value="" name="clearpage" id="clearpage" size="64"/>*必填项填写地址，例如:http://v.joyme.com 或者http://v.joyme.com/xxx.html
<br/>
<input type="button" value="提交" id="clearpage_button">
