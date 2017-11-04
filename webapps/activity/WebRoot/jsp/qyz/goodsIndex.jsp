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
    <title>积分商城</title>
    <link rel="stylesheet" type="text/css" href="http://static.joyme.com/mobile/cms/jfsc/css/common.css">
    <script type="text/javascript">
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {return false}, true)
        }, true);
        var openId = '${openid}';
    </script>
</head>
<body onload="init()">
<div id="wrapper">
    <div id="main">
        <div class="banner-box">
            <a href="javascript:;">
                <img src="http://static.joyme.com/mobile/cms/jfsc/images/banner-img.jpg">
            </a>
        </div>
        <!-- 商城列表 开始 -->
        <div class="list-box" id="goodsList">

        </div>
        <div class="load-more" id="showMoreId" style="display: none">
            <a href="javascript:;" onclick="showMoreInfo()">点击查看更多</a>
        </div>
        <!-- 商城列表 结束 -->
    </div>
</div>
<div class="nav-box">
    <div class="nav-con">
        <a href="javascript:;" class="active">积分商城</a>
        <a href="/weixinop/qyzExchange/page.do?openid=${openid}">兑换记录</a>
        <a href="/weixinop/qyzUserinfo/page.do?openid=${openid}">个人中心</a>
    </div>
</div>
<!-- 详情弹窗 开始 -->
<div class="xq-cont">
    <div class="close-icon">关闭</div>
    <div class="xq-des" >
        <div id="showDescription">
            <h2>青云志公测豪华大礼包</h2>
            <p>内含金币“200000，炼器符”2，灌注符“4，初级完璧符”1</p>
            <p>每人可兑换1个，改礼包与新手礼包冲突。</p>
        </div>
        <span>每人仅可兑换一个</span>
    </div>
</div>
<div class="mask-box"></div>
<!-- 详情弹窗 结束 -->
<!-- 兑换弹窗  开始 -->
<div class="dh-box" id="exchangeGoodsDiv">
    <div class="close-icon">关闭</div>
    <div class="btn-sure">确定</div>
    <!-- 兑换成功 -->
    <div class="dh-suc dh-ele" id="vExchangeSuccess">
        <h2>兑换成功</h2>
        <p>您获得的激活码为：</p>
        <font id="cdkCode">17508150809582058</font>
        <p>请进入青云志手游公众号[下载游戏]进行激活</p>
    </div>
    <!-- 获得奖品 -->
    <div class="jp dh-ele" id="rExchangeSuccess">
        <p>兑换成功！您获得</p>
        <h2 id="realDesc">李易峰水杯</h2>
    </div>
    <!-- 积分不足  -->
    <div class="bz dh-ele" id="exchangeFail">
    </div>
    <div class="wbd dh-ele" id="exchangeFailTel">
    </div>
