var hostname = window.location.hostname;
var wwwHost = 'http://www.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var apiHost = 'http://api.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var passportHost = 'http://passport.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';
var articleHost = 'http://article.joyme.' + hostname.substring(hostname.lastIndexOf('.') + 1, hostname.length) + '/';

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
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

var uri = window.location.href;
if (uri.indexOf("?") > 0) {
    uri = uri.substring(0, uri.indexOf("?"));
}
if (uri.indexOf("#") > 0) {
    uri = uri.substring(0, uri.indexOf("#"));
}

var groupId = '';
var path = window.location.pathname;
if (uri.indexOf('http://') == 0 || uri.indexOf('https://') == 0) {
    var number = '';
    var arr = path.split('/');
    for (var i = 0; i < arr.length; i++) {
        var item = arr[i];
        if (item.indexOf(".html") > 0) {
            item = item.replace(".html", "");
            var position = item.indexOf("_");
            if (position >= 0) {
                item = item.substring(0, position);
            }
        }

        if ($.isNumeric(item)) {
            number += item;
        }
    }
    if (number.length > 8) {
        groupId = number.substring(8, number.length);
    }
}

var getLiveStatusBlock = false;
var getLiveListBlock = false;
var getLastLiveBlock = false;

if (groupId.length > 0) {
    if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad) {
        $(document).ready(function () {
            $('#live_more').hide();
            getLiveStatus(groupId);
            getLiveList(groupId, 10, 1, 20);
            $('.article-page').on('scroll', function(){
                var loading = $('.zb-load-text');
                var commentlist = $('.zb-box');
                var h = $('.article-page')[0].scrollHeight;
                var wh = $('.article-page').height();
                var n = $('.zb-box').offset().top;
                var sTop = $(this).scrollTop();
                var loaded = false;
                if (!loaded && sTop >= h - wh - 50) {
                    loaded = true;
                    setTimeout(function () {
                        var cp = loading.attr('data-cp');
                        if(cp == null || cp == undefined){
                            cp = '0';
                        }
                        var psize = loading.attr('data-ps');
                        if(psize == null || psize == undefined){
                            psize = '20';
                        }
                        getLiveList(groupId, 10, parseInt(cp) + 1, parseInt(psize));
                        loaded = false;
                        loading.text('');
                    }, 2000);
                    loading.text('正在加载中...');
                }
            });

//            var timer = setInterval(function () {
//                getLiveStatus(groupId);
//                var flag = 0;
//                if($('#live_area dl').length > 0){
//                    flag = parseInt($('#live_area dl:first').attr('data-flag'));
//                }
//                getLastLive(groupId, 10, flag);
//            }, 10000);
        });
    } else {
        $(document).ready(function () {
            $('#live_more').hide();
            getLiveStatus(groupId);
            getLiveList(groupId, 10, 1, 20);
            $('#live_more').on('click', function () {
                var cp = $(this).attr('data-cp');
                if(cp == null || cp == undefined){
                    cp = '0';
                }
                var psize = $(this).attr('data-ps');
                if(psize == null || psize == undefined){
                    psize = '20';
                }
                getLiveList(groupId, 10, parseInt(cp) + 1, parseInt(psize));
            });

//            var timer = setInterval(function () {
//                getLiveStatus(groupId);
//                var flag = 0;
//                if($('#live_area dl').length > 0){
//                    flag = parseInt($('#live_area dl:first').attr('data-flag'));
//                }
//                getLastLive(groupId, 10, flag);
//            }, 10000);
        });
    }
}

function getLiveStatus(groupId) {
    if(getLiveStatusBlock){
        return;
    }
    getLiveStatusBlock = true;
    $.ajax({
        url: articleHost + "plus/api.php?a=getLiveStatus&aid=" + groupId,
        type: "get",
        dataType: "jsonp",
        jsonpCallback: "getlivestatuscallback",
        success: function (req) {
            if (req != null && req != undefined) {
                var status = req.status;
                $('#zhibo_status').html("【" + status + "】");
            }
            return;
        },
        error: function () {
            alert('获取失败，请刷新');
            getLiveStatusBlock = false;
            return
        },
        complete:function(){
            getLiveStatusBlock = false;
            return;
        }
    });
}

