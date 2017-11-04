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
    <title>得龙脉者得天下</title>
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
    <link rel="stylesheet" href="http://static.joyme.com/mobile/cp/ylzt20160828/css/style.css?v=20160830">
</head>
<body onload="init()">
<div class="hp_tips">请切换到竖屏观看~</div>
<div id="wrap">
    <!--music-->
    <div class="music_btn btn">
        <audio preload="true" id="audioBtn" autoplay="true" loop="true">
            <source src="http://static.joyme.com/mobile/cp/ylzt20160828/audio/bgm.mp3" type="audio/mp3"/>
        </audio>
    </div>
    <div class="gz_btn btn"></div>
    <div class="content">
        <!--loading-->
        <div class="loading active">
            <cite></cite>
            <span>0%</span>
        </div>
        <!--homepage-->
        <div class="homepage">
            <div class="btn begin_btn"></div>
        </div>
        <!--infopage-->
        <div class="infopage">
            <div class="nextpage btn">跳过</div>
            <div class="infocont">
                <p>中平元年，黄巾贼起</p>

                <p>致朝野震荡，饿殍遍地</p>

                <p>四周百姓苦不堪言</p>

                <p>后有神卜，名管辂，夜观天象</p>

                <p>言有龙脉一说</p>

                <p>寻之可平黄巾，定天下</p>

                <p>将军应天命诞生于乱世</p>

                <p>集天下万民之愿</p>

                <p>定能寻得此龙脉</p>

                <p>救民于水火，成天下之共生…</p>
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
                    <cite class="wdbx_icon">
                        <span class="bx_bg"></span>
                        <span class="bx_icon"></span>
                    </cite>
                </div>
            </div>
            <!--骰子弹窗-->
            <div class="sz_bot"><cite></cite></div>
            <!--动画弹窗-->
            <div class="animat">
                <div class="animat_con"></div>
                <div class="animat_tit"><span class="animat_text"></span></div>
            </div>
            <!--dialog-->
            <div class="dialog_mask"></div>
            <!--二次进入-->
            <div class="dialog again_d">
                <div class="dialog_con">
                    <font class="pt">寻宝旅途已重置，请在开始位置重新出发，之前寻得的宝藏并没有丢哦~还存在右下角【我的宝箱】内，点击即可查看所获CDK。</font>

                    <div class="dialog_btn an">
                        <cite class="qd">确定</cite>
                    </div>
                </div>
            </div>
            <!--三顾茅庐-->
            <div class="dialog sgml_d">
                <div class="dialog_con">
                    <font class="pt">路遇刘、关、张三兄弟拜访诸葛先生，被邀之畅谈一番，获赠锦囊一枚</font>
                    <cite class="jn-icon"></cite>

                    <div class="dialog_btn an">
                        <cite class="djck">点击拆开</cite>
                    </div>
                </div>
            </div>
            <div class="dialog djck_d">
                <div class="clickSZ">
                    <div class="clickSZ_con">
                        <cite></cite><cite></cite><cite></cite><cite></cite><cite></cite><cite></cite></div>
                    <p>点击骰子选择固定点数前进</p>
                </div>
            </div>
            <!--国战-->
            <div class="dialog gza_d">
                <div class="dialog_con">
                    <font class="pt">前方3格偶遇扬州和益州的国战，是否传送至主战场观战？</font>

                    <div class="dialog_btn an">
                        <cite class="shi">是</cite>
                        <cite class="fou">否</cite>
                    </div>
                </div>
            </div>
            <!--乐不思蜀-->
            <div class="dialog lbss_d">
                <div class="dialog_con">
                    <font class="pt">偶遇故友，受邀酒宴洗尘，乐不思蜀，原地停留5秒。</font>
                    <code class="djs"></code>
                </div>
            </div>
            <!--偷猪-->
            <div class="dialog tz_d">
                <div class="dialog_con">
                    <font class="pt">长途寻宝，口粮不足，需返回原来村庄偷猪，后退3步</font>

                    <div class="dialog_btn an">
                        <cite class="qd" data-qj="-3">确定</cite>
                    </div>
                </div>
            </div>
            <!--华容道-->
            <div class="dialog hrd_d">
                <div class="dialog_con">
                    <font class="pt">进入华容道，遇关羽拦截曹操，前往协助，请摇晃手机告知己军曹操位置</font>

                    <div class="dialog_btn an">
                        <cite class="ksyh">开始摇晃</cite>
                    </div>
                </div>
            </div>
            <div class="dialog ksyh_d"><!--active-->
                <div class="yh_con">
                    <div class="yh_tits"></div>
                    <div class="yh_icon"></div>
                </div>
            </div>
            <div class="dialog ksyh_end">
                <div class="dialog_con">
                    <font class="pt">关公义释曹操，忠义神武，获世人奉崇，激励你前进3步。</font>

                    <div class="dialog_btn an">
                        <cite class="qd" data-qj="3">确定</cite>
                    </div>
                </div>
            </div>
            <!--赤壁之战-->
            <div class="dialog cbzj_d">
                <div class="dialog_con">
                    <font class="pt">赤壁之战开战在即，众将士即将为国出征！请为国呐喊助力！</font>

                    <div class="dialog_btn an">
                        <cite class="ksnh">开始呐喊</cite>
                    </div>
                </div>
            </div>
            <div class="dialog ksnh_d"><!--active-->
                <div class="nh_con"></div>
            </div>
            <div class="dialog ksnh_end">
                <div class="dialog_con">
                    <font class="pt">将军呐喊助威，使得我军士气高昂，必能打赢这场战役。</font>

                    <div class="dialog_btn an">
                        <cite class="qd">确定</cite>
                    </div>
                </div>
            </div>
            <!--玩法规则-->
            <div class="dialog gz_d">
                <div class="dialog_con">
                    <div class="dialog_close btn"></div>
                    <h4>玩法规则</h4>

                    <p>1. 玩家点击【掷骰子】按钮抛掷骰子来决定前进的步数，并按照占领的格子开启对应的龙脉宝箱；</p>

                    <p>2. 占据【益州】、【豫州】、【荆州】、【青州】、【扬州】、【终极龙脉】即可开启不同的龙脉宝箱；</p>

                    <p>3. 奖品设置：【益州】：二倍经验丹*1【豫州】：白色讨伐令*3【荆州】：玄武血剂*1【青州】：免做令牌*1【扬州】： 二级桃子酒*1【终极龙脉】：无双之攻*1</p>
                    <span>注：</span>

                    <p>1. 每个龙脉宝藏只可领取一次；</p>

                    <p>2. 走完36步后，可立即重新开始寻宝，即寻宝次数不限。</p>
                </div>
            </div>
            <!--宝藏弹窗-->
            <div class="dialog bz_d"><!--gzhy_d-->
                <div class="dialog_con">
                    <div class="dialog_close btn"></div>
                    <h3>长按复制下面CDK</h3>
                    <ul class="lb_city">
                        <li><b>益州：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                        <li><b>豫州：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                        <li><b>荆州：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                        <li><b>青州：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                        <li><b>扬州：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                        <li><b>终极：</b>

                            <p>寻得此处龙脉，即可获得CDK</p></li>
                    </ul>
                    <em>点击【礼包兑换】按钮即可兑换所获CDK</em>

                    <div class="dialog_btn three">
                        <a href="javascript:void(0)" id="startGame">进入游戏</a>
                        <a href="http://ylzt.qq.com/act/agile/38450/index.html">礼包兑换</a>
                        <cite class="gzhy_btn">告知好友</cite>
                    </div>
                </div>
            </div>
            <!--结束情景-->
            <div class="dialog end_d">
                <div class="dialog_con">
                    <div class="dialog_close btn end_btn"></div>
                    <font class="ending_text"></font>

                    <div class="dialog_btn two">
                        <cite class="wdbz">我的宝藏</cite>
                        <cite class="zwyc">在玩一次</cite>
                    </div>
                </div>
            </div>
            <!--国家奖励-->
            <div class="dialog city_d">
                <div class="dialog_con">
                    <div class="dialog_close btn"></div>
                    <div class="city_text"></div>
                </div>
            </div>
            <!--提示-->
            <div class="dialog tip_d">
                <div class="dialog_con">
                    <div class="dialog_close btn"></div>
                    <font class="tip_text"></font>
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
        $(".dialog_mask,.again_d").show();
    }
    $('#startGame').on('click', function () {
        try{
            MtaH5.clickStat('GZ2');
        }catch(e){}
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
<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500294195" ></script>
</body>
<script type="text/javascript" src="http://static.joyme.com/mobile/cp/ylzt20160805/js/preloadjs.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/yulonghuntcomm.js?v=20160830"></script>
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

    function init() {
        var loadingbar = $('.loading').children('cite');
        var loadingnum = $('.loading').children('span');
        var url = 'http://static.joyme.com/mobile/cp/ylzt20160828/';
        var manifest;
        var preload;
        //定义相关JSON格式文件列表
        function setupManifest() {
            manifest = [{
                src: "http://static.joyme.com/mobile/cp/ylzt20160805/js/preloadjs.min.js",
                id: "preloadjs"
            }, {
                src: "http://static.joyme.com/js/jquery-1.9.1.min.js",
                id: "jqueryjs"
            }, {
                src: url + 'images/loading.jpg',
                id: "images"
            }, {
                src: url + 'images/info-bg.jpg',
                id: "images"
            }, {
                src: url + 'images/begin.jpg',
                id: "images"
            }, {
                src: url + 'images/main.jpg',
                id: "images"
            }, {
                src: url + 'images/btn.jpg',
                id: "images"
            }, {
                src: url + 'images/wdbz.jpg',
                id: "images"
            }, {
                src: url + 'audio/bgm.mp3',
                id: "music"
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
            loadingbar.css('height', progress + '%');
            loadingnum.text(progress + " %");
        };
        function loadComplete(event) {
            console.log("已加载完毕全部资源");
            pageInit();
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
                title: '绝世宝藏，等您来寻',
                link: 'http://t.cn/RcvOkRO',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '得龙脉者得天下',
                desc: '绝世宝藏，等您来寻',
                link: 'http://t.cn/RcvOkRO',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '得龙脉者得天下',
                desc: '绝世宝藏，等您来寻',
                link: 'http://t.cn/RcvOkRO',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '得龙脉者得天下',
                desc: '绝世宝藏，等您来寻',
                link: 'http://t.cn/RcvOkRO',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>