</div>
<!-- 兑换弹窗  结束 -->
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script src="http://static.joyme.com/mobile/cms/jfsc/js/action.js"></script>
</body>
<script>
    function init() {
        $.post("/weixinop/qyzGoods/getGoodsPage.do", {openid: openId, pSize: 10, pNum: 0}, function (data) {
            if (data != "") {
                var countryScoreObj = eval("(" + data + ")");
                var exchangeLogs = countryScoreObj.result;
                var innerHtml="";
                var hasMore=countryScoreObj.hasMore;
                for(var i=0;i<exchangeLogs.length;i++){
                    var tempHtml='<div>';
                    tempHtml+='<cite><img src="'+exchangeLogs[i].imagePath+'"></cite>';
                    tempHtml+='<div class="list-cont">';
                    tempHtml+='<font>'+exchangeLogs[i].goodsName+'</font>';
                    tempHtml+='<b>兑换积分：'+exchangeLogs[i].requireScore+'</b>';
                    tempHtml+='<b >剩余数量：<i id="surplusNum'+ exchangeLogs[i].goodsId+'">'+exchangeLogs[i].surplusNum+'</i></b>';
                    tempHtml+='<div class="btn-box fn-clear">';
                    tempHtml+='<span class="btn-xq" onclick="showGoodsDesc(\''+exchangeLogs[i].description +'\')">查看详情</span>';
                    if (exchangeLogs[i].surplusNum=='0'){
                        tempHtml+='<span class="btn-dh  un-dh" >兑换</span>';
                    }else {
                        tempHtml+='<span class="btn-dh" id="exchangeBtn'+ exchangeLogs[i].goodsId+'" onclick="exchangeGoods(\''+exchangeLogs[i].requireScore+'\',\''+exchangeLogs[i].goodsId +'\')">兑换</span>';
                    }
                    tempHtml+='</div>';
                    tempHtml+='</div>';
                    tempHtml+='</div>';
                    innerHtml =innerHtml + tempHtml;
                }
                $('#goodsList').append(innerHtml);
                if(hasMore=="1"){
                    $('#showMoreId').show();
                }else {
                    $('#showMoreId').hide();
                }
            }
        })
    }
    var n=2;
    function showMoreInfo() {
        $.post("/weixinop/qyzGoods/getGoodsPage.do", {openid: openId, pSize: 10, pNum: n++}, function (data) {
            if (data != "") {
                var countryScoreObj = eval("(" + data + ")");
                var exchangeLogs = countryScoreObj.result;
                var innerHtml="";
                var hasMore=countryScoreObj.hasMore;
                for(var i=0;i<exchangeLogs.length;i++){
                    var tempHtml='<div>';
                    tempHtml+='<cite><img src="'+exchangeLogs[i].imagePath+'"></cite>';
                    tempHtml+='<div class="list-cont">';
                    tempHtml+='<font>'+exchangeLogs[i].goodsName+'</font>';
                    tempHtml+='<b>兑换积分：'+exchangeLogs[i].requireScore+'</b>';
                    tempHtml+='<b >剩余数量：<i id="surplusNum'+ exchangeLogs[i].goodsId+'">'+exchangeLogs[i].surplusNum+'</i></b>';
                    tempHtml+='<div class="btn-box fn-clear">';
                    tempHtml+='<span class="btn-xq" onclick="showGoodsDesc(\''+exchangeLogs[i].description +'\')">查看详情</span>';
                    if (exchangeLogs[i].surplusNum=='0'){
                        tempHtml+='<span class="btn-dh  un-dh" >兑换</span>';
                    }else {
                        tempHtml+='<span class="btn-dh" id="exchangeBtn'+ exchangeLogs[i].goodsId+'" onclick="exchangeGoods(\''+exchangeLogs[i].requireScore+'\',\''+exchangeLogs[i].goodsId +'\')">兑换</span>';
                    }
                    tempHtml+='</div>';
                    tempHtml+='</div>';
                    tempHtml+='</div>';
                    innerHtml =innerHtml + tempHtml;
                }
                $('#goodsList').append(innerHtml);
                if(hasMore=="1"){
                    $('#showMoreId').show();
                }else {
                    $('#showMoreId').hide();
                }
            }
        })
    }
    function showGoodsDesc(descObj) {
        $('#showDescription').empty();
        $('#showDescription').append(descObj);
        oXq = $('.xq-cont');
        oXq.show();
        $('.mask-box').show();
    }
    var totalScore = '${totalScore}';
    function exchangeGoods(requireScore,goodsId) {
        if(parseInt(totalScore)>=parseInt(requireScore)){
            $.post("/weixinop/qyzGoods/exchangeGoods.do", {openid: openId,goodId:goodsId}, function (data) {
                if (data != "") {
                    var exchangeGoodsObj = eval("(" + data + ")");
                    var rs = exchangeGoodsObj.rs;
                    var suprplusNumId = "#surplusNum"+goodsId;
                    var exchangeBtnId = "#exchangeBtn"+goodsId;
                    var oldNum = $(suprplusNumId).text();
                    var newNum = parseInt(oldNum)-1;
                    if (rs=='0'){
                        $('#exchangeGoodsDiv').show();
                        $('#vExchangeSuccess').hide();
                        $('#rExchangeSuccess').hide();
                        if(exchangeGoodsObj.result.indexOf("您的剩余积分不足")>-1 || exchangeGoodsObj.result.indexOf("商品剩余数量不足")>-1){
                            $('#exchangeFail').empty();
                            $('#exchangeFail').html(exchangeGoodsObj.result);
                            $('#exchangeFail').show();
                            $('#exchangeFailTel').hide();
                        }else {
                            $('#exchangeFailTel').empty();
                            $('#exchangeFailTel').html(exchangeGoodsObj.result);
                            $('#exchangeFailTel').show();
                            $('#exchangeFail').hide();
                        }
                        $('.mask-box').show();
                    }else if (rs=='1'){
                        $('#exchangeGoodsDiv').show();
                        $('#vExchangeSuccess').show();
                        $('#cdkCode').text(exchangeGoodsObj.result);
                        $(suprplusNumId).html(newNum);
                        $('#rExchangeSuccess').hide();
                        $('#exchangeFail').hide();
                        $('#exchangeFailTel').hide();
                        totalScore = parseInt(totalScore)-parseInt(requireScore);
                        if(newNum==0){
                            $(exchangeBtnId).addClass('un-dh');
                            $(exchangeBtnId).removeAttr("onclick");
                        }
                        $('.mask-box').show();
                    }else if (rs=='2'){
                        $('#exchangeGoodsDiv').show();
                        $('#vExchangeSuccess').hide();
                        $('#realDesc').html(exchangeGoodsObj.result);
                        $(suprplusNumId).html(newNum);
                        $('#rExchangeSuccess').show();
                        $('#exchangeFail').hide();
                        $('#exchangeFailTel').hide();
                        totalScore = parseInt(totalScore)-parseInt(requireScore);
                        if(newNum==0){
                            $(exchangeBtnId).addClass('un-dh');
                            $(exchangeBtnId).removeAttr("onclick");
                        }
                        $('.mask-box').show();
                    }

                }
            })
        } else {
            $('#exchangeGoodsDiv').show();
            $('#vExchangeSuccess').hide();
            $('#rExchangeSuccess').hide();
            $('#exchangeFail').empty();
            $('#exchangeFailTel').hide();
            var tempHtml='<p>您的剩余积分不足!</p>';
            $('#exchangeFail').append(tempHtml);
            $('#exchangeFail').show();
            $('.mask-box').show();
        }

    }
</script>
</html>