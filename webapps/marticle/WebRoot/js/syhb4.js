$(document).ready(function () {

    $(".zan-btn").on('touchstart', function () {
        //$(this).find("b").addClass("active");
        $(".zan-btn b").addClass("active");
        var archivid = window.localStorage.getItem("agree_"+unikey);
        //存在
        if(typeof (archivid)!="undefined" && archivid!=null ){
           $(".tip").addClass("show");
            var timer=setTimeout(function(){
                clearTimeout(timer);
                $('.tip').removeClass('show');
            },1000);
            return false;
        }
        agreecheat();
    });


    try{
        var isagree = window.localStorage.getItem("agree_"+unikey);
        if(typeof (isagree)!="undefined" && isagree!=null ){
            $(".zan-btn b").addClass("active");
        }

        JParam = _jclient.getJParam();
    }catch (e){
        JParam ="";
    }

    try{
        getcheat();
        $(".re-article-main a").on('touchstart',function(){
            _jclient.jump('jt=23&ji='+$(this).attr("jump"));
        })
    }catch(e){

    }
});

var host = window.location.host.substr(window.location.host.indexOf('.'));
//host = ".joyme.dev";
function getcheat() {
    $.ajax({
        url: "http://api" + host + "/joymeapp/gameclient/webview/marticle/getcheat?archiveid=" + unikey+"&JParam="+JParam,
        type: "get",
        dataType: "jsonp",
        jsonpCallback: "getcheatcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if(result!=""){
                    var readNum = parseInt(result.read_num);
                    $("#readTop,#readBottom").html(readNum);
                    $("#agreeTop,#agreeBottom").html(result.agree_num);
                }else{
                    $("#readTop,#readBottom").html(0);
                    $("#agreeTop,#agreeBottom").html(0);
                }
            } else {
                return;
            }
        }
    });
}


function agreecheat() {
    var agreenum = parseInt($("#agreeTop").html());
    $("#agreeTop,#agreeBottom").html(agreenum+1);
    $.ajax({
        url: "http://api" + host + "/joymeapp/gameclient/webview/marticle/agreecheat?archiveid=" + unikey+"&JParam="+JParam,
        type: "get",
        async: false,
        dataType: "jsonp",
        jsonpCallback: "agreecheatcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                window.localStorage.setItem("agree_"+unikey,unikey);
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