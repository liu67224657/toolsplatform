<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>星际火线</title>
    <link href="http://static.joyme.com/mobile/xinjihuoxian/css/index.css" rel="stylesheet" type="text/css" />
    <link href="http://static.joyme.com/mobile/xinjihuoxian/css/base.css" rel="stylesheet" type="text/css" />
    <link href="http://static.joyme.com/mobile/xinjihuoxian/css/animate.css" rel="stylesheet" type="text/css" />
    <script>
        var openId = '${openid}';
        var nickname="";
        var headimgurl="";
        var userScore =0;
    </script>
</head>
<body>
<c:if test="${activityCountry!=null}">
    <script>
        nickname =  "${activityCountry.nickname}";
        headimgurl = "${activityCountry.headimgurl}";
    </script>
</c:if>
    <div class="warp">
    <!--logo-->
    <img class="logo" src="http://static.joyme.com/mobile/xinjihuoxian/img/logo.png">
    <!--通用背景-->
    <div class="joyme-game-bg">
        <img class="joyme-game-bg-fc1 fadeToggle" src="http://static.joyme.com/mobile/xinjihuoxian/img/bg-fc1.png">
        <img class="joyme-game-bg-fc2 fadeToggle" src="http://static.joyme.com/mobile/xinjihuoxian/img/bg-fc2.png">
    </div>
    <img class="joyme-game-bg-box" src="http://static.joyme.com/mobile/xinjihuoxian/img/bg.jpg">
    <!--信封页-->
    <div class="joyme-game-mailer">
        <div class="joyme-game-mailer-box">
            <img class="joyme-game-bg-round zoom-plu" src="http://static.joyme.com/mobile/xinjihuoxian/img/round.png">
            <img class="joyme-game-bg-mailer" src="http://static.joyme.com/mobile/xinjihuoxian/img/mailer.png">
        </div>
        <img class="joyme-game-bg-notice" src="http://static.joyme.com/mobile/xinjihuoxian/img/notice.png">
    </div>
    <!--紧急通知-->
    <div class="joyme-game-notice-box">
        <img class="joyme-game-notice-img" src="http://static.joyme.com/mobile/xinjihuoxian/img/notice-box.png">
        <img class="joyme-game-notice-btn" src="http://static.joyme.com/mobile/xinjihuoxian/img/btn.png">
        <div class="joyme-game-notice-test">
            <p><img class="joyme-game-notice-arr" src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">尊敬的<c:if test="${activityCountry!=null}">${activityCountry.nickname}</c:if></p>
            <span>四年一度的全宇宙体育盛会——奥运会即将举行！近日，我们收到可靠情报：居然有怪物族不法分子潜伏在拉拉队妹纸中间，企图破坏奥运会！广大星际指挥官们，是时候该你们出手了！正大光明地盯着拉拉队每一个妹纸看吧，直到找出隐匿其中的怪物！</span>
            <p class="t-right">——星际火线奥组委</p>
        </div>
    </div>
    <!--开始游戏-->
    <div class="joyme-game-answer">
        <div class="joyme-game-answer-head">
            <img src="${activityCountry.headimgurl}">
        </div>
        <div class="joyme-game-answer-mark">

        </div>
        <div class="joyme-game-answer-time">

        </div>
        <div class="joyme-game-answer-name">

        </div>
        <div class="joyme-game-answer-music">

        </div>

        <div class="joyme-game-answer-bg">
            <img id="answerbg" src="http://static.joyme.com/mobile/xinjihuoxian/img/answer-bg.jpg">
            <div class="joyme-game-answer-img">
                <div></div><img class="people" src="http://static.joyme.com/mobile/xinjihuoxian/img/people.jpg">
            </div>
        </div>
        <img class="joyme-game-answer-tips" src="http://static.joyme.com/mobile/xinjihuoxian/img/tips.png">
    </div>
    <!--结束游戏-->
    <div class="joyme-game-end">
        <div class="joyme-game-end-box">
            <img class="joyme-game-end-yq joyme-game-end-tips" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-y-1.png">
            <div class="joyme-game-end-user">
                <img id="" src="${activityCountry.headimgurl}">
                <p>您的成绩为：<span id="mark">50分</span><span>分</span></p>
                <p>当前排名：<span >NO.</span><span id="ranking">50</span></p>
                <img  class="joyme-game-show-ranking" src="http://static.joyme.com/mobile/xinjihuoxian/img/ranking.jpg">
            </div>
            <div class="joyme-game-end-test">
                <p><img class="joyme-game-notice-arr" src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">尊敬的<c:if test="${activityCountry!=null}">${activityCountry.nickname}</c:if></p>
                <span>四年一度的全宇宙体育盛会——奥运会即将举行！近日，我们收到可靠情报：居然有怪物族不法分子潜伏在拉拉队妹纸中间，企图破坏奥运会！广大星际指挥官们，是时候该你们出手了！正大光明地盯着拉拉队每一个妹纸看吧，直到找出隐匿其中的怪物！</span>
                <p class="t-right">——星际火线奥组委</p>
            </div>
            <img class="joyme-game-end-bg" src="http://static.joyme.com/mobile/xinjihuoxian/img/box-bg.png">
        </div>
        <div class="joyme-game-end-btn">
            <img  class="joyme-game-show-share" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-icon6.png">
            <img  class="joyme-game-show-again" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-icon2.png">
            <img  class="joyme-game-show-code" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-icon3.png">
        </div>
    </div>

    <!--排行榜-->
    <div class="joyme-game-ranking">
        <div class="joyme-game-ranking-box">

            <img class="joyme-game-ranking-close" src="http://static.joyme.com/mobile/xinjihuoxian/img/close.png" >
            <div class="joyme-game-ranking-info">
                <ul style="width: 90%;margin-left: 5%">
                    <li class="joyme-game-ranking-scrool">排行</li>
                    <li>头像</li>
                    <li>昵称</li>
                    <li>成绩</li>
                </ul>
                <div class="joyme-game-ranking-viewport">
                <div class="scrollbar">
                    <div class="track">
                        <div class="thumb"></div>
                    </div>
                </div>
                <div class="viewport">
                <div class="joyme-game-ranking-user-info overview">
                <ul>
                    <li>01</li>
                    <li><img src="http://static.joyme.com/mobile/xinjihuoxian/img/people.jpg"></li>
                    <li>小强者</li>
                    <li>95</li>
                    <div class="joyme-game-ranking-hover">
                        <img src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">
                    </div>
                </ul>
                <ul>
                    <li>01</li>
                    <li><img src="http://static.joyme.com/mobile/xinjihuoxian/img/lechong.png"></li>
                    <li>小强者</li>
                    <li>95</li>
                    <div class="joyme-game-ranking-hover">
                        <img src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">
                    </div>
                </ul>
                <ul>
                    <li>01</li>
                    <li><img src="http://static.joyme.com/mobile/xinjihuoxian/img/xindao.jpg"></li>
                    <li>小强者</li>
                    <li>95</li>
                    <div class="joyme-game-ranking-hover">
                        <img src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">
                    </div>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                    <div class="joyme-game-ranking-hover">
                        <img src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">
                    </div>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>
                <ul>
                    <li>01</li>
                    <li>小强者</li>
                    <li>小强者</li>
                    <li>95</li>
                </ul>

                </div>
            </div>
            </div>
            </div>

            <img  class="joyme-game-ranking-bg" src="http://static.joyme.com/mobile/xinjihuoxian/img/box-bg.png">
        </div>
        <div class="joyme-game-end-btn">
            <img class="joyme-game-show-share" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-icon6.png">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img  class="joyme-game-show-again" src="http://static.joyme.com/mobile/xinjihuoxian/img/end-icon2.png">
        </div>

    </div>

    <img class="share" src="http://static.joyme.com/mobile/xinjihuoxian/img/share.png" >

    <div class="code">
        <div><img src="http://static.joyme.com/mobile/xinjihuoxian/img/code.png"></div>
    </div>
    </div>
