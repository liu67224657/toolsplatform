<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <title>全宇宙最顶级游戏人交流大会独家报名通道！相约MGF西雅图！</title>
    <script src="/static/js/mgf/js/adaptive.js"></script>
    <script src="/static/js/mgf/libs/mobile-detect.min.js"></script>
    <script src="/static/js/mgf/libs/TweenMax.min.js"></script>
    <script src="/static/js/mgf/libs/easing/EasePack.min.js"></script>
    <script src="/static/js/mgf/libs/ScrollToPlugin.js"></script>
    <script src="/static/js/mgf/libs/easeljs-0.8.1.min.js"></script>
    <script src="/static/js/mgf/libs/tweenjs-0.6.1.min.js"></script>
    <script src="/static/js/mgf/libs/movieclip-0.8.1.min.js"></script>
    <script src="/static/js/mgf/libs/createjs-2015.11.26.min.js"></script>
    <script src="/static/js/mgf/index.js?d=1"></script>

    <script>

        window['adaptive'].desinWidth = 750;
        window['adaptive'].baseFont = 10;
        window['adaptive'].maxWidth =750;
        window['adaptive'].init();              // 调用初始化方法

    </script>

    <script>
        var canvas, stage, exportRoot;

        function init() {

            var loader = new createjs.LoadQueue(false);
            loader.loadManifest(lib.properties.manifest);

            loader.addEventListener("fileload", function(evt){
                if (evt.item.type == "image") { images[evt.item.id] = evt.result; }
            });
            loader.addEventListener("complete", function(evt){

                $("#loaderDIV").hide();
                //$("#loaderPROGRESS").css("width",evt.loaded*100);
                canvas = document.getElementById("canvas");
                exportRoot = new lib.index();

                stage = new createjs.Stage(canvas);
                stage.addChild(exportRoot);
                stage.update();

                createjs.Ticker.setFPS(lib.properties.fps);
                createjs.Ticker.addEventListener("tick", stage);

            });

            loader.addEventListener("progress",function(evt){
                $("#loaderPROGRESS").css("width",evt.loaded*100);

            });

            /*

             var md = new MobileDetect(window.navigator.userAgent);
             var VIEWPORT = document.querySelector("meta[name=viewport]");
             var e = parseInt(window.screen.width);
             var t = e / 750;
             if( md.os()=="AndroidOS" ){
             VIEWPORT.setAttribute('content', 'width=750, initial-scale=' + t + ', minimum-scale=' + t + ', maximum-scale=' + t + ', target-densitydpi=device-dpi, user-scalable=no');
             }else if( md.os()=="iOS" ){
             VIEWPORT.setAttribute('content', 'width=750, initial-scale=' + t + ', minimum-scale=' + t + ', maximum-scale=' + t + ', user-scalable=no');
             }else{
             VIEWPORT.setAttribute('content', 'width=device-width');
             }
             */
        }



    </script>

    <style>
        *{ margin:0 }
        html, body{ /*height:100%*/ }
        body{ background-color:#000 }
        #canvas{ width:100%; background-color:#000 }

        .tip_border{
            display: none;
            position: absolute;
            top:0;
            width: 100%;
            height: 100%;;
            z-index: 11;
        }

        .tip_border img{
            width: 100%;
            height: 100%;
        }
        .btn_close{
            display: block;
            position: absolute;
            width: 50px;
            height: 41px;
            background: url(/static/js/mgf/img/r8.png) no-repeat;
            margin:40px 0 0 20px;
        }

        .sign{
            display: none;
            position: absolute;
            top:0.5rem;
            left: 0.82rem;
            width: 5.84rem;
            height: 7.48rem;
            background: #061b45;
            z-index: 12;
        }

        .map{
            display: none;
            position: absolute;
            top:0.5rem;
            left: 0.82rem;
            width: 5.84rem;
            height: 8.48rem;
            background: #061b45;
            z-index: 12;
        }

        .map img{
            display: block;
            width: 5rem;
            height: 6.9rem;
            margin: 0 auto;
        }
        .con1{
            width: 5.49rem;
            height: 7.46rem;
            padding:0.6rem 0 0 0;
            border:2px solid #00eaff;
            margin:0.2rem auto 0 auto;
            border-radius: 4px;
        }

        .con{
            width: 5.49rem;
            height: 6.46rem;
            padding:0.6rem 0 0 0;
            border:2px solid #00eaff;
            margin:0.2rem auto 0 auto;
            border-radius: 4px;
        }

        .layer{
            display: block;
            margin:0.3rem 0 0 0;
        }

        .shade{
            display: none;
            position: absolute;
            top: 0;
            z-index: 10;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.7);
        }

        .layer span{
            width: 1.8rem;
            display: inline-block;
            font-size: 0.32rem;
            color: #00f0ff;
            line-height: 0.64rem;
            height: 0.54rem;
            margin:0 0 0 0.2rem;
        }

        .layer input{
            display: inline-block;
            width: 2.8rem;
            padding:0 0.1rem;
            height: 0.52rem;
            border:2px solid #204eaa;
            border-radius: 4px;
            font-size: 0.3rem;
            background: #0c2b69;
            color: #e4ff00;
        }

        .nd{
            display: inline-block;
            letter-spacing: 0.16rem;
        }

        .btn_submit{
            display: block;
            width: 2.25rem;
            height: 0.68rem;
            margin: 0.4rem auto 0 auto;
            background: url(/static/js/mgf/img/btn_submit.jpg) no-repeat;
            background-size: 2.25rem 0.68rem;
        }

        .btn_sum_close{
            position: absolute;
            display: block;
            width: 0.57rem;
            height: 0.57rem;
            background:url(/static/js/mgf/img/close.jpg) no-repeat #00eaff center;
            border-radius: 100px;
            background-size: 0.32rem 0.32rem;
            margin:0 0 0 5.4rem;
        }

        .text_ds{
            display: block;
            text-align:center;
            color: #8fa9dd;
            font-size: 0.24rem;
            margin:0.2rem 0 0 0;
        }


        .hideLoader{
            width: 100%;
            height: 100%;
            position:absolute;
            background-color:#000;
            z-index:2;
            top: 0;
            display:block;
        }

        .loaderImg{
            display: block;
            margin: 40% auto 0 auto;
        }

        .loadingBg{
            display: block;
            width:100%;
            height:0.03rem;
            background-color:#EAEAEA;
        }

        .loaderBox{
            display: block;
            width:2rem;
            height:0.03rem;
            margin:0.5rem auto 0 auto;
        }

        .loadContent{
            position: absolute;
            margin:-2px 0 0 0;
            width:0;
            height:0.03rem;
            background-color:#F7C204;
        }
    </style>
