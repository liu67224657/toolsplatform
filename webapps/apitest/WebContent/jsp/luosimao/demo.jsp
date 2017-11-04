<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>https://luosimao.com</title>
    <meta name="description" content="rice">
    <meta name="keywords" content="rice">
    <script src="//captcha.luosimao.com/static/dist/api.js?vvv=1"></script>
    <script src="../../js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script>

    </script>
</head>
<body>
<div>
    1、官网地址：<a href="https://luosimao.com">luosimao</a></br>
    2、免费说明:<a href="https://luosimao.com/service/captcha">链接在此</a></br>
    3、难度选择文档：<a href="https://luosimao.com/docs/api/56#level">查看</a>，图形及难度可动态配置</br>
    4、有一定的统计后台。
</div>

<div style="margin-left: 200px;margin-top: 200px;">

    <input type="hidden" id="lc-captcha-response" name="luotest_response" value="" ><br/>

    <div class="l-captcha" data-site-key="0c4561bb455353db936475c651ee7ac4" data-callback="getResponse"></div>

    <input type="button" value="提交" id="sub" disabled/>
    <input type="button" value="重新验证" onclick="reset()"/>
</div>
<script>
    function getResponse(resp){
        console.log("验证成功后获取的值-->"+resp);
        $("#sub").attr("disabled",false);
    }

    function reset(){
        LUOCAPTCHA.reset();
        $("#sub").attr("disabled",true);
    }
    $(document).ready(function () {
        $("#sub").bind("click", function () {
            var username = $("#username").val();
            var passsword = $("#passsword").val();
            var luotest_response = $("#lc-captcha-response").val();

            $.ajax({
                url: "/luosimao/api",
                data: {username: username, passsword: passsword, luotest_response: luotest_response},
                dataType: "json",
                type: "POST",
                success: function (data) {
                    alert(JSON.stringify(data));
                }
            });
        })

    });
</script>
</body>
</html>

