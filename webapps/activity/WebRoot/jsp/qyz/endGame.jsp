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

    .game-1{
        display: block;
        text-align: center;
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
<body>

    <div class="main">

        <audio loop src="http://static.joyme.com/mobile/qyz/video/background.mp3" id="media" style="visibility: hidden"  preload=""></audio>
        <div class="nav-right">
            <img class="music" src="http://static.joyme.com/mobile/qyz/images/music_open.png">
        </div>

        <div class="videobox">
            <video id="myvideo"   src="http://static.joyme.com/mobile/qyz/video/video-2.mp4" controls=""  preload="auto">
                您的浏览器不支持 video 标签。
            </video>
        </div>
        <div class="game-1">
                <img style="margin-top: 15%;" width="56%" src="http://static.joyme.com/mobile/qyz/images/end-ts.png"><br>
                <a href="/activity/qyz/page.do?map=1&user=1&code="+code><img width="22.5%" src="http://static.joyme.com/mobile/qyz/images/index.png"></a>
                <img width="22.5%" class="game-tbn-share" src="http://static.joyme.com/mobile/qyz/images/game-share.png">

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
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);

    $(document).ready(function(){
        $('.loading').hide();
        var audio = document.getElementById("myvideo");
        audio.loop = false;
        audio.addEventListener('ended', function () {
            $('.videobox,#myvideo').remove();
            $('.videobox').hide();
            mediaPlay();
        }, false);

    });
    $('.game-tbn-share').click(function (){
        pointsWindow($('.game-share-window') , 0);
    });
    function pointsWindow( doc , dex ){
        doc.show();
        $(doc.selector+'-bg').show();
        var img = doc.find('img').eq(dex);
        var docw = img.attr('width')*clientWidth();
        img.css({width:docw});
        doc.find('img').hide();
        doc.find('img').eq(dex).show().css({'opacity':0});
        doc.find('.decrypt-1-close').show().css({'opacity':0});

        setTimeout(function (){
            var docH = img.height();
            doc.css({
                width:docw,
                height:docH,
                marginTop:-(docH/2),
                marginLeft:-(docw/2)
            });
            doc.find('img').eq(dex).show().css({'opacity':1});
            doc.find('.decrypt-1-close').show().css({'opacity':1});
            img.css({
                width:'100%'
            })
        },100)

    }
    function clientWidth(){
        if(document.body.clientHeight > document.body.clientWidth){
            return  document.body.clientHeight/100;
        }else{
            return document.body.clientWidth/100;
        }
    }
    $('.decrypt-1-close').click(function (){
        $(this).parent().hide();
        $(this).parent().prev().hide();
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