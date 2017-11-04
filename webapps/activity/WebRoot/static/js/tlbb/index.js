/**
 * Created by renhuimin on 16/9/26.
 */
var tianlongbabu = (function (){
    var dataNum = 1;
    function init(){
        $.post("/weixinop/tlbb/getAllDate.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                setDate(dataNum,userRankObj.result.split(";"));
            }
        });

        $.post("/weixinop/tlbb/checkSign.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs=="1"){
                    $('.btn').attr('src','http://static.joyme.com/mobile/tlbb/img/btn-not.png');
                }else {
                    $('.btn').attr('src','http://static.joyme.com/mobile/tlbb/img/btn.png');
                }
            }
        });
        $.post("/weixinop/tlbb/getSignDates.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs=="1"){
                    $('.top-pd span').html(userRankObj.result);
                }else {
                    $('.top-pd span').html(0);
                }
            }
        });
    }

    //签到接口
    function past(){
        $.post("/weixinop/tlbb/sign.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
               if (rs=="1"){
                   modifyTotalScore();
                   $('.btn').attr('src','http://static.joyme.com/mobile/tlbb/img/btn-not.png');
               }else {
                   $('.popup,.lost').show();
                   $('.btn').attr('src','http://static.joyme.com/mobile/tlbb/img/btn.png');
               }
               init();
            }
        });
    }
    $('.close').click(function (){
        $('.popup').hide();
        $('.popup>div').hide();
    });
    function modifyTotalScore() {
        $.post("/weixinop/signHistory/getTotalScore.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs=="1"){
                    $('#totalScore').html(userRankObj.result);
                    $('.popup,.success').show();
                }else {
                    $('#totalScore').html(10);
                }
            }
        });
    }
    function dateStyle(){
        var dateMain= $('.getDate').width()-15;
        var r = dateMain/7;
        $('.getDate span').css({
            'height':r,
            'width':r,
            'line-height':r+'px'
        });
    }
    function setDate(n , data){
        $('.getDate').html('')
        var day = show(n);
        $('.day p').text(day[0]+'年'+day[1]+'月');
        for(var i = 0 ; i < getFirstDayInMonth(day[0],day[1]); i++ ){
            $('.getDate').append('<span style="visibility: hidden">1</span>');
        }

        for(var i = 1 ; i <= getDateNum(day[0],day[1]); i++ ){
            if(i<day[2]){
                if(data[i-1] == 1){
                    $('.getDate').append('<span class="detes weiqian">'+i+'</span>');
                }else{
                    $('.getDate').append('<span class="detes">'+i+'</span>');
                }
            }else if(i == day[2] && data[i-1] == 1){
                $('.getDate').append('<span class="detes qiandao">'+i+'</span>');
            }else{
                $('.getDate').append('<span class="detes">'+i+'</span>');
            }

        }
        dateStyle();
    }
    //年月日
    function show(n){
        var str = [];
        var mydate = new Date();
        var Year = mydate.getFullYear();
        var Month = mydate.getMonth()+n;
        var r = mydate.getDate();
        if(Month > 12){
            Year = Year+1;
            Month = 1;
        }else if(Month < 1){
            Year = Year-1;
            Month = 12;
        }
        str.push(Year)
        str.push(Month)
        str.push(r)
        return str;
    }
    //当月天数
    function getDateNum(year ,month){
        return new Date(year, month, 0).getDate();
    }
    //单月第一天星期几
    function getFirstDayInMonth(year,month){
        return new Date(year,month-1,1).getDay();
    }
    $('.arrow-right').click(function (){
        dataNum = dataNum+1;
        setDate(dataNum);
    })
    $('.arrow-left').click(function (){
        dataNum = dataNum-1;
        setDate(dataNum);
    })
    return {
        past:past,
        init:init
    }
})();