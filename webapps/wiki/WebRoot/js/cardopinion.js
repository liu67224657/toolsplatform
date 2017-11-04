jQuery.extend({
    handleError: function( s, xhr, status, e ) 		{
        // If a local callback was specified, fire it
        if ( s.error ) {
            s.error.call( s.context || s, xhr, status, e );
        }
        // Fire the global callback
        if ( s.global ) {
            (s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );
        }
    },
    createUploadIframe: function(id, uri) {
        var frameId = 'jUploadFrame' + id;
        if(window.ActiveXObject) {
            if(jQuery.browser.version=="9.0") {
                io = document.createElement('iframe');
                io.id = frameId;
                io.name = frameId;
            }else if(jQuery.browser.version=="6.0" || jQuery.browser.version=="7.0" || jQuery.browser.version=="8.0") {
                var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
                if(typeof uri== 'boolean'){
                    io.src = 'javascript:false';
                }
                else if(typeof uri== 'string'){
                    io.src = uri;
                }
            }
        }else {
            var io = document.createElement('iframe');
            io.id = frameId;
            io.name = frameId;
        }
        io.style.position = 'absolute';
        io.style.top = '-1000px';
        io.style.left = '-1000px';
        document.body.appendChild(io);
        return io;
    },
    ajaxUpload:function(s,xml){
        //if((fromFiles.nodeType&&!((fileList=fromFiles.files)&&fileList[0].name)))
        var uid = new Date().getTime(),idIO='jUploadFrame'+uid,_this=this;
        var jIO=$('<iframe name="'+idIO+'" id="'+idIO+'" style="display:none">').appendTo('body');
        var jForm=$('<form action="'+s.url+'" target="'+idIO+'" method="post" enctype="multipart/form-data"></form>').appendTo('body');
        var oldElement = $('#'+s.fileElementId);
        var newElement = $(oldElement).clone();
        //
        var title = '<input type="text" name="title" value="'+ s.data.title+'">';
        var opinionKey = '<input type="text" name="opinionKey" value="'+ s.data.opinionKey+'">';
        var opinionValue = '<input type="text" name="opinionValue" value="'+ s.data.opinionValue+'">';
        var nickName = '<input type="text" name="nickName" value="'+ s.data.nickName+'">';
        var contacts = '<input type="text" name="contacts" value="'+ s.data.contacts+'">';
        $(title).appendTo(jForm);
        $(opinionKey).appendTo(jForm);
        $(opinionValue).appendTo(jForm);
        $(nickName).appendTo(jForm);
        $(contacts).appendTo(jForm);

        $(oldElement).attr('id', 'jUploadFile'+uid);
        $(oldElement).before(newElement);
        $(oldElement).appendTo(jForm);

        this.remove=function() {
            if(_this!==null) {
                jNewFile.before(jOldFile).remove();
                jIO.remove();jForm.remove();
                _this=null;
            }
        }
        this.onLoad=function(){
            var data=$(jIO[0].contentWindow.document.body).text();
            try{
                if(data!=undefined){
                    data = eval('(' + data + ')');
                    try {
                        if (s.success)
                            s.success(data, status);
                        // Fire the global callback
                        if(s.global)
                            jQuery.event.trigger("ajaxSuccess", [xml, s]);
                        if (s.complete)
                            s.complete(data, status);
                        xml = null;
                    } catch(e)
                    {
                        status = "error";
                        jQuery.handleError(s, xml, status, e);
                    }
                    // The request was completed
                    if(s.global)
                        jQuery.event.trigger( "ajaxComplete", [xml, s] );
                    // Handle the global AJAX counter
                    if (s.global && ! --jQuery.active )
                        jQuery.event.trigger("ajaxStop");
                    // Process result
                }
            }catch(ex){
                alert(ex.message);
            };
        }
        this.start=function(){jForm.submit();jIO.load(_this.onLoad);};
        return this;
    },
    createUploadForm: function(id, url,fileElementId, data) {
        //create form
        var formId = 'jUploadForm' + id;
        var fileId = 'jUploadFile' + id;
        var form = jQuery('<form  action="'+url+'" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
        if(data) {
            for(var i in data) {
                jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
            }
        }
        var oldElement = jQuery('#' + fileElementId);
        var newElement = jQuery(oldElement).clone();
        jQuery(oldElement).attr('id', fileId);
        jQuery(oldElement).before(newElement);
        jQuery(oldElement).appendTo(form);

        //set attributes
        jQuery(form).css('position', 'absolute');
        jQuery(form).css('top', '-1200px');
        jQuery(form).css('left', '-1200px');
        jQuery(form).appendTo('body');
        return form;
    },
    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout
        // Create the request object
        var xml = {};
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        if(window.ActiveXObject){
            var upload =  new jQuery.ajaxUpload(s,xml);
            upload.start();

        }else{
            var id = new Date().getTime();
            var form = jQuery.createUploadForm(id,s.url, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
            var io = jQuery.createUploadIframe(id, s.secureuri);
            var frameId = 'jUploadFrame' + id;
            var formId = 'jUploadForm' + id;
            // Watch for a new set of requests
            if ( s.global && ! jQuery.active++ ) {
                jQuery.event.trigger( "ajaxStart" );
            }
            var requestDone = false;

            if ( s.global )
                jQuery.event.trigger("ajaxSend", [xml, s]);
            // Wait for a response to come back
            var uploadCallback = function(isTimeout) {
                var io = document.getElementById(frameId);
                try {
                    if(io.contentWindow) {
                        xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                        xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
                    }else if(io.contentDocument) {
                        xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                        xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
                    }
                }catch(e) {
                    jQuery.handleError(s, xml, null, e);
                }
                if ( xml || isTimeout == "timeout") {
                    requestDone = true;
                    var status;
                    try {
                        status = isTimeout != "timeout" ? "success" : "error";
                        // Make sure that the request was successful or notmodified
                        if ( status != "error" ) {
                            // process the data (runs the xml through httpData regardless of callback)
                            var data = jQuery.uploadHttpData(xml, s.dataType);
                            // If a local callback was specified, fire it and pass it the data
                            if (s.success)
                                s.success(data, status);
                            // Fire the global callback
                            if(s.global)
                                jQuery.event.trigger("ajaxSuccess", [xml, s]);
                            if (s.complete)
                                s.complete(data, status);
                        } else
                            jQuery.handleError(s, xml, status);
                    } catch(e) {
                        status = "error";
                        jQuery.handleError(s, xml, status, e);
                    }
                    // The request was completed
                    if(s.global)
                        jQuery.event.trigger( "ajaxComplete", [xml, s] );
                    // Handle the global AJAX counter
                    if (s.global && ! --jQuery.active )
                        jQuery.event.trigger("ajaxStop");
                    // Process result
                    jQuery(io).unbind();
                    setTimeout(function() {
                  try {
                        jQuery(io).remove();
                        jQuery(form).remove();

                    } catch(e)
                    {
                        jQuery.handleError(s, xml, null, e);
                    }

                    }, 100);

                    xml = null;

                }
            };
            // Timeout checker
            if (s.timeout>0)
            {
                setTimeout(function(){
                    // Check to see if the request is still happening
                    if( !requestDone ) uploadCallback("timeout");
                }, s.timeout);
            }

            try
            {

                var form = jQuery('#' + formId);
                jQuery(form).attr('action', s.url);
                jQuery(form).attr('method', 'POST');
                jQuery(form).attr('target', frameId);

                if(form.encoding)
                {
                    jQuery(form).attr('encoding', 'multipart/form-data');
                }
                else
                {
                    jQuery(form).attr('enctype', 'multipart/form-data');
                }


                jQuery(form).submit();

            } catch(e)
            {
                jQuery.handleError(s, xml, null, e);
            }

            jQuery('#'+ frameId).load(uploadCallback);
            return {abort: function () {}};

        }
    },

    uploadHttpData: function( r, type ) {

        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" ){

            eval( "data = " + $(data).html() );
        }
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();

        return data;
    }
});


