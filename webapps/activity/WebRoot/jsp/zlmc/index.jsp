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
    <link rel="stylesheet" href="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/css/style.css">
    <title>智龙迷城</title>
    <script>
        var openId = '${openid}';
        var oldScore =  ${activityCountry.score};
        document.addEventListener("DOMContentLoaded", function (e) {
            var w=e.target.activeElement.clientWidth>=1024?1024:e.target.activeElement.clientWidth;
            document.getElementById('wrap').style.zoom = w / 320;
        });
    </script>
</head>
<body>
<div id="wrap">
    <div class="audio_btn">
        <audio preload="true" id="audioBtn" autoplay="true" loop="true">
            <source src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/audio/bgm-1.mp3" type="audio/mp3" />
            <source src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/audio/bgm-1.ogg" type="audio/ogg" />
        </audio>
    </div>
    <div class="contant">
        <div class="indexPage">
            <div class="down_icon"></div>
        </div>
        <div class="infoPage">
            <div class="info_con">
                <div class="gif"></div>
            </div>
            <div class="begin_btn"></div>
        </div>
        <div class="playPage">
            <div class="play_con">
                <div class="xt_list">
                    <div class="xt-box"><span class="xt-now" style="width:100%;"></span></div>
                </div>
                <div class="zp_box">
                    <div>
                        <span></span>
                    </div>
                    <div>
                        <span></span>
                    </div>
                    <div>
                        <span></span>
                    </div>
                </div>
            </div>
            <div class="dialog_maskt"></div>
            <div class="dialog_mask"></div>
            <div class="dialog_begin dialog">
                <font></font>
            </div>
            <div class="dialog_fail dialog">
                <font></font>
                <p>继续加油哦~<br/>您共扫荡<span class="fail_num"></span>只宠物</p>
            </div>
            <div class="dialog_win dialog"></div>
            <div class="infor">
                <cite class="btn close infor_close">关闭</cite>
                <input type="text" class="name">
                <input type="text" class="weixin">
                <input type="text" class="iphone">
                <input type="text" class="dizhi">
                <div class="submit btn">提交</div>
            </div>
            <div class="prize">
                <div class="share_con"></div>
                <div class="phb_con">
                    <cite class="btn close phb_close"></cite>
                    <div class="phb_text">
                        <ul id="rankingInfo">
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>2</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>3</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>4</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                            <li><span>1</span><cite><img src="http://share-ued.enjoyf.com/UED/%E6%98%93%E8%88%AA/%E8%85%BE%E8%AE%AF%E5%BE%AE%E4%BF%A1%E6%8E%A8%E5%B9%BF%E9%A1%B5/%E6%99%BA%E9%BE%99%E8%BF%B7%E5%9F%8E-%E7%BF%BB%E7%89%8C%E9%A1%B5%E9%9D%A2/images/bg.jpg" alt=""></cite><font>天天向上</font><em>44</em></li>
                        </ul>
                    </div>
                </div>
                <div class="prize_con">
                    <span class="number">2</span>
                    <cite class="btn atplay">在玩一次</cite>
                    <a href="http://awp.qq.com/weixin/html/zlmc_download.html?ADTAG=gong.zhong.hao.download" class="btn loadbtn">下载智龙迷城</a>
                    <cite class="btn sharebtn">告诉好友</cite>
                    <cite class="btn phbbtn">排行榜</cite>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="/static/js/preloadjs.min.js"></script>
<script type="text/javascript" src="/static/js/zlmc/zlmc.js"></script>
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
                'hideMenuItems'
            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });


        wx.ready(function () {
            //分享到朋友圈
            wx.onMenuShareTimeline({
                title: '【智龙迷城】一切凭手快，扫荡宠物赢手办、抱枕、魔法石。',
                link: 'http://t.cn/RcjVDfb',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '宠物大扫荡 好礼拿不停',
                desc: '【智龙迷城】手快有，手慢无！扫荡智龙迷城宠物赢手办、抱枕、魔法石啦~',
                link: 'http://t.cn/RcjVDfb',
                imgUrl: 'http://static.joyme.com/mobile/cp/ylzt20160805/images/share-icon.png',
                success: function (res) {
                    alert("分享成功");
                }
            });
            wx.hideMenuItems({
                menuList: ['menuItem:share:qq','menuItem:share:weiboApp','menuItem:share:QZone'] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
            });
        });

    });
    function addUserScore(newscore) {
        if (newscore > oldScore){
            $.post("/activity/zlmc/addUserScore.do", {openid: openId,score:newscore}, function (data) {
                if (data != "") {
                    var userRankObj = eval("(" + data + ")");
                    var result = userRankObj.result;
                    if(result.rs == '1'){
                        oldScore = newscore;
                        getUserOrder(newscore);
                    }else {
                        $('.infor').show();
                    }
                }
            })
        }else {
            $('.infor').show();
        }
    }
    function getUserOrder(newscore) {
        $.post("/activity/zlmc/getUserOrder.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var result = userRankObj.result;
                if (parseInt(result)>20){
                    $('.prize').show();
                }else {
                    $('.infor').show();
                }
            }
        })
    }
</script>
</html>