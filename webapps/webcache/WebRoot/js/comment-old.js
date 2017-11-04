var uno = getCookie('jmuc_uno');
var uid = getCookie('jmuc_u');
var token = getCookie('jmuc_token');
var sign = getCookie('jmuc_s');
var login = getCookie('jmuc_lgdomain');
var pid = getCookie('jmuc_pid');

//uno uid token sign login pid

var hostname = window.location.hostname;
var wwwHost = 'http://www' + hostname.substring(hostname.indexOf('.'), hostname.length) + '/';
var apiHost = 'http://api' + hostname.substring(hostname.indexOf('.'), hostname.length) + '/';
var passportHost = 'http://passport' + hostname.substring(hostname.indexOf('.'), hostname.length) + '/';

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
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
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

var domain = 2;

var uniKey = '';
var path = window.location.pathname;
if (uri.indexOf('http://') == 0 || uri.indexOf('https://') == 0) {
    var number = '';
    var arr = path.split('/');
    for(var i=0;i<arr.length;i++){
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
        uniKey = number.substring(8, number.length);
    }
}

var title = $('title').eq(0).text();
if (title.indexOf("_") > 0) {
    title = title.substring(0, title.indexOf("_"));
}

if (uniKey.length > 0) {
    if (bs.versions.mobile) {
        if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.ios) {
            $(document).ready(function () {
                var jsonParam = {
                    title: title,
                    pic: "",
                    description: "",
                    uri: uri
                }
                getMCommentList(uniKey, domain, jsonParam);
            });
        }
    } else {
        $(document).ready(function () {
            initPostMask();
            var jsonParam = {
                title: title,
                pic: "",
                description: "",
                uri: uri
            }

            getCommentList(uniKey, domain, jsonParam);

            //发表评论
            $('#comment_submit').click(function () {
                if (window.uno == null || window.uid == null) {
                    alert('请先保存您的内容，登录之后再回来~');
                    return;
                }

                var text = $('#textarea_body').val();
                if (text == null || text.length == 0 || text == '我来说两句…') {
                    $('#reply_error').html('评论内容不能为空');
                    return false;
                }
                if (getStrlen(text) > 300) {
                    $('#reply_error').html('评论内容长度不能超过300字符!');
                    return false;
                }
                //全角
                //text = ToDBC(text);

                var body = {
                    text: text,
                    pic: ""
                }
                postComment(uniKey, domain, body, 0, 0);
            });

            //点赞
            $(document).on('click', 'a[id^=agreelink_]', function () {
                if (window.uno == null || window.uid == null) {
                    alert('请先保存您的内容，登录之后再回来~');
                    return;
                }

                var rid = $(this).attr('data-commentid');
                agreeComment(uniKey, domain, rid);
            });

            //点赞
            $(document).on('click', 'a[name=agree_num]', function () {
                if (window.uno == null || window.uid == null) {
                    alert('请先保存您的内容，登录之后再回来~');
                    return;
                }

                var replyid = $(this).attr('data-commentid');
                agreeComment(uniKey, domain, replyid);
            });

            //回复遮罩
            $(document).on('click', 'a[name=togglechildrenreply_area]', function () {
                if ($(this).hasClass('putaway')) {
                    $(this).parent().next().fadeOut();
                    var replyNum = parseInt($(this).attr('data-replynum'));
                    var html = '回复';
                    if (replyNum > 0) {
                        html += '(' + replyNum + ')'
                    }
                    $(this).html(html).removeClass();
                } else {
                    $(this).parent().next().fadeIn();
                    $(this).html('收起回复').attr('class', 'putaway');
                }
            });

            //回复
            $(document).on('click', 'a[name=replypost_mask]', function () {
                $(this).hide();
                var obj = $(this).next();
                obj.show();
                var textAreaObj = obj.find('textarea[id^=textarea_recomment_body_]');
                var body = textAreaObj.val();
                textAreaObj.val("").focus().val(body);
                textAreaObj.AutoHeight({maxHeight: 200});
            });

            $(document).on('click', 'a[name=link_recommentparent_mask]', function () {
                var oid = $(this).attr('data-oid');
                var pid = $(this).attr('data-pid');
                var nickname = $(this).attr('data-nick');
                var postCommentArea = $('#post_recomment_area_' + oid);
                var replyMaskLink = postCommentArea.find('a[name=replypost_mask]');
                var submitReComment = postCommentArea.find('a[name=submit_recomment]');
                submitReComment.attr("data-pid", pid);
                submitReComment.attr("data-nick", nickname);
                $('#textarea_recomment_body_' + oid).val('@' + nickname + ':');
                replyMaskLink.click();
            });

            //回复
            $(document).on('click', 'a[name=submit_recomment]', function () {
                if (window.uno == null || window.uid == null) {
                    alert('请先保存您的内容，登录之后再回来~');
                    return;
                }

                var oid = $(this).attr('data-oid');
                var text = $('#textarea_recomment_body_' + oid).val();
                var submitReComment = $('#post_recomment_area_' + oid).find('a[name=submit_recomment]');
                if (submitReComment.length > 0) {
                    var pname = submitReComment.attr('data-nick');
                    text = text.replace('@' + pname + ":", '');
                }
                if (text == null || text.length == 0 || text == '我来说两句…') {
                    $('#reply_error').html('评论内容不能为空');
                    return false;
                }
                if (getStrlen(text) > 300) {
                    $('#reply_error').html('评论内容长度不能超过300字符!');
                    return false;
                }

                //全角
                //text = ToDBC(text);

                var body = {
                    text: text,
                    pic: ""
                }
                var pid = $(this).attr('data-pid');
                if (pid == null || pid.length <= 0) {
                    pid = 0;
                }
                postSubComment(uniKey, domain, body, oid, pid, $(this));
            });

            //删除
            $(document).on('click', 'a[name=remove_comment]', function () {
                if (window.uno == null || window.uid == null) {
                    alert('请先保存您的内容，登录之后再回来~');
                    return;
                }

                var rid = $(this).attr('data-rid');
                var oid = $(this).attr('data-oid');
                if (oid == null || oid.length <= 0) {
                    oid = 0;
                }
                if (confirm('确定要删除吗？')) {
                    removeComment(uniKey, domain, rid, oid);
                }
            });

            $('#textarea_body').bind('focusin',
                function () {
                    var val = $(this).val();
                    if (val == '我来说两句…') {
                        $(this).val('').css('color', '#333');
                    }
                }).bind('focusout', function () {
                    var val = $(this).val();
                    if (val == null || val == '') {
                        $(this).val('我来说两句…').css('color', '#ccc');
                    }
                });
        });
    }
}

