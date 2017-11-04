$(document).ready(function () {
    var joymeEnv = window.location.host.substring(window.location.host.lastIndexOf('.')+1,window.location.host.length);
    var joymeEnvNoPort = joymeEnv;
    if(joymeEnv.indexOf(':') > 0){
        joymeEnvNoPort = joymeEnv.substring(0, joymeEnv.indexOf(':'));
    }
    window.www = 'http://www.joyme.' + joymeEnvNoPort + '/';
    window.api = 'http://api.joyme.' + joymeEnvNoPort + '/';
    window.passport = 'http://passport.joyme.' + joymeEnvNoPort + '/';

    try {
        var jsonParam = {
            title: title,
            pic: "",
            description: "",
            uri: uri
        }
        if (typeof(unikey) != "undefined" && typeof(comment_domain) != "undefined") {
            getCommentList(unikey, comment_domain, jsonParam);
        }
    } catch (e) {
    }
});

function getCommentList(unikey, domain, jsonparam) {
    $.ajax({
        url: window.api + "jsoncomment/reply/query",
        type: "post",
        async: true,
        data: {unikey: unikey, domain: domain, jsonparam: JSON.stringify(jsonparam), pnum: 1, psize: 1},
        dataType: "jsonp",
        jsonpCallback: "querycallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                $("#replynum").text(result.comment_sum);
            } else {
                $('#reply_error').html(resMsg.msg);
                return;
            }
        }
    });
}