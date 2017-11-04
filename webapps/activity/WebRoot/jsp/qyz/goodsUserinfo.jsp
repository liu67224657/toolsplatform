<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="http://static.joyme.com/mobile/cms/jfsc/css/common.css">
    <script type="text/javascript">
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {return false}, true)
        }, true);
        var openId = '${openid}';
    </script>
</head>
<body>
<div id="wrapper">
    <div id="main">
        <div class="user-box">
            <div class="user-mess">
                <cite><img src="${activityCountry.headimgurl}" alt=""></cite>
                <span>
						<font>${activityCountry.nickname}</font>
						<b>总积分：${totalScore}</b>
					</span>
            </div>
            <form class="sub-form">
                <input type="text" id="nickname" name="nickname" placeholder="收件人" value="${tempNickname}">

                <c:if test="${userinfo==null}">
                    <div>您尚未绑定手机号，请去公众号绑定</div>
                    <textarea id="address" name="address" placeholder="收货地址"></textarea>
                </c:if>
                <c:if test="${userinfo!=null}">
                    <input type="text" id="telephone" name="telephone" readonly value="${userinfo.telephone}">
                    <textarea id="address" name="address" placeholder="收货地址">${userinfo.address}</textarea>
                </c:if>
                <a href="javascript:;" id="saveUserALink" onclick="saveUserinfo()" >保存</a>
            </form>
            <dl>
                <dt>*温馨提示</dt>
                <dd>1.请务必<em>确认以上信息无误</em>哦，不然你可能将无法收到兑换的奖励哦；</dd>
                <dd>2.由于信息输入错误导致无法发放或发放错误的，不予补发奖品，不予补偿微信积分；</dd>
                <dd>3.实物奖品将于兑换之日起<em>5个工作日内</em>发放；</dd>
                <dd>4.同一游戏内道具，一个微信号仅能兑换一次；</dd>
                <dd>5.进入青云志手游<em> (qyzgame)</em>公众号→<em>点击【青云福利】→【绑定手机】</em>进行绑定。</dd>
                <dd>6.同一游戏内道具，每个游戏角色仅能通过微信兑换获得一次，如有多个微信号兑换给同一角色的，<em>仅能使用一次</em>，已扣除的微信积分不予补发。</dd>
                <dd>7、所有实物奖励的邮寄费用需由申请兑换人承担。</dd>
            </dl>
        </div>

    </div>
</div>
<div class="nav-box">
    <div class="nav-con">
        <a href="/weixinop/qyzGoods/page.do?openid=${openid}">积分商城</a>
        <a href="/weixinop/qyzExchange/page.do?openid=${openid}">兑换记录</a>
        <a href="javascript:;" class="active">个人中心</a>
    </div>
</div>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script src="http://static.joyme.com/mobile/cms/jfsc/js/action.js"></script>
</body>
<script>
    var addressVal = $('#address').val();
    if(addressVal !=null && addressVal !='' ){
        $('#saveUserALink').addClass("on");
    }
    function saveUserinfo() {
        var nickname = $('#nickname').val();
        var address = $('#address').val();
        var telephone = $('#telephone').val();
        if (nickname == '' || nickname == null) {
            alert("收件人不能为空");
            $('#nickname').val('');
        } else if (address == '' || address == null) {
            alert("收货地址不能为空");
        } else if (telephone == '' || telephone == null) {
            alert("手机号不能为空");
            $('#telephone').val('');
        } else {
            $.post("/weixinop/qyzUserinfo/addUserInfo.do", {
                openid:openId,
                nickname: nickname,
                telephone: telephone,
                address: address
            }, function (data) {
                if (data != "") {
                    var userRankObj = eval("(" + data + ")");
                    var result = userRankObj.rs;
                    if (result == "1") {
                        alert("添加成功");
                    } else {
                        alert("添加失败，请重新添加");
                    }
                }
            })
        }
    }
</script>
</html>