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


var host = window.location.host.substr(window.location.host.indexOf('.'));
//host=".joyme.dev";
$(document).ready(function() {
    try{
        window.login='http://passport'+host+'/auth/loginwappage';
        window.uno = getCookie('jmuc_uno');
        window.uid = getCookie('jmuc_u');
        window.token = getCookie('jmuc_token');
        window.timestamp = getCookie('jmuc_t');

        var jsonParam = {
            title: title,
            pic: "",
            description: "",
            uri: uri
        }
        if(typeof(unikey)!="undefined" && typeof(domain)!="undefined"){
            getCommentList(unikey, domain , jsonParam);
        }
    }catch(e){
    }
});
// wiki文章页点评论跳转
function toComment(obj){
    var opinionUri="http://marticle"+host+"/marticle/comment/2/"+unikey+"/list.do";
    //var opinionUri="http://172.16.76.54:8081/marticle/comment/2/"+unikey+"/list.do";
   //var opinionUri="http://172.16.75.30:28080/marticle/comment/2/"+unikey+"/list.do";
    if($(obj).attr('id') == 'commentSum'){
        location.href = opinionUri;
        return;
    }
    if (window.uno == null || window.uno.length == 0 || window.uid == null || window.uid.length == 0 || window.token == null || window.token.length == 0) {
        var url = window.login+'?reurl='+encodeURI(opinionUri);
        location.href = url;
    }else{
        location.href = opinionUri;
        return;
    }
}
function getCommentList(unikey, domain, jsonparam) {
    $.ajax({
        url: "http://api"+host+"/jsoncomment/reply/query",
        type: "post",
        async: true,
        data: {unikey:unikey,domain:domain,jsonparam:JSON.stringify(jsonparam),pnum:1,psize:1},
        dataType: "jsonp",
        jsonpCallback: "querycallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if(result.comment_sum>0){
                    $("#commentSum").text("已有"+result.comment_sum+"条评论");
                }
            } else {
                return;
            }
        },
        error: function () {
            alert('获取失败，请刷新');
        }
    });
}