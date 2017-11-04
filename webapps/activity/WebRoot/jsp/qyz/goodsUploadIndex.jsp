<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="pragram" content="no-cache">
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <title>上传兑换商品</title>
</head>

<body>
<form name="myForm" action="/weixinop/goodsUpload/page.do" method="post" enctype="multipart/form-data" >
    <table>
        <tr>
            <td>商品名称<font color="red">*</font></td>
            <td colspan="3"><input name="goodsName" type="text"></td>
        </tr>
        <tr>
            <td>商品的类型<font color="red">*</font></td>
            <td  colspan="3">虚拟<input name="goodsType" type="radio" value="V">&nbsp;&nbsp;实物<input name="goodsType" type="radio" value="R"></td>
        </tr>
        <tr>
            <td>商品图片<font color="red">*</font></td>
            <td>
                <input type="text" name="imageUrl" id="imageUrl" readonly>&nbsp;
                <div  id="container"><a class="" id="pickfiles" href="#">上传图片</a></div>
                <div class="up_block_line" id="up_block_line" ></div>
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td>虚拟商品码</td>--%>
            <%--<td><input type="file" name="goodsItemFile" id="goodsItemFile">(支持txt格式，每行一个兑换码)</td>--%>
        <%--</tr>--%>
        <tr>
            <td>虚拟商品码</td>
            <td><textarea name="goodsItems"></textarea></td>
        </tr>
        <tr>
            <td>兑换商品需要的积分<font color="red">*</font></td>
            <td><input name="requireScore" type="text"></td>
        </tr>
        <tr>
            <td>活动的APPID<font color="red">*</font></td>
            <td><input name="gameId" type="text"></td>
        </tr>
        <tr>
            <td>商品的数目<font color="red">*</font></td>
            <td><input name="totalNum" type="text"></td>
        </tr>
        <tr>
            <td>过期时间<font color="red">*</font></td>
            <td><input name="expireTime" type="text"></td>
        </tr>
        <tr>
            <td>详情<font color="red">*</font></td>
            <td><textarea name="description" cols="20" rows="10"></textarea></td>
        </tr>
        <tr ><td>&nbsp;</td>
        </tr>
        <tr ><td>&nbsp;</td>
            <td colspan="2"><input type="button" name="submitBtn" value="提交" onclick="submitGoods()"></td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript" src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://static.joyme.com/js/plupload.full.min.js"></script>
<script type="text/javascript" src="http://static.joyme.com/js/qiniu.js"></script>
<script>
    var pid='c0b9fbf18884ac1c3b9be365025da686';
    var apiHost = 'http://api.joyme.com/';
    var uploadToken="";
    $(document).ready(function () {
        uploadInit();
    });

    function uploadInit() {
        $.ajax({
            url: apiHost + "comment/bean/json/check",
            type: "post",
            data: {profileid: pid},
            dataType: "jsonp",
            jsonpCallback: "checkcallback",
            success: function (req) {
                var resMsg = req[0];
                if (resMsg.rs == '1') {
                    uploadToken=resMsg.uploadtoken;
                    upload();
                }
            }
        });
    }
    function submitGoods() {
        document.myForm.submit();
    }
     //图片传进度条
     function uploadPicProcessLine(uploadRate){
         $('#up_block_line').html('<span style="width:'+uploadRate+'%">'+uploadRate+'%</span>');
     }
    function upload(){
        var QiniuPic = new QiniuJsSDK();
        var picUploader = QiniuPic.uploader({
            runtimes: 'html5,flash,html4',    //上传模式,依次退化
            browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
            uptoken: uploadToken,
            unique_names: false ,
            save_key: false,
            domain: 'http://joymepic.joyme.com/',
            container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
            max_file_size: '8mb',           //最大文件体积限制
            flash_swf_url: 'http://lib.joyme.com/static/third/qiniuupload/plupload/Moxie.swf',  //引入flash,相对路径
            max_retries: 3,                   //上传失败最大重试次数
            dragdrop: true,                   //开启可拖曳上传
            chunk_size: '4mb',                //分块上传时，每片的体积
            auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
            init: {
                'FilesAdded': function (up, files) {
                    var i = 1;
                    plupload.each(files, function (file) {
                        files.splice(i,1);
                        // 文件添加进队列后,处理相关的事情
                    });
                },
                'BeforeUpload': function (up, file) {
                    var picType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toUpperCase();

                    if(picType != '.JPG' && picType != '.PNG' && picType != '.GIF' && picType != '.JPEG'){
                        this.trigger('Error', {
                            code : -1,
                            message : "只能上传*.jpg,*.png,*.gif,*.jpeg的图片",
                            file:file
                        });
                        return false;
                    }
                    if (file.size >= 1024 * 1024 * 8) {
                        this.trigger('Error', {
                            code : -1,
                            message : "上传的图片大小不超过18mb",
                            file:file
                        });
                        return false;
                    }
                },
                'UploadProgress': function (up, file) {
                    var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                    var uploadRate=Math.ceil(file.loaded*100/file.size);
                    uploadPicProcessLine(uploadRate);

                },
                'FileUploaded': function (up, file, info) {
                    try {
                        var domain = up.getOption('domain');
                        var res = JSON.parse(info);
                        var sourceLink = domain +res.key; //获取上传成功后的文件的Url
                        $('#imageUrl').val(sourceLink);
                    } catch (ex) {
                        alert(ex);
                    }
                },
                'Error': function (up, err, errTip) {
                    alert(errTip);
                    //上传出错时,处理相关的事情
                },
                'UploadComplete': function () {

                },
                'Key': function (up, file) {
                    // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
                    var picType = file.name.substring(file.name.lastIndexOf('.'),file.name.length).toLowerCase();
                    var day = new Date();
                    var Year= day.getFullYear();
                    var Month= day.getMonth()+1;
                    if(Month<10){
                        Month='0'+Month;
                    }
                    var Day = day.getDate();
                    var timestamp=Math.ceil(day.getTime());
                    var key = "live/pic/"+Year+Month+'/'+Day+timestamp+picType;

                    return key
                }
            }

        });
        picUploader.stop();

    }
</script>
</html>