function toPostComment(dom) {
    if (window.uno == null || window.uid == null) {
        alert('请先保存您的内容，登录之后再回来~');
        return false;
    }

    var text = $('#textarea_body').val();
    if (text == null || text.length == 0 || text == '我来说两句…') {
        $('#reply_error').html('评论内容不能为空');
        return false;
    }
    if (getStrlen(text) > 300) {
        $('#reply_error').html('评论内容长度不能超过300字符!');
        return false;
    }
    //全角
    //text = ToDBC(text);

    var body = {
        text: text,
        pic: ""
    }
    postComment(uniKey, domain, body, 0, 0);
}

function getCommentList(unikey, domain, jsonparam) {
    $.ajax({
        url: window.api + "jsoncomment/reply/query",
        type: "post",
        data: {unikey: unikey, domain: domain, jsonparam: JSON.stringify(jsonparam), pnum: 1, psize: 10},
        dataType: "jsonp",
        jsonpCallback: "querycallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null) {
                    $('#comment_list_area').html('<p style="height:30px; line-height:30px; text-align:center">目前没有评论，欢迎你发表评论</p>');
                    return;
                }

                var replyNum = '<a href="' + window.www + 'comment/reply/page?unikey=' + unikey + '&domain=' + domain + '&pnum=1&psize=10"><span>' + result.comment_sum + '条评论</span></a>'
                $('#reply_num').html(replyNum);

                if (result.mainreplys == null || result.mainreplys.rows == null || result.mainreplys.rows.length == 0) {
                    if (result.mainreplys.page != null && result.mainreplys.page.maxPage > 1) {
                        $('#comment_list_area').html('<p style="height:30px; line-height:30px; text-align:center">当前页的评论已经被删除~</p><div class="more-comment"><a href="' + window.www + 'comment/reply/page?unikey=' + unikey + '&domain=' + domain + '&pnum=1&psize=10">更多评论&gt;&gt;</a></div>');
                    } else {
                        $('#comment_list_area').html('<p style="height:30px; line-height:30px; text-align:center">目前没有评论，欢迎你发表评论</p>');
                    }

                } else {
                    var html = '';
                    for (var i = 0; i < result.mainreplys.rows.length; i++) {
                        html += getCommentListCallBack(result.mainreplys.rows[i], unikey, domain);
                    }
                    var moreLink = '<div class="more-comment"><a href="' + window.www + 'comment/reply/page?unikey=' + unikey + '&domain=' + domain + '&pnum=1&psize=10">更多评论&gt;&gt;</a></div>';
                    html += moreLink;
                    $('#comment_list_area').html(html);
                }
            } else {
                $('#reply_error').html(resMsg.msg);
                return;
            }
        }
    });
}

