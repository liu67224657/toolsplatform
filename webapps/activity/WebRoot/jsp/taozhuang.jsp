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
    <link rel="stylesheet"
          href="http://static.joyme.com/mobile/cp/ylzt20160805/css/style.css">
    <link rel="stylesheet" type="text/css"
          href="http://static.joyme.com/mobile/cp/ylzt20160805/css/taozhuang.css">
    <title>天命圣旨，战力比拼</title>
    <script>
        var openId = '${openid}';
        var record =  ${activityCountry.country};
        var nickname = "${activityCountry.nickname}";
        var times = '${usedTimes}';

        function getTime() {
            var myDate = new Date();
            return myDate.getFullYear() + "_" + myDate.getMonth() + "_" + myDate.getDate() + openId;
        }

        var _times = window.localStorage.getItem('_times' + getTime());
        if (_times != null && parseInt(_times) < parseInt(times)) {
            window.localStorage.setItem('_times' + getTime(), _times);
        }

        document.addEventListener("DOMContentLoaded", function (e) {
            var w = e.target.activeElement.clientWidth >= 1024 ? 1024 : e.target.activeElement.clientWidth;
            document.getElementById('wrap').style.zoom = w / 375;
        });
        function showSunAndMoon(starCount) {
            if (starCount == "0") {
                return "<span class=\"moon-1\"></span>";
            } else if (starCount == "3") {
                return "<span class=\"moon-2\"></span>";
            } else if (starCount == "6") {
                return "<span class=\"sun\"></span>";
            } else if (starCount == "9") {
                return "<span class=\"sun\"></span>   <span class=\"moon-2\"></span>";
            } else if (starCount == "12") {
                return "<span class=\"sun\"></span>  <span class=\"sun\"></span>";
            } else if (starCount == "15") {
                return "<span class=\"sun\"></span>  <span class=\"sun\"></span>  <span class=\"moon-2\"></span>";
            } else if (starCount == "18") {
                return "<span class=\"sun\"></span> <span class=\"sun\"></span> <span class=\"sun\"></span>";
            } else {
                return "<span class=\"moon-1\"></span>";
            }
        }
        function displayScore() {
            $.post("/activity/accountInfo/getCountryScore.do", {}, function (data) {
                if (data != "") {
                    var countryScoreObj = eval("(" + data + ")");
                    var countryScoreArray = countryScoreObj.result;
                    $("#zlUl").empty();
                    var innerHtml = ' ';
                    var count = countryScoreArray.length;
                    for (var i = 0; i < count; i++) {
                        var countryScore = countryScoreArray[i].split("-");
                        var countryName = countryScore[0];
                        var cityName = getCityName(countryName);
                        var tempHtml = "<li>  <i class=\"num-" + (i + 1) + "\"></i> <font>" + cityName + "州</font>" +
                                "<div class=\"zb-level\">" +
                                showSunAndMoon(countryScore[2]) +
                                "</div>" +
                                "<cite>战力:" + countryScore[1] + "</cite>" +
                                " </li>";
                        innerHtml = innerHtml + tempHtml;
                    }
                    $("#zlUl").html(innerHtml);
                }
            })
            $.post("/activity/accountInfo/getUserScore.do", {}, function (data) {
                if (data != "") {
                    var userScoreObj = eval("(" + data + ")");
                    var userScoreArray = userScoreObj.result;
                    $("#gxUl").empty();
                    var innerHtml = ' ';
                    for (var i = 0; i < userScoreArray.length; i++) {
                        var userScore = userScoreArray[i].split("-");
                        var tempHtml = "<li>  <i class=\"num-" + (i + 1) + "\"></i> " +
                                "<cite><img src=\"" + userScore[2] + "\" alt=\"\"></cite>" +
                                "<font>" + userScore[0] + "</font>" +
                                "<span>贡献战力:" + userScore[1] + "</span>" +
                                " </li>";
                        innerHtml = innerHtml + tempHtml;
                    }
                    if (userScoreArray.length < 5) {
                        var addCount = 5 - userScoreArray.length;
                        for (var i = 0; i < addCount; i++) {
                            var tempHtml = "<li></li>";
                            innerHtml = innerHtml + tempHtml;
                        }
                    }
                    $("#gxUl").html(innerHtml);
                }
            })
        }
        function getCityName(cityCode) {
            if (cityCode == 1) {
                return "扬";
            } else if (cityCode == 2) {
                return "荆";
            } else if (cityCode == 3) {
                return "豫";
            } else if (cityCode == 4) {
                return "益";
            } else if (cityCode == 5) {
                return "青";
            }
        }
        function showXtDialog() {
            $.post("/activity/accountInfo/getUserTaozhuang.do", {openId: openId}, function (data) {
                if (data != "") {
                    var taoZhuangObj = eval("(" + data + ")");
                    var userTaoZhuang = taoZhuangObj.result;
                    $("#currentTaozhuang").text("目前星套数：" + userTaoZhuang.currentTaozhuang);
                    $("#score").text("目前战力：" + userTaoZhuang.score);
                    $("#nextTaozhuang").text("下一星套：" + userTaoZhuang.nextTaozhuang);
                    $("#requireScore").text("激活需要战力：" + userTaoZhuang.requireScore);
                }
            })
            $('.xt_dialog').show();
        }
    </script>
