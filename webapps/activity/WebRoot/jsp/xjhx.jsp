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
    <title>星际火线</title>

    <script>
        document.addEventListener("DOMContentLoaded", function (e) {
            var ua = navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i)=="micromessenger") {
                //return true;
            } else {
                document.getElementById('body').innerHTML = '<p class="ps">请在微信端打开网页</p>';
            }

            var w=e.target.activeElement.clientWidth>=1024?1024:e.target.activeElement.clientWidth;
            if(w<375 && w>320){
                w=320+(w-320)/3;
            }
            
            var ch = 0;
            if($('body').height() < 450){
                ch=0.15;
            }
            document.getElementById('music').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-1').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-2').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-3').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-4').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-5').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-6').style.zoom = w / 375-0.05-ch;
            document.getElementById('swiper-slide-7').style.zoom = w / 375-0.05-ch;
        });
    </script>
    <link rel="stylesheet" href="http://static.joyme.com/mobile/xjhx/css/swiper-3.4.0.min.css">
    <link rel="stylesheet" href="http://static.joyme.com/mobile/xjhx/css/index.css?20161118">
    <link rel="stylesheet" href="http://static.joyme.com/mobile/xjhx/css/animate.css">
    <style>
        .fotter-6-btn{
            background: url("http://static.joyme.com/mobile/xjhx/images/footer-c.png") no-repeat;
            background-size: cover;
        }
    </style>
</head>
<body onload="init()" id="body" onpagehide="myFunction()">