</head>

<body onload="init();">
<canvas id="canvas" width="750" height="1205"></canvas>


<img src="/static/js/mgf/img/fx.png" width="50%" style="position: absolute; top: 0;left: 50% ;z-index: 11; display: none" id="fx">

<div id="loaderDIV"  class="hideLoader">
    <img src="/static/js/mgf/img/loading.gif" class="loaderImg">
    <div class="loaderBox">
        <div class="loadingBg"></div>
        <div class="loadContent" id="loaderPROGRESS"></div>
    </div>
</div>

<section id="tip_human" class="tip_border">
    <a href="javascript:void(0)" class="btn_close" onclick="closeTipHuman()"></a>
    <img src="/static/js/mgf/img/r1.jpg" id="tip_main1">
    <img src="/static/js/mgf/img/r2.jpg" id="tip_main2">
    <img src="/static/js/mgf/img/r3.jpg" id="tip_main3">
    <img src="/static/js/mgf/img/r4.jpg" id="tip_main4">
    <img src="/static/js/mgf/img/r5.jpg" id="tip_main5">
    <img src="/static/js/mgf/img/r6.jpg" id="tip_main6">
    <img src="/static/js/mgf/img/r7.jpg" id="tip_main7">
    <img src="/static/js/mgf/img/r8.jpg" id="tip_main8">

