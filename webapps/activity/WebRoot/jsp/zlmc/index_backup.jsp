<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta name="wap-font-scale" content="no">
    <title>test</title>

    <script>
        var openId = '${openid}';
    </script>
</head>
<body >
${openid}<br/>
<input type="button" name="addUserScore" value="添加用户分数" onclick="addUserScore();"/><br/>
<input type="button" name="getUserOrder" value="获取用户排名" onclick="getUserOrder();"/><br/>
<input type="button" name="addUserInfo" value="增加用户信息" onclick="addUserInfo();"/><br/>
<input type="button" name="findRanking" value="排行榜" onclick="findRanking();"/><br/>
<div id="showInfo">

</div>
</body>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/preloadjs.min.js"></script>
<script>
    function addUserScore() {
        $.post("/activity/zlmc/addUserScore.do", {openid: openId,score:30}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var result = userRankObj.result;
                $('#showInfo').html( result );
            }
        })
    }
    function getUserOrder() {
        $.post("/activity/zlmc/getUserOrder.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var result = userRankObj.result;
                $('#showInfo').html( result );
            }
        })
    }
    function addUserInfo() {
        $.post("/activity/zlmc/addUserInfo.do", {openid: openId,username:'test1',nickname:'test1nick',telephone:'13800000000',address:'中国北京市海淀区知春路27号量子芯座大厦11-12层'}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var result = userRankObj.result;
                $('#showInfo').html(result);
            }
        })
    }
    function findRanking() {
        $.post("/activity/zlmc/findRanking.do", {topNum: 20}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                $('#showInfo').html('');
                var resultArray = userRankObj.result;
                for (var i=0 ; i < resultArray.length;i++){
                    alert(resultArray[i]);
                    $('#showInfo').append(resultArray[i].order+":" + resultArray[i].headimgurl+":" + resultArray[i].nickname+":" + resultArray[i].score);
                }
            }
        })
    }
</script>
</html>