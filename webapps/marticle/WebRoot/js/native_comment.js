host = ".joyme." + marticle_host;
var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {//移动终端浏览器版本信息
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}
$(document).ready(function () {
    //加载热门评论
//    try {
//        $(".wp-comment").show();
//        var jsonParam = {
//            title: title,
//            pic: "",
//            description: "",
//            uri: uri
//        }
//        if (typeof(unikey) != "undefined" && typeof(domain) != "undefined") {
//            getCommentList(unikey, domain, jsonParam);
//        }
//    } catch (e) {
//    }


    //页面文章点赞
    $(".zan-btn").on('tap', function () {
        $(".zan-btn b").addClass("active");
        var archivid = window.localStorage.getItem("agree_" + unikey);
        //存在
        if (typeof (archivid) != "undefined" && archivid != null) {
            $(".tip").addClass("show");
            var timer = setTimeout(function () {
                clearTimeout(timer);
                $('.tip').removeClass('show');
            }, 1000);
            return false;
        }
        agreecheat();
    });

    //加载文章点赞按钮样式
    try {
        var isagree = window.localStorage.getItem("agree_" + unikey);
        if (typeof (isagree) != "undefined" && isagree != null) {
            $(".zan-btn b").addClass("active");
        }
        JParam = _jclient.getJParam();
    } catch (e) {
        JParam = "";
    }

    //相关文章点击
    try {
        getcheat();
        $(".re-article-main a").on('click', function () {
            _jclient.jump('jt=23&ji=' + $(this).attr("jump"));
        })
    } catch (e) {
    }

    //大图浏览
//    browsePicture($('.article-wrapper'));
    //大图浏览下载
    $(".large-load").on('tap', function () {
        try {

            if (browser.versions.ios) {
                _jclient.saveImage("src=" + encodeURIComponent($("#large-icon").attr("src")), function (response) {
                    if (response) {
                        $('.Threebox').show();
                        var threeboxTimer = setTimeout(function () {
                            clearTimeout(threeboxTimer);
                            $('.Threebox').hide()
                        }, 2000);
                    }
                });
            }
            if (browser.versions.android) {
                _jclient.saveImage("src=" + encodeURIComponent($("#large-icon").attr("src")));
            }

        } catch (e) {
        }
    })

    //分享
    $(".large-collect").on('tap', function () {
        try {
            _jclient.share("title=" + title + "&url=" + encodeURIComponent($("#_share_url").html()) + "&picurl=" + encodeURIComponent(clientpic) + "&content=" + $("#_desc").html());
        } catch (e) {
        }
    })
});

function browsePicture(parentbox) {
    var $imgs = parentbox.find('img');
    $imgs.on('tap', function () {
        try {
            _jclient.showPreview(0);
        } catch (e) {
        }
        var url = '';
        url = $(this).attr('src');
        $('.large-pic>span').html('<img id="large-icon" src="' + url + '" />')
        $('.large-box').show();
    });
    $('.large-pic').on('tap', function () {
        try {
            _jclient.showPreview(1);
        } catch (e) {
        }
        $('.large-box').hide();
    })
}
//加载热门评论
function getCommentList(unikey, domain, jsonparam) {
    $.ajax({
                url: "http://api" + host + "/jsoncomment/reply/query",
                type: "get",
                async: true,
                data: {unikey: unikey, domain: domain, jsonparam: JSON.stringify(jsonparam), pnum: 1, psize: 15, flag: 'hot'},
                dataType: "jsonp",
                jsonpCallback: "querycallback",
                success: function (req) {
                    var resMsg = req[0];
                    if (resMsg.rs == '1') {
                        var result = resMsg.result;
                        if (result.comment_sum > 0) {
                            $("#commentSum").text("已有" + result.comment_sum + "条评论");
                        }
                        if (result.hotlist.length > 0) {
                            var html = "";
                            for (var i = 0; i < result.hotlist.length; i++) {
                                if (i >= 2) {
                                    continue;
                                }
                                html += '<a class="module-list" style="display: block" href="' + jumpUrl + '">';
                                html += '<div class="module-infobox">';
                                html += '<cite class="user-headImg"><img src="' + result.hotlist[i].reply.user.icon + '"/></cite>';
                                html += '<div class="module-shop">';
                                html += '<div><em>' + result.hotlist[i].reply.user.name + '</em><span class="module-reply fr">回复</span><span class="module-zan fr">' + result.hotlist[i].reply.reply.agree_sum + '</span></div>';
                                html += '<div class="module-date"><span>' + result.hotlist[i].reply.reply.post_date + '</span></div>';
                                html += '</div>';
                                html += '</div>';
                                html += '<div class="module-txt">' + result.hotlist[i].reply.reply.body.text + '</div>';
                                html += '</a>';
                            }
                            $('#hot-comment').show();
                            $("#all-comment").html(html);
                        }
                    } else {
                        return;
                    }
                }
            });
}
//获取文章点赞数阅读数
function getcheat() {
    $.ajax({
                url: "http://api" + host + "/joymeapp/gameclient/webview/marticle/getcheat?archiveid=" + unikey + "&JParam=" + JParam,
                type: "get",
                dataType: "jsonp",
                jsonpCallback: "getcheatcallback",
                success: function (req) {
                    var resMsg = req[0];
                    if (resMsg.rs == '1') {
                        var result = resMsg.result;
                        if (result != "") {
                            var readNum = parseInt(result.read_num);
                            $("#readTop,#readBottom").html(readNum);
                            $("#agreeTop,#agreeBottom").html(result.agree_num);
                        } else {
                            $("#readTop,#readBottom").html(0);
                            $("#agreeTop,#agreeBottom").html(0);
                        }
                    } else {
                        return;
                    }
                }
            });
}
//文章点赞动作
function agreecheat() {
    var agreenum = parseInt($("#agreeTop").html());
    $("#agreeTop,#agreeBottom").html(agreenum + 1);
    $.ajax({
                url: "http://api" + host + "/joymeapp/gameclient/webview/marticle/agreecheat?archiveid=" + unikey + "&JParam=" + JParam,
                type: "get",
                async: false,
                dataType: "jsonp",
                jsonpCallback: "agreecheatcallback",
                success: function (req) {
                    var resMsg = req[0];
                    if (resMsg.rs == '1') {
                        window.localStorage.setItem("agree_" + unikey, unikey);
                    }
                }
            });
}
function getOffset(Node, offset) {
    if (!offset) {
        offset = {};
        offset.top = 0;
        offset.left = 0;
    }
    if (Node == document.body) {//当该节点为body节点时，结束递归
        return offset;
    }
    offset.top += Node.offsetTop;
    offset.left += Node.offsetLeft;
    return getOffset(Node.parentNode, offset);//向上累加offset里的值
}