</section>

<section class="sign" id="sign">
    <a href="javascript:void(0)" class="btn_sum_close"></a>
    <div class="con">
        <div class="layer">
            <span class="nd">姓名：</span>
            <input type="text"  id="usename" name="username">
        </div>

        <div class="layer">
            <span class="nd">职位：</span>
            <input type="text"  id="duty" name="duty">
        </div>

        <div class="layer">
            <span class="nd">公司：</span>
            <input type="text" id="company" name="company">
        </div>

        <div class="layer">
            <span>代表作：</span>
            <input type="text" id="representativeWork" name="representativeWork">
        </div>

        <div class="layer">
            <span>手机号：</span>
            <input type="text" id="telephone" name="telephone">
        </div>

        <a href="javascript:void(0)" class="btn_submit"></a>

        <div class="text_ds">报名截止至9月30日，名额有限，官方抽取</div>
    </div>
</section>


<section class="map">
    <a href="javascript:void(0)" class="btn_sum_close"></a>
    <div class="con1">
        <img src="/static/js/mgf/img/v1.jpg" id="map_pic1" style="display: none">
        <img src="/static/js/mgf/img/v2.jpg" id="map_pic2" style="display: none">
        <img src="/static/js/mgf/img/v3.jpg" id="map_pic3" style="display: none">
        <img src="/static/js/mgf/img/v4.jpg" id="map_pic4" style="display: none">

    </div>


</section>


<section class="shade" id="shade"></section>


<audio  autoplay="autoplay">

    <source src="/static/js/mgf/sounds/WeAreElectric.mp3" type="audio/mpeg" />

</audio>
</body>
<script src="/static/js/mgf/js/zepto.min.js"></script>



<script>

    //提交
    $(".btn_submit").bind("click",function(){
        var usename = $('#usename').val();
        var duty = $('#duty').val();
        var telephone = $('#telephone').val();
        var representativeWork = $('#representativeWork').val();
        var company = $('#company').val();
        if (usename == '' || usename == null) {
            alert("名字不能为空");
            $('#usename').val('');
        } else if (duty == '' || duty == null) {
            alert("职务不能为空");
            $('#duty').val('');
        } else if (company == '' || company == null) {
            alert("公司不能为空");
            $('#company').val('');
        } else if (telephone == '' || telephone == null) {
            alert("电话不能为空");
            $('#telephone').val('');
        } else {
                $.post("/activity/mgf/addUserInfo.do", {
                    username: usename,
                    company: company,
                    duty: duty,
                    telephone: telephone,
                    representativeWork: representativeWork
                }, function (data) {
                    if (data != "") {
                        var userRankObj = eval("(" + data + ")");
                        var result = userRankObj.rs;
                        if (result == "1") {
                            $("#shade,#sign").hide(0);
                            alert("添加成功");
                        } else {
                            alert("添加失败，请重新添加");
                        }
                    }
                })
        }
    });

    $(".btn_sum_close").bind("click",function(){
        $("#shade,#sign,.map").hide(0);
    });

    function showSign(){
        showShade();
        $("#sign").show(0);
    }

    function showShade(){
        $("#shade").show(0);
    }

    function showMap(type){
        $(".map").show(0);
        $("#map_pic1,#map_pic2,#map_pic3,#map_pic4").hide(0);
        $("#map_pic"+type).show(0);
        showShade();
    }

    function showTipHuman(type){
        $("#tip_human").show(0);
        $("#tip_main1,#tip_main2,#tip_main3,#tip_main4,#tip_main5,#tip_main6,#tip_main7,#tip_main8").hide(0);
        $("#tip_main"+type).show(0);
    }

    function closeTipHuman(){
        getEle("tip_human").style.display="none";
    }

    function getEle(ID){
        return document.getElementById(ID);
    }

    function showShare(){
        $("#fx").show(0);
        showShade();

        setTimeout(function(){
            $("#fx,#shade").hide(0);

        },2000);
    }
</script>


</html>