<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>java补丁格式化</title>
    <meta name="description" content="rice">
    <meta name="keywords" content="rice">
    <script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
</head>
<body>


输入<br/>
<textarea name="content" id="content" cols="150" rows="10" placeholder="//online/platform/main/framework/src/main/java/com/enjoyf/platform/serv/ask/AskLogic.java#123
//online/platform/main/webapps/tools/htdocs/view/jsp/wanba/admenu/createtopmenu.jsp#5
//online/platform/main/webapps/tools/java/com/enjoyf/webapps/tools/webpage/controller/wanba/AskRecommentAdMenuController.java#7"></textarea>
<br/>
<input type="button" value="提交" id="format"/>

<br/>
<br/>
输出<br/>
<textarea name="content" id="result" cols="150" rows="10" placeholder="com.enjoyf.platform.serv.ask.AskLogic com.enjoyf.webapps.tools.webpage.controller.wanba.AskRecommentAdMenuController"></textarea>
<script>
    $(document).ready(function () {
        $("#format").bind("click", function () {
            var con = $("#content").val();

            if (con == "") {
                alert("请填写内容");
                return false;
            }
            $.ajax({
                url: "/patch/format",
                data: {content: con},
                dataType: "json",
                type: "post",
                success: function (data) {
                  $("#result").val(data.result);
                }
            });
        });
    });
</script>
</body>
</html>