</head>
<body onload="init()">
<div id="wrap">
    <div class="audio_btn">
        <audio preload="true" id="audioBtn" autoplay="true" loop="true">
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/bgm-2.mp3"
                    type="audio/mp3"/>
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/bgm-2.ogg"
                    type="audio/ogg"/>
        </audio>
    </div>
    <div class="loading">
        <div class="loading-con">
            <div class="curload"></div>
        </div>
    </div>
    <div class="zb_content">
        <div class="page-zb active">
            <div class="zb-user-box">
                <cite class="zb-user-img">
                    <img src="${activityCountry.headimgurl}">
                </cite>

                <div class="zb-tit">
                    <span class="zb-tit-t y-icon"><font>${activityCountry.nickname}</font><i
                            class="cityIcon_1"></i></span>
                    <!--1扬 2荆 3豫 4益 5青-->
                    <span class="zb-tit-b zl_add">个人贡献战力：<i><font>${activityCountry.score}</font><em></em></i></span>
                </div>
            </div>
            <c:if test="${userStar=='0' || userStar=='3' }">
            <div class="zb-box level-0">
                </c:if>
                <c:if test="${userStar=='6'}">
                <div class="zb-box level-6">
                    </c:if>
                    <c:if test="${userStar=='9'}">
                    <div class="zb-box level-9">
                        </c:if>
                        <c:if test="${userStar=='12'}">
                        <div class="zb-box level-12">
                            </c:if>
                            <c:if test="${userStar=='15'}">
                            <div class="zb-box level-15">
                                </c:if>
                                <c:if test="${userStar=='18'}">
                                <div class="zb-box level-18">
                                    </c:if>

                                    <div class="zb-bg"></div>
                                    <!-- 用户信息 开始 -->
                                    <div class="fn-clear zb-level-box">
                                        <span class="fn-left zb-que"></span>

                                        <div class="fn-right fn-clear zb-level on">
                                            <c:if test="${userStar=='0'}">
                                                <span class="moon-1"></span>
                                            </c:if>
                                            <c:if test="${userStar=='3'}">
                                                <span class="moon-2"></span>
                                            </c:if>
                                            <c:if test="${userStar=='6'}">
                                                <span class="sun"></span>
                                            </c:if>
                                            <c:if test="${userStar=='9'}">
                                                <span class="sun"></span>
                                                <span class="moon-2"></span>
                                            </c:if>
                                            <c:if test="${userStar=='12'}">
                                                <span class="sun"></span>
                                                <span class="sun"></span>
                                            </c:if>
                                            <c:if test="${userStar=='15'}">
                                                <span class="sun"></span>
                                                <span class="sun"></span>
                                                <span class="moon-2"></span>
                                            </c:if>
                                            <c:if test="${userStar=='18'}">
                                                <span class="sun"></span>
                                                <span class="sun"></span>
                                                <span class="sun"></span>
                                            </c:if>
                                            <cite class="tips_3"><i></i>点击查看星套详情</cite>
                                        </div>
                                    </div>
                                    <!-- 用户信息 结束 -->
                                    <!-- 装备信息 开始 -->
                                    <div class="zb-rw">
                                        <img src="http://static.joyme.com/mobile/cp/ylzt20160805/images/level-0.png">
                                    </div>
                                    <!-- 装备信息 结束 -->
                                    <!-- 战力 开始 -->
                                    <div class="zb-zl zl_add">
                                        <span>国家战力：<i><font>${countryScore}</font><em></em></i></span>
                                    </div>
                                    <!-- 战力 结束 -->
                                </div>
                                <div class="up-icon-box slideUp"><cite class="up-icon"></cite></div>
                                <!-- 3s提示 开始 -->
                                <div class="sec-hint tips_3">
                                    <font>当前星套：${userStar}套</font>
                                    <span>手动截图</br>保存星套效果图</br>上拉继续</span>
                                </div>
                                <!-- 3s提示 结束 -->
                                <!-- 提升、排行 开始 -->
                                <div class="b-btn-box slideUp">
                                    <span class="ts-btn">提升战力：${usedTimes}次</span>
                                    <span class="ranking-btn">排行榜</span>
                                    <a href="http://game.weixin.qq.com/cgi-bin/h5/static/gamecenter/detail.html?appid=wx780e9d6f0c1bbc7a&abt=12&gift=1&ssid=1"
                                       class="order-btn">游戏预约</a>
                                </div>
                                <!-- 提升、排行 结束 -->
                                <!-- 战力互动条  开始 -->

                                <div class="hand-box">
                                    <div class="hand-text">
                                        点击【提升战力】按钮，战力槽中间的宝剑左右摆动，再次点击后，玩家即为所属国家贡献宝剑所在位置的对应战力；
                                    </div>
                                    <div class="hand-icon">
                                        <cite class="hand-num hand-l">0</cite>
                                        <cite class="hand-num hand-r">500</cite>
                                        <span class="hand-move"></span>
                                    </div>
                                </div>

                                <!-- 战力互动条  结束 -->
                                <!-- 互动次数 开始 -->
                                <div class="dialog zl_dialog">
                                    <div class="dialog_bg"></div>
                                    <div class="dialog_con zl_dialog_con">
                                        <cite class="close btn">关闭</cite>
                                    </div>
                                </div>
                                <!-- 互动次数 结束 -->
                                <!--游戏规则 开始-->
                                <div class="dialog gz_dialog">
                                    <div class="dialog_bg"></div>
                                    <div class="dialog_con gz_dialog_con">
                                        <h4><span>玩法规则</span><cite class="close btn"></cite></h4>

                                        <div class="gz_dia_text">
                                            <div></div>
                                        </div>
                                    </div>
                                </div>
                                <!--游戏规则 结束-->
                                <!--国家贡献排行-->
                                <div class="zl-gx-box border-nor">
                                    <i class="close-btn">关闭</i>

                                    <div class="tab-box">
				<span class="tab-tit">
					<cite class="on">国家战力 </cite>
					<cite class="">个人贡献战力</cite>
				</span>

                                        <div class="tab-con">
                                            <div class="zl on" id="zlDiv">
                                                <ul id="zlUl">
                                                    <li>
                                                        <i class="num-1"></i>
                                                        <font>扬州</font>

                                                        <div class="zb-level">
                                                            <span class="sun"></span>
                                                            <span class="sun"></span>
                                                            <span class="sun"></span>
                                                        </div>
                                                        <cite>战力：148600</cite>
                                                    </li>
                                                    <li>
                                                        <i class="num-2"></i>
                                                        <font>荆州</font>

                                                        <div class="zb-level">
                                                            <span class="sun"></span>
                                                            <span class="sun"></span>
                                                            <span class="moon-2"></span>
                                                        </div>
                                                        <cite>战力：14800</cite>
                                                    </li>
                                                    <li>
                                                        <i class="num-3"></i>
                                                        <font>益州</font>

                                                        <div class="zb-level">
                                                            <span class="sun"></span>
                                                            <span class="sun"></span>
                                                        </div>
                                                        <cite>战力：12805</cite>
                                                    </li>
                                                    <li>
                                                        <i class="num-4"></i>
                                                        <font>情州</font>

                                                        <div class="zb-level">
                                                            <span class="sun"></span>
                                                            <span class="moon-2"></span>
                                                        </div>
                                                        <cite>战力：9600</cite>
                                                    </li>
                                                    <li>
                                                        <i class="num-5"></i>
                                                        <font>情州</font>

                                                        <div class="zb-level">
                                                            <span class="sun"></span>
                                                        </div>
                                                        <cite>战力：8800</cite>
                                                    </li>
                                                </ul>
                                                <p>国家战力不够？邀好友一起为国出征…</p>

                                                <div class="tab-btn help-btn">好友帮忙</div>
                                            </div>
                                            <div class="gx" id="gxDiv">
                                                <ul id="gxUl">
                                                    <li>
                                                        <i class="num-1"></i>
                                                        <cite><img src="" alt=""></cite>
                                                        <font>今晚打老虎</font>
                                                        <span>贡献战力：14800</span>
                                                    </li>

                                                    <li>
                                                        <i class="num-2"></i>
                                                        <cite><img src="" alt=""></cite>
                                                        <font>完颜不破</font>
                                                        <span>贡献战力：14800</span>
                                                    </li>
                                                    <li>
                                                        <i class="num-3"></i>
                                                        <cite><img src="" alt=""></cite>
                                                        <font>完颜不破</font>
                                                        <span>贡献战力：14800</span>
                                                    </li>
                                                    <li>
                                                        <i class="num-4"></i>
                                                        <cite><img src="" alt=""></cite>
                                                        <font>拿剑刺青春</font>
                                                        <span>贡献战力：9600</span>
                                                    </li>
                                                    <li>
                                                        <i class="num-5"></i>
                                                        <cite><img src="" alt=""></cite>
                                                        <font>拿剑刺青春</font>
                                                        <span>贡献战力：8800</span>
                                                    </li>
                                                </ul>
                                                <p>将军战功无敌，邀好友一起沙场切磋…</p>

                                                <div class="tab-btn xy-btn">炫耀一下</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--星套弹窗-->
                                <div class="dialog xt_dialog">
                                    <div class="dialog_bg"></div>
                                    <div class="dialog_con xt_dialog_con">
                                        <cite class="close btn">关闭</cite>

                                        <p class="xt_dialog_text">
                                            <span id="currentTaozhuang">目前星套数：0星套</span>
                                            <span id="score">目前战力：2000</span>
                                            <span id="nextTaozhuang">下一星套：3星套</span>
                                            <span id="requireScore">激活需要战力：3000</span>
                                        </p>
                                    </div>
                                </div>
                                <!--分享弹窗-->
                                <div class="dialog share_dialog">
                                    <div class="dialog_bg"></div>
                                    <div class="dialog_con share_dialog_con">
                                        <cite class="close btn">关闭</cite>
                                    </div>
                                </div>
                                <div class="mask-box"></div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5"
                            sid="500163490"></script>
