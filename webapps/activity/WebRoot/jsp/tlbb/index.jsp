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
    <script>
        var openId = '${openid}';
    </script>
</head>
<body>
<div class="warp">
    <div class="main-top">
        <div class="top-pd">
            <img width="27.2%" class="logo" src="http://static.joyme.com/mobile/tlbb/img/logo.png">
            <a HREF="/weixinop/tlbbRule/rule.do"><img width="11.9%" class="rule" src="http://static.joyme.com/mobile/tlbb/img/rule.png"></a>
            <img class="btn" src="http://static.joyme.com/mobile/tlbb/img/btn.png">
            <p>少侠本月已签到<span>0</span>天，请继续努力哦~</p>
        </div>
    </div>
    <div class="main-bottom">
        <div class="date">
            <img width="37.8%" src="http://static.joyme.com/mobile/tlbb/img/date-title.png">
            <div class="format">
                <div class="day">
                    <!--<spa class="arrow-left"></spa>-->
                    <!--<spa class="arrow-right"></spa>-->
                    <p>2016年9月</p>
                </div>
                <div class="week">
                    <span>日</span>
                    <span>一</span>
                    <span>二</span>
                    <span>三</span>
                    <span>四</span>
                    <span>五</span>
                    <span>六</span>
                </div>
                <div class="getDate">
                    <span>1</span>
                    <span>2</span>
                    <span>3</span>
                    <span>4</span>
                    <span>5</span>
                    <span>6</span>
                    <span>7</span>
                    <span>8</span>
                    <span>9</span>
                    <span>10</span>
                    <span>11</span>
                    <span>12</span>
                    <span>13</span>
                    <span>14</span>
                    <span>15</span>
                    <span>16</span>
                    <span>17</span>
                    <span>17</span>
                    <span>17</span>
                    <span>17</span>
                    <span>17</span>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <a href="/weixinop/signHistory/integral.do">
            <img height="100%" src="http://static.joyme.com/mobile/tlbb/img/inquire.png">
        </a>
    </div>
</div>

<div class="popup">
    <div class="success">
        <img class="close" src="http://static.joyme.com/mobile/tlbb/img/close.png">
        <p class="p-tips-1">恭喜少侠，签到成功！</p>
        <p class="p-tips-2">获得<span>10</span>积分</p>
        <p class="p-tips-3">您目前共累计获得<span id="totalScore">100</span>积分</p>
    </div>
    <div class="lost">
        <img class="close" src="http://static.joyme.com/mobile/tlbb/img/close.png">
        <img class="tips" src="http://static.joyme.com/mobile/tlbb/img/tips.jpg">
        <p>少侠今日已成功签到</p>
        <p>请明天再来哦</p>
    </div>
</div>
</body>
<script src="http://static.joyme.com/mobile/tlbb/js/jquery.min.js"></script>
<script src="/static/js/tlbb/index.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    tianlongbabu.init();
    $('.btn').click(function (){
        if($(this).attr('src') != 'http://static.joyme.com/mobile/tlbb/img/btn-not.png'){
            tianlongbabu.past();
        }else{
            $('.popup,.lost').show();
        }
    })
</script>
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