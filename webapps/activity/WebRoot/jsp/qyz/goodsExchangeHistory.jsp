<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>兑换记录</title>
    <link rel="stylesheet" type="text/css" href="http://static.joyme.com/mobile/cms/jfsc/css/common.css">
    <script>
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {return false}, true)
        }, true);
        var openId = '${openid}';
    </script>
</head>
<body onload="showMoreInfo('1')">
<div id="wrapper">
    <div id="main">
        <div class="banner-box">
            <a href="javascript:;">
                <img src="http://static.joyme.com/mobile/cms/jfsc/images/banner-img.jpg">
            </a>
        </div>
        <!-- 兑换记录 开始 -->
        <!-- 无兑换 开始 -->
        <div class="un-dq-text" id="showNoItem" style="display: none">您目前还没有兑奖记录！</div>

        <!-- 无兑换 结束 -->
        <div class="list-box" style="display: none" id="showItems">

        </div>
        <div class="load-more" style="display: none" id="showMoreId">
            <a href="javascript:;" onclick="showMoreInfo('0')">点击查看更多</a>
        </div>
        <!-- 兑换记录 结束 -->
    </div>

</div>
<div class="nav-box">
    <div class="nav-con">
        <a href="/weixinop/qyzGoods/page.do?openid=${openid}">积分商城</a>
        <a href="javascript:;"  class="active">兑换记录</a>
        <a href="/weixinop/qyzUserinfo/page.do?openid=${openid}">个人中心</a>
    </div>
</div>
<!-- 详情弹窗 开始 -->
<div class="dh-box">
    <div class="close-icon">关闭</div>
    <div class="btn-sure">确定</div>
    <!-- 兑换成功 -->
    <div class="dh-suc dh-ele" id="showDescription">
        <h2>兑换成功</h2>
        <p>您获得的激活码为：</p>
        <font>17508150809582058</font>
        <p>请进入青云志手游公众号[下载游戏]进行激活</p>
    </div>
</div>
<div class="mask-box"></div>
<!-- 详情弹窗 结束 -->
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script src="http://static.joyme.com/mobile/cms/jfsc/js/action.js"></script>
</body>
<script>
    var n = 1;
    function showMoreInfo(objFlag) {
        $.post("/weixinop/qyzExchange/getExchangeGoodsPage.do", {openid: openId, pSize: 10, pNum: n++}, function (data) {
            if (data != "") {
                var countryScoreObj = eval("(" + data + ")");
                var rs = countryScoreObj.rs;
                if (objFlag=='1'){
                    if (rs=="1"){
                        var exchangeLogs = countryScoreObj.result;
                        var innerHtml="";
                        var total = exchangeLogs.length;
                        var hasMore=countryScoreObj.hasMore;
                        for(var i=0;i<exchangeLogs.length;i++){
                            var tempHtml='<div>';
                            tempHtml+='<cite><img src="'+exchangeLogs[i].imagePath+'"></cite>';
                            tempHtml+='<div class="list-cont">';
                            tempHtml+='<font>'+exchangeLogs[i].goodsName+'</font>';
                            tempHtml+='<b>兑换积分：'+exchangeLogs[i].requireScore+'</b>';
                            tempHtml+='<b>兑换时间：'+exchangeLogs[i].exchangeTime+'</b>';
                            tempHtml+='<div class="btn-box fn-clear">';
                            tempHtml+='<span class="btn-dh jl" onclick="showGoodsDesc(\''+exchangeLogs[i].description +'\')">查看详情</span>';
                            tempHtml+='</div>';
                            tempHtml+='</div>';
                            tempHtml+='</div>';
                            innerHtml =innerHtml + tempHtml;
                        }
                        $('#showItems').append(innerHtml);
                        $('#showItems').show();
                        if(hasMore=="1"){
                            $('#showMoreId').show();
                        }else {
                            $('#showMoreId').hide();
                        }

                    }else if (rs=="0"){
                        $('#showNoItem').show();
                    }
                }else if(objFlag=="0"){
                    if (rs=="1"){
                        var exchangeLogs = countryScoreObj.result;
                        var innerHtml="";
                        var total = exchangeLogs.length;
                        var hasMore=countryScoreObj.hasMore;
                        for(var i=0;i<exchangeLogs.length;i++){
                            var tempHtml='<div>';
                            tempHtml+='<cite><img src="'+exchangeLogs[i].imagePath+'"></cite>';
                            tempHtml+='<div class="list-cont">';
                            tempHtml+='<font>'+exchangeLogs[i].goodsName+'</font>';
                            tempHtml+='<b>兑换积分：'+exchangeLogs[i].requireScore+'</b>';
                            tempHtml+='<b>兑换时间：'+exchangeLogs[i].exchangeTime+'</b>';
                            tempHtml+='<div class="btn-box fn-clear">';
                            tempHtml+='<span class="btn-dh jl" onclick="showGoodsDesc(\''+exchangeLogs[i].description +'\')">查看详情</span>';
                            tempHtml+='</div>';
                            tempHtml+='</div>';
                            tempHtml+='</div>';
                            innerHtml =innerHtml + tempHtml;
                        }
                        $('#showItems').append(innerHtml);
                        if(hasMore=="1"){
                            $('#showMoreId').show();
                        }else {
                            $('#showMoreId').hide();
                        }
                    }else if (rs=="0"){
                        //$('#showNoItem').show();
                    }
                }
            }
        })
    }
    function showGoodsDesc(descObj) {
        $('#showDescription').empty();
        $('#showDescription').append(descObj);
        oDhbox = $('.dh-box');
        oDhbox.show();
        $('.mask-box').show();
    }
</script>
</html>