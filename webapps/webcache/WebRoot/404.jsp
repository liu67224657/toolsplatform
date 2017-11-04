<%@ page import="com.enjoyf.util.AppUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (!AppUtil.checkIsAndroid(request) && !AppUtil.checkIsIOS(request)) {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="applicable-device" content="pc" />
    <meta name="mobile-agent" content="format=xhtml;url=http://m.joyme.com/" />
    <meta name="mobile-agent" content="format=html5;url=http://m.joyme.com" />
    <script type="text/javascript">
        var _speedMark = new Date();
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="shenma-site-verification" content="362ee286130d388f82f998599b8c30dc_1443432989" />
    <title>着迷网_中国领先的泛娱乐门户网站_电脑游戏_电视游戏_手机游戏</title>
    <meta name="Keywords" content="着迷网,泛娱乐,电脑游戏,电视游戏,手机游戏" />
    <meta name="description" content="着迷网是中国领先的泛娱乐门户网站，为玩家提供电脑游戏、电视游戏、手机游戏等娱乐领域深度服务，及WIKI开放型中文维基百科。只有着迷，才有乐趣！" />
    <meta name="360-site-verification" content="1de1c482f5c029917d9e65306dcb78d4" />
    <meta name="sogou_site_verification" content="VEFkoCjIwA" />
    <meta property="qc:admins" content="21404323606271556375" />
    <link href="http://static.joyme.com/pc/cms/jmsy/css/header-index_201607.css?v20160719" rel="stylesheet" type="text/css" />
    <link href="http://static.joyme.com/pc/cms/jmsy/css/index_201607.css?v20160719" rel="stylesheet" type="text/css" />
    <link href="http://static.joyme.com/pc/cms/jmsy/css/index.css?v20160727" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var require={
            urlArgs:'20160719000'
        }
    </script>
    <script src="http://static.joyme.com/js/jquery-1.9.1.min.js" language="javascript"></script>
    <script>
        var bs = {
            versions: function () {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {//移动终端浏览器版本信息
                    trident: u.indexOf('Trident') > -1, //IE内核
                    presto: u.indexOf('Presto') > -1, //opera内核
                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                    iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                    WPhone: u.indexOf('Windows Phone') > -1,//windows phone
                    iPad: u.indexOf('iPad') > -1, //是否iPad
                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                };
            }(),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        }

        var jumpUrl = 'http://m.joyme.com/index.html';
        var domain = window.location.hostname;
        var env = domain.substring(domain.lastIndexOf('.'), domain.length);

        if (bs.versions.mobile) {
            if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.ios || bs.versions.WPhone) {
                if(window.location.hash.indexOf('wappc') <= 0){
                    var flag = getCookie('jumpflag');
                    if(flag == '' || flag == null || flag == undefined){
                        window.location.href = jumpUrl;
                    }
                }else{
                    var timeOutDate = new Date();
                    timeOutDate.setTime(timeOutDate.getTime() + (24*60*60*1000));
                    setCookie('jumpflag', 'wappc', timeOutDate, env);
                }
            }
        }

        function getCookie(objName) {
            var arrStr = document.cookie.split("; ");
            for (var i = 0; i < arrStr.length; i++) {
                var temp = arrStr[i].split("=");
                if (temp[0] == objName && temp[1] != '\'\'' && temp[1] != "\"\"") {
                    return unescape(temp[1]);
                }
            }
            return null;
        }

        function setCookie(key, value, exDate, env) {
            var cookie = "";
            if (!!key)
                cookie += key + "=" + escape(value) + ";path=/;domain=.joyme"+env+";expires="+exDate.toUTCString();
            document.cookie = cookie;
        }

    </script>
