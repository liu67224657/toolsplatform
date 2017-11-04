$(document).ready(function () {
    var locationHref = window.location.href;
    var block = false;
    $("#moreArticle").on('click', function () {
        if(block){
            return false;
        }
        block = true;
        $.ajax({
            url: "http://webcache.joyme.com/article/page/nextpage.do",
            data: {href: locationHref},
            type: "post",
            dataType: "jsonp",
            jsonpCallback: "nextpagecallback",
            success: function (req) {
                if (req[0].rs == 1) {
                    locationHref = req[0].href;
                    $("#tuwenwrapper").append(req[0].body);
                    $('.lazy').lazyImg();
                    block = false;
                } else if (req[0].rs == 2) {
                    $('#moreArticle').html('木有更多啦');
                    block = true;
                    return false;
                } else if (req[0].rs == 0) {
                    alert("系统繁忙");
                    block = false;
                    return false;
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                alert('加载错误啦');
                block = false;
                return false;
            }
        });
    });
});