function getMCommentList(unikey, domain, jsonparam) {
    $.ajax({
        url: apiHost + "jsoncomment/reply/mlist",
        type: "post",
        data: {unikey: unikey, domain: domain, jsonparam: JSON.stringify(jsonparam), pnum: 1, psize: 10},
        dataType: "jsonp",
        jsonpCallback: "getmcommentlistcallback",
        success: function (req) {
            var resMsg = req[0];

            var toCommentHtml = '<a href="' + wwwHost + '/comment/reply/mpage?unikey=' + uniKey + '&domain=' + domain + '&flag=post"><cite>发表评论...</cite></a>';
            var commentListHtml = '';
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null || result.rows == null || result.rows.length == 0) {
                    commentListHtml = '<p style="height:2.5rem; line-height:2.5rem; text-align:center">目前没有评论，欢迎你发表评论</p>';
                }else{
                    for (var i = 0; i < result.rows.length; i++) {
                        commentListHtml += getMCommentHtml(result.rows[i], unikey, domain);
                    }
                }
            } else {
                commentListHtml = '<p style="height:2.5rem; line-height:2.5rem; text-align:center">' + resMsg.msg + '</p>';
            }
            
            var html = '<div class="tj-pl">' +
                '<h3>评论</h3>' +
                '<a href="' + wwwHost + '/comment/reply/mpage?unikey=' + uniKey + '&domain=' + domain + '" class="all-comment">查看评论</a>' +
                '<div class="cmnt-list">' +
                commentListHtml +
                '</div>' +
                '<div class="cmnt-column border-t">' +
                toCommentHtml +
                '</div>' +
                '</div>';
            $('#comment_area_old').after(html);
            $('#comment_area_old').remove();
        }
    });
}

