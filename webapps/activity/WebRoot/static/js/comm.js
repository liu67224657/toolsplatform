//圣旨页面
var sz_page = {
    record: record,//0新用户 1扬 2荆 3豫 4益 5青
    names: '易航易航易航',//用户昵称
    init: function () {
        var audio = $('.audio_btn');
        sz_page.tabshow($('.page2'));
        var sz_box = $('.sz_box').children();
        var sz_city = $('.sz_country');
        var sz_click = $('.sz_click_btn').children('cite');
        var sz_city_con = $('.sz_country_con');
        var timer = null;
        var sz_audio = document.querySelector('#sz_audioBtn');
        var bg_audio = document.querySelector('#audioBtn');
        audio.addClass('active');
        sz_audio.volume = 1;
        bg_audio.volume = 0.5;
        bg_audio.play();
        //点击音乐暂停
        audio.on('click', function () {
            if (!$(this).hasClass('p')) {
                bg_audio.pause();
                sz_audio.pause();
                $(this).addClass('p');
            } else {
                bg_audio.play();
                $(this).removeClass('p');
            }
        })
        //开始
        $('.start_b').on('click', function () {
            if (!sz_page.record) {
                sz_page.tabshow($('.page3'));
                bg_audio.pause();
                sz_audio.play();
                clearTimeout(timer);
                timer = setTimeout(function () {
                    if (sz_page.record) {
                        sz_box.eq(sz_page.record - 1).addClass('turn_on').siblings().addClass('turn_off');
                    } else {
                        sz_box.addClass('turn_on');
                    }
                    ;
                    sz_page.tabshow($('.page4'));
                    if (audio.hasClass('p')) {
                        bg_audio.pause();
                    } else {
                        bg_audio.play();
                    }
                }, 25000)
            } else {
                if (sz_page.record) {
                    sz_box.eq(sz_page.record - 1).addClass('turn_on').siblings().addClass('turn_off');
                } else {
                    sz_box.addClass('turn_on');
                };
                sz_page.tabshow($('.page4'));
            }
        });
        //选择圣旨
        //可选圣旨
        $('.sz_box').on('click', '.turn_on', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var cityN = $(this).attr('data-city');
            sz_city.attr('id', 'sz_country_' + cityN);
            sz_page.tabshow($('.page5'));
            audio.removeClass('active');
        })
        //不可选圣旨
        $('.sz_box').on('click', '.turn_off', function () {
            //alert('您已经选择过城市!');
        })
        //点击圣旨
        sz_click.on('click', function () {
            if (sz_page.record) {
                sz_city_con.attr('id', 'sz_country_con' + sz_page.record);
                $('.name').text(sz_page.names);
                sz_page.tabshow($('.page6'));
            } else {
                $('.sz_dialog').show();
            }
            ;
        })
        //重新选择
        $('.reselect').on('click', function () {
            $('.dialog').hide();
            sz_page.tabshow($('.page4'));
            audio.addClass('active');
        })
        //接旨
        $('.sure').on('click', function () {
            var curcity = sz_city.attr('id');
            curcity = curcity.charAt(curcity.length - 1);
            $.post("/activity/dragon/confirmCountry.do", {openId: openId, countryCode: curcity}, function (data) {
                var obj = eval("(" + data + ")");
                if (obj.rs == "1") {
                    sz_city_con.attr('id', 'sz_country_con' + curcity);
                    $('.name').text(sz_page.names);
                    sz_page.tabshow($('.page6'), true);
                } else {
                    $('.sz_dialog').show();
                }
            })

        });
        _swipe('up', function () {
            window.location = '/activity/accountInfo/findUserInfo.do';
        });
    },
    tabshow: function (tag, callback) {
        tag.addClass('active').siblings().removeClass('active');
    }
};
//套装页面
var tz_page = {
    record: record,//1扬 2荆 3豫 4益 5青
    init: function () {
        var bg_audio = document.querySelector('#audioBtn');
        bg_audio.play();
        $('.loading').hide();
        //国家icon
        $('.zb-tit-t').children('font').text(tz_page.names);
        $('.zb-tit-t').children('i').attr('class', 'cityIcon_' + tz_page.record);
        tz_page.tips_3();//3秒消失提示
        var moveX = false;
        var degree = parseInt(times);//提升战力次数
        var zl_num = 0;//战力值
        var zl_add = $('.zl_add');
        //游戏规则
        $('.zb-que').on('click', function () {
            $('.gz_dialog').show();
        })
        tz_page.tabView({tit: '.tab-tit', con: '.tab-con', cla: 'on'});
        //排行榜
        $('.ranking-btn').on('click', function () {
            displayScore();
            $('.zl-gx-box,.mask-box').addClass('on');
        })
        //关闭排行榜
        $('.close-btn').on('click', function () {
            $('.zl-gx-box,.mask-box').removeClass('on');
        })
        //星套
        $('.zb-level').children('span').on('click', function () {
            showXtDialog();
            $('.xt_dialog').show();
        })
        //好友帮忙
        $('.tab-btn').on('click', function () {
            $('.share_dialog').show();
        })
        //提升战力按钮
        $('.ts-btn').on('click', function () {
            if (!moveX) {
                if (degree == 0) {
                    $('.zl_dialog').show();
                    $('.hand-box').removeClass('on').removeClass('show');
                    return false;
                }
                $('.b-btn-box').removeClass('show on').addClass('disable');
                zl_add.removeClass('on');
                $('.hand-box').addClass('on');
                $(this).text('点击停止');
                degree--;
                $.post("/activity/accountInfo/addUserScore.do", {openId: openId}, function (data) {
                    var obj = eval("(" + data + ")");
                    var num = obj.result;
                    if (num != null) {
                        window.localStorage.setItem('_times' + getTime(), degree);
                        zl_num = parseInt(num);
                    }
                })
                moveX = true;
            } else {
                var a = (304 / 500) * zl_num;
                $('.hand-move').css('left', a);
                $('.hand-box').removeClass('on').addClass('show');
                $('.b-btn-box').addClass('show').removeClass('disable');
                $(this).text('提升战力：' + degree + '次');
                zl_add.each(function () {
                    $(this).addClass('on');
                    var curnum = $(this).find('font');
                    var zl_addNum = $(this).find('em');
                    curnum.text(curnum.text() * 1 + zl_num);
                    zl_addNum.text('+' + zl_num);
                })
                var score = $('.zb-zl').find('font').text() * 1;
                var tz_level = $('.zb-box');
                var zb_level = $('.zb-level');
                if (score >= 0 && score < 3000) {
                    zb_level.html('<span class="moon-1"></span>');
                    tz_level.attr('class', 'zb-box level-0');
                } else if (score >= 3000 && score < 10000) {
                    zb_level.html('<span class="moon-2"></span>');
                    tz_level.attr('class', 'zb-box level-0');
                } else if (score >= 10000 && score < 30000) {
                    zb_level.html('<span class="sun"></span>');
                    tz_level.attr('class', 'zb-box level-6');
                } else if (score >= 30000 && score < 100000) {
                    zb_level.html('<span class="sun"></span><span class="moon-2"></span>');
                    tz_level.attr('class', 'zb-box level-9');
                } else if (score >= 100000 && score < 1000000) {
                    zb_level.html('<span class="sun"></span><span class="sun"></span>');
                    tz_level.attr('class', 'zb-box level-12');
                } else if (score >= 1000000 && score < 5000000) {
                    zb_level.html('<span class="sun"></span><span class="sun"></span><span class="moon-2"></span>');
                    tz_level.attr('class', 'zb-box level-15');
                } else if (score >= 5000000) {
                    zb_level.html('<span class="sun"></span><span class="sun"></span><span class="sun"></span>');
                    tz_level.attr('class', 'zb-box level-18');
                }
                moveX = false;
            }
        });
    },
    tips_3: function () {
        var timer, timer1 = null;
        clearTimeout(timer, timer1);
        timer = setTimeout(function () {
            $('.tips_3').hide();
        }, 3000)
        _swipe('up', function () {
            var text = $('.ts-btn').text();
            if (text == '提升战力：3次') {
                $('.b-btn-box').addClass('on');
                clearTimeout(timer1);
                timer1 = setTimeout(function () {
                    $('.b-btn-box').removeClass('on').addClass('show');
                }, 3000)
            } else {
                $('.b-btn-box').removeClass('on').addClass('show');
            }
        })
        _swipe('down', function () {
            $('.b-btn-box').removeClass('show').removeClass('on');
        })
    },
    tabView: function (config) {
        var tit = $(config.tit),
            con = $(config.con),
            cla = config.cla;
        tit.children('cite').on('click', function () {
            var ind = $(this).index();
            $(this).addClass(cla).siblings().removeClass(cla);
            con.children('div').eq(ind).addClass(cla).siblings().removeClass(cla);
        })
    }
};
//关闭按钮
$('.close').on('click', function () {
    $('.dialog').hide();
});
//阻止遮罩默认行为
$('.dialog_bg,.mask-box').on('touchstart', function (e) {
    e.preventDefault()
});
$('.page3,.page6').on('touchmove', function (e) {
    e.preventDefault()
});
function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
};
function showweixin() {
    var tip = '<div class="wx-tip"><div>请在微信端打开</div></div>'
    if (!isWeiXin()) {
        $('#wrap').append(tip)
    }
};
showweixin();
//向上滑动事件
function _swipe(direction, callback) {
    var mmb = document.querySelectorAll('.slideUp');
    var startX, startY, endX, endY, touch;
    var x, y;
    for (var i = 0; i < mmb.length; i++) {
        mmb[i].addEventListener('touchstart', function () {
            var e = event || window.event;
            //e.preventDefault();
            if (e.targetTouches.length == 1) {
                touch = event.targetTouches[0];
                startX = touch.pageX;
                startY = touch.pageY;
            }
        }, false);
        mmb[i].addEventListener('touchmove', function () {
            var e = event || window.event;
            // e.preventDefault();
            if (e.targetTouches.length == 1) {
                touch = event.targetTouches[0];
                endX = touch.pageX;
                endY = touch.pageY;
            }
            ;
        }, false);
        mmb[i].addEventListener('touchend', function () {
            x = ~~(endX - startX);
            y = ~~(endY - startY);
            if (direction == 'up') {
                if (Math.abs(y) > Math.abs(x)) {
                    if (y > 10) {
                        return false;
                    } else {
                        callback();
                    }
                }
            } else {
                if (Math.abs(y) > Math.abs(x)) {
                    if (y < 0) {
                        return false;
                    } else {
                        callback();
                    }
                }
            }
        }, false);
    }
    ;
};