function _sqClick(id){
    $('.alertBox,#ab-1').show();
    $('#ab-2,#icon-error1,#icon-error2,#icon-error3').hide();
    $("#opinionValue").val("");
    if(id==-1){
        $("#opinionKey").html("图片：");
        $("#pic").html('<input type="file" class="a_inputTxt" name="filename" id="filename" style="max-width: 170px;" onblur="checkFileChange()"/>');
        $("#sub").html('<a href="javascript:void(0)" onclick="saveq(-1)" class="a_inputBtn">提交</a>');
    }else{
        $("#pic").html('<input type="text" class="a_inputTxt" name="opinionValue" id="opinionValue"/>');
        $("#sub").html('<a href="javascript:void(0)" onclick="saveq(1)" class="a_inputBtn">提交</a>');
        var opinionKey = $("#_id"+id).html();
        $("#opinionKey").html(opinionKey+"：");
    }
    var _nickName =getCookie("_nickName");
    if(_nickName!="" && _nickName!=null){
        $("#nickName").val(unescape(_nickName));
    }
    var _contacts =getCookie("_contacts");
    if(_contacts!="" && _contacts!=null){
        $("#contacts").val(unescape(_contacts));
    }

}
function closeW(){
    $('.alertBox').hide();
    $('#ab-2').hide();
}
function setCookie(name,value){
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}