function postComment(unikey, domain, body, oid, pid) {
    $.ajax({
        url: window.api + "jsoncomment/reply/post",
        type: "post",
        data: {unikey: unikey, domain: domain, body: JSON.stringify(body), oid: oid, pid: pid},
        dataType: "jsonp",
        jsonpCallback: "postcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                $('#reply_error').html(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                $('#reply_error').html('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-10102') {
                $('#reply_error').html('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-100') {
                $('#reply_error').html('app不存在~');
                return;
            } else if (resMsg.rs == '-10104') {
                $('#reply_error').html('用户不存在~');
                return;
            } else if (resMsg.rs == '-40011') {
                $('#reply_error').html('评论不存在~');
                return;
            } else if (resMsg.rs == '-40012') {
                $('#reply_error').html('评论不存在~');
                return;
            } else if (resMsg.rs == '-40000') {
                $('#reply_error').html('您的请求缺少unikey参数~');
                return;
            } else if (resMsg.rs == '-40001') {
                $('#reply_error').html('您的请求缺少domain参数~');
                return;
            } else if (resMsg.rs == '-40002') {
                $('#reply_error').html('您的请求缺少jsonparam参数~');
                return;
            } else if (resMsg.rs == '-40003') {
                $('#reply_error').html('您的请求中jsonparam参数格式错误~');
                return;
            } else if (resMsg.rs == '-40013') {
                $('#reply_error').html('您的请求中domain参数错误~');
                return;
            } else if (resMsg.rs == '-40005') {
                $('#reply_error').html('评论内容未填写~');
                return;
            } else if (resMsg.rs == '-40008') {
                $('#reply_error').html('评论对象不存在~');
                return;
            } else if (resMsg.rs == '-40006') {
                $('#reply_error').html('oid参数错误~');
                return;
            } else if (resMsg.rs == '-40009') {
                $('#reply_error').html('主楼评论已删除~');
                return;
            } else if (resMsg.rs == '-40007') {
                $('#reply_error').html('pid参数错误~');
                return;
            } else if (resMsg.rs == '-40010') {
                $('#reply_error').html('上级回复已删除~');
                return;
            } else if (resMsg.rs == '-40016') {
                $('#reply_error').html('您已经赞过了~');
                return;
            } else if (resMsg.rs == '-40017') {
                $('#reply_error').html('内容含有敏感词：' + resMsg.result[0]);
                return;
            } else if (resMsg.rs == '-40018') {
                $('#reply_error').html('评论不存在~');
                return;
            } else if (resMsg.rs == '-40019') {
                $('#reply_error').html('您已被禁言~');
                return;
            } else if (resMsg.rs == '-40020') {
                $('#reply_error').html('一分钟内不能重复发送相同的内容~');
                return;
            } else if (resMsg.rs == '-1') {
                $('#reply_error').html('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null) {
                    return;
                }

                var oid = result.reply.oid;
                var rid = result.reply.rid;
                if (oid == 0) {
                    var spanHtml = '条评论';
                    var numHtml = $('#reply_num a span').text();
                    var numStr = numHtml.replace(spanHtml, '');
                    var num = parseInt(numStr) + 1;
                    $('#reply_num a span').html(num + spanHtml);
                    $('#cont_cmt_list_' + rid).remove();
                } else {
                    var numStr = $('#togglechildrenreply_area_' + oid).attr('data-replynum');
                    $('#togglechildrenreply_area_' + oid).attr('data-replynum', parseInt(numStr) + 1);
                    $('#cont_recmt_list_' + rid).remove();
                }

                var commentHtml = '<div id="cont_cmt_list_' + result.reply.rid + '" class="area blogopinion clearfix ">' +
                    '<dl>' +
                    '<dt class="personface">' +
                    '<a title="' + result.user.name + '" name="atLink" href="javascript:void(0);">' +
                    '<img width="58" height="58" class="user" src="' + result.user.icon + '"></a>' +
                    '</dt>' +
                    '<dd class="textcon discuss_building">' +
                    '<a title="' + result.user.name + '" class="author" name="atLink" href="javascript:void(0);">' + result.user.name + '</a><code>'+result.reply.floor_num+'楼</code>' +
                    '<p>' + result.reply.body.text + '</p>' +
                    '<div class="discuss_bdfoot">' + result.reply.post_date + '&nbsp;' +
                    '<a href="javascript:void(0);" id="agreelink_' + result.reply.rid + '" data-commentid="' + result.reply.rid + '" class="dianzan"></a>' +
                    '<span id="agreenum_' + result.reply.rid + '">' +
                    '<a href="javascript:void(0);" name="agree_num" data-commentid="' + result.reply.rid + '"></a>' +
                    '</span><a name="remove_comment" href="javascript:void(0);" data-oid="0" data-rid="' + result.reply.rid + '">删除</a>' +
                    '<a name="togglechildrenreply_area" href="javascript:void(0);" id="togglechildrenreply_area_' + result.reply.rid + '" data-replynum="0">回复</a>' +
                    '</div>' +
                    '<div class="discuss_bd_list discuss_border" style="display:none">' +
                    '<div id="children_reply_list_' + result.reply.rid + '"></div>' +
                    '<div id="post_recomment_area_' + result.reply.rid + '" class="discuss_reply">' +
                    '<a class="discuss_text01" href="javascript:void(0);" name="replypost_mask">我也说一句</a>' +
                    '<div style="display:none" class="discuss_reply reply_box01">' +
                    '<textarea warp="off" style="font-family:Tahoma, ' + "宋体" + ';" id="textarea_recomment_body_' + result.reply.rid + '" class="discuss_text focus" rows="" cols="" name="content"></textarea>' +
                    '<div class="related clearfix">' +
                    '<div class="transmit clearfix">' +
                    '<a class="submitbtn fr" name="submit_recomment" data-oid="' + result.reply.rid + '">' +
                    '<span name="span_pinglun">评 论</span></a></div></div></div></div></div></dd></dl></div>';

                if ($('#comment_list_area div.area').length > 0) {
                    $('#comment_list_area div:first').before(commentHtml);
                } else {
                    var moreHtml = '<div class="more-comment"><a href="' + window.www + 'comment/reply/page?unikey=' + unikey + '&domain=' + domain + '&pnum=1&psize=10">更多评论&gt;&gt;</a></div>';
                    $('#comment_list_area').html(commentHtml + moreHtml);
                }
                $("#textarea_body").val('我来说两句…').css('color', '#ccc');
                $('#reply_error').html('');
            } else {
                $('#reply_error').html(resMsg.msg);
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}

function postSubComment(unikey, domain, body, oid, pid, reCommentDom) {
    $.ajax({
        url: window.api + "jsoncomment/reply/post",
        type: "post",
        data: {unikey: unikey, domain: domain, body: JSON.stringify(body), oid: oid, pid: pid},
        dataType: "jsonp",
        jsonpCallback: "postcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">' + resMsg.msg + '</span>');
                return;
            } else if (resMsg.rs == '-1001') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">请先保存您的内容，登录之后再回来~</span>');
                return;
            } else if (resMsg.rs == '-10102') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">请先保存您的内容，登录之后再回来~</span>');
                return;
            } else if (resMsg.rs == '-100') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">app不存在~</span>');
                return;
            } else if (resMsg.rs == '-10104') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">用户不存在~</span>');
                return;
            } else if (resMsg.rs == '-40011') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">评论不存在~</span>');
                return;
            } else if (resMsg.rs == '-40012') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">评论不存在~</span>');
                return;
            } else if (resMsg.rs == '-40000') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您的请求缺少unikey参数~</span>');
                return;
            } else if (resMsg.rs == '-40001') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您的请求缺少domain参数~</span>');
                return;
            } else if (resMsg.rs == '-40002') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您的请求缺少jsonparam参数~</span>');
                return;
            } else if (resMsg.rs == '-40003') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您的请求中jsonparam参数格式错误~</span>');
                return;
            } else if (resMsg.rs == '-40013') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您的请求中domain参数错误~</span>');
                return;
            } else if (resMsg.rs == '-40005') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">评论内容未填写~</span>');
                return;
            } else if (resMsg.rs == '-40008') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">评论对象不存在~</span>');
                return;
            } else if (resMsg.rs == '-40006') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">oid参数错误~</span>');
                return;
            } else if (resMsg.rs == '-40009') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">主楼评论已删除~</span>');
                return;
            } else if (resMsg.rs == '-40007') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">pid参数错误~</span>');
                return;
            } else if (resMsg.rs == '-40010') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">上级回复已删除~</span>');
                return;
            } else if (resMsg.rs == '-40016') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您已经赞过了~</span>');
                return;
            } else if (resMsg.rs == '-40017') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">内容含有敏感词：' + resMsg.result[0] + '</span>');
                return;
            } else if (resMsg.rs == '-40018') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">评论不存在~</span>');
                return;
            } else if (resMsg.rs == '-40019') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">您已被禁言~</span>');
                return;
            } else if (resMsg.rs == '-40020') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">一分钟内不能重复发送相同的内容~</span>');
                return;
            } else if (resMsg.rs == '-1') {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">请先保存您的内容，登录之后再回来~</span>');
                return;
            } else if (resMsg.rs == '1') {
                var result = resMsg.result;
                if (result == null) {
                    return;
                }
                var html = getReCommentHtml(result);
                $('#children_reply_list_' + oid).find('p').remove();
                var moreDom = $('#children_reply_list_' + oid).find('div.more-comment');
                if (moreDom == null || moreDom.length == 0 || moreDom.html().length == 0) {
                    $('#children_reply_list_' + oid).append(html);
                } else {
                    moreDom.before(html);
                }

                var num = parseInt($('#togglechildrenreply_area_' + oid).attr('data-replynum'));
                $('#togglechildrenreply_area_' + oid).attr('data-replynum', (num + 1));
                $('#textarea_recomment_body_' + oid).val("");
                $('#post_callback_msg_' + oid).remove();
            } else {
                $('#post_callback_msg_' + oid).remove();
                reCommentDom.before('<span style="color: #f00; padding-top: 10px;margin-top:10px;padding-left: 10px;" id="post_callback_msg_' + oid + '">' + resMsg.msg + '</span>');
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}

