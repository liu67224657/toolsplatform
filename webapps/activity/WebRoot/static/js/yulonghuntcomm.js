start_bot = endVal;
var end_bot = endVal;
//console.log("start_bot :" + start_bot + "<--------------->end_bot:" + end_bot);
function pageInit() {
    tabshow('homepage');
    musicBtn();
    var endtext = ['恭喜将军，历经千辛万苦，终于寻得终极龙脉：<span>无双之弓*1</span>，今后定能平定天下，救民于水火。点击【我的宝藏】查看已获得的所有CDK。', '这一段寻龙脉的旅程结束了，是不是还有宝物没有获得？<br/>那么就一起开启新的寻宝之旅吧！<br/>点击【我的宝藏】查看已获得的所有CDK。', '终极龙脉宝藏已被将军寻得，将军真乃人中龙凤，快点击【我的宝藏】去兑换所获CDK吧。'];
    var city_text = ['将军威武，寻得青州龙脉<br/>获得：<span>免做令牌*1</span><br/>奖励将在到达终点后发放', '将军威武，寻得荆州龙脉<br/>获得：<span>玄武血剂*1</span><br/>奖励将在到达终点后发放', '将军威武，寻得豫州龙脉<br/>获得：<span>白色讨伐令*3</span><br/>奖励将在到达终点后发放', '将军威武，寻得扬州龙脉<br/>获得：<span>二级桃子酒*1</span><br/>奖励将在到达终点后发放', '将军威武，寻得益州龙脉<br/>获得：<span>二倍经验丹*1</span><br/>奖励将在到达终点后发放'];
   // var tip_text = ['此处龙脉宝藏已被将军寻得，后面还有更多丰厚奖励在等着您哦，继续寻找吧~', '好可惜呀，将军没有寻对龙脉位置，走到对应的龙脉宝箱位置才可获得奖励哦，继续加油寻找吧~'];
    var tip_text=['此处龙脉宝藏已被将军寻得，后面还有更多丰厚奖励在等着您哦，继续寻找吧~','好可惜呀，将军没有寻对龙脉位置，走到<span>带宝箱</span>的五国龙脉和终极龙脉位置才可获得奖励哦，继续加油寻找吧~ '];
    var animat = $('.animat');
    var animat_text = $('.animat_text')
    var dialog_mask = $('.dialog_mask');
    var animat_text = $('.animat_text');
    $('.infocont,.dialog').on('touchmove', function (e) {
        e.preventDefault();
    });
    $('.animat').on('touchstart', function (e) {
        e.preventDefault();
    });
    dialog_mask.on('touchstart', function (e) {
        e.preventDefault();
    });
    var StorageGetter = function (key) {
        return localStorage.getItem(key);
    }
    var StorageSetter = function (key, val) {
        return localStorage.setItem(key, val);
    }
    var timer = null;
    //var start_bot=0,end_bot=0;
    //start_bot = StorageGetter('_browserBack' + openId);
    //点击开始
    $('.begin_btn').on('click', function () {
        tabshow('infopage');
        clearTimeout(timer);
        timer = setTimeout(function () {
            infoNextpage();
        }, 9800);

        try {
            MtaH5.clickStat('KS1');
        } catch (e) {
        }
    });
    //跳过
    $('.nextpage').on('click', function () {
        clearTimeout(timer);
        infoNextpage();
    })
    function infoNextpage() {
        tabshow('contpage');
        scrollBot();
        movebot(start_bot, end_bot);
        $('.gz_btn').show();
    }

    if (StorageGetter('handShow')) {
        $('.hand_box,.jt_box').hide();
    }
    //点击头像摇骰子
    var yh_tit = $('.yh_tit');
    yh_tit.on('click', function () {
        var $this = $(this);
        $('.hand_box,.jt_box').hide();
        StorageSetter('handShow', true);
        $this.addClass('active');
        if ($this.hasClass('active')) {
            var timer = null;
            var sz_bot = $('.sz_bot');
            clearTimeout(timer);
            $.post("/activity/yulonghunt/click.do", {openId: openId, end_bot: end_bot, num: ""}, function (data) {
                var obj = eval("(" + data + ")");
                if (obj.rs == "1") {
                    var page = window.localStorage.getItem('_inpage_' + openId);
                    window.localStorage.setItem('_inpage_' + openId, parseInt(page) + 1);
                    //服务器返回的数
                    var sj_bot = obj.result;
                    if (obj.counreycode != "") {
                        counreycode = obj.counreycode;
                        StorageSetter(obj.counreycode + openId, obj.counreycode);
                    }
                    sz_bot.addClass('sz_' + sj_bot + ' active').show();
                    timer = setTimeout(function () {
                        sz_bot.removeClass('sz_' + sj_bot + ' active').hide();
                        botNum(sj_bot);
                        $this.removeClass('active');
                    }, 1500)

                }
            });
        } else {
            $this.removeClass('active');
        }
    })

    function botNum(sjnum) {
        end_bot = parseInt(start_bot) + parseInt(sjnum);
        var movebot_num = sjnum;
        if (end_bot > 36) {
            movebot_num = end_bot - 34;
            end_bot = 37;
        }
        movebot_num = movebot_num < 0 ? movebot_num = movebot_num * -1 : movebot_num = movebot_num;
        movebot(start_bot, end_bot);
        showAnimat(end_bot, movebot_num);
        start_bot = end_bot;
        if (end_bot == 37) {
            start_bot = 0;
            end_bot = 0;
        }
        StorageSetter('_browserBack_' + openId, end_bot);
    }

    function popCode(country){
        $.post("/activity/yulonghunt/popcode.do", {openId: openId, counreyCode: country}, function (data) {
            var obj = eval("(" + data + ")");
            if (obj.rs == "1") {
                //服务器返回的数
                var sj_bot = obj.result;
                if (obj.counreycode != "") {
                    counreycode = obj.counreycode;
                    StorageSetter(obj.counreycode + openId, obj.counreycode);
                }
            }
        });
    }

    function showAnimat(num, bot) {
        var city_d = $('.city_d');
        var city_textb = $('.city_text');
        var tip_d = $('.tip_d');
        var tip_d_text = $('.tip_text');
        var city_close = city_textb.parents('.dialog_con').find('.dialog_close');
        var timer = null;
        clearTimeout(timer);
        var times = (bot + 2) * 300;
        timer = setTimeout(function () {
            _frombluepoint = window.localStorage.getItem('_frombluepoint' + openId)
            if (_frombluepoint == "" || _frombluepoint == null) {
                _frombluepoint = "";
            }
/**
            counreycode = StorageGetter(counreycode + openId)
            if (counreycode != "" && counreycode == "yizhou") {
                $('.yiz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "yuzhou") {
                $('.yuz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "jingzhou") {
                $('.jingz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "qingzhou") {
                $('.qingz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "yangzhou") {
                $('.yangz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "end") {
                $('.gzlm_dot').addClass('show');
            }**/

            if ((num == 1 && _frombluepoint == "") || (num == 2 && _frombluepoint == "") || ( num == 6 && _frombluepoint == "")
                || (num == 7 && _frombluepoint == "") || (num == 9 && _frombluepoint == "") || (num == 13 && _frombluepoint == "")
                || (num == 14 && _frombluepoint == "") || (num == 16 && _frombluepoint == "") || (num == 18 && _frombluepoint == "")
                || (num == 23 && _frombluepoint == "") || (num == 24 && _frombluepoint == "") || (num == 26 && _frombluepoint == "")
                || (num == 27 && _frombluepoint == "") || (num == 29 && _frombluepoint == "") || (num == 31 && _frombluepoint == "")
                || (num == 32 && _frombluepoint == "") || (num == 34 && _frombluepoint == "") || (num == 35 && _frombluepoint == "")) {
                window.localStorage.setItem('_frombluepoint' + openId, "true")
                dialog_mask.show();
                tip_d.show();
                tip_d_text.html(tip_text[1]);
            } else if (num == 3 || num == 12 || num == 33) {
                animat.addClass('lbss_d active');
                animat_text.text('乐不思蜀');
                animatEnd('lbss');
            } else if (num == 4 || num == 21) {
                animat.addClass('gz_d active');
                animat_text.text('国战');
                animatEnd('gz');
            } else if (num == 8 || num == 22) {
                animat.addClass('sgml_d active');
                animat_text.text('三顾茅庐');
                animatEnd('sgml');
            } else if (num == 11) {
                animat.addClass('cbzj_d active');
                animat_text.text('赤壁之战');
                animatEnd('cbzj');
            } else if (num == 17 || num == 28) {
                animat.addClass('hrd_d active');
                animat_text.text('华容道');
                animatEnd('hrd');
            } else if (num == 19 || num == 30) {
                animat.addClass('tz_d active');
                animat_text.text('偷猪');
                animatEnd('tz');
            } else if (num == 5) {
                dialog_mask.show();
                if ($('.yiz_city').hasClass('show')) {
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                } else {
                    popCode('yizhou');
                    city_d.show();
                    city_textb.html(city_text[4]);
                    city_close.attr('city-I', 'yiz_city')
                }
            } else if (num == 10) {
                dialog_mask.show();
                if ($('.yuz_city').hasClass('show')) {
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                } else {
                    popCode('yuzhou');
                    city_d.show();
                    city_textb.html(city_text[2]);
                    city_close.attr('city-I', 'yuz_city')
                }
            } else if (num == 15) {
                dialog_mask.show();
                if ($('.jingz_city').hasClass('show')) {
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                } else {
                    popCode('jingzhou');
                    city_d.show();
                    city_textb.html(city_text[1]);
                    city_close.attr('city-I', 'jingz_city')
                }
            } else if (num == 20) {
                dialog_mask.show();
                if ($('.qingz_city').hasClass('show')) {
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                } else {
                    popCode('qingzhou');
                    city_d.show();
                    city_textb.html(city_text[0]);
                    city_close.attr('city-I', 'qingz_city')
                }
            } else if (num == 25) {
                dialog_mask.show();
                if ($('.yangz_city').hasClass('show')) {
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                } else {
                    popCode('yangzhou');
                    city_d.show();
                    city_textb.html(city_text[3]);
                    city_close.attr('city-I', 'yangz_city')
                }
            } else if (num == 36) {
                dialog_mask.show();
                if ($('.gzlm_dot').hasClass('show')) {
                    $('.end_d').show();
                    $('.ending_text').html(endtext[2]);
                } else {
                    popCode('end');
                    $('.end_d').show();
                    $('.ending_text').html(endtext[0]);
                    $('.end_btn').attr('city-I', 'gzlm_dot')
                }
            } else if (num == 37) {
                dialog_mask.show();
                $('.ending_text').html(endtext[1]);
                $('.end_d').show();
            }
            counreycode = StorageGetter(counreycode + openId)
            if (counreycode != "" && counreycode == "yizhou") {
                $('.yiz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "yuzhou") {
                $('.yuz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "jingzhou") {
                $('.jingz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "qingzhou") {
                $('.qingz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "yangzhou") {
                $('.yangz_city').addClass('show');
            } else if (counreycode != "" && counreycode == "end") {
                $('.gzlm_dot').addClass('show');
            }
        }, times)
    }

    function animatEnd(animat_t) {
        var an_timer = null;
        clearTimeout(an_timer);
        an_timer = setTimeout(function () {
            animat.attr('class', 'animat');
            dialog_mask.show();
            switch (animat_t) {
                case 'lbss':
                    $('.lbss_d').show();
                    djs(6);
                    break;
                case 'gz':
                    $('.gza_d').show();
                    break;
                case 'sgml':
                    $('.sgml_d').show();
                    break;
                case 'cbzj':
                    $('.cbzj_d').show();
                    break;
                case 'hrd':
                    $('.hrd_d').show();
                    break;
                case 'tz':
                    $('.tz_d').show();
                    break;
            }
        }, 3000)
    };
    function djs(times) {
        times--;
        $('.djs').html(times + 's');
        if (times > 0) {
            var tz_timer = null;
            clearTimeout(tz_timer);
            tz_timer = setTimeout(function () {
                djs(times);
                if (times == 1) {
                    $('.lbss_d,.dialog_mask').hide();
                }
            }, 1000)
        }
    };
    //告知好友
    $('.gzhy_btn').on('click', function () {
        $(this).parents('.dialog').addClass('gzhy_d');
        try {
            MtaH5.clickStat('GZ2');
        } catch (e) {
        }
    })
    //在玩一次
    $('.zwyc').on('click', function () {
        $('.dialog,.dialog_mask').hide();
        movebot(0, 0);
        start_bot = 0;
        end_bot = 0;
    })
    $('.wdbx_icon').on('click', function () {
        $.post("/activity/yulonghunt/my.do", {openId: openId, end_bot: end_bot}, function (data) {
            window.localStorage.setItem('_browserBack_' + openId, end_bot);
            var obj = eval("(" + data + ")");
            if (obj.rs == "1") {
                var page = window.localStorage.getItem('_inpage_' + openId);
                window.localStorage.setItem('_inpage_' + openId, parseInt(page) + 1);
                if (obj.result != "") {
                    var _result = eval("(" + obj.result + ")");
                    var _html = '<li><b>益州：</b><p>' + _result.yizhou + '</p></li>';
                    _html += '<li><b>豫州：</b><p>' + _result.yuzhou + '</p></li>';
                    _html += '<li><b>荆州：</b><p>' + _result.jingzhou + '</p></li>';
                    _html += '<li><b>青州：</b><p>' + _result.qingzhou + '</p></li>';
                    _html += '<li><b>扬州：</b><p>' + _result.yangzhou + '</p></li>';
                    _html += '<li><b>终极：</b><p>' + _result.end + '</p></li>';
                    $(".lb_city").html(_html);
                }
                $('.dialog').hide();
                $('.bz_d').show();
                dialog_mask.show();
            }
        });
    })


    $('.wdbz').on('click', function () {
        $.post("/activity/yulonghunt/my.do", {openId: openId, end_bot: end_bot}, function (data) {
            window.localStorage.setItem('_browserBack_' + openId, end_bot);
            var obj = eval("(" + data + ")");
            if (obj.rs == "1") {
                var page = window.localStorage.getItem('_inpage_' + openId);
                window.localStorage.setItem('_inpage_' + openId, parseInt(page) + 1);
                if (obj.result != "") {
                    var _result = eval("(" + obj.result + ")");
                    var _html = '<li><b>益州：</b><p>' + _result.yizhou + '</p></li>';
                    _html += '<li><b>豫州：</b><p>' + _result.yuzhou + '</p></li>';
                    _html += '<li><b>荆州：</b><p>' + _result.jingzhou + '</p></li>';
                    _html += '<li><b>青州：</b><p>' + _result.qingzhou + '</p></li>';
                    _html += '<li><b>扬州：</b><p>' + _result.yangzhou + '</p></li>';
                    _html += '<li><b>终极：</b><p>' + _result.end + '</p></li>';
                    $(".lb_city").html(_html);
                }
                $('.dialog').hide();
                $('.bz_d').show();
                dialog_mask.show();
                movebot(0, 0);
                start_bot = 0;
                end_bot = 0;
            }
        });
        //$('.dialog').hide();
        //$('.bz_d').show();
        //dialog_mask.show();
        //movebot(0, 0);
        //start_bot = 0;
        //end_bot = 0;
    })
    //开始摇晃
    $('.ksyh').on('click', function () {
        $('.dialog').hide();
        $('.ksyh_d').show().addClass('active');
    })
    //重力事件
    if (window.DeviceMotionEvent) {
        var speed = 15;
        var x = y = z = lastX = lastY = lastZ = 0;
        var timer = null;
        window.addEventListener('devicemotion', function () {
            var acceleration = event.accelerationIncludingGravity;
            x = acceleration.x;
            y = acceleration.y;
            if (Math.abs(x - lastX) > speed || Math.abs(y - lastY) > speed) {
                clearTimeout(timer);
                timer = setTimeout(function () {
                    $('.ksyh_d').removeClass('active');
                    $('.dialog').hide();
                    $('.ksyh_end').show();
                }, 800)
                return false;
            }
            lastX = x;
            lastY = y;
        }, false);
    }
    //点骰子选择固定点数
    $('.clickSZ_con').children('cite').on('click', function () {
        var ind = $(this).index() + 1;
        botNum(ind);
        $('.dialog,.dialog_mask').hide();
    })
    //点击拆开
    $('.djck').on('click', function () {
        $('.dialog').hide();
        $('.djck_d').show();
    })
    //开始呐喊
    $('.ksnh').on('click', function () {
        $('.dialog').hide();
        $('.ksnh_d').show();
        longtap();
    })
    //长按
    function longtap() {
        var $this = document.querySelector(".nh_con");
        var startX, startY, endTx, endTy, timer;
        $this.addEventListener('touchstart', function (e) {
            clearTimeout(timer);
            var touches = e.touches[0];
            startX = touches.clientX;
            startY = touches.clientY;
            $('.ksnh_d').addClass('active');
            timer = setTimeout(function () {
                $('.ksnh_d').removeClass('active');
                $('.dialog').hide();
                $('.ksnh_end').show();
            }, 750);
            e.preventDefault();
        }, false);
        $this.addEventListener('touchmove', function (e) {
            var touches = e.touches[0];
            endTx = touches.clientX;
            endTy = touches.clientY;
            if (timer && (Math.abs(endTx - startX) > 5 || Math.abs(endTy - startY) > 5)) {
                clearTimeout(timer);
                timer = null;
            }
        }, false);
        $this.addEventListener('touchend', function (e) {
            if (timer) {
                clearTimeout(timer);
                timer = null;
            }
        }, false);
    }

    //是前进3步
    $('.shi').on('click', function () {
        botNum(3);
        $('.dialog,.dialog_mask').hide();
    })
    $('.qd').on('click', function () {
        var qu_num = $(this).attr('data-qj');
        if (qu_num) {
            botNum(qu_num * 1);
        }
        $('.dialog,.dialog_mask').hide();
    })
    //关闭弹窗
    $('.dialog_close,.fou').on('click', function () {
        var city_icon = $(this).attr('city-I');
        $('.dialog,.dialog_mask').hide();
        if (city_icon) {
            $('.' + city_icon).addClass('show');
        }
    })
    //玩法规则
    $('.gz_btn').on('click', function () {
        $('.dialog_mask,.gz_d').show();
    })
    //点击音乐
    function musicBtn() {
        $('.music_btn').show();
        var music_t = false;
        var audioBtn = document.querySelector('#audioBtn');
        $('.music_btn').on('click', function () {
            if (!music_t) {
                $(this).addClass('pasue');
                audioBtn.pause();
                music_t = true;
            } else {
                $(this).removeClass('pasue');
                audioBtn.play();
                music_t = false;
            }
        })
    };
    function randomMun() {
        var Max = 6;
        var Min = 1;
        var Range = Max - Min;
        var Rand = Math.random();
        return (Min + Math.round(Rand * Range));
    };
    var zb = [[73, 40], [77, 113], [70, 169], [76, 236], [110, 300], [180, 290], [192, 226], [180, 168], [216, 116], [271, 133], [305, 202], [344, 292], [420, 316], [466, 268], [462, 206], [516, 160], [590, 210], [576, 274], [626, 300], [684, 264], [750, 226], [834, 264], [908, 274], [928, 222], [910, 172], [866, 96], [824, 42], [756, 42], [710, 92], [642, 86], [604, 42], [536, 52], [466, 54], [400, 70], [332, 50], [264, 40], [180, 43], [73, 40]];

    function movebot(startd, endd) {
        var times = null;
        if (endd > startd) {
            yh_tit.addClass('active');
            startd++;
            clearTimeout(times);
            times = setTimeout(function () {
                yh_tit.css({'bottom': zb[startd][0], 'left': zb[startd][1]});
                movebot(startd, endd);
            }, 300);
        } else if (endd == startd) {
            yh_tit.css({'bottom': zb[startd][0], 'left': zb[startd][1]});
        } else if (endd < startd) {
            yh_tit.addClass('active');
            startd--;
            clearTimeout(times);
            times = setTimeout(function () {
                yh_tit.css({'bottom': zb[startd][0], 'left': zb[startd][1]});
                movebot(startd, endd);
            }, 300);
        }
    };
    function scrollBot() {
        var $this = $('.contpage'),
            viewH = $this.height(),
            contentH = $this.get(0).scrollHeight;
        $this.scrollTop(contentH - viewH)
    };
    function tabshow(tag) {
        $('.' + tag).addClass('active').siblings().removeClass('active');
    };
};