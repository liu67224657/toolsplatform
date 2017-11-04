$.cookie = function(name, value, options) {
    if (typeof value != 'undefined') {
        options = options || {};
        if (value === null) {
            value = '';
            options = $.extend({}, options);
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString();
        }
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else {
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

$(document).ready(function() {
    var isRefresh = $.cookie('isRefresh');
    var dateTime = $("#wikiupdatetime").html();
    var cookieId = $("#cookieId").val();
    if (isRefresh == '' || isRefresh == null) {
        $.cookie('isRefresh', dateTime, {expires:10});
    } else {
        if (parseInt(isRefresh) == parseInt(dateTime)) {
            if (cookieId != null || cookieId != '') {
                var cookieIdValue = cookieId.split('|');
                for (var i = 0; i < cookieIdValue.length; i++) {
                    if ($.cookie(cookieIdValue[i]) != null) {
                        $("#" + cookieIdValue[i]).remove();
                    }
                }
            }
        } else {
             $.cookie('isRefresh', dateTime, {expires:10});
              if (cookieId != null || cookieId != '') {
                var cookieIdValue = cookieId.split('|');
                for (var i = 0; i < cookieIdValue.length; i++) {
                    $.cookie(cookieIdValue[i], null);
                }
            }
        }
    }
});

function createCookie(id) {
    var cookieValue = $.cookie(id);
    if (cookieValue == null || cookieValue == '') {
        $.cookie(id, id, {expires:10});
        $("#" + cookieValue).remove();
    }
}