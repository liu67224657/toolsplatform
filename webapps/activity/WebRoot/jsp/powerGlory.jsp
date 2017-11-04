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
    <title>权利与荣耀</title>
    <script type="text/javascript" src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function (e) {
            var w = e.target.activeElement.clientWidth >= 1024 ? 1024 : e.target.activeElement.clientWidth;
            document.getElementById('wrap').style.zoom = w / 375;
        });
        var openId = '${openid}';
        //是否第一次进入
        var firstIn = ${firstIn};

        //是否进入过蓝点
        var blueKey = "${blueKey}";
        var _frombluepoint = "";
        if (blueKey != "") {
            _frombluepoint = "true";
        }

        //6个宝藏
        var yizhou = "${yizhou}", yuzhou = "${yuzhou}", jingzhou = "${jingzhou}", qingzhou = "${qingzhou}", yangzhou = "${yangzhou}", end = "${end}", counreycode = "${counreycode}";

        var inpage = "${inpage}";
        //浏览器回退，默认为true
        var endVal = 0;

        var _inpage = window.localStorage.getItem('_inpage_' + openId);
        if(_inpage==null){_inpage=0;}
        if (parseInt(inpage) >= parseInt(_inpage)) {
            //alert("111inpage===>"+inpage+",_inpage="+_inpage)
            window.localStorage.removeItem('handShow');
            window.localStorage.setItem('_inpage_' + openId,inpage);
            window.localStorage.setItem('_browserBack_' + openId, 0);
        }else{
            //alert("22222222222inpage===>"+inpage+",_inpage="+_inpage)
            var _browserBack = window.localStorage.getItem('_browserBack_' + openId);
            if (_browserBack != null && typeof (_browserBack) != "undefined") {
                endVal = _browserBack;
            }
            firstIn=true;
        }
    </script>
    <link rel="stylesheet" href="http://static.joyme.com/mobile/cp/qlyry20161108/css/style.css?v=20161108">