</body>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="http://static.joyme.com/mobile/xinjihuoxian/js/jquery.min.js"></script>
<script src="http://static.joyme.com/mobile/xinjihuoxian/js/iscroll.js"></script>
<script src="http://static.joyme.com/mobile/xinjihuoxian/js/fastclick.js"></script>
<script src="../../static/js/starline/index.js"></script>
<script src="http://static.joyme.com/mobile/xinjihuoxian/js/landscape.js"></script>
<script>
    window.addEventListener('load', function () {
        FastClick.attach(document.body);
    }, false);
    xinjihuoxian.init();
    xinjihuoxian.sizeBox();
    //开始游戏
    $('.joyme-game-notice-btn').click(function (){
        xinjihuoxian.startGame();
    });
    //排行榜
    $('.joyme-game-show-ranking').click(function (){
        xinjihuoxian.getRanking();
    });
    //游戏重玩
    $('.joyme-game-show-again').click(function (){
        xinjihuoxian.againGame();
    });
    var weixinReady = false;
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
            weixinReady = true;
            //分享到朋友圈
            var title = '紧急通知';
            var desc = '据说奥运啦啦队里，混进了这个女人，你能找出她吗？';
            var link = 'http://t.cn/RtnhMId';
            var imgUrl = '${activityCountry.headimgurl}';
            wx.onMenuShareTimeline({
                title: desc,
                link: link,
                imgUrl: imgUrl,
                success: function (res) {
                    alert("分享成功");
                }
            });
           wx.onMenuShareAppMessage({
                title: title,
                desc: desc,
                link: link,
                imgUrl: imgUrl,
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: title,
                desc: desc,
                link: link,
                //link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: imgUrl,
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: title,
                desc: desc,
                link: link,
                // link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: imgUrl,
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
    function setMenuShareTimeline(data){
        if(weixinReady){
            wx.onMenuShareTimeline(data);
        }

    }
    function setMenuShareAppMessage(data){
        if(weixinReady){
            wx.onMenuShareAppMessage(data);
        }
    }
    function setMenuShareQQ(data){
        if(weixinReady){
            wx.onMenuShareQQ(data);
        }
    }
    function setMenuShareQZone(data){
        if(weixinReady){
            wx.onMenuShareQZone(data);
        }
    }
    function setDescByScore(score){
        weixinReady = true;
        var title = '紧急通知';
        var desc = '据说奥运啦啦队里，混进了这个女人，你能找出她吗？';
        var link = 'http://api.activity.joyme.com/activity/starline/page.do';
        var shortLink = 'http://t.cn/RtnhMId';
        var imgUrl = '${activityCountry.headimgurl}';
        //分享到朋友圈
        setMenuShareTimeline({
            title: desc, // 分享标题
            desc : desc,
            link: shortLink, // 分享链接
            imgUrl: imgUrl // 分享图标
        });
        setMenuShareAppMessage({
            title: title, // 分享标题
            desc : desc,
            link: shortLink, // 分享链接
            imgUrl: imgUrl // 分享图标
        });
        setMenuShareQQ({
            title: title, // 分享标题
            desc : desc,
            link: link, // 分享链接
            imgUrl: imgUrl // 分享图标
        });
        setMenuShareQZone({
            title: title, // 分享标题
            desc : desc,
            link: link, // 分享链接
            imgUrl: imgUrl // 分享图标
        });
    }

</script>
</html>