//读取cookies
function getCookie(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return (arr[2]);
    else
        return null;
}

var notEmptyValue="";
var formatValue="";
var stringleng="";
if(wikitype=="wiki"){
    notEmptyValue="不能为空";
    formatValue="格式不对";
    stringleng="字符太长";
}

function checkFileChange(){
    if($.trim($(this).val)!=""){
        $("#icon-error1").hide().html('<i class="icon-error"></i>'+notEmptyValue);
    }
}

$(document).ready(function(){
    $('.close-btn').click(function(){
        $('.alertBox').hide();
    });

    $("#nickName").bind("input propertychange",function(){
        if($.trim($(this).val)!=""){
            $("#icon-error2").hide().html('<i class="icon-error"></i>'+notEmptyValue);
        }
    });
    $("#contacts").bind("input propertychange",function(){
        if($.trim($(this).val)!=""){
            $("#icon-error3").hide().html('<i class="icon-error"></i>'+notEmptyValue);
        }
    });
});

var ImageFileExtend = ".gif,.png,.jpg,.ico,.bmp,.jpeg";
function saveq(id){
    var opinionValue = "";
    var filename =$("#filename").val();
    if(id==-1){
        if(filename.length <= 0){
            $("#icon-error1").show();
            return false;
        }else{
            $("#icon-error1").hide();
        }
        //获得文件后缀
        var photoExt=filename.substr(filename.lastIndexOf(".")).toLocaleLowerCase();
        if(ImageFileExtend.indexOf(photoExt)<=-1){
            $("#icon-error1").html('<i class="icon-error"></i>'+formatValue).show();
            return false;
        }
        $("#icon-error1").html('<i class="icon-error"></i>'+notEmptyValue).hide();
    }else{
        opinionValue= $("#opinionValue").val();
        if(jQuery.trim(opinionValue)==""){
            $("#opinionValue").focus();
            $("#icon-error1").show();
            return false;
        }else{
            $("#icon-error1").hide();
        }
    }



    //判断姓名
    var nickName = $("#nickName").val();
    if(jQuery.trim(nickName)==""){
        $("#nickName").focus();
        $("#icon-error2").show();
        return false;
    }else{
        if(nickName.length>20){
            $("#icon-error2").html('<i class="icon-error"></i>'+stringleng).show();
            return false;
        }
        $("#icon-error2").html('<i class="icon-error"></i>'+notEmptyValue).hide();
    }
    //判断联系方式
    var contacts = $("#contacts").val();
    if(jQuery.trim(contacts)==""){
        $("#contacts").focus();
        $("#icon-error3").show();
        return false;
    }else{
        if(contacts.length>20){
            $("#icon-error3").html('<i class="icon-error"></i>'+stringleng).show();
            return false;
        }
        $("#icon-error3").html('<i class="icon-error"></i>'+notEmptyValue).hide();
    }
    var opinionKey = $("#opinionKey").html().replace("：","");
    $.ajaxFileUpload({
            url: '/wiki/tools/'+wikiCode+'/'+pageid+'/saveq.do',
            secureuri: false,
            fileElementId: 'filename',
            data:{title:cardName,opinionKey:opinionKey,opinionValue:opinionValue,nickName:nickName,contacts:contacts},
            dataType: 'HTML',
            success: function (data){
                var resultJson = "";
                if(typeof data=="string"){
                    resultJson= eval('(' + data + ')');
                }else{
                    resultJson = data;
                }
                if (resultJson.success == true) {
                    $('#ab-1').hide();
                    $('.alertBox,#ab-2').show();
                    setCookie("_nickName",nickName);
                    setCookie("_contacts",contacts);
                }
            },error:function(){
                $('#ab-1').hide();
                $('.alertBox,#ab-2').show();
                setCookie("_nickName",nickName);
                setCookie("_contacts",contacts);
            }
        }
    );
}