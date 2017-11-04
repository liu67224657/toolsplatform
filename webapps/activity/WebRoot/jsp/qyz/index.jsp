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
    <script>
        var openId = '${openid}';
    </script>
</head>
<style>
    html,body {
        padding: 0;
        margin: 0;
        width: 100%;
        height: 100%;
        -webkit-user-select: none;
        user-select: none;
        overflow: hidden;
    }

    .videobox {
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0px;
        top: 0;
        overflow: hidden;
        background: #000000;
        z-index: 999;
    }

    video {width: 1px;display:block}
    .main{
        overflow: hidden;
    }
</style>
<body>

<div class="main">

    <div class="loading">

    </div>
    <audio loop src="http://static.joyme.com/mobile/qyz/video/background.mp3" id="media" style="visibility: hidden"  preload=""></audio>
    <div class="videobox">
        <video id="myvideo"  src="http://static.joyme.com/mobile/qyz/video/video-1.mp4" controls=""  preload="auto">
            您的浏览器不支持 video 标签。
        </video>
    </div>
    <div class="nav-right">
        <img class="bonuses p2" src="http://static.joyme.com/mobile/qyz/images/me-bonuses.png">
        <img class="rule p2" src="http://static.joyme.com/mobile/qyz/images/rule.png">
        <img class="link p1" src="http://static.joyme.com/mobile/qyz/images/link.png">
        <img class="share-btn p3" src="http://static.joyme.com/mobile/qyz/images/share-btn.png">
        <img class="music" src="http://static.joyme.com/mobile/qyz/images/music_open.png">
    </div>
    <div class="page-1">
        <div class="page-1-text">
            <div wt="100" class="page-1-text1"></div>
            <div wt="100" class="page-1-text2"></div>
            <div wt="100" class="page-1-text3"></div>
            <div wt="100" class="page-1-text4"></div>
            <div wt="100" class="page-1-text5"></div>
        </div>
        <div class="page-1-bottom">
            <img width="7.7%" src="http://static.joyme.com/mobile/qyz/images/qyz.png">
            <img class="page-1-btn" width="29.8%" src="http://static.joyme.com/mobile/qyz/images/page-1-btn.png">
        </div>
    </div>
    <div class="page-2">
        <img data-h="1" class="gk game-icon-not  " src="http://static.joyme.com/mobile/qyz/images/page-2-icon1.png">
        <img src="http://static.joyme.com/mobile/qyz/images/arrow.png">
        <img data-h="2" class="gk game-icon-not" src="http://static.joyme.com/mobile/qyz/images/page-2-icon2.png">
        <img src="http://static.joyme.com/mobile/qyz/images/arrow.png">
        <img data-h="3" class="gk game-icon-not" src="http://static.joyme.com/mobile/qyz/images/page-2-icon3.png">
        <div class="window">
            <div class="po-rule">
                <img class="close-rule"  src="http://static.joyme.com/mobile/qyz/images/close-rule.png">
                <img class="rule-main"  src="http://static.joyme.com/mobile/qyz/images/po-rule.png">
            </div>
            <div class="cdk">
                <img class="close-tips"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
                <p>CDK：<span class="set-cdk">83JD87D749A</span></p>
                <span>（长按复制CDK）</span>
            </div>
            <div class="cdk-points">
                <img class="close-tips"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
                <p>少侠您尚未找全张小凡的三魂，还需继续努力。</p>
            </div>
            <div class="points">
                <img class="close-tips"  src="http://static.joyme.com/mobile/qyz/images/close-tips.png">
                <img class="jl"  src="http://static.joyme.com/mobile/qyz/images/jl.png">
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
<script src="http://static.joyme.com/mobile/qyz/js/video.js"></script>
<script src="/static/js/qyz/getInfo.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    $(document).ready(function(){
        gameLevel();
        if(!getQueryString('map')){
            setTimeout(function (){
                $('.loading').hide();
                setVideoStyle();
            },1000);
            var audio = document.getElementById("myvideo");
            audio.loop = false;
            audio.addEventListener('ended', function () {
                audio.style.display = 'none';
                $('.videobox,#myvideo').remove();
                qingyunzhi.init();
                mediaPlay();
            }, false);
        }else{
            mediaPlay();
            $('.videobox').remove();
            $('.loading').hide();
            qingyunzhi.page2();
        }

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