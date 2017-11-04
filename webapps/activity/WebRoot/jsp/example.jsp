<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>微信JSDK部分示例</title>
    <script src="http://static.joyme.com/js/jquery-1.9.1.min.js" language="javascript"></script>
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/0.4.3/weui.min.css"/>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body>

${openid}
<div class="weui_cells_title">用户信息</div>
<div class="weui_cells">
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            昵称
        </div>
        <div class="weui_cell_ft" id="nickname">
            ${weixinUser.nickname}
        </div>

    </div>
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            openid(用户唯一标识)
        </div>
        <div class="weui_cell_ft" id="openid"
             style="display: inline;overflow: hidden;width: 30%;white-space:nowrap;text-overflow:ellipsis;">
            ${openid}
        </div>

    </div>
    <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
            头像地址
        </div>
        <div class="weui_cell_ft">
            <img id="headimgurl" src="${weixinUser.headimgurl}" height="50" width="100">
        </div>

    </div>
</div>
<div class="lbox_close wxapi_form" style="display: none;">


    <a href="#" class="weui_btn weui_btn_primary" id="checkJsApi">判断当前客户端是否支持指定JS接口</a>

    <a href="#" class="weui_btn weui_btn_primary" id="onMenuShareAppMessage">分享给朋友</a>
    <a href="#" class="weui_btn weui_btn_primary" id="onMenuShareTimeline">分享给朋友圈</a>
    <a href="#" class="weui_btn weui_btn_primary" id="onMenuShareQQ">分享给QQ</a>
    <a href="#" class="weui_btn weui_btn_primary" id="onMenuShareQZone">分享给QQ空间</a>

    <a href="#" class="weui_btn weui_btn_primary" id="getNetworkType">获取网络状态接口</a>
    <a href="#" class="weui_btn weui_btn_primary" id="getLocation">获取地理位置接口</a>

    <a href="#" class="weui_btn weui_btn_primary" id="hideOptionMenu">隐藏右上角菜单接口</a>
    <a href="#" class="weui_btn weui_btn_primary" id="showOptionMenu">显示右上角菜单接口</a>


    <a href="#" class="weui_btn weui_btn_primary" id="hideMenuItems">批量隐藏功能按钮接口</a>
    <a href="#" class="weui_btn weui_btn_primary" id="showMenuItems">批量显示功能按钮接口</a>


    <a href="#" class="weui_btn weui_btn_primary" id="closeWindow">关闭当前网页窗口接口</a>

    <a href="#" class="weui_btn weui_btn_primary" id="hideAllNonBaseMenuItem">隐藏所有非基础按钮接口</a>
    <a href="#" class="weui_btn weui_btn_primary" id="showAllNonBaseMenuItem">显示所有功能按钮接口</a>
</div>
</body>
<script>
    wx.config({
        debug: false,
        appId: '${appId}',
        timestamp: '${timestamp}',
        nonceStr: '${nonceStr}',
        signature: '${signature}',
        jsApiList: [
            'checkJsApi',
            'onMenuShareAppMessage',
            'onMenuShareTimeline',
            'onMenuShareQQ',
            'onMenuShareQZone',
            'getNetworkType',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'hideMenuItems',
            'showMenuItems',
            'closeWindow',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem'
        ]
    });
    wx.ready(function () {
        // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
        document.querySelector('#checkJsApi').onclick = function () {
            wx.checkJsApi({
                jsApiList: [
                    'getNetworkType',
                    'previewImage'
                ],
                success: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        //分享给朋友
        document.querySelector('#onMenuShareAppMessage').onclick = function () {
            wx.onMenuShareAppMessage({
                title: '分享的标题',
                desc: '分享的描述',
                link: 'http://www.baidu.com',
                imgUrl: 'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png',
                success: function (res) {
                    alert('已分享');
                },
                cancel: function (res) {
                    alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        //分享给朋友圈
        document.querySelector('#onMenuShareTimeline').onclick = function () {
            wx.onMenuShareTimeline({
                title: '分享的标题',
                link: 'http://www.baidu.com',
                imgUrl: 'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png',
                success: function (res) {
                    alert('已分享');
                },
                cancel: function (res) {
                    alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };
        //分享给QQ
        document.querySelector('#onMenuShareQQ').onclick = function () {
            wx.onMenuShareQQ({
                title: '分享的标题',
                desc: '分享的描述',
                link: 'http://www.baidu.com',
                imgUrl: 'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png',
                success: function (res) {
                    alert('已分享');
                },
                cancel: function (res) {
                    alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        //分享给QQ空间
        document.querySelector('#onMenuShareQZone').onclick = function () {
            wx.onMenuShareQZone({
                title: '分享的标题',
                desc: '分享的描述',
                link: 'http://www.baidu.com',
                imgUrl: 'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png',
                success: function (res) {
                    alert('已分享');
                },
                cancel: function (res) {
                    alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        //获取当前网络状态
        document.querySelector('#getNetworkType').onclick = function () {
            wx.getNetworkType({
                success: function (res) {
                    alert(res.networkType);
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        // 获取当前地理位置
        document.querySelector('#getLocation').onclick = function () {
            wx.getLocation({
                success: function (res) {
                    alert(JSON.stringify(res));
                },
                cancel: function (res) {
                    alert('用户拒绝授权获取地理位置');
                }
            });
        };

        // 隐藏右上角菜单
        document.querySelector('#hideOptionMenu').onclick = function () {
            wx.hideOptionMenu();
        };

        // 显示右上角菜单
        document.querySelector('#showOptionMenu').onclick = function () {
            wx.showOptionMenu();
        };


        //批量隐藏菜单项
        document.querySelector('#hideMenuItems').onclick = function () {
            wx.hideMenuItems({
                menuList: [
                    'menuItem:share:timeline', // 分享到朋友圈
                    'menuItem:copyUrl' // 复制链接
                ],
                success: function (res) {
                    alert('已隐藏“分享到朋友圈”，“复制链接”等按钮');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        // 批量显示菜单项
        document.querySelector('#showMenuItems').onclick = function () {
            wx.showMenuItems({
                menuList: [
                    'menuItem:share:timeline', // 分享到朋友圈
                    'menuItem:copyUrl' // 复制链接
                ],
                success: function (res) {
                    alert('已显示:“分享到朋友圈”，“复制链接”等按钮');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        };

        // 隐藏所有非基本菜单项
        document.querySelector('#hideAllNonBaseMenuItem').onclick = function () {
            wx.hideAllNonBaseMenuItem({
                success: function () {
                    alert('已隐藏所有非基本菜单项');
                }
            });
        };

        // 显示所有被隐藏的非基本菜单项
        document.querySelector('#showAllNonBaseMenuItem').onclick = function () {
            wx.showAllNonBaseMenuItem({
                success: function () {
                    alert('已显示所有非基本菜单项');
                }
            });
        };

        //关闭当前窗口
        document.querySelector('#closeWindow').onclick = function () {
            wx.closeWindow();
        };
    });
</script>

</html>