function agreeComment(unikey, domain, rid) {
    $.ajax({
        url: window.api + "jsoncomment/reply/agree",
        type: "post",
        data: {unikey: unikey, domain: domain, rid: rid},
        dataType: "jsonp",
        jsonpCallback: "agreecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                alert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-10102') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-100') {
                alert('app不存在~');
                return;
            } else if (resMsg.rs == '-10104') {
                alert('用户不存在~');
                return;
            } else if (resMsg.rs == '-40011') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-40012') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-40000') {
                alert('您的请求缺少unikey参数~');
                return;
            } else if (resMsg.rs == '-40001') {
                alert('您的请求缺少domain参数~');
                return;
            } else if (resMsg.rs == '-40002') {
                alert('您的请求缺少jsonparam参数~');
                return;
            } else if (resMsg.rs == '-40003') {
                alert('您的请求中jsonparam参数格式错误~')
                return;
            } else if (resMsg.rs == '-40013') {
                alert('您的请求中domain参数错误~')
                return;
            } else if (resMsg.rs == '-40005') {
                alert('评论内容未填写~')
                return;
            } else if (resMsg.rs == '-40008') {
                alert('评论对象不存在~');
                return;
            } else if (resMsg.rs == '-40006') {
                alert('oid参数错误~');
                return;
            } else if (resMsg.rs == '-40009') {
                alert('主楼评论已删除~');
                return;
            } else if (resMsg.rs == '-40007') {
                alert('pid参数错误~');
                return;
            } else if (resMsg.rs == '-40010') {
                alert('上级回复已删除~');
                return;
            } else if (resMsg.rs == '-40016') {
                alert('您已经赞过了~');
                return;
            } else if (resMsg.rs == '-40017') {
                alert('内容含有敏感词：' + resMsg.result[0]);
                return;
            } else if (resMsg.rs == '-40018') {
                alert('评论不存在~');
                return;
            } else if (resMsg.rs == '-40019') {
                alert('您已被禁言~');
                return;
            } else if (resMsg.rs == '-1') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '1') {
                var numObj = $('a[name=agree_num][data-commentid=' + rid + ']');
                var objStr = numObj.html();
                var num;
                if (numObj.length == 0 || objStr == null || objStr.length == 0) {
                    num = parseInt(1);
                } else {
                    var num = numObj.html().replace("(", '').replace(')', '');
                    num = parseInt(num);
                    num = num + 1;
                }
                numObj.html('(' + num + ')');
            } else {
                alert(resMsg.msg);
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}

