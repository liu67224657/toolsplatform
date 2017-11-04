$(document).ready(function(){
    $('a[name=joyme-appdownload]').each(function(){
        var aid = $(this).attr('aid');
        if(aid.length > 0){
            parseAppDownload(this, aid);
        }
    });
});
function parseAppDownload(dom, aid) {
    $.ajax({
        url: "http://api.joyme.com/json/my/hotapp/getapp",
        type: "post",
        data: {appid: aid},
        dataType: "jsonp",
        jsonpCallback: "getappdownloadcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == '1') {
                var result = resMsg.result;
                if(result != null){
                    $(dom).attr('name', 'idfa_downloadlink');
                    $(dom).attr('data-aid', result.appid);
                    $(dom).attr('href', result.url);
                }
            }
        }
    });
}
