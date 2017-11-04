$(document).ready(function () {
    $('p[name=joyme-download]').each(function () {
        var gid = $(this).attr('id');
        if (gid.length > 0) {
            parseGameDownload(this, gid);
        }
    });
});
function parseGameDownload(dom, gid) {
    $.ajax({
        url: "http://api.joyme.com/json/gamedb/getgamedbbyid",
        type: "post",
        data: {gamedbid: gid},
        dataType: "jsonp",
        jsonpCallback: "getgamedownloadcallback",
        success: function (req) {
            var resMsg = req[0];
            if (resMsg.rs == 1) {
                var result = resMsg.result;
                if (result != null) {
                    var platformHtml = '';
                    if (result.gameDevice != null && result.gameDevice.length > 0) {
                        for (var i = 0; i < result.gameDevice.length; i++) {
                            platformHtml += result.gameDevice[i];
                            if (i < result.gameDevice.length - 1) {
                                platformHtml += ',';
                            }
                        }
                    }

                    var catHtml = '';
                    if (result.gameCategroy != null && result.gameCategroy.length > 0) {
                        for (var i = 0; i < result.gameCategroy.length; i++) {
                            catHtml += result.gameCategroy[i];
                            if (i < result.gameCategroy.length - 1) {
                                catHtml += '/';
                            }
                        }
                    }
                    if (catHtml.length > 15) {
                        catHtml = catHtml.substring(0, 15) + '..'
                    }

                    var sizeHtml = '';

                    var iphoneUrl = '';
                    var androidUrl = '';
                    var ipadUrl = '';

                    var html = '<div class="game-data">' +
                        '<h2 class="fl">' +
                        '<a>' +
                        '<img title="" alt="" src="' + result.gameIcon + '"/>' +
                        '</a>' +
                        '</h2>' +
                        '<ul class="fl">' +
                        '<li class="l-1"><span>游戏平台：</span>' + platformHtml + '</li>' +
                        '<li class="l-2"><span>游戏类型：</span>' + catHtml + '</li>' +
                        '<li class="l-3"><span>发行厂商：</span>' + result.gamePublishers + '</li>' +
                        '<li class="l-4"><span>游戏大小：</span>' + sizeHtml + '</li>' +
                        '<li class="l-5"><span>更新时间：</span>' + result.gamePublicTime + '</li>' +
                        '<li class="l-6"><span>游戏评分：</span>' + result.gameRate + '</li>' +
                        '</ul>' +
                        '<dl class="fl">' +
                        '<dt>游戏下载</dt>' +
                        '<dd>' +
                        '<a class="a-dw" href="' + iphoneUrl + '">iphone下载</a>' +
                        '<a class="a-dw" href="' + ipadUrl + '">ipad下载</a>' +
                        '<a class="i-dw" href="' + androidUrl + '">安卓版下载</a>' +
                        '</dd>' +
                        '</dl>' +
                        '</div>';
                    //String iphoneURL = gameService.getDownloadLink(bean, GameDownloadService.IPHONE, GameDownloadService.DEFAULT_CHANNEL);
                    //String ipadURL = gameService.getDownloadLink(bean, GameDownloadService.IPAD, GameDownloadService.DEFAULT_CHANNEL);
                    //String androidURL = gameService.getDownloadLink(bean, GameDownloadService.ANDROID, GameDownloadService.DEFAULT_CHANNEL);
                    $(dom).html(html);
                }
            }
        }
    });
}