function initPostMask() {
    var textareaHtml = '';
    //uno uid token sign login pid
    if (uno == null || uno.length == 0 || uid == null || uid.length == 0 || token == null || token.length == 0 || sign == null || sign.length == 0 || login == null || login.length == 0 || pid == null || pid.length == 0 || login == 'client') {
       textareaHtml += '<textarea warp="off" style="font-family:Tahoma, 宋体;" class="talk_text" id="textarea_body" name="body" disabled="disabled"></textarea>' +
       '<div class="wrapper_unlogin" style="text-align:center; margin:-65px 0 65px">您需要<a id="login_link" href="' + passportHost + 'auth/loginpage'+'">登录</a>后才能评论</div>';

    } else {
        textareaHtml += '<textarea warp="off" style="font-family:Tahoma, 宋体;color: #ccc" class="talk_text" id="textarea_body" name="body" disabled="disabled">我来说两句…</textarea>'
    }

    var html = '<div id="post_reply" class="blog_comment">' +
        '<div class="clearfix" style="padding:10px 6px 4px">' +
        '<span class="fl">评论</span>' +
        '<span class="fr" id="reply_num"></span>' +
        '</div>' +
        '<form action="" method="post" id="post_comment">' +
        '<div class="talk_wrapper clearfix">' +
        '<input type="hidden" id="hidden_unikey" value="'+uniKey+'">' +
        '<input type="hidden" id="hidden_domain" value="'+domain+'">' +
        '<input type="hidden" id="hidden_uri" value="'+uri+'"/>' +
        '<input type="hidden" id="hidden_title" value="'+title+'"/>' +
        textareaHtml +
        '</div>' +
        '<div class="related clearfix">' +
        '<div class="transmit_pic clearfix">' +
        '<a class="submitbtn fr" id="comment_submit" href="javascript:void(0);" onclick="toPostComment(this)"><span>评 论</span></a>' +
        '<span style="color: #f00; padding-top: 2px; float: right;margin-top: 6px;margin-right: 4px;" id="reply_error"></span>' +
        '</div>' +
        '</div>' +
        '</form>' +
        '</div>' +
        '<a id="ap_comment" name="ap_comment"></a>' +
        '<div id="comment_list_area">' +
        '</div>';
    $('#comment_area_old').after(html);
    $('#comment_area_old').remove();
}

function removeComment(unikey, domain, rid, oid) {
    $.ajax({
        url: window.api + "jsoncomment/reply/remove",
        type: "post",
        data: {unikey: unikey, domain: domain, rid: rid},
        dataType: "jsonp",
        jsonpCallback: "removecallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '0') {
                alert(resMsg.msg);
                return;
            } else if (resMsg.rs == '-1001') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-100') {
                alert('app不存在~');
                return;
            } else if (resMsg.rs == '-10104') {
                alert('用户不存在~');
                return;
            } else if (resMsg.rs == '-40011') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-40012') {
                alert('评论不存在~~');
                return;
            } else if (resMsg.rs == '-10102') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '-40000') {
                alert('您的请求缺少unikey参数~');
                return;
            } else if (resMsg.rs == '-40001') {
                alert('您的请求缺少domain参数~');
                return;
            } else if (resMsg.rs == '-40002') {
                alert('您的请求缺少jsonparam参数~');
                return;
            } else if (resMsg.rs == '-40003') {
                alert('您的请求中jsonparam参数格式错误~')
                return;
            } else if (resMsg.rs == '-40013') {
                alert('您的请求中domain参数错误~')
                return;
            } else if (resMsg.rs == '-40005') {
                alert('评论内容未填写~')
                return;
            } else if (resMsg.rs == '-40008') {
                alert('评论对象不存在~');
                return;
            } else if (resMsg.rs == '-40006') {
                alert('oid参数错误~');
                return;
            } else if (resMsg.rs == '-40009') {
                alert('主楼评论已删除~');
                return;
            } else if (resMsg.rs == '-40007') {
                alert('pid参数错误~');
                return;
            } else if (resMsg.rs == '-40010') {
                alert('上级回复已删除~');
                return;
            } else if (resMsg.rs == '-40016') {
                alert('您已经赞过了~');
                return;
            } else if (resMsg.rs == '-40017') {
                alert('内容含有敏感词：' + resMsg.result[0]);
                return;
            } else if (resMsg.rs == '-40018') {
                alert('评论不存在~');
                return;
            } else if (resMsg.rs == '-40019') {
                alert('您已被禁言~');
                return;
            } else if (resMsg.rs == '-1') {
                alert('请先保存您的内容，登录之后再回来~');
                return;
            } else if (resMsg.rs == '1') {
                if (oid == 0) {
                    var spanHtml = '条评论';
                    var numHtml = $('#reply_num a span').text();
                    var numStr = numHtml.replace(spanHtml, '');
                    var num = parseInt(numStr) - 1;
                    $('#reply_num a span').html(num + spanHtml);
                    $('#cont_cmt_list_' + rid).remove();
                } else {
                    var numStr = $('#togglechildrenreply_area_' + oid).attr('data-replynum');
                    $('#togglechildrenreply_area_' + oid).attr('data-replynum', parseInt(numStr) - 1);
                    $('#cont_recmt_list_' + rid).remove();
                }
            } else {
                alert(resMsg.msg);
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}


