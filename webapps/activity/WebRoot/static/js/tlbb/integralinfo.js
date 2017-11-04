/**
 * Created by renhuimin on 16/9/26.
 */
    var flag = true;
    var n = 1;
    var winH = $(window).height();
    $.post("/weixinop/signHistory/getTotalScore.do", {openid: openId}, function (data) {
        if (data != "") {
            var userRankObj = eval("(" + data + ")");
            var rs = userRankObj.rs;
            if (rs=="1"){
                $('.bg-top span').html(userRankObj.result);
            }else {
                $('.bg-top span').html(0)
            }
        }
    });
    $(window).on('touchmove', function() {
        var sct = $(window).scrollTop();
        var scrollWrapper = $('body');
        var scrollH = scrollWrapper.height();
        if ((sct - (scrollH - winH)) >= -100) {
            if (flag) {
                getAllList();
            }
        }
    });
getAllList();
    var load = '<div class="dropload-down" style="transition: all 300ms; height: 50px;">\
				<div class="dropload-load"></div>\
				<span class="loading"></span>\
				加载中...\
				</div>';
    function getAllList() {
        flag = false;
        $('.dropload-down').remove();
        $('.integral').append(load);
        $.post("/weixinop/tlbb/getSignlogPage.do", {openid: openId, pSize: 10, pNum: n++}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                if (userRankObj.rs == "1") {
                    var resultArray = userRankObj.result;
                    var html = '';
                    for (var i = 0; i < resultArray.length; i++) {
                        html += '<div class="list">\
                           <p>每日签到 <span>+10</span></p>\
                           <p>' + resultArray[i].createTime + '</p>\
                       </div>';
                    }
                    $('.dropload-down').remove();
                    $('.integral').append(html);
                    flag = true;
                } else {
                    $('.dropload-down').text('没有更多了!');
                    return false;
                }
            }
        })
    }