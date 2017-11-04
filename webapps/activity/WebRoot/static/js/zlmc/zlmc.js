/**
 * Created by wengangsai on 2016/9/23.
 */
$(document).ready(function(){
    var setPage={
        number:0,//翻牌张数
        init:function(){
            this.musicBtn();
            this._swipe('up',function(){
                $('.contant').children('div').hide();
                $('.infoPage').show();
            });
            this.beginBtn();
            this.screening();
        },
        //开始扫荡
        beginBtn:function(){
            var w=$('#wrap').width();
            var h=$('#wrap').height();
            $('.begin_btn').on('click',function(){
                var orientation = window.orientation;
                $('.contant').children('div').hide();
                $('.playPage').show();
                $('#wrap').css({'-webkit-transform':'rotate(90deg)','transform':'rotate(90deg)','height':w,'width':h,'position':'absolute','top':(h-w)/2,'left':0-(h-w)/2,'transform-origin':'50% 50%'});
                $('.playPage').css({'height':w,'width':h});
                setPage.beginPlay(1,2000,3);
            })
        },
        //翻牌顺序
        suijiNum:function(min,max,len){
            var arr=[];
            for(var i=0;i<len;i++){
                var Range = max - min;
                var Rand = Math.random();
                var rand_num=min + Math.round(Rand * Range);
                arr.push(rand_num);
            }
            return arr;
        },
        //生成纸牌
        xipai:function(min,max,len){
            var zp_box=$('.zp_box').children('div');
            var Range = max - min;
            var Rand = Math.random();
            var rand_num=min + Math.round(Rand * Range);
            for(var i=0;i<len;i++){
                rand_num++;
                if(rand_num>max){
                    rand_num=1;
                }
                zp_box.eq(i).attr('class','zp_'+rand_num);
            }
        },
        caozuo:function(sunxu,gk_num,time_num){
            var xt_timer=null;
            var fz_timer=null;
            var zp_box=$('.zp_box').children('div');
            var sunxu=sunxu;
            var win_num=0;
            clearInterval(xt_timer);
            var i=time_num/100;
            var a=100;
            xt_timer=setInterval(function(){
                $('.xt-now').css('width',a+'%');
                if(a<=0){
                    clearInterval(xt_timer);
                    $('.dialog_fail').children('font').text(gk_num);
                    $('.fail_num').text(setPage.number);
                    $('.dialog_mask,.dialog_fail').show();
                }
                a--;
            },i);
            zp_box.unbind("touchstart");
            zp_box.on('touchstart',function(e){
                e.stopPropagation();
                e.preventDefault();
                var ind=$(this).index();
                $(this).addClass('fz');
                clearTimeout(fz_timer);
                fz_timer=setTimeout(function(){
                    zp_box.removeClass('fz');
                },300);
                if(ind==sunxu[win_num]){
                    win_num++;
                    setPage.number++;
                    if(win_num>=sunxu.length){
                        clearInterval(xt_timer);
                        $('.dialog_win').attr('class','dialog_win dialog win_'+gk_num);
                        $('.dialog_mask,.dialog_win').show();
                    }
                }else{
                    clearInterval(xt_timer);
                    $('.dialog_fail').children('font').text(gk_num);
                    $('.fail_num').text(setPage.number);
                    $('.dialog_mask,.dialog_fail').show();
                }
            });
        },
        //演示
        yanshi:function(fz_num,gk_num,time_num){
            $('.dialog_maskt').show();
            var zp_box=$('.zp_box').children('div');
            var fz_num=fz_num;
            var time_num=time_num;
            var sunxu=setPage.suijiNum(0,2,fz_num);
            var dc_times=1000;
            var times=dc_times*(fz_num+1);
            var gk_num=gk_num;
            var now_n=0;
            yanshi_a();
            function yanshi_a(){
                var timer=null;
                var timea=null;
                timea=setTimeout(function(){
                    if(now_n<fz_num){
                        zp_box.eq(sunxu[now_n]).addClass('fz');
                        now_n++;
                        clearTimeout(timer);
                        timer=setTimeout(function(){
                            zp_box.removeClass('fz');
                        },dc_times-500);
                        yanshi_a();
                    }
                },dc_times)
            };
            var timeb=null;
            clearTimeout(timeb);
            timeb=setTimeout(function(){
                kaishi();
            },times)
            function kaishi(){
                $('.dialog_begin').children('font').text(gk_num);
                $('.dialog_mask,.dialog_begin').show();
                $('.dialog_begin').unbind("click");
                $('.dialog_begin').on('click',function(){
                    $('.dialog_begin').children('font').text('');
                    $('.dialog_mask,.dialog_begin,.dialog_maskt').hide();
                    setPage.caozuo(sunxu,gk_num,time_num);
                });
            };
        },
        beginPlay:function(gk_num,time_num,fz_num){
            $('.dialog,.dialog_mask').hide();
            var gk_num=gk_num;
            var time_num=time_num;
            var fz_num=fz_num;
            setPage.xipai(0,4,3);
            setPage.yanshi(fz_num,gk_num,time_num);
            setPage.number = 0;
            $('.number').text(0);
            $('.xt-now').css('width','100%');
        },
        //过关
        screening:function(){
            var isfail=true;//是否显示填写信息
            //过关点击
            $('.dialog_win').on('click',function(){
                var classn=$(this).attr('class');
                switch(classn)
                {
                    case 'dialog_win dialog win_1':
                        setPage.beginPlay(2,3000,5);//5
                        break;
                    case 'dialog_win dialog win_2':
                        setPage.beginPlay(3,5000,7);//7
                        break;
                    case 'dialog_win dialog win_3':
                        setPage.beginPlay(4,7000,9);//9
                        break;
                    case 'dialog_win dialog win_4':
                        setPage.beginPlay(5,9000,11);//11
                        break;
                    case 'dialog_win dialog win_5':
                        setPage.beginPlay(6,11000,13);//13
                        break;
                    case 'dialog_win dialog win_6':
                        setPage.beginPlay(7,13000,15);//15
                        break;
                    default:
                        $('.play_con,.dialog_mask,.dialog').hide();
                        $('.number').text(setPage.number);
                        addUserScore(setPage.number);
                }
            });
            //失败点击
            $('.dialog_fail').on('click',function(){
                $('.play_con,.dialog_mask,.dialog').hide();
                $('.number').text(setPage.number);
               addUserScore(setPage.number);
            });
            //填写信息关闭
            $('.infor_close').on('click',function(){
                $('.infor').hide();
                $('.prize').show();
            })
            //告诉好友
            $('.sharebtn').on('click',function(){
                $('.dialog_mask').addClass('share_mask');
                $('.dialog_mask,.share_con').show();
            })
            //关闭分享
            $('.dialog_mask').on('click',function(){
                if($(this).hasClass('share_mask')){
                    $('.dialog_mask').removeClass('share_mask');
                    $('.dialog_mask,.share_con').hide();
                }else{
                    return false;
                }
            })
            //再玩一次
            $('.atplay').on('click',function(){
                $('.prize').hide();
                $('.play_con').show();
                setPage.beginPlay(1,2000,3);
            })
            //点击排行
            $('.phbbtn').on('click',function(){
                $.post("/activity/zlmc/findRanking.do", {topNum: 20}, function (data) {
                    if (data != "") {
                        var userRankObj = eval("(" + data + ")");
                        $('#rankingInfo').empty();
                        var resultArray = userRankObj.result;
                        for (var i=0 ; i < resultArray.length;i++){
                            var rankingLi = '<li><span>'+resultArray[i].order+'</span><cite><img src="'+resultArray[i].headimgurl+'" alt=""></cite><font>'+resultArray[i].nickname+'</font><em>'+resultArray[i].score+'</em></li>';
                            $('#rankingInfo').append(rankingLi);
                        }
                    }
                })
                $('.dialog_mask,.phb_con').show();
            })
            //关闭排行榜
            $('.phb_close').on('click',function(){
                $('.dialog_mask,.phb_con').hide();
            })
            //提交
            $('.submit').on('click',function(){
                var name_val=$(".name").val();
                var weixin_val=$(".weixin").val();
                var iphone_val=$(".iphone").val();
                var dezhi_val=$(".dizhi").val();
                $.post("/activity/zlmc/addUserInfo.do", {openid: openId,username:name_val,nickname:weixin_val,telephone:iphone_val,address:dezhi_val}, function (data) {
                    if (data != "") {
                        var userRankObj = eval("(" + data + ")");
                        var result = userRankObj.result;
                        $('.infor').hide();
                        $('.prize').show();
                    }
                })
            })
        },
        //点击音乐
        musicBtn:function(){
            $('.audio_btn').show();
            var music_t=false;
            var audioBtn=document.querySelector('#audioBtn');;
            $('.audio_btn').on('click',function(){
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
        },
        //上滑事件
        _swipe:function(direction,callback){
            var mmb = document.querySelectorAll('.indexPage');
            var startX, startY, endX, endY,touch;
            for (var i = 0; i < mmb.length; i++) {
                mmb[i].addEventListener('touchstart', function () {
                    var e = event || window.event;
                    e.preventDefault();
                    if (e.targetTouches.length == 1) {
                        touch = event.targetTouches[0];
                        startX = touch.pageX;
                        startY = touch.pageY;
                    }
                }, false);
                mmb[i].addEventListener('touchmove', function () {
                    var e = event || window.event;
                    e.preventDefault();
                    if (e.targetTouches.length == 1) {
                        touch = event.targetTouches[0];
                        endX = touch.pageX;
                        endY = touch.pageY;
                    };
                }, false);
                mmb[i].addEventListener('touchend', function () {
                    var x, y;
                    x = ~~(endX - startX);
                    y = ~~(endY - startY);
                    if(direction=='up'){
                        if (Math.abs(y) > Math.abs(x)) {
                            if (y >10) {
                                return false;
                            } else {
                                callback();
                            }
                        }
                    }else{
                        if (Math.abs(y) > Math.abs(x)) {
                            if (y < 0) {
                                return false;
                            } else {
                                callback();
                            }
                        }
                    }
                }, false);
            };
        },
    };
    setPage.init();
})