function getLastLive(groupId, domain, timeflag) {
    if(getLastLiveBlock){
        return;
    }
    getLastLiveBlock = true;
    $.ajax({
        url: apiHost + "comment/bean/json/querybyscore",
        type: "post",
        data: {groupid: groupId, domain: domain, flag: timeflag},
        dataType: "jsonp",
        jsonpCallback: "querybyscorecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                alert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                alert('缺少参数~');
                return;
            } else if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null || result == undefined) {
                    return;
                }
                if (result.rows != null && result.rows != undefined) {
                    var html = '';
                    for (var i = 0; i < result.rows.length; i++) {
                        var expand = result.rows[i].expandstr;

                        var videoHtml = '';
                        if(expand.videoiframe.length > 0){
                            videoHtml = expand.videoiframe;
                        }else if (expand.video.length > 0) {
                            videoHtml = '<video controls="controls" poster="'+expand.video+'?vframe/jpg/offset/0"><source src="' + expand.video + '" type="video/mp4">你的浏览器不支持html5视频播放</video>';
                        } else {
                            if (result.rows[i].pic.length > 0) {
                                videoHtml = '<img src="' + result.rows[i].pic + '?imageView/2/w/640" alt="' + result.rows[i].pic + '"/>';
                            }
                        }

                        html += '<dl data-flag="' + (result.rows[i].createTime / 1000) + '">' +
                        '<dt>' +
                        '<cite><img src="' + expand.compereicon + '"></cite>' +
                        '</dt>' +
                        '<dd>' +
                        '<code></code>' +
                        '<font>'+result.rows[i].dateStr+'</font>' +
                        '<span>[' + expand.comperetype + ']' + expand.comperename + '</span>' +
                        '<p>' + result.rows[i].description + '</p>' +
                        '<em>' + videoHtml + '</em>' +
                        '</dd>' +
                        '</dl>';
                    }
                    if($('#live_area dl').length > 0){
                        $('#live_area dl:first').before(html);

                        setTimeout(function(){
                            $('#live_area dl').addClass('on')
                        },10);
                    }else{
                        $('#live_area').append(html);
                        setTimeout(function(){
                            $('#live_area dl').addClass('on')
                        },10);
                    }
                }
            } else {
                alert(resMsg.msg);
            }
            return;
        },
        error: function () {
            alert('获取失败，请刷新');
            getLastLiveBlock = false;
            return;
        },
        complete:function(){
            getLastLiveBlock = false;
            return;
        }
    });
}

function getLiveList(groupId, domain, pnum, psize) {
    if(getLiveListBlock){
        return;
    }
    getLiveListBlock = true;
    $.ajax({
        url: apiHost + "comment/bean/json/querybygroup",
        type: "post",
        data: {groupid: groupId, domain: domain, pnum: pnum, psize: psize},
        dataType: "jsonp",
        jsonpCallback: "querybygroupcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                alert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                alert('缺少参数~');
                return;
            } else if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null || result == undefined) {
                    return;
                }
                if (result.rows != null && result.rows != undefined) {
                    var html = '';
                    for (var i = 0; i < result.rows.length; i++) {
                        var expand = result.rows[i].expandstr;

                        var videoHtml = '';
                        if(expand.videoiframe.length > 0){
                            videoHtml = expand.videoiframe;
                        }else if (expand.video.length > 0) {
                            videoHtml = '<video controls="controls" poster="'+expand.video+'?vframe/jpg/offset/0"><source src="' + expand.video + '" type="video/mp4">你的浏览器不支持html5视频播放</video>';
                        } else {
                            if (result.rows[i].pic.length > 0) {
                                videoHtml = '<img src="' + result.rows[i].pic + '?imageView/2/w/640" alt="' + result.rows[i].pic + '"/>';
                            }
                        }

                        html += '<dl class="on" data-flag="' + (result.rows[i].createTime / 1000) + '">' +
                        '<dt>' +
                        '<cite><img src="' + expand.compereicon + '"></cite>' +
                        '</dt>' +
                        '<dd>' +
                        '<code></code>' +
                        '<font>'+result.rows[i].dateStr+'</font>' +
                        '<span>[' + expand.comperetype + ']' + expand.comperename + '</span>' +
                        '<p>' + result.rows[i].description + '</p>' +
                        '<em>' + videoHtml + '</em>' +
                        '</dd>' +
                        '</dl>';
                    }
                    $('#live_area').append(html);
                }
                var curPage = 1;
                var pageSize = 20;
                var maxPage = 1;
                if (result.page != null && result.page != undefined) {
                    curPage = result.page.curPage;
                    pageSize = result.page.pageSize;
                    maxPage = result.page.maxPage;
                }
                $('#live_more').attr('data-cp', curPage).attr('data-ps', pageSize);
                if(curPage < maxPage){
                    $('#live_more').show();
                }else{
                    $('#live_more').hide();
                }
            } else {
                alert(resMsg.msg);
            }
            return;
        },
        error: function () {
            alert('获取失败，请刷新');
            getLiveListBlock = false;
            return;
        },
        complete:function(){
            getLiveListBlock = false;
            return;
        }
    });
}