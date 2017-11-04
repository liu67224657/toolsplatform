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
    <title>青云志</title>
    <link rel="stylesheet" href="http://static.joyme.com/mobile/qyz/css/base.css">
    <link rel="stylesheet" href="http://static.joyme.com/mobile/qyz/css/index.css">
    <style>
        .ti-add{
            position: absolute;
            top: 18%;
            width: 6.2%;
            left: 7%;
        }
    </style>
    <script>
        var openId = '${openid}';
        function getQueryCode(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
        var code = getQueryCode('code');
    </script>
</head>
<body>
<div class="main">

     <audio loop src="http://static.joyme.com/mobile/qyz/video/game.mp3" id="media" style="visibility: hidden"  preload=""></audio>
    <div class="nav-right">
        <img class="bonuses p2" src="http://static.joyme.com/mobile/qyz/images/me-bonuses.png">
        <img class="rule p2" src="http://static.joyme.com/mobile/qyz/images/rule.png">
        <img class="link p1" src="http://static.joyme.com/mobile/qyz/images/link.png">
        <img class="share-btn p3" src="http://static.joyme.com/mobile/qyz/images/share-btn.png">
        <img class="music" src="http://static.joyme.com/mobile/qyz/images/music_open.png">
    </div>


    <div class="decrypt-3">
    <img class="d3-step-1" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-1.png">
    <img class="d3-step-2" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-2.png">
    <img class="d3-step-3" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-3.png">
    <img class="d3-step-4" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-4.png">

    <img class="d3-step-5" src="http://static.joyme.com/mobile/qyz/images/jiemi-1-2.png">

    <div class="decrypt-3-window-bg"></div>
    <div class="decrypt-3-window">
    <img width="51" src="http://static.joyme.com/mobile/qyz/images/jiemi-1-ts-3.jpg">
    <img width="58" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-5.jpg">
    <img width="52" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-6.jpg">
    <img width="62.5" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-7.jpg">
    <img width="56.5" src="http://static.joyme.com/mobile/qyz/images/jiemi-3-ts-8.jpg">
    <img class="decrypt-1-close"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
    </div>
    </div>

    <div class="game-2">
        <img class="ti-add" src="http://static.joyme.com/mobile/qyz/images/ti.png">
        <div class="game-time">
            时间：<span>30</span> 秒
        </div>
        <div class="game2-main">
            <div class="hp">
                <div class="hp-main">
                    <s></s>
                </div>
            </div>
            <div class="game-num-main">
            </div>
            <img class="game-2-ql" width="44%" src="http://static.joyme.com/mobile/qyz/images/ql.png">
            <div class="game2-btn">
                <div class="ace-1"><s></s><img src="http://static.joyme.com/mobile/qyz/images/ace-1.png"></div>
                <div class="ace-2"><s></s><img src="http://static.joyme.com/mobile/qyz/images/ace-2.png"></div>
                <div class="ace-3"><s></s><img src="http://static.joyme.com/mobile/qyz/images/ace-3.png"></div>
            </div>
        </div>
        <div class="game-window-bg"></div>
        <div class="game-window game-1-show">
            <img class="game-1-ts-2" width="100%" src="http://static.joyme.com/mobile/qyz/images/game-2-ts-2.png">
            <img style="margin-top: 23%;" class="game-1-ts-1" width="100%" src="http://static.joyme.com/mobile/qyz/images/game-2-ts-1.png">

            <a href="/activity/qyz/page.do?map=1"><img class="game-tbn btn-back" width="30%" src="http://static.joyme.com/mobile/qyz/images/btn-back.png"></a>
            <a href="/activity/qyzThird/game.do?code="+code><img class="game-tbn btn-back" width="30%" src="http://static.joyme.com/mobile/qyz/images/btn-start.png"></a>

            <img class="game-main-bg" width="76" src="http://static.joyme.com/mobile/qyz/images/game-main-bg.png">
        </div>

        <div class="game-share">
            <div class="game-share-window-bg"></div>
            <div class="game-share-window">
                <img width="51" src="http://static.joyme.com/mobile/qyz/images/jiemi-1-ts-3.jpg">
                <img class="decrypt-1-close"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
            </div>
        </div>

    </div>



    <div class="game-share">
        <div class="game-share-window-bg"></div>
        <div class="game-share-window">
            <img width="51" src="http://static.joyme.com/mobile/qyz/images/jiemi-1-ts-3.jpg">
            <img class="decrypt-1-close"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
        </div>
    </div>

</div>

</body>
<script src="http://static.joyme.com/mobile/qyz/js/jquery.min.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/landscape.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/fastclick.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/index.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/debug.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/decrypt.js"></script>
<script src="http://static.joyme.com/mobile/qyz/js/game3.js"></script>
<script src="/static/js/qyz/getInfo.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    $(document).ready(function(){
        $('.loading').hide();
         decrypt3();
          mediaPlay();
    });
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
                title: '天呐，小凡又出事了。',
                link: 'http://t.cn/RcgbUW1',
                imgUrl: 'http://static.joyme.com/mobile/qyz/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '天呐，小凡又出事了。',
                desc: '命悬一线，张小凡遭神秘人迫害危在旦夕，只能你才能拯救他，赶快穿越去救他吧。',
                link: 'http://t.cn/RcgbUW1',
                imgUrl: 'http://static.joyme.com/mobile/qyz/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '天呐，小凡又出事了。',
                desc: '命悬一线，张小凡遭神秘人迫害危在旦夕，只能你才能拯救他，赶快穿越去救他吧。',
                link: 'http://api.activity.joyme.com/activity/qyz/page.do',
                imgUrl: 'http://static.joyme.com/mobile/qyz/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '天呐，小凡又出事了。',
                desc: '命悬一线，张小凡遭神秘人迫害危在旦夕，只能你才能拯救他，赶快穿越去救他吧。',
                link: 'http://api.activity.joyme.com/activity/qyz/page.do',
                //link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: 'http://static.joyme.com/mobile/qyz/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>