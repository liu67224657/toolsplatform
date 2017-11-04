<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="pragram" content="no-cache">
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <title>天龙八部</title>
    <link rel="stylesheet" href="http://static.joyme.com/mobile/tlbb/css/base.css">
    <link rel="stylesheet" href="http://static.joyme.com/mobile/tlbb/css/index.css">
</head>
<body>
<div class="warp warp-rule-bg">
    <img class="rule-1" width="90.6%" src="http://static.joyme.com/mobile/tlbb/img/rule-1.png">
    <p>1、每日签到可获得10积分；</p>
    <p>2、本签到漏签不能补签；</p>
    <p>3、签到积分可用于后续兑换游戏礼品；</p>
    <p>4、积分越多所兑换礼品价值越高。</p>
    <a href="javascript:history.go(-1)" ><img width="28%" class="back" src="http://static.joyme.com/mobile/tlbb/img/back.png"></a>
    <img width="100%" src="http://static.joyme.com/mobile/tlbb/img/rule-2.png">
</div>
</body>
<script src="http://static.joyme.com/mobile/tlbb/js/jquery.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    $(document).ready(function () {
        //设置openid
        wx.config({
            debug: false,
            appId: '${appId}', // 必填，公众号的唯一标识
            timestamp: '${timestamp}', // 必填，生成签名的时间戳
            nonceStr: '${nonceStr}', // 必填，生成签名的随机串
            signature: '${signature}',// 必填，签名，见附录1
            jsApiList: [
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareQZone'
            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });


        wx.ready(function () {
            //分享到朋友圈
            wx.onMenuShareTimeline({
                title: '【天龙八部手游】人在江湖飘 怎能不签到？',
                link: 'http://t.cn/RcrGSed',
                imgUrl: 'http://static.joyme.com/mobile/tlbb/img/share-icon.jpg',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '签到上线 少侠速归',
                desc: '【天龙八部手游】每日签到上线啦 快来领取积分！',
                link: 'http://t.cn/RcrGSed',
                imgUrl: 'http://static.joyme.com/mobile/tlbb/img/share-icon.jpg',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '签到上线 少侠速归',
                desc: '【天龙八部手游】每日签到上线啦 快来领取积分！',
                link: 'http://api.activity.joyme.com/weixinop/tlbb/page.do',
                imgUrl: 'http://static.joyme.com/mobile/tlbb/img/share-icon.jpg',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '签到上线 少侠速归',
                desc: '【天龙八部手游】每日签到上线啦 快来领取积分！',
                link: 'http://api.activity.joyme.com/weixinop/tlbb/page.do',
                imgUrl: 'http://static.joyme.com/mobile/tlbb/img/share-icon.jpg',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>