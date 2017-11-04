var xinjihuoxian = (function (){

    var people = new Object();
    people.peopleTrue = '<img class="people" src="http://static.joyme.com/mobile/xinjihuoxian/img/people.jpg">';
    people.peopleFlase = 'http://static.joyme.com/mobile/xinjihuoxian/img/nopeople.jpg';
    people.number = 0;
    people.isNumber = 1;
    people.mark = 0;
    people.value = 0;
    people.TIME = 3000;
    people.timeId = true;

    var endArr= ['http://static.joyme.com/mobile/xinjihuoxian/img/end-y-1.png','http://static.joyme.com/mobile/xinjihuoxian/img/end-y-2.png','很明显你就是那个万中无一的高手，没有迷失在拉拉队的妹纸之中，成功地找到了怪物一族的不法分子，拯救了奥运会。像你这样多读书、多看报、少吃零食、多睡觉的四好青年，我们只想跟你说五个字：请联系我！','本以为你是一个骨骼清奇、天赋秉异、智力超群的少年，一定能够轻松完成任务。但你居然天天偷看“小电影”导致眼力退化，没能及时发现怪物，还好有别的指挥官及时发现，才没有发生太大的损失。失败的你还不赶快去回炉重造。','http://static.joyme.com/mobile/xinjihuoxian/img/end-icon2.png','http://static.joyme.com/mobile/xinjihuoxian/img/end-icon4.png','http://static.joyme.com/mobile/xinjihuoxian/img/end-icon3.png','http://static.joyme.com/mobile/xinjihuoxian/img/end-icon5.png'];
    var timeId;
    var sum = ['2','3','4','4','5','5','6','6','7','7','8','8','9','9','10','10','11','11','12','12','13','13','14','14','15'];
    function init(){
        //跳转第二页
        $('.joyme-game-mailer-box').click(function () {
            $('.joyme-game-mailer').fadeOut();
            $('.joyme-game-notice-box').fadeIn();
        });
        //显示二维码
        $('.joyme-game-show-code').click(function (){
           /* $('.code').fadeIn();
            $('.code').click(function (){
                $(this).fadeOut();
            })*/
            window.location.href='https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzNzIxNTY4Mw==&from=singlemessage&isappinstalled=0#wechat_redirect';
        });
        //分享指示图
        $('.joyme-game-show-share').click(function (){
            $('.share').fadeIn();
            $('.share').click(function (){
                $(this).fadeOut();
            })

        });
    }
    //音乐
    var newAu=new Audio();
    function newAudi(){
        newAu.src = 'http://static.joyme.com/mobile/xinjihuoxian/media/music.mp3';
        newAu.volume = 0.3;
        newAu.loop = true;
        newAu.play();
    }
    $('.joyme-game-answer-music').click(function(){
        if(newAu.paused){
            newAu.play();
            people.value=0;
        }else{
            newAu.pause();
            people.value=1;
        }
        if(people.value==1){
            $('.joyme-game-answer-music').css({'background':'url(http://static.joyme.com/mobile/xinjihuoxian/img/music-clear.png)','background-size':'100% 100%'});
        }else{
            $('.joyme-game-answer-music').css({'background':'url(http://static.joyme.com/mobile/xinjihuoxian/img/music.png)','background-size':'100% 100%'});
        }
    })

    //改变图片大小
    function answer(n){
        var answerHeight = $('.joyme-game-answer-bg').height();
        var answerImg = document.body.clientWidth * 0.9*0.9;
        $('.joyme-game-answer-img').css({'top':(answerHeight - answerImg ) /2 })
        $('.joyme-game-answer-img').css({'height':answerImg})

        $('.joyme-game-answer-img img').css({'width':Math.floor( answerImg/n ),'height':answerImg/n})
    }

    //过关增加图片数量/随机出现怪物
    function getAnswer(number){
        clearHtml();
        number > 15 ? 15 : number ;
        var num = number*number;
        for (var index = 0, len = num; index < len; index++) {
            $('.joyme-game-answer-img').append(people.peopleTrue);
        }
        answer(number);
        var ran = getRandom(num);
        $('.joyme-game-answer-img img').eq(ran).attr('src' ,people.peopleFlase).addClass('nopeople');
    }

    //随机函数
    function getRandom(n){
        return Math.floor(Math.random()*n)
    }

    //清除
    function clearHtml(){
        $('.joyme-game-answer-img').html('');
    }

    //倒计时
    $.extend({
        a:function(){
            people.timeId =false;
            if( people.TIME <= 0){
                clearInterval( timeId );
                if(people.mark >= 70){
                    endGame(0);
                }else{
                    endGame(1);
                }
            }else{
                people.TIME--;
            }
            $('.joyme-game-answer-time').html( timeString(people.TIME) );
        }
    });
    //进入下一关
    $('.joyme-game-answer-img').on('click' , '.people' ,function (){
        if(!$(this).hasClass('nopeople')){
            clearInterval( timeId );
            endGame( people.mark >= 70 ? 0 : 1);
        }else{
            if(people.timeId){
                timeId = setInterval("$.a("+people.TIME+")",10);
            }
            people.mark = people.mark + 5;
            $('.joyme-game-answer-mark').html( people.mark );
            people.number++;
            if( people.number > 24 ){
                people.number = 24;
            }
            getAnswer(sum[people.number]);
        }
    });
    //字符转换
    function timeString(time){
        var timeLen = String(time);
        if(timeLen.length == 4){
            var str = timeLen.substring(0,2);
            var end = timeLen.substring(2,4);
            return  str+' : '+end;
        }
        if(timeLen.length == 3){
            var str = timeLen.substring(0,1);
            var end = timeLen.substring(1,3);
            return  '0'+str+' : '+end;
        }
        if(timeLen.length == 2){
            return '00 : '+people.TIME;
        }
        if(timeLen.length == 1){
            return '00 : 0'+people.TIME;
        }
    }

    //游戏结果
    function endGame( n ){
        newAu.pause();
        people.value=1;
        $('#mark').html( people.mark );

        //提交分数 获取排名 people.mark分数
        //$.getJSON('http',{ mark : people.mark}, function(data){
        //      $('#ranking').html( data.mark );
        //});

        $.post("/activity/starline/findOrderByScore.do", {openId: openId,score:people.mark}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var userRank = userRankObj.result;
                $('#ranking').html( userRank );
            }
        })
        setDescByScore(people.mark);
        $('.joyme-game-end').show();
        $('.joyme-game-answer').hide();
        $('.joyme-game-end-tips').attr('src',endArr[ n ]);
        $('.joyme-game-end-test span').html( endArr[ n+2 ] );
        $('.joyme-game-show-again').attr('src',endArr[ n+4 ]);
        $('.joyme-game-show-code').attr('src',endArr[ n+6 ]);

        //var s= $('.joyme-game-end-bg').height();
        //var y= $('.joyme-game-end-box').height();
        //$('.joyme-game-end-btn').css({'margin-top':(s - y)/2+10 });
        $('.joyme-game-end-box').css({'height':$('.joyme-game-end-bg').height()+6});
    }

    function sizeBox(){
        var client = window.screen.height;
        if(client < 600 && client > 500){
            $('.joyme-game-notice-box,.joyme-game-end-box,.joyme-game-ranking-box').css({'height':client*0.7,'margin-top':client*0.15});
            $('.joyme-game-answer').css({'margin-top':client*0.15})
            $('.joyme-game-notice-test').css({'margin-top':'6rem'});
            $('.joyme-game-end-yq').css({'margin-top':'1rem'});
            $('.t-right').css({'margin-top':'0.2rem'})
        }
        if(client < 500){
            $('.joyme-game-notice-box,.joyme-game-end-box,.joyme-game-ranking-box').css({'height':client*0.7,'margin-top':client*0.1145});
            $('.joyme-game-notice-img').css({'height' : 'auto'});
            $('.joyme-game-notice-test,.joyme-game-end-test').css({'font-size':'1.3rem'});
            $('.joyme-game-notice-btn').css({'bottom':'-7rem'})
            $('.joyme-game-answer').css({'margin-top':client*0.15})
            $('.joyme-game-notice-test').css({'margin-top':'6rem'});
            $('.joyme-game-end-yq').css({'margin-top':'1rem'});
            $('.t-right').css({'margin-top':'0.2rem'})
        }
    }

    //获取排行榜
    function getRanking(){
        //
        //$.getJSON('http', function(data){
        //    var str = '';
        //    for (var i = 0 ; i < data.list.length ; i++){
        //        str+='<ul>\
        //                <li>01</li>\
        //                <li><img src="../img/people.jpg"></li>\
        //                <li>小强者</li>\
        //                <li>95</li>\
        //                <div class="joyme-game-ranking-hover">\
        //                <img src="../img/arr.png">\
        //                </div>\
        //              </ul>';
        //    }
        //    $('.joyme-game-ranking-user-info').html(str);
        //    showRankingList(); 数据调用成功函数 showRankingList
        //});
        $.post("/activity/starline/findTopHundred.do", {}, function (data) {
            if (data != "") {
                var userOrderObj = eval("(" + data + ")");
                var userOrderArray = userOrderObj.result;
                var innerHtml = '';
                var count = userOrderArray.length;
                for (var i = 0; i < count; i++) {
                    var userOrder = userOrderArray[i].split("---");
                    innerHtml+='<ul>'+
                        '<li>' + userOrder[0] + '</li>'+
                        '<li><img src="' + userOrder[1] + '"></li>' +
                        '<li>' + userOrder[2] + '</li>'+
                        '<li>' + userOrder[3] + '</li>'+
                        '<div class="joyme-game-ranking-hover">'+
                        '<img src="http://static.joyme.com/mobile/xinjihuoxian/img/arr.png">'+
                        '</div>'+
                        '</ul>';
                }
                $('.joyme-game-ranking-user-info').html(innerHtml);
                showRankingList(); //数据调用成功函数
            }
        })

        showRankingList();
        //touchstart
        $('.joyme-game-ranking-user-info').on('touchstart','ul',function(){
            $(this).addClass('curr');
            $(this).find('.joyme-game-ranking-hover').show();
        });
        $('.joyme-game-ranking-user-info').on('touchend','ul',function(){
            $(this).removeClass('curr');
            $(this).find('.joyme-game-ranking-hover').hide();
        });
    };
    //显示排行榜
    function showRankingList(){
        $('.joyme-game-ranking').show();
        $('.joyme-game-end').hide();
        $('.joyme-game-ranking .joyme-game-end-btn').css({'margin-top':'7px'});
        var scroll = $('.joyme-game-ranking-bg').height();
        var xh = $('.joyme-game-ranking-scrool').height();
        $('.viewport').css({'height' : scroll - xh-5});
        $('.joyme-game-ranking-scrool').parent().css({'height':$('.joyme-game-ranking-scrool').height()})
        var LayerScroll = new IScroll(".viewport", {
            scrollbars:true ,
            fadeScrollbars:true
        })
        $('.iScrollVerticalScrollbar').hide();
    }
    //开始游戏
    function startGame(){
        $('.joyme-game-notice-box').fadeOut();
        $('.joyme-game-answer').fadeIn();
        $('.joyme-game-answer-mark').html( people.mark );
        newAudi();
        getAnswer(sum[people.number++]);
        $('.joyme-game-answer-time').html('30:00');
        //timeId = setInterval("$.a("+people.TIME+")",10);

        $('.joyme-game-answer-head').css
        ({
            'height':$('.joyme-game-answer-head').width()
        });
        $('.joyme-game-answer-time').css
        ({
            'height':$('.joyme-game-answer-head').width()-4,
            'margin-top':'2px',
            'line-height':$('.joyme-game-answer-head').width()-4+'px'
        });
        $('.joyme-game-answer-music').css
        ({
            'height':$('.joyme-game-answer-head').width()
        })
        $('.joyme-game-answer-mark').css
        ({
            'height':$('.joyme-game-answer-head').width(),
            'line-height':$('.joyme-game-answer-head').width()-4+'px'
        })
    };
    //游戏重置
    function  againGame(){
        people.number = 0;
        people.isNumber = 1;
        people.mark = 0;
        people.value = 0;
        people.TIME = 3000;
        people.timeId = true;
        $('.warp>div').hide();
        $('.joyme-game-answer').show();
        $('.joyme-game-answer-mark').html('0');
        $('.joyme-game-answer-music').css({'background':'url(http://static.joyme.com/mobile/xinjihuoxian/img/music.png)','background-size':'100% 100%'});
        setDescByScore(0);
        startGame();
    }
    //关闭排行榜
    $('.joyme-game-ranking-close').click(function (){
        $('.joyme-game-ranking').hide();
        $('.joyme-game-end').show();
    });
    //pad适配
    function isPad(){
        var pad = window.screen.height;
        if(pad > 1000){
            $('.joyme-game-mailer').css({'top':'-100px'});
            $('.joyme-game-notice-box').css({'top':'100px'});
            $('.joyme-game-notice-img,.joyme-game-end-bg').css({'height':'600px'});
            $('.joyme-game-notice-test,.joyme-game-end-test').css({'font-size':'2.5rem','line-height':'6.5rem'});
            $('.joyme-game-notice-test span,.joyme-game-end-test span').css({'line-height':'4.5rem'});
            $('.joyme-game-ranking-box').css({'width':'84%','margin-left':'8%'});
            $('.joyme-game-end-bg').css({'height':'650px'});
            $('.joyme-game-end-box').css({'margin-top':'13rem'});
            $('.logo').css({'width':'40%'});
        }
    }
    isPad();
    return {
        init:init,
        startGame:startGame,
        endGame:endGame,
        getRanking:getRanking,
        sizeBox:sizeBox,
        againGame:againGame
    }

})();