</head>
<body>
<div id="joyme-wrapper">
    <!--header-->
    <header>
        <div class="joyme-header">
            <div class="joyme-header-top">
                <div class="joyme-nav fn-ma">
                    <div class="joyme-logo fn-left fn-ovf">
                        <h1><a href="http://www.joyme.com/" title="着迷网"><img src="http://static.joyme.com/pc/cms/jmsy/images/joyme-logo.png" alt="着迷网" title="着迷网" /></a></h1>
                    </div>
                    <ul class="joyme-nav-ul">
                        <li> <a target="_blank" href="http://wiki.joyme.com/">WIKI</a> </li>
                        <li> <a target="_blank" href="http://www.joyme.com/news/#areaid=1010">资讯</a>
                            <div style="width:1000px;">
                                <a target="_blank" href="http://www.joyme.com/xinwen/#areaid=1016">新游资讯</a>
                                <a target="_blank" href="http://www.joyme.com/news/official/#areaid=1018">业界新闻</a>
                                <a target="_blank" href="http://www.joyme.com/news/ztqh/#areaid=1016">原创专栏</a>
                                <a target="_blank" href="http://www.joyme.com/news/reviews/#areaid=1314">游戏评测</a>
                                <a target="_blank" href="http://www.joyme.com/news/gameguide/#areaid=1011">游戏攻略</a>
                                <a target="_blank" href="http://www.joyme.com/news/rwzf/#areaid=1015">人物专访</a>
                                <a target="_blank" href="http://www.joyme.com/news/newpicture/#areaid=1012">美图囧图</a>
                                <a target="_blank" href="http://www.joyme.com/news/asiangames/#areaid=1017">热讯聚焦</a>
                                <a target="_blank" href="http://www.joyme.com/news/blue/#areaid=1013">精品推荐</a>
                            </div> </li>
                        <li> <a target="_blank" href="http://www.joyme.com/collection/">游戏库</a> </li>
                        <li> <a target="_blank" href="http://v.joyme.com/">视频</a>
                            <div>
                                <a target="_blank" href="http://v.joyme.com/wmdj/">玩命点击</a>
                                <a target="_blank" href="http://v.joyme.com/xypc/">游猎天下</a>
                                <a target="_blank" href="http://v.joyme.com/zzmj/">掌中妙计</a>
                                <a target="_blank" href="http://v.joyme.com/jygl/">游必有方</a>
                                <a target="_blank" href="http://v.joyme.com/yxjbd/">游戏劲爆点</a>
                            </div> </li>
                        <li> <a target="_blank" href="http://www.joyme.com/gift">礼包</a> </li>
                        <li> <a target="_blank" href="http://bbs.joyme.com/">论坛</a> </li>
                        <li> <a target="_blank" href="http://www.joyme.com/vr/">VR</a> </li>
                        <!--小绿点提示为cite标签-->
                    </ul>
                    <div class="joyme-search-bar fn-clear fn-ovf">
                        <script type="text/javascript">
                            (function(){
                                document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));
                                var bdcs = document.createElement('script');
                                bdcs.type = 'text/javascript';
                                bdcs.async = true;
                                bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=6135004928527906484' + '&plate_url=' + encodeURIComponent(window.location.href) + '&t=' + Math.ceil(new Date()/3600000);
                                var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(bdcs, s);})();


                        </script>
                    </div>
                    <div class="joyme-loginbar fn-right">
                        <script type="text/javascript">
                            document.write('<script src="http://passport.joyme.com/auth/header/userinfo?t=index&'+Math.random()+'"><\/script>');
                        </script>
                    </div>
                </div>
            </div>
        </div>
        <style>
            #ujian_BtnDiv{ display:none;}

        </style>
    </header>
</div>
<!--header==end-->
<div id="cont-404" >
    <cite><img src="http://static.joyme.com/pc/cms/jmsy/images/404.png" alt="" /></cite>
    <a id="back-btn" href="http://www.joyme.com" target="_blank">前往首页 &gt;</a>
</div>

<!--footer==sstar-->
<div class="footer">
    <div class="footer-con">
        <span>2011－2016 joyme.com, all rights reserved</span>
        <a href="http://www.joyme.com/help/aboutus" target="_blank" rel="nofollow">关于着迷</a> |
        <a href="http://www.joyme.com/about/products" target="_blank" rel="nofollow">着迷产品</a> |
        <a href="http://www.joyme.com/help/milestone" target="_blank" rel="nofollow">着迷大事记</a> |
        <a href="http://www.joyme.com/gopublic/" target="_blank">着迷·新三板</a> |
        <a href="http://www.joyme.com/about/press" target="_blank" rel="nofollow">媒体报道</a> |
        <a href="http://www.joyme.com/about/business" target="_blank" rel="nofollow">商务合作</a> |
        <a href="http://www.joyme.com/about/job/zhaopin" target="_blank" rel="nofollow">加入着迷</a> |
        <a href="http://www.joyme.com/about/contactus" target="_blank" rel="nofollow">联系我们</a>|
        <a href="http://www.joyme.com/help/law/parentsprotect" target="_blank" rel="nofollow">家长监护</a>|
        <a href="http://www.joyme.com/sitemap.htm" target="_blank">网站地图</a>
        <br>
        <br>
        <span>北京乐享方登网络科技股份有限公司</span>
        <span> 北京市海淀区知春路27号11层1107室&nbsp;&nbsp;&nbsp;客服电话：010-51292727</span>
        <span><a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备11029291号</a></span>
        <span>京公网安备110108001706号</span>
        <span><a href="http://joymepic.joyme.com/article/uploads/allimg/201603/1457504308371218.jpg" target="_blank">京网文[2014]0925-225号</a></span>
        <script type="text/javascript" src="http://tajs.qq.com/stats?sId=56598033" charset="UTF-8">
        </script>
    </div>
</div>
<!--footer==end-->

</div>
</body>
<script>
    $(function(){
        /*5秒后跳转*/
        (function(){
            var num = 5;
            var showtime = $("<p>您访问的页面不存在，页面将在<em>"+num+"</em>s之后跳转至<a id='jm-back' href='http://www.joyme.com'>首页</a><br />如果没有跳转，请点击下面的链接</p>");
            var showtimeNum = showtime.find("em");
            showtime.insertBefore("#back-btn");
            num-=1;
            var countDown = setInterval(function(){
                if(num){
                    showtimeNum.html(num);
                    num-=1;
                }else{
                    countDown && clearInterval(countDown);
                    document.location.href = document.getElementById("back-btn").getAttribute("href");
                }
            },1000);
        })();
    });
</script>
</html>
<% } else {%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>404</title>
    <link href="http://www.joyme.com/static/theme/wap/css/errorpage.css" rel="stylesheet" type="text/css">
<body>
<div id="wrapper">
    <div class="no-net">
        <b><img src="http://www.joyme.com/static/theme/wap/images/no-net.jpg" alt=""></b>

        <p>页面加载失败</p>

        <p>你访问的页面走丢了</p>
    </div>
</div>
</body>
</html>
<%}%>