</body>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/preloadjs.min.js"></script>
<script type="text/javascript" src="/static/js/comm.js"></script>
<script type="text/javascript">
    function init() {
        var _times = window.localStorage.getItem('_times' + getTime());
        if (_times != null) {
            $(".ts-btn").html("提升战力：" + _times + "次");
        }
        var loadingbar = $('.curload')
        var url = 'http://static.joyme.com/mobile/cp/ylzt20160805';
        var manifest;
        var preload;
        //定义相关JSON格式文件列表
        function setupManifest() {
            manifest = [{
                src: "http://api.activity.joyme.com/static/js/preloadjs.min.js",
                id: "preloadjs"
            }, {
                src: "http://static.joyme.com/js/jquery-1.9.1.min.js",
                id: "jqueryjs"
            }, {
                src: url + "/images/bg.jpg",
                id: "images"
            }, {
                src: url + "/audio/bgm-2.mp3",
                id: "audio"
            }
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
            loadingbar.css('width', progress + '%');
        };
        function loadComplete(event) {
            console.log("已加载完毕全部资源");
            tz_page.record = record;//1扬 2荆 3豫 4益 5青
            tz_page.names = nickname;//用户昵称
            tz_page.init();
        }

        setupManifest();
        startPreload();
    }
    ;

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
                title: '我在御龙为国出征，还不快一起共战沙场',
                link: 'http://t.cn/RtSAiyD',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '天命圣旨，战力比拼',
                desc: '我在御龙为国出征，还不快一起共战沙场',
                link: 'http://t.cn/RtSAiyD',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '天命圣旨，战力比拼',
                desc: '我在御龙为国出征，还不快一起共战沙场',
                //  link: 'http://testbeta.joyme.com/activity/yulong/sharepage.do',
                link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '天命圣旨，战力比拼',
                desc: '我在御龙为国出征，还不快一起共战沙场',
                // link: 'http://testbeta.joyme.com/activity/yulong/sharepage.do',
                link: 'http://api.activity.joyme.com/activity/yulong/sharepage.do',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });

</script>
</html>