<div class="swiper-container" >
    <!--音乐-->
    <div id="music">
        <div class="music trans-music"></div>
    </div>

    <div class="swiper-wrapper">
        <div class="swiper-slide swiper-no-swiping swiper-slide-1">
            <div id="swiper-slide-1">
                <div class="logo"></div>

                <div class="bar">
                    <cite>
                        <s></s>
                    </cite>
                    <span>加载中…… <em>0</em>%</span>
                </div>

                <div class="start button">

                </div>
                <div class="start button hxt">

                </div>
            </div>
        </div>
        <div class="swiper-slide swiper-slide-2">
            <div id="swiper-slide-2">
                <div class="title">
                    <span class="shine"></span>
                    测试人数 </div>
                <div class="arrow-bottom pulse-pics"></div>
                <div class="footer">
                    <div class="page-2-ani-3 ani" data-slide-in="at 1000 from bounceInLeft during 1000"></div>
                    <div class="page-2-ani-4 ani" data-slide-in="at 1000 from bounceInLeft during 1500"></div>
                </div>

                <div class="page-2-ani-2"></div>

                <div class="test-left ani" data-slide-in="at 0 from bounceInLeft  during 1000">
                    <div class="page-2-ani-1 ani" data-slide-in="at 1500 from fadeIn  during 1000"></div>
                    <p>本次测试一共有<em>53853</em></p>
                    <span>指挥官被招募</span>
                    <p class="martop">其中男女比例<em>9:1</em></p>
                    <p class=""><em>一对情侣四对基</em></p>
                    <p class="martop">期间，一共有</p>
                    <span><em>1544149</em></span>
                    <p>组队！</p>
                </div>
            </div>
        </div>
        <div class="swiper-slide swiper-slide-3">
            <div id="swiper-slide-3">
                <div class="title"><span class="shine"></span>最悲催的BOSS </div>
                <div class="arrow-bottom pulse-pics"></div>
                <div class="footer">
                    <span class="ani" data-slide-in="at 1000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1200 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1400 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1600 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1800 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2200 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2400 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2600 from bounceInUp  during 500"></span><br>
                    <span class="ani" data-slide-in="at 2800 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 3000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 3200 from bounceInUp  during 500"></span>
                </div>

                <div class="page-3-ani-2"></div>

                <div class="test-right ani" data-slide-in="at 0 from bounceInRight  during 500">
                    <div class="page-3-ani-1 pulse-pic-3"></div>
                    <p>最悲剧副本</p>
                    <span><em>梅塔图隆</em></span>
                    <p class="martop">测试期间被指挥官强</p>
                    <span>OOXX共 <em>1231854</em> 次</span>
                    <p class="martop">平均每天 <em>43994</em> 次</p>
                </div>
            </div>
        </div>
        <div class="swiper-slide swiper-slide-4">
            <div id="swiper-slide-4">
                <div class="title"> <span class="shine"></span>采矿金币 </div>
                <div class="arrow-bottom pulse-pics"></div>
                <div class="footer">
                    <span class="ani" data-slide-in="at 1000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1200 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1400 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1600 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 1800 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2200 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2400 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 2600 from bounceInUp  during 500"></span><br>
                    <span class="ani" data-slide-in="at 2800 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 3000 from bounceInUp  during 500"></span>
                    <span class="ani" data-slide-in="at 3200 from bounceInUp  during 500"></span>
                </div>

                <div class="page-s4-ani-1 pulse-pic"></div>
                <div class="page-s4-ani-2"></div>

                <div class="test-left ani" data-slide-in="at 500 from bounceInLeft  during 0">
                    <p>一共挖了</p>
                    <span><em>313598594391</em> 个金币</span>
                    <p class="martop"> 折合人民币</p>
                    <span> <em>21456467469</em> 元</span>
                    <p class="martop">可以买下一个天宫二号</p>
                </div>
            </div>
        </div>
        <div class="swiper-slide swiper-slide-5">
            <div id="swiper-slide-5">
                <div class="title"><span class="shine"></span>丧尸围城 </div>
                <div class="arrow-bottom pulse-pics"></div>
                <div class="footer">
                    <div class="page-4-ani-1 ani" data-slide-in="at 500 from bounceInLeft  during 500"></div>
                    <div class="page-4-ani-2 ani" data-slide-in="at 500 from bounceInRight  during 1000"></div>
                </div>

                <div class="page-4-ani-3"></div>

                <div class="test-right ani" data-slide-in="at 500 from bounceInRight  during 0">
                    <p>指挥官在丧尸围城中</p>
                    <p>共消灭</p>
                    <span><em>62555648 </em> 个僵尸</span>
                    <p class="martop"> 韩国<em>釜山行</em>僵尸人数才</p>
                    <span> <em>5000w</em></span>
                    <p class="martop">比整个<em>釜山行</em>僵尸还多</p>
                </div>
            </div>
        </div>
        <div class="swiper-slide swiper-slide-6">

            <div id="swiper-slide-6">
                <div class="title"><span class="shine"></span>机甲 </div>
                <div class="arrow-bottom pulse-pics"></div>
                <div class="footer">
                    <div class="fotter-6-btn ani" data-slide-in="at 1000 from bounceInRight  during 500"></div>
                </div>

                <div class="page-6-ani-1 ani" data-slide-in="at 500 from bounceInRight  during 500"></div>
                <div class="page-6-ani-2 ani" data-slide-in="at 500 from bounceInRight  during 1000"></div>

                <div class="test-left ani" data-slide-in="at 500 from bounceInLeft  during 0">
                    <p>本次测试期间</p>
                    <p>一共召唤</p>
                    <span><em>26757544 </em> 个机甲。</span>
                    <p class="martop"> 所有机甲手牵手可绕地球</p>
                    <p><em>七周半</em></p>
		    <p class="martop">共为玩家荡平了 </p>
                    <p><em>9781465</em>个关卡</p>
                    <p>看这是朕为你</p>
                    <p>打下的江山</p>
		    <!--p class="martop">出击吧，爆裂旋风！ </p-->
                </div>
            </div>

        </div>

        <div class="swiper-slide swiper-slide-7">
            <div id="swiper-slide-7">
                <div class="title"><span class="shine"></span>枪械 </div>
                <!--<div class="arrow-bottom"></div>-->
                <div class="footer">
                    <div class="page-7-ani-1 ani" data-slide-in="at 0 from bounceInLeft  during 500" ></div>
                    <div class="page-7-ani-2 ani" data-slide-in="at 500 from bounceInRight  during 1000" ></div>
                    <div class="btn">
                        <div class="share ani" data-slide-in="at 500 from fadeInUp  during 1500"></div>
                        <div class="share hxt"></div>
                        <div class="concern ani" data-slide-in="at 500 from fadeInUp  during 1500"></div>
                        <a href="http://mp.weixin.qq.com/s/4uHA10SlGLHVcBmkXcTdMA">
                            <div class="concern hxt"></div>
                        </a>
                    </div>

                </div>

                <div class="page-7-ani-3 ani" data-slide-in="at 500 from bounceInLeft  during 1000">

                </div>
                <div class="page-7-ani-4 ani" data-slide-in="at 1000 from bounceInLeft  during 1000">

                </div>

                <div class="test-right ani" data-slide-in="at 0 from bounceInRight  during 500">
                    <p>本次测试期间，最受欢迎</p>
                    <p>的枪械是 <em>黄金•地狱烈焰</em></p>
                    <p class="martop">出场次数<em>6084175 </em> 次</p>
                    <p class="martop"> 平均每五场战斗就有</p>
                    <span> <em>地狱烈焰</em>出战</span>
                    <p>烧死无数恩爱狗！</p>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="pop"> <span></span> </div>
