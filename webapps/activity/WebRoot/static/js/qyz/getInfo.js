/**
 * Created by renhuimin on 16/9/26.
 */

    var userDate = {};
        userDate.cdk='';
    var LevelArr= [];

    //地图页面3个关卡状态
    function gameLevel(){
        $.post("/activity/qyzGame/gameList.do", {openid: openId}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var gameArray = userRankObj.result;
                if (gameArray.firstGame!="-1"){
                    LevelArr.push('game1')
                }
                if (gameArray.secondGame!="-1"){
                    LevelArr.push('game2')
                }
                if (gameArray.thirdGame!="-1"){
                    LevelArr.push('game3')
                }
                if (gameArray.cdkCode!="-1"){
                    userDate.cdk =gameArray.cdkCode;
                    if(userDate.cdk.length > 10){
                        $('.cdk p').html(gameArray.cdkCode);
                        $('.cdk span').remove();
                    }else{
                        $('.set-cdk').html(gameArray.cdkCode);
                    }

                }
                setUserlevel(); //成功调用
            }
        });

    }
    function setUserlevel(){
        switch(LevelArr.length){
            case 0:
                $('.page-2 .gk').eq(0).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon1-b.png');
                break;
            case 1:
                $('.page-2 .gk').eq(0).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon1-c.png');
                $('.page-2 .gk').eq(1).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon2-b.png');
                break;
            case 2:
                $('.page-2 .gk').eq(0).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon1-c.png');
                $('.page-2 .gk').eq(1).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon2-c.png');
                $('.page-2 .gk').eq(2).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon3-b.png');
                break;
            default:
                $('.page-2 .gk').eq(0).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon1-c.png');
                $('.page-2 .gk').eq(1).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon2-c.png');
                $('.page-2 .gk').eq(2).removeClass('game-icon-not').attr('src','http://static.joyme.com/mobile/qyz/images/page-2-icon3-c.png');
                break;
        }

        $('.page-2 .gk').click(function (){
            if(!$(this).hasClass('game-icon-not')){
                var code = getQueryString('code');
                if ($(this).attr('data-h')=="1"){
                    window.location.href = '/activity/qyzFirst/game.do?code='+code;
                }else if ($(this).attr('data-h')=="2"){
                    window.location.href = '/activity/qyzSecond/game.do?code='+code;
                }else if ($(this).attr('data-h')=="3"){
                    window.location.href = '/activity/qyzThird/game.do?code='+code;
                }
            }
        });
        $('.bonuses').click(function (){
            $('.window').show();
            $('.window>div').hide();
            if(userDate.cdk != ''){
                $('.cdk').show();
                $('.set-cdk').html();
            }else{
                $('.cdk-points').show();
            }
        });
        $('.close-rule,.close-tips').click(function (){
            $('.window').hide();
        })

    }
    function getQueryCode(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    var code = getQueryCode('code');
    //游戏一
    function setGamePassed1(){
        $.post("/activity/qyzGame/saveGame.do", {openid: openId,gameLevel:"1"}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs =='1'){
                    //success
                }
            }
        });
    }
    //游戏2
    function setGamePassed2(){
        $.post("/activity/qyzGame/saveGame.do", {openid: openId,gameLevel:"2"}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs =='1'){
                    //success
                }
            }
        });
    }
    //游戏3
    function setGamePassed3(){
        $.post("/activity/qyzGame/saveGame.do", {openid: openId,gameLevel:"3"}, function (data) {
            if (data != "") {
                var userRankObj = eval("(" + data + ")");
                var rs = userRankObj.rs;
                if (rs =='1'){
                    //success
                }
            }
        });

        setTimeout(function (){
            window.location.href = '/activity/qyzEnd/game.do?code=' + code;
        },5000)
    }