</head>
<body onload="init()">
<div class="hp_tips">请切换到竖屏观看~</div>
<div id="wrap">
    <!--music-->
    <div class="music_btn btn">
        <audio preload="true" id="audioBtn" loop="true">
            <source src="http://static.joyme.com/mobile/cp/qlyry20161108/audio/bgm.mp3" type="audio/mp3" />
        </audio>
    </div>
    <div class="gz_btn btn"></div>
    <div class="content">
        <!--loading-->
        <div class="loading active">
            <div class="loading_cont">
                <span><cite></cite></span>
            </div>
        </div>
        <!--homepage-->
        <div class="homepage">
            <div class="begin_box">
                <div class="btn begin_btn"></div>
            </div>
        </div>
        <!--infopage-->
        <div class="infopage">
            <div class="nextpage btn">跳过</div>
            <div class="infocont">
                <div class="infotop"></div>
                <div class="infoan">
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                </div>
            </div>
        </div>
        <!--contpage-->
        <div class="contpage">
            <div class="qp_cont">
                <div class="yh_tit"><img src="${activityCountry.headimgurl}" alt=""></div>
                <div class="qp_dotCont">
                    <div class="start_dot">
                        <div class="hand_box">
                            <div class="hand_icon"></div>
                        </div>
                        <div class="jt_box"></div>
                    </div>
                    <div class="yiz_city"></div>
                    <div class="yuz_city"></div>
                    <div class="jingz_city"></div>
                    <div class="qingz_city"></div>
                    <div class="yangz_city"></div>
                    <div class="gzlm_dot"></div>
                    <cite class="wdbx_icon"></cite>
                </div>
            </div>
            <!--骰子弹窗-->
            <div class="sz_bot"><cite></cite></div>
            <!--玩法规则-->
            <div class="touch_dialog gz_d">
                <div class="t_dialog_con wfgz_dialog_con">
                    <h6><span>玩法规则</span></h6>
                    <p>1. 玩家点击【头像】抛掷骰子来决定前进的步数，并按照占领的格子开启对应的宝藏宝箱。</p>
                    <p>2.占据【雷熊】、【霜狼】、【飞狮】、【魔牛】、【影蛇】、【战鹰】即可开启对应的国家宝藏;</p>
                    <h6><span>奖品设置</span></h6>
                    <p>【雷熊】：神秘礼包1</p>
                    <p>【霜狼】：神秘礼包2</p>
                    <p>【飞狮】：神秘礼包3</p>
                    <p>【魔牛】：神秘礼包4</p>
                    <p>【影蛇】：神秘礼包5</p>
                    <p>【战鹰】：神秘礼包6</p>
                    <p style="color:#9d2900;">注：每个国家宝藏只可领取一次</p>
                </div>
            </div>
            <!--提示-->
            <div class="touch_dialog tip_d">
                <div class="t_dialog_con">
                    <font class="tip_text"></font>
                </div>
            </div>
        </div>
        <!--国家奖励-->
        <div class="touch_dialog city_d">
            <div class="t_dialog_con">
                <div class="city_text"></div>
            </div>
        </div>
        <!--dialog-->
        <div class="dialog_mask"><span>点击这里关闭</span></div>
        <!--  二次进入
        <div class="dialog again_d">
            <div class="dialog_con">
                <font class="pt">寻宝旅途已重置，请在开始位置重新出发，之前寻得的宝藏并没有丢哦~还存在右下角【我的宝箱】内，点击即可查看所获CDK。</font>
                <div class="dialog_btn an">
                    <cite class="qd">确定</cite>
                </div>
            </div>
        </div> -->
        <!--幻境-->
        <div class="animate_dialog sgml_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">
                    <h3>勇士英明神武，消灭幻境的神使</h3>
                    <h4>获得锦囊一个</h4>
                    <h3>点击拆开</h3>
                    <div class="jinnang"></div>
                </div>
            </div>
        </div>
        <div class="animate_dialog djck_d">
            <div class="clickSZ">
                <div class="clickSZ_con"><cite></cite><cite></cite><cite></cite><cite></cite><cite></cite><cite></cite></div>
                <p>点击骰子选择固定点数前进</p>
            </div>
        </div>
        <!--骑士-->
        <div class="animate_dialog gza_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">偶遇骑士职业大师<br/>习得<span>【光之冲击】</span>技能<br/>往前冲锋，前进<span>2</span>步</div>
            </div>
        </div>
        <!--镖车-->
        <div class="animate_dialog lbss_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">运气超好，车辆性能爆棚<br/>可加速前进<span>2</span>步</div>
            </div>
        </div>
        <!--弓箭手-->
        <div class="animate_dialog cbzj_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">遇到公会弓手职业大师<br/>习得<span>【雷电祝福】</span>技能<br/>战力提升，前进<span>2</span>步</div>
            </div>
        </div>
        <!--国战-->
        <div class="animate_dialog hrd_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">前方3格偶遇雷熊和影蛇的国战<br/>是否传送至主战场观战？</div>
                <div class="dialog_btn">
                    <cite class="yes">是</cite><cite class="no">否</cite>
                </div>
            </div>
        </div>
        <!--法师-->
        <div class="animate_dialog tz_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">遇到法师职业<br/>对你施展技能<span>【炽足烈焰】</span><br/>被定身<span>2</span>秒</div>
                <div class="djs"></div>
            </div>
        </div>
        <!--战士-->
        <div class="animate_dialog zs_d">
            <div class="animate_dialog_con">
                <div class="animate_top"></div>
                <div class="animate_text">遇到法师职业<br/>对你施展技能<span>【炽足烈焰】</span><br/>被定身<span>2</span>秒</div>
                <div class="djs"></div>
            </div>
        </div>
        <!--分享弹窗-->
        <div class="gzhy_d"></div>
        <!--宝藏弹窗-->
        <div class="dialog bz_d">
            <div class="dialog_con">
                <h4>长按复制即可</h4>
                <h3>复制下面CDK</h3>
                <ul class="lb_city">
                    <li><b>雷熊</b><p>TVGaiKtpTAna</p></li>
                    <li><b>霜狼</b><p>勇士还没有获得宝藏，请去寻找</p></li>
                    <li><b>飞狮</b><p>TVGaiKtpTAna</p></li>
                    <li><b>魔牛</b><p>TVGaiKtpTAna</p></li>
                    <li><b>影蛇</b><p>勇士还没有获得宝藏，请去寻找</p></li>
                    <li><b>战鹰</b><p>勇士还没有获得宝藏，请去寻找</p></li>
                </ul>
                <div class="dialog_btn two">
                    <a href="http://ql.autopatch.zulong.com/seven/zulong/QLYRY_A_ZL.apk">下载游戏</a>
                    <cite class="gzhy_btn">告知好友</cite>
                </div>
            </div>
        </div>
        <!--结束情景-->
        <div class="dialog end_d">
            <div class="dialog_con">
                <font class="ending_text">
                    这段寻宝的旅程结束了，<br/>是不是还有宝物没有获得?<br/>那么就一起开始新的寻宝之旅吧~<br/><br/>点击【我的宝藏】<br/>查看已获得的所有CDK。
                </font>
                <div class="dialog_btn">
                    <cite class="zwyc">再玩一次</cite><cite class="wdbz">我的宝藏</cite>
                </div>
            </div>
        </div>
        <!--contpage==end-->
    </div>
    <!--contant==end-->
