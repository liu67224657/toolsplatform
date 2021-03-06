<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset='UTF-8'>
    <meta content='width=device-width, initial-scale=1.0, user-scalable=no' name='viewport'>
    <meta content='yes' name='apple-mobile-web-app-capable'>
    <meta content='black' name='apple-mobile-web-app-status-bar-style'>
    <meta content='telephone=no' name='format-detection'>
    <title>评论</title>
    <link href='/opinion/css/HB_comment.css' rel='stylesheet' type='text/css'>
    <script type='text/javascript'>
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {
                return false
            }, true)
        }, true);
        window.uniKey = "${unikey}";
        window.domain = "${domain}";
        window.channel = "syhb2";
        window.uno = "${uno}";
        window.uid = "${uid}";
        window.token = "${token}";
        window.logindomain = "${logindomain}";
        if (window.uno == "") {
            window.uno = null;
        }
        if (window.uid == "") {
            window.uid = null;
        }
        if (window.token == "") {
            window.token = null;
        }
        if (window.logindomain == "") {
            window.logindomain = null;
        }
        window.marticle_host = "${marticle_host}";
    </script>
</head>
<body>
<div id="_btn_status" style="display: none;">no</div>
<div id='content'>
    <!--outer-div-->
    <div id="outer-div">

        <!--module-comment-->
        <div class='module-comment clearfix' id="commentBody" style="display: none">
            <!--hot-tit-->
            <div class='info-bar hot-tit' id="loginTips" style="display: none;"><a href="javascript:void(0);" class="fr"
                                                                                   id="loginout" style="display: none;">登录</a><span>热门评论</span>
            </div>
            <div class='comment-box clearfix' id='hot-comment'>
                <!--module-list==end-->
            </div>
            <!--hot-tit==end-->
            <!--all-tit-->
            <div class='info-bar all-tit'><span>全部评论</span></div>
            <div class='comment-box' id='all-comment'>

                <!--module-list==end-->
            </div>
            <!--all-tit==end-->
        </div>
        <div class='module-comment clearfix' id="nocomment" style="display:none;">
            <!--null-comment-->
            <div class="null-comment">
                <span>暂无评论</span>
            </div>
            <!--null-comment==end-->
        </div>

        <!--module-comment==end-->
    </div>
    <!--outer-div-->
    <!--wp-comment-->
    <div class='wp-comment'>
        <a href='javascript:void(0);' class="tucao" onclick="commentAlert(this)">说点什么吧&nbsp;&nbsp;◕‿‿◕</a>

    </div>
    <!--wp-comment==end-->
    <!--alert-comment-->
    <div class='alert-comment'>
        <div class='info-alert-box'>
            <!--info-beyond-->
            <div class='info-beyond'>
                <span></span>
            </div>
            <!--info-beyond==end-->
            <div class='info-alert-form'>
                <textarea class='info-textarea' placeholder='请输入内容，140字以内' id="textarea_body"></textarea>
            </div>
            <div class='info-alert-shop clearfix'>
                <span class='fl' style='display:none'>已超出<b class='inp-num'>0</b>个字</span>

                <div class='fr'>
                    <span class="cancel-btn">取消</span>
                    <span class="reply-btn">来一发</span>
                </div>
            </div>
        </div>
    </div>
    <!--alert-comment==end-->
    <div class='wp-bg'></div>
    <!--wp_comment_alert-->
    <div class="wp_comment_alert">
        <div class="wp_comment_tips">您还没登录<br/>请先<a href="#">登录</a>后再评论~</div>
        <div class="wp_comment_btn">
            <span class="wp_cancel">取消</span>
            <span class="wp_confirm">登录</span>
        </div>
    </div>

    <div class="wp_tc_dialog"></div>

    <!--wp_comment_alert==end-->
    <div class="no-more" style="display: none;">没有更多...</div>
</div>
<script src='/js/jquery-1.9.1.min.js'></script>
<script src='/js/nativewapcomment.js'></script>
</body>
</html>