<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500347377" ></script>
</body>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script src="http://static.joyme.com/mobile/xjhx/js/swiper.min.js"></script>
<script src="http://static.joyme.com/mobile/xjhx/js/animate.js"></script>
<script src="http://static.joyme.com/mobile/xjhx/js/landscape.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<!--<script src="js/wxshare.js"></script>-->
<script type="text/javascript" src="http://static.joyme.com/mobile/cp/ylzt20160805/js/preloadjs.min.js"></script>

<script>
    var url = 'http://static.joyme.com/mobile/xjhx/';
    function init() {
        var loadingbar = $('.bar');
        var manifest;
        var preload;
        //定义相关JSON格式文件列表
        function setupManifest() {
            manifest = [
                {src: url + "js/jquery.min.js", id: "jquery"},
                {src: url + "js/swiper.min.js", id: "swiper"},
                {src: url + "js/animate.js", id: "animate"},
                {src: url + 'images/bg-1.jpg', id: "images"},
                {src: url + 'images/arrow.png', id: "images"},
                {src: url + 'images/bar-bg.jpg', id: "images"},
                {src: url + 'images/bar-bg2.jpg', id: "images"},
                {src: url + 'images/bg-2.jpg', id: "images"},
                {src: url + 'images/bottom.png', id: "images"},
                {src: url + 'images/concern.png', id: "images"},
                {src: url + 'images/footer.jpg', id: "images"},
                {src: url + 'images/hou.png', id: "images"},
                {src: url + 'images/logo.png', id: "images"},
                {src: url + 'images/music-close.png', id: "images"},
                {src: url + 'images/music-open.png', id: "images"},
                {src: url + 'images/page-1-1.png', id: "images"},
                {src: url + 'images/page-1-2.png', id: "images"},
                {src: url + 'images/page-1-3.png', id: "images"},
                {src: url + 'images/page-1-4.png', id: "images"},
                {src: url + 'images/page-2-1.png', id: "images"},
                {src: url + 'images/page-2-2.png', id: "images"},
                {src: url + 'images/page-2-3.png', id: "images"},
                {src: url + 'images/page-2-4.png', id: "images"},
                {src: url + 'images/page-4-1.png', id: "images"},
                {src: url + 'images/page-4-2.png', id: "images"},
                {src: url + 'images/page-4-3.png', id: "images"},
                {src: url + 'images/page-5-1.png', id: "images"},
                {src: url + 'images/page-5-2.png', id: "images"},
                {src: url + 'images/page-6-1.png', id: "images"},
                {src: url + 'images/page-6-2.png', id: "images"},
                {src: url + 'images/page-7-2.png', id: "images"},
                {src: url + 'images/page-7-bg.png', id: "images"},
                {src: url + 'images/result.png', id: "images"},
                {src: url + 'images/result-2.png', id: "images"},
                {src: url + 'images/result-3.png', id: "images"},
                {src: url + 'images/share.png', id: "images"},
                {src: url + 'images/title.png', id: "images"},
                {src: url + 'images/top.png', id: "images"},
                {src: url + 'images/start.mp3', id: "music"}
            ];
        };
        //开始预加载
        function startPreload() {
            preload = new createjs.LoadQueue(true);
            //注意加载音频文件需要调用如下代码行
            preload.installPlugin(createjs.Sound);
            preload.on("fileload", handleFileLoad);
            preload.on("progress", handleFileProgress);
            preload.on("complete", loadComplete);
            preload.on("error", loadError);
            preload.loadManifest(manifest);
        };
        function handleFileLoad(event) {
            console.log("文件类型: " + event.item.type);
        };
        function loadError(evt) {
            console.log("加载出错！", evt.text);
        };
        function handleFileProgress(event) {
            var progress = preload.progress * 100 | 0;
            loadingbar.find('s').css('left', -(100-progress) + '%');
            loadingbar.find('em').text(progress);
            if(progress == 100){
                $('.bar').hide();
                $('.start').show();
            }
        };
        function loadComplete(event) {
            console.log("已加载完毕全部资源");
            pageInit();
        }
        setupManifest();
        startPreload();
    }

    function pageInit(){
        $('.bar').hide();
        $('.start').show();
    }
    var swiper = new Swiper('.swiper-container', {
        paginationClickable: true,
        direction: 'vertical',
        noSwiping : true,
        onInit: function(swiper) {
            swiperAnimateCache(swiper); //隐藏动画元素
            swiperAnimate(swiper); //初始化完成开始动画
        },
        onSlideChangeEnd: function(swiper) {
            swiperAnimate(swiper);
            if(swiper.activeIndex == 6){
                setTimeout( function(){
                    $('.btn .hxt').show()
                },1800)
            }else{
                $('.btn .hxt').hide()
            }
        }
    });

    $('.start').click(function () {
        $('.swiper-slide-1').removeClass('swiper-no-swiping');
        swiper.slideNext();
    })

    var valu=0;
    var newAu=new Audio();
    $(window).load(function() {
        function newAudi(){
            newAu.src = url+'images/start.mp3';
            newAu.volume = 0.3;
            newAu.loop = true;
            newAu.play();
        }
        newAudi();
        $('.music').click(function(event){
            event.stopPropagation();
            if(newAu.paused){
                newAu.play();
                $(this).addClass('trans-music');
                valu=0;
            }else{
                newAu.pause();
                $(this).removeClass('trans-music');
                valu=1;
            }

        });
        $(document).one("touchstart",function(){ newAu.play(); });
    });

    $('.share').click(function (){
        $('.pop').show();
    });
    $('.pop').click(function (){
        $(this).hide();
    });

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
                title: '星际火线终极封测趣味数据盘点',
                link: 'http://api.activity.joyme.com/activity/xjhx/page.do',
                imgUrl: 'http://static.joyme.com/mobile/xjhx/images/wxshare.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '星际火线终极封测趣味数据盘点',
                desc: '短短的测试已经过去了，更长的旅行即将到来。',
                link: 'http://api.activity.joyme.com/activity/xjhx/page.do',
                imgUrl: 'http://static.joyme.com/mobile/xjhx/images/wxshare.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '星际火线终极封测趣味数据盘点',
                desc: '短短的测试已经过去了，更长的旅行即将到来。',
                link: 'http://api.activity.joyme.com/activity/xjhx/page.do',
                //link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: 'http://static.joyme.com/mobile/xjhx/images/wxshare.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '星际火线终极封测趣味数据盘点',
                desc: '',//'短短的测试已经过去了，更长的旅行即将到来。',
                link: 'http://api.activity.joyme.com/activity/xjhx/page.do',
                //link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: 'http://static.joyme.com/mobile/xjhx/images/wxshare.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>