function getCommentListCallBack(commentObj, unikey, domain) {
    var reply = commentObj.reply;
    var reReplyArray = null;
    if (commentObj.subreplys != null) {
        reReplyArray = commentObj.subreplys.rows;
    }

    var hasRe = (reReplyArray != null && reReplyArray.length > 0);

    var reCommentListHtml = '<div id="children_reply_list_' + reply.reply.rid + '">';
    if (hasRe) {
        for (var i = 0; i < reReplyArray.length; i++) {
            var reCommentObj = reReplyArray[i];
            reCommentListHtml += getReCommentHtml(reCommentObj);
        }
        if (commentObj.subreplys.page != null && commentObj.subreplys.page.maxPage > 1) {
            reCommentListHtml += '<div class="more-comment"><a href="' + window.www + 'comment/reply/page?unikey=' + unikey + '&domain=' + domain + '&pnum=1&psize=10">更多回复&gt;&gt;</a></div>';
        }

    } else {
        if (commentObj.subreplys != null && commentObj.subreplys.page != null && commentObj.subreplys.page.maxPage > 1) {
            reCommentListHtml += '<p style="text-align:center">当前页的评论已经被删除~</p><div class="more-comment"><a href="' + window.www + 'comment/reply/page?unikey=' + uniKey + '&domain=' + domain + '&pnum=1&psize=10">更多回复&gt;&gt;</a></div>';
        }
    }
    reCommentListHtml += '</div>';

    var removeHtml = '';
    if (window.uno != null && window.uid != null && window.uno == reply.user.uno && window.uid == reply.user.uid) {
        removeHtml = '<a name="remove_comment" href="javascript:void(0);" data-oid="' + reply.reply.oid + '" data-rid="' + reply.reply.rid + '" >删除</a>'
    }

    var toogleReHtml = '<a name="togglechildrenreply_area" href="javascript:void(0);" id="togglechildrenreply_area_' + reply.reply.rid + '" data-replynum="' + reply.reply.sub_reply_sum + '">回复' + (reply.reply.sub_reply_sum > 0 ? ('(' + reply.reply.sub_reply_sum + ')') : '') + '</a>';

    var postReCommentHtml = '<div id="post_recomment_area_' + reply.reply.rid + '" class="discuss_reply">' +
        ' <a class="discuss_text01" href="javascript:void(0);" name="replypost_mask">我也说一句</a>' +
        '<div style="display:none" class="discuss_reply reply_box01">' +
        '<textarea warp="off" style="font-family:Tahoma, \'宋体\';" id="textarea_recomment_body_' + reply.reply.rid + '" class="discuss_text focus" rows="" cols="" name="content"></textarea>' +
        '<div class="related clearfix">' +
        '<div class="transmit clearfix">' +
        ' <a class="submitbtn fr" name="submit_recomment"  data-oid="' + reply.reply.rid + '" name="childreply_submit"><span name="span_pinglun">评 论</span></a>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

    var puserHtml = '';
    if (reply.puser != null && reply.puser.name != null) {
        puserHtml = '@' + reply.puser.name + ":";
    }
    var html = '<div id="cont_cmt_list_' + reply.reply.rid + '" class="area blogopinion clearfix ">' +
        '<dl>' +
        '<dt class="personface">' +
        '<a title="' + reply.user.name + '" name="atLink" href="javascript:void(0);">' +
        '<img width="58" height="58" class="user" src="' + reply.user.icon + '">' +
        '</a>' +
        '</dt>' +
        '<dd class="textcon discuss_building">' +
        '<a title="' + reply.user.name + '" class="author" name="atLink" href="javascript:void(0);">' + reply.user.name + '</a><code>'+result.reply.floor_num+'楼</code>'+
        '<p>' + puserHtml + reply.reply.body.text + '</p>' +
        '<div class="discuss_bdfoot">' + reply.reply.post_date + '&nbsp;<a href="javascript:void(0);" id="agreelink_' + reply.reply.rid + '" data-commentid="' + reply.reply.rid + '" class="dianzan"></a><span id="agreenum_' + reply.reply.rid + '"><a href="javascript:void(0);" name="agree_num" data-commentid="' + reply.reply.rid + '">' + ((reply.reply.agree_sum == null || reply.reply.agree_sum == 0) ? '' : ('(' + reply.reply.agree_sum + ')')) + '</a></span>' + removeHtml + toogleReHtml + '</div>' +
        '<div class="discuss_bd_list discuss_border" style="display:none"> ' +
        reCommentListHtml +
        postReCommentHtml +
        '</div> ' +
        '</dd>' +
        '</dl>' +
        '</div>';
    return html;
}

function getMCommentHtml(commentObj, unikey, domain) {
    var atHtml = '';
    if (commentObj.puser != null) {
        atHtml += '<a href="javascript:void(0);">@' + commentObj.puser.name + ':</a>';
    }

    var html = '<div class="cmnt-box">' +
        '<dl>' +
        '<dt>' +
        '<span class="fn-clear">' +
        '<a href="javascript:void(0);" class="userhead fn-left">' +
        '<img class="lazy" src="' + commentObj.user.icon + '" alt="" title="" width="100%">' +
        '</a>' +
        '<font class="fn-left">' +
        '<a href="javascript:void(0);" class="userName">' + commentObj.user.name + '</a><code>' + commentObj.reply.post_date + '</code>' +
        '</font>' +
        '</span>' +
        '<cite class="cmnt-reply fn-right" onclick="toJump('+commentObj.reply.rid+')">回复</cite>' +
        '</dt>' +
        '<dd>' +
        '<span class="border-b">' +
        atHtml + commentObj.reply.body.text +
        '</span>' +
        '</dd>' +
        '</dl>' +
        '</div>';
    return html;
}

