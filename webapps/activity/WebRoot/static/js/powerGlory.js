start_bot = endVal;
var end_bot = endVal;
function pageInit(){
    musicBtn();
    tabshow('homepage');
    var city_text=['恭喜勇士寻得雷熊国家宝藏<br/>获得：<span>神秘礼包1</span><br/>点击右下角【我的宝藏】查看详情','恭喜勇士寻得霜狼国家宝藏<br/>获得：<span>神秘礼包2</span><br/>点击右下角【我的宝藏】查看详情','恭喜勇士寻得飞狮国家宝藏<br/>获得：<span>神秘礼包3</span><br/>点击右下角【我的宝藏】查看详情','恭喜勇士寻得魔牛国家宝藏<br/>获得：<span>神秘礼包4</span><br/>点击右下角【我的宝藏】查看详情','恭喜勇士寻得影蛇国家宝藏<br/>获得：<span>神秘礼包5</span><br/>点击右下角【我的宝藏】查看详情','恭喜勇士寻得战鹰国家宝藏<br/>获得：<span>神秘礼包6</span><br/>点击右下角【我的宝藏】查看详情'];
    var tip_text=['此处国家宝藏已被勇士寻得，<br/>不要灰心，继续寻找，<br/>还有其他宝藏等着您哦~','好可惜呀，勇士没有寻对宝藏位置<br/>请再接再厉，继续寻找吧~'];
    var dialog_mask=$('.dialog_mask');
    $('.infocont,.dialog').on('touchmove',function(e){
        e.preventDefault();
    });
    dialog_mask.on('touchmove',function(e){
        e.preventDefault();
    });
    $('.dialog_mask,.touch_dialog').on('click',function(){
        $(this).hide();
        $('.dialog,.gzhy_d').hide();
        var city_icon=$(this).attr('city-I');
        if(city_icon){
            $('.'+city_icon).addClass('show');
        }
        if($(this).attr('last')=="true"){
            $('.city_d').attr('last','false');
            $('.tip_d').attr('last','false');
            movebot(0,0);
            start_bot=0;
            end_bot=0;
            setTimeout(function(){
                dialog_mask.show();
                $('.end_d').show();
            },300);
        }
    });
    var StorageGetter = function(key) {
        return localStorage.getItem(key);
    }
    var StorageSetter = function(key, val) {
        return localStorage.setItem(key, val);
    }

    var timer=null;
    /* var timer=null;
     var start_bot=0,end_bot=0;
     start_bot=StorageGetter('start');
     end_bot=StorageGetter('start');*/
    //点击开始
    $('.begin_btn').on('click',function(){
        tabshow('infopage');
        clearTimeout(timer);
        timer=setTimeout(function(){
            infoNextpage();
        },6800);
    });
    //跳过
    $('.nextpage').on('click',function(){
        clearTimeout(timer);
        infoNextpage();
    })
    function infoNextpage(){
        tabshow('contpage');
        scrollBot();
        $('.gz_btn').show();
        movebot(start_bot, end_bot);
    }
    if(StorageGetter('handShow')){
        $('.start_dot').hide();
    }
    //点击头像摇骰子
    var yh_tit=$('.yh_tit');
    yh_tit.on('click',function(){
        var $this=$(this);
        $('.start_dot').hide();
        StorageSetter('handShow', true);
        $this.addClass('active');
        if($this.hasClass('active')){
            var timer=null;
            var sz_bot=$('.sz_bot');
            clearTimeout(timer);
            $.post("/activity/powerglory/click.do", {openId: openId, end_bot: end_bot, num: ""}, function (data) {
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
        }else{
            $this.removeClass('active');
        }
    })
    function botNum(sjnum){
        end_bot=parseInt(start_bot)+parseInt(sjnum);
        var movebot_num=sjnum;
        if (end_bot>36){
            movebot_num=end_bot-36;
            end_bot=37;
        }
        movebot_num=movebot_num<0 ? movebot_num=movebot_num*-1 : movebot_num=movebot_num;
        movebot(start_bot,end_bot);
        showAnimat(end_bot,movebot_num);
        start_bot=end_bot;
        if(end_bot==37){
            start_bot=0;
            end_bot=0;
        }
        StorageSetter('_browserBack_' + openId, end_bot);
    }
    function popCode(country){
        $.post("/activity/powerglory/popcode.do", {openId: openId, counreyCode: country}, function (data) {
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
    function showAnimat(num,bot){
        var city_d=$('.city_d');
        var city_textb=$('.city_text');
        var tip_d=$('.tip_d');
        var tip_d_text=$('.tip_text');
        var city_close=$('.touch_dialog')
        var timer=null;
        clearTimeout(timer);
        var times=(bot+2)*300;
        timer=setTimeout(function(){
            _frombluepoint = window.localStorage.getItem('_frombluepoint' + openId)
            if (_frombluepoint == "" || _frombluepoint == null) {
                _frombluepoint = "";
            };
            if((num == 3 && _frombluepoint == "") || (num == 2 && _frombluepoint == "") || ( num == 6 && _frombluepoint == "")){
                window.localStorage.setItem('_frombluepoint' + openId, "true")
                tip_d.show();
                tip_d_text.html(tip_text[1]);
            }else if(num==1 || num==29){
                $('.lbss_d').addClass('show');//镖车
            }else if(num==4){
                $('.gza_d').addClass('show');
            }else if(num==8 || num==22){
                $('.sgml_d').addClass('show');
            }else if(num==12){
                $('.cbzj_d').addClass('show');
            }else if(num==17 || num==33){
                $('.hrd_d').addClass('show');
            }else if(num==24){
                $('.tz_d').addClass('show');
                djs(2);
            }else if(num==31){
                $('.zs_d').addClass('show');
                djs(2);
            }else if(num==5){
                if($('.yiz_city').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                }else{
                    popCode('yizhou');
                    city_d.show();
                    city_textb.html(city_text[0]);
                    city_close.attr('city-I','yiz_city')
                }
            }else if(num==10){
                if($('.yuz_city').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                }else{
                    popCode('yuzhou');
                    city_d.show();
                    city_textb.html(city_text[1]);
                    city_close.attr('city-I','yuz_city')
                }
            }else if(num==15){
                if($('.jingz_city').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                }else{
                    popCode('jingzhou');
                    city_d.show();
                    city_textb.html(city_text[2]);
                    city_close.attr('city-I','jingz_city')
                }
            }else if(num==20){
                if($('.qingz_city').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                }else{
                    popCode('qingzhou');
                    city_d.show();
                    city_textb.html(city_text[3]);
                    city_close.attr('city-I','qingz_city')
                }
            }else if(num==26){
                if($('.yangz_city').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                }else{
                    popCode('yangzhou');
                    city_d.show();
                    city_textb.html(city_text[4]);
                    city_close.attr('city-I','yangz_city')
                }
            }else if(num==36){
                if($('.gzlm_dot').hasClass('show')){
                    tip_d.show();
                    tip_d_text.html(tip_text[0]);
                    tip_d.attr('last','true');
                }else{
                    popCode('end');
                    city_d.show();
                    city_textb.html(city_text[5]);
                    city_close.attr('city-I','gzlm_dot')
                    city_d.attr('last','true');
                }
            }else if(num==37){
                dialog_mask.show();
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
        },times)
    };
    function djs(times){
        times--;
        $('.djs').html(times+'s');
        if(times>=0){
            var tz_timer=null;
            clearTimeout(tz_timer);
            tz_timer=setTimeout(function(){
                djs(times);
                if(times==0){
                    $('.animate_dialog').removeClass('show');
                }
            },1000)
        }
    };
    //点击锦囊
    $('.jinnang').on('click',function(){
        $('.animate_dialog').removeClass('show');
        $('.djck_d').addClass('show');
    })
    //前进两步
    $('.lbss_d,.gza_d,.cbzj_d').on('click',function() {
        $('.animate_dialog').removeClass('show');
        botNum(2);
    })
    //前进3步
    $('.yes').on('click',function() {
        $('.animate_dialog').removeClass('show');
        botNum(3);
    })
    //点击否原地不动
    $('.no').on('click',function() {
        $('.animate_dialog').removeClass('show');
    })
    //告知好友
    $('.gzhy_btn').on('click',function(){
        $('.gzhy_d').show();
    })
    //在玩一次
    $('.zwyc').on('click',function(){
        $('.dialog,.dialog_mask').hide();
        movebot(0,0);
        start_bot=0;
        end_bot=0;
    })
    //我的宝藏页面
    $('.wdbx_icon').on('click',function(){
        myPackage();
    })
    //我的宝藏
    $('.wdbz').on('click',function(){
        myPackage();
        movebot(0,0);
        start_bot=0;
        end_bot=0;
    })
    function myPackage(callback){
        $.post("/activity/powerglory/my.do", {openId: openId, end_bot: end_bot}, function (data) {
            window.localStorage.setItem('_browserBack_' + openId, end_bot);
            var obj = eval("(" + data + ")");
            if (obj.rs == "1") {
                var page = window.localStorage.getItem('_inpage_' + openId);
                window.localStorage.setItem('_inpage_' + openId, parseInt(page) + 1);
                if (obj.result != "") {
                    var _result = eval("(" + obj.result + ")");
                    var _html = '<li><b>雷熊：</b><p>' + _result.yizhou + '</p></li>';
                    _html += '<li><b>霜狼：</b><p>' + _result.yuzhou + '</p></li>';
                    _html += '<li><b>飞狮：</b><p>' + _result.jingzhou + '</p></li>';
                    _html += '<li><b>魔牛：</b><p>' + _result.qingzhou + '</p></li>';
                    _html += '<li><b>影蛇：</b><p>' + _result.yangzhou + '</p></li>';
                    _html += '<li><b>战鹰：</b><p>' + _result.end + '</p></li>';
                    $(".lb_city").html(_html);
                }
                $('.dialog').hide();
                $('.bz_d').show();
                dialog_mask.show();
            }
        });
    };
    //点骰子选择固定点数
    $('.clickSZ_con').children('cite').on('click',function(){
        var ind=$(this).index()+1;
        botNum(ind);
        $('.animate_dialog').removeClass('show');
    })
    //点击拆开
    $('.djck').on('click',function(){
        $('.dialog').hide();
        $('.djck_d').show();
    })
    //开始呐喊
    $('.ksnh').on('click',function(){
        $('.dialog').hide();
        $('.ksnh_d').show();
    })
    //是前进3步
    $('.shi').on('click',function(){
        botNum(3);
        $('.dialog,.dialog_mask').hide();
    })
    //玩法规则
    $('.gz_btn').on('click',function(){
        $('.gz_d').show();
    })
    //点击音乐
    function musicBtn(){
        $('.music_btn').show();
        var music_t=false;
        var audioBtn=document.querySelector('#audioBtn');
        audioBtn.play();
        $('.music_btn').on('click',function(){
            if(!music_t){
                $(this).addClass('pasue');
                audioBtn.pause();
                music_t=true;
            }else{
                $(this).removeClass('pasue');
                audioBtn.play();
                music_t=false;
            }
        })
    };
    function randomMun(){
        var Max=6;
        var Min=1;
        var Range = Max - Min;
        var Rand = Math.random();
        return(Min + Math.round(Rand * Range));
    };
    var zb=[[112,51],[129,133],[146,188],[154,225],[172,280],[250,280],[264,210],[245,160],[277,128],[325,135],[344,196],[382,265],
        [437,291],[502,262],[498,196],[551,159],[608,199],[591,275],[645,277],[690,254],[760,224],
        [819,252],[873,255],[895,195],[891,119],[852,70],[778,64],[716,87],[685,93],[640,76],[608,49],
        [556,49],[499,44],[440,45],[379,59],[339,51],[265,51],[112,51]];

    function movebot(startd,endd){
        var times=null;
        if(endd>startd){
            yh_tit.addClass('active');
            startd++;
            clearTimeout(times);
            times=setTimeout(function(){
                yh_tit.css({'bottom':zb[startd][0],'left':zb[startd][1]});
                movebot(startd,endd);
            },300);
        }else if(endd==startd){
            yh_tit.css({'bottom':zb[startd][0],'left':zb[startd][1]});
        }else if(endd<startd){
            yh_tit.addClass('active');
            startd--;
            clearTimeout(times);
            times=setTimeout(function(){
                yh_tit.css({'bottom':zb[startd][0],'left':zb[startd][1]});
                movebot(startd,endd);
            },300);
        }
    };
    function scrollBot(){
        var $this =$('.contpage'),
            viewH =$this.height(),
            contentH =$this.get(0).scrollHeight;
        $this.scrollTop(contentH-viewH)
    };
    function tabshow(tag){
        $('.'+tag).addClass('active').siblings().removeClass('active');
    };
};
