$(document).ready(function () {
    var locationhref = window.location.href;
    $("#nextpage").bind('click', function () {
        $.ajax({
            url: "http://cmsimage.joyme.alpha/article/page/nextpage.do",
            data: {href: locationhref},
            type: "post",
            dataType: "jsonp",
            jsonpCallback: "nextpage",
            success: function (req) {
                if (req[0].rs == 1) {
                    locationhref = req[0].href;
                    $(".special-article").append(req[0].body);
                } else if (req[0].rs == 2) {
                    alert("没有了");
                } else if (req[0].rs == 0) {
                    alert("系统繁忙");
                }
            }
        });
    });
});