function toJump(pid){
    window.location.href = wwwHost + '/comment/reply/mpage?unikey=' + uniKey + '&domain=' + domain + '&pid='+pid;
}

function getReCommentHtml(reCommentObj) {
    var removeHtml = '';
    if (window.uno != null && window.uid != null && window.uno == reCommentObj.user.uno && window.uid == reCommentObj.user.uid) {
        removeHtml = '<a name="remove_comment" href="javascript:void(0);" data-rid="' + reCommentObj.reply.rid + '" data-oid="' + reCommentObj.reply.oid + '">删除</a>'
    }

    var puserHtml = '';
    if (reCommentObj.puser != null && reCommentObj.puser.name != null) {
        puserHtml = '@' + reCommentObj.puser.name + ":";
    }

    var reCommentHtml = '<div style="" id="cont_recmt_list_' + reCommentObj.reply.rid + '" class="conmenttx clearfix">' +
        '<div class="conmentface">' +
        '<div class="commenfacecon">' +
        '<a href="javascript:void(0);" title="' + reCommentObj.user.name + '" name="atLink" class="cont_cl_left">' +
        '<img width="33px" height="33px" src="' + reCommentObj.user.icon + '">' +
        '</a>' +
        '</div>' +
        '</div>' +
        '<div class="conmentcon">' +
        '<a title="' + reCommentObj.user.name + '" name="atLink" href="javascript:void(0);">' + reCommentObj.user.name + '</a>' +
        ':' + puserHtml + reCommentObj.reply.body.text + '<div class="commontft clearfix"><span class="reply_time">' + reCommentObj.reply.post_date + '</span>' +
        '<span class="delete">' +
        '<a href="javascript:void(0);" id="agreelink_' + reCommentObj.reply.rid + '" data-commentid="' + reCommentObj.reply.rid + '" class="dianzan"></a>' +
        '<span id="agreenum_' + reCommentObj.reply.rid + '"><a href="javascript:void(0);" name="agree_num" data-commentid="' + reCommentObj.reply.rid + '">' + ((reCommentObj.reply.agree_sum == null || reCommentObj.reply.agree_sum == 0) ? '' : ('(' + reCommentObj.reply.agree_sum + ')')) + '</a></span>' +
        removeHtml +
        '<a href="javascript:void(0);"  name="link_recommentparent_mask" data-nick="' + reCommentObj.user.name + '" data-oid="' + reCommentObj.reply.oid + '" data-pid="' + reCommentObj.reply.rid + '">回复</a>' +
        '</span>' +
        '</div>' +
        '</div>' +
        '</div>';
    return reCommentHtml;
}

var errorArray = {
    'comment.post.limit': '您说话的频率太快了，请歇一歇哦~',
    'user.has.agree': '你已经支持过了',
    'comment.body.empty': '评论内容不能为空',
    'content.isnot.exists': '文章不存在！',
    'reply.not.exist': '该评论已经被删除，无法回复！',
    'comment.body.illegl': '评论中含有不适当的内容！'
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

function getStrlen(str) {
    if (str == null || str.length == 0) {
        return 0;
    }
    var len = str.length;
    var reLen = 0;
    for (var i = 0; i < len; i++) {
        if (str.charCodeAt(i) < 27 || str.charCodeAt(i) > 126) {
            // 全角
            reLen += 1;
        } else {
            reLen += 0.5;
        }
    }
    return Math.ceil(reLen);
}

function ToDBC(txtstring) {
    var tmp = "";
    for (var i = 0; i < txtstring.length; i++) {
        if (txtstring.charCodeAt(i) == 32) {
            tmp = tmp + String.fromCharCode(12288);
        }
        if (txtstring.charCodeAt(i) < 127) {
            tmp = tmp + String.fromCharCode(txtstring.charCodeAt(i) + 65248);
        }
    }
    return tmp;
}

$.fn.AutoHeight = function (options) {
    var defaults = {
        maxHeight: null,
        minHeight: $(this).height()
    };
    var opts = (typeof (options) === 'object') ? $.extend({}, defaults, options) : {};
    this.each(function () {
        $(this).bind("paste cut keydown keyup focus blur", function () {
            var height, style = this.style;
            this.style.height = opts.minHeight + 'px';
            if (this.scrollHeight > opts.minHeight) {
                if (opts.maxHeight && this.scrollHeight > opts.maxHeight) {
                    height = opts.maxHeight;
                    style.overflowY = 'scroll';
                } else {
                    if ('\v' == 'v') {
                        height = this.scrollHeight - 2;
                    } else {
                        height = this.scrollHeight;
                    }
                    style.overflowY = 'hidden';
                }
                style.height = height + 'px';
            }
        });
    });
    return this;
};