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
    <link rel="stylesheet" href="http://static.joyme.com/mobile/cp/ylzt20160805/css/style.css">
    <title>天命圣旨，战力比拼</title>

    <script>
        var openId = '${openid}';
        var record="";
        var nickname="";
        document.addEventListener("DOMContentLoaded", function (e) {
            var w=e.target.activeElement.clientWidth>=1024?1024:e.target.activeElement.clientWidth;
            document.getElementById('wrap').style.zoom = w / 375;
        });
    </script>
</head>
<body onload="init()">
<c:if test="${activityCountry!=null}">
    <script>
         record =  ${activityCountry.country};
         nickname =  "${activityCountry.nickname}";
    </script>
</c:if>
<div id="wrap">
    <div class="audio_btn">
        <audio preload="true" id="audioBtn" autoplay="true" loop="true">
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/bgm-1.mp3" type="audio/mp3" />
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/bgm-1.ogg" type="audio/ogg" />
        </audio>
        <audio preload="true" id="sz_audioBtn">
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/sz-bgm.mp3" type="audio/mp3" />
            <source src="http://static.joyme.com/mobile/cp/ylzt20160805/audio/sz-bgm.ogg" type="audio/ogg" />
        </audio>
    </div>
    <div class="content">
        <div class="page1 active">
            <div class="loading_box">
                <div class="logo"></div>
                <div class="loading_bar">
                    <cite style="width:0;"></cite>
                </div>
                <h4 class="loading_num"></h4>
            </div>
        </div>
        <div class="page2">
            <div class="btn start_b"></div>
        </div>
        <div class="page3 ">
            <div class="sz_info">
                <p></p>
                <p></p>
                <p></p>
                <p></p>
                <p></p>
                <p></p>
                <p></p>
            </div>
        </div>
        <div class="page4">
            <div class="sz_box">
                <div data-city="1"></div>
                <div data-city="2"></div>
                <div data-city="3"></div>
                <div data-city="4"></div>
                <div data-city="5"></div>
            </div>
        </div>
        <div class="page5">
            <div class="sz_country">
                <div class="sz_country_m">
                    <div class="sz_country_t"></div>
                    <div class="sz_country_c"></div>
                    <div class="sz_country_b"></div>
                </div>
                <div class="sz_click_btn btn">
                    <cite><em></em><em></em></cite>
                    <span>点击圣旨</span>
                </div>
            </div>
            <div class="dialog sz_dialog">
                <div class="dialog_bg"></div>
                <div class="dialog_con sz_dialog_con">
                    <cite class="close btn">关闭</cite>
                    <div class="sure btn">接旨</div>
                    <div class="reselect btn">重新选择</div>
                </div>
            </div>
        </div>
        <div class="page6">
            <div class="sz_info sz_country_con">
                <p></p>
                <p></p>
                <p></p>
                <p></p>
                <p><span><font class="name">曹志强</font></span></p>
                <p></p>
                <p></p>
            </div>
            <div class="sikp_box slideUp">
                <cite class="up_btn btn"></cite>
                <span class="btn">生成国王套装</span>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500163490" ></script>
</body>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/preloadjs.min.js"></script>
<script type="text/javascript" src="/static/js/comm.js"></script>
<script type="text/javascript">
    function init(){
        var loadingbar=$('.loading_bar').children('cite');
        var loadingnum =$(".loading_num");
        var url='http://static.joyme.com/mobile/cp/ylzt20160805';
        var manifest;
        var preload;
        //定义相关JSON格式文件列表
        function setupManifest() {
            manifest = [{
                src:  "http://api.activity.joyme.com/static/js/preloadjs.min.js",
                id: "preloadjs"
            }, {
                src: "http://static.joyme.com/js/jquery-1.9.1.min.js",
                id: "jqueryjs"
            }, {
                src: url+"/images/bg.jpg",
                id: "images"
            }, {
                src:  url+"/images/loading-bg.jpg",
                id: "images"
            }
                , {
                    src:  url+"/images/bg-2.jpg",
                    id: "images"
                }
                ,{
                    src: url+"/audio/bgm-1.mp3",
                    id:"audio"
                }
                ,{
                    src:url+"/audio/sz-bgm.mp3",
                    id:"audio"
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
            console.log("加载出错！",evt.text);
        };
        function handleFileProgress(event) {
            var progress=preload.progress*100|0;
            loadingbar.css('width',progress+'%');
            loadingnum.text(progress+ " %");
        };
        function loadComplete(event) {
            console.log("已加载完毕全部资源");
            sz_page.record=record;//0新用户 1扬 2荆 3豫 4益 5青
            sz_page.names=nickname;//用户昵称
            sz_page.init();
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
               // link: 'http://testbeta.joyme.com/activity/yulong/sharepage.do',
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
                //link: 'http://testbeta.joyme.com/activity/yulong/sharepage.do',
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