</div>
<script>
    //第二次进入
    if (!firstIn) {
       // $(".dialog_mask,.again_d").show();
    }
    $('#startGame').on('click', function () {
        window.location.href="http://game.weixin.qq.com/cgi-bin/h5/static/gamecenter/detail.html?appid=wx780e9d6f0c1bbc7a&ssid=11#wechat_redirect";
    })

    if (window.localStorage.getItem('yizhou' + openId) != null && window.localStorage.getItem('yizhou' + openId) != "") {
        $(".yiz_city").addClass("show");
    }
    if (window.localStorage.getItem('yuzhou' + openId) != null && window.localStorage.getItem('yuzhou' + openId) != "") {
        $(".yuz_city").addClass("show");
    }

    if (window.localStorage.getItem('jingzhou' + openId) != null && window.localStorage.getItem('jingzhou' + openId) != "") {
        $(".jingz_city").addClass("show");
    }

    if (window.localStorage.getItem('qingzhou' + openId) != null && window.localStorage.getItem('qingzhou' + openId) != "") {
        $(".qingz_city").addClass("show");
    }
    if (window.localStorage.getItem('yangzhou' + openId) != null && window.localStorage.getItem('yangzhou' + openId) != "") {
        $(".yangz_city").addClass("show");
    }
    if (window.localStorage.getItem('end' + openId) != null && window.localStorage.getItem('end' + openId) != "") {
        $(".gzlm_dot").addClass("show");
    }

    if (yizhou != "") {
        $(".yiz_city").addClass("show");
    }
    if (yuzhou != "") {
        $(".yuz_city").addClass("show");
    }
    if (jingzhou != "") {
        $(".jingz_city").addClass("show");
    }
    if (qingzhou != "") {
        $(".qingz_city").addClass("show");
    }
    if (yangzhou != "") {
        $(".yangz_city").addClass("show");
    }
    if (end != "") {
        $(".gzlm_dot").addClass("show");
    }
</script>
</body>
<script type="text/javascript" src="http://static.joyme.com/mobile/cp/ylzt20160805/js/preloadjs.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/powerGlory.js?v=20161108"></script>
<script type="text/javascript">
    function isWeiXin(){
        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            return true;
        }else{
            return false;
        }
    };
    function showweixin(){
        var tip='<div class="wx-tip"><div>请在微信端打开</div></div>'
        if(!isWeiXin()){
            $('#wrap').html('');
            $('#wrap').append(tip)
        }
    };

   showweixin();

    function init(){
        var loadingbar=$('.loading_cont').find('cite');
        var url='http://static.joyme.com/mobile/cp/qlyry20161108/';
        var manifest;
        var preload;
        //定义相关JSON格式文件列表
        function setupManifest() {
            manifest = [
                {src:  "http://static.joyme.com/mobile/cp/ylzt20160805/js/preloadjs.min.js",id: "preloadjs"},
                {src: "http://static.joyme.com/js/jquery-1.9.1.min.js",id: "jqueryjs"},
                {src: url+'images/begin.jpg',id:"images"},
                {src: url+'images/info-bg.jpg',id:"images"},
                {src: url+'images/map.jpg',id:"images"},
                {src: url+'images/zs.png',id:"images"},
                {src: url+'images/tz.png',id:"images"},
                {src: url+'images/tg.png',id:"images"},
                {src: url+'images/sz-bg.png',id:"images"},
                {src: url+'images/sz_6.png',id:"images"},
                {src: url+'images/sz_5.png',id:"images"},
                {src: url+'images/sz_4.png',id:"images"},
                {src: url+'images/sz_3.png',id:"images"},
                {src: url+'images/sz_2.png',id:"images"},
                {src: url+'images/sz_1.png',id:"images"},
                {src: url+'images/sgml.png',id:"images"},
                {src: url+'images/loading.png',id:"images"},
                {src: url+'images/lbss.png',id:"images"},
                {src: url+'images/jin.png',id:"images"},
                {src: url+'images/info-top.png',id:"images"},
                {src: url+'images/info-text.png',id:"images"},
                {src: url+'images/hrd.png',id:"images"},
                {src: url+'images/gz.png',id:"images"},
                {src: url+'images/city-icon.png',id:"images"},
                {src: url+'images/cbzj.png',id:"images"},
                {src: url+'images/begin-btn.png',id:"images"},
                {src: url+'audio/bgm.mp3',id:"music"}
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
            console.log("加载出错！",evt.text);
        };
        function handleFileProgress(event) {
            var progress=preload.progress*100|0;
            loadingbar.css('width',progress+'%');
        };
        function loadComplete(event) {
            console.log("已加载完毕全部资源");
            pageInit();
        }
        setupManifest();
        startPreload();
    };

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
                title: '集天下勇士之愿，和兄弟一起寻国家宝藏，重塑王权荣耀！',
                link: 'http://t.cn/RfzMaqy',
              // link: 'http://t.cn/RfzMaqy',//com地址
                imgUrl: 'http://static.joyme.com/mobile/cp/qlyry20161108/images/share.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '寻宝藏塑荣耀',
                desc: '集天下勇士之愿，和兄弟一起寻国家宝藏，重塑王权荣耀！',
                link: 'http://t.cn/RfzMaqy',
                imgUrl: 'http://static.joyme.com/mobile/cp/qlyry20161108/images/share.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '寻宝藏塑荣耀',
                desc: '集天下勇士之愿，和兄弟一起寻国家宝藏，重塑王权荣耀！',
                link: 'http://t.cn/RfzMaqy',
                imgUrl: 'http://static.joyme.com/mobile/cp/qlyry20161108/images/share.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '寻宝藏塑荣耀',
                desc: '集天下勇士之愿，和兄弟一起寻国家宝藏，重塑王权荣耀！',
                link: 'http://t.cn/RfzMaqy',
                imgUrl: 'http://static.joyme.com/mobile/cp/qlyry20161108/images/share.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>