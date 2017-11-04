<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.*" %>
<%@ page import="com.joyme.parameter.*" %>
<%@page import="com.joyme.servlet.MessagePropertie" %>
<html>
<head>
    <title></title>
    <link href="../css/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div id="header">
    常用地址：<a href="/format" target="_blank">json格式化</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a target="_blank" href="http://wiki.enjoyf.com/index.php?title=Api_test">wiki帮助</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a target="_blank" href="/gps">内部导航</a>
</div>

<div class="clear-both"></div>
<div id="container">
    <div id="menu">
        <table id="table1">
            <tr>
                <td>key</td>
                <td>value</td>
            </tr>
            <tr>
                <td>
                    应用:
                    <select name="product" id="product" onchange="testSelect();">
                        <%
                            List<ParamJson> listProuct = MessagePropertie.getListProuct();
                            for (ParamJson paramJson : listProuct) {
                                String key = paramJson.getKey();
                                String value = paramJson.getValue();
                        %>
                        <option value="<%=key%>"><%=value%>
                        </option>
                        <%}%>
                    </select>
                </td>
                <td>
                    接口：
                    <span id="cspan"></span>
                    <%
                        List<ParamJson> list = MessagePropertie.getListProuct();
                        Map<String, List<ParamJson>> productMaps = MessagePropertie.getProductMaps();
                        for (ParamJson paramJson : list) {
                            String key = paramJson.getKey();
                            List<ParamJson> paramJsonList = productMaps.get("product." + key);

                    %>
                    <select id="child_<%=key%>" style="display: none;" name="api">
                        <%
                            if (paramJsonList != null) {
                                for (ParamJson paramJson1 : paramJsonList) {
                                    String key1 = paramJson1.getKey();
                                    String value = paramJson1.getValue();
                        %>
                        <option value="<%=key1%>"><%=value%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <%
                        }%>


                </td>
            </tr>
            <tr>
                <td>
                    环境：<select name="env" id="env">
                    <option value="dev" selected="selected">dev</option>
                    <option value="alpha">alpha</option>
                    <option value="beta">beta</option>
                    <option value="com">com</option>
                    <option value="">无</option>
                </select>
                </td>
                <td><input type="text" name="url" size="50" id="url" placeholder="填写请求的URL"/></td>
            </tr>
            <tr>
                <td><input type="text" name="key" size="15" placeholder="参数的key"/></td>
                <td><input type="text" name="value" size="50" placeholder="参数的value"/></td>
            </tr>
            <tr>
                <td><input type="text" name="key" size="15" placeholder="参数的key"/></td>
                <td><input type="text" name="value" size="50" placeholder="参数的value"/></td>
            </tr>
            <tr>
                <td><input type="text" name="key" size="15" placeholder="参数的key"/></td>
                <td><input type="text" name="value" size="50" placeholder="参数的value"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><input type="button" value="新增一行" id="addrow"/></td>
                <td><input type="button" value="提交" id="submit"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td id="getUrl" style="font-size:10px;"></td>
            </tr>
        </table>
        <!--
        <table class="tablecls" style="background-color: #b9d8f3;width: 100%">
            <tr>
                <td>社交端 com appkey</td>
                <td>06aM10yEF6XrG8DgSQREFJ</td>
            </tr>
            <tr>
                <td>社交端 dev alpha beta appkey</td>
                <td>0VsYSLLsN8CrbBSMUOlLNx</td>
            </tr>
            <tr>
                <td>手游画报（4个环境一样） appkey</td>
                <td>17yfn24TFexGybOF0PqjdY</td>
            </tr>
        </table>
        -->
        <div style="padding: 20px;margin-top: 20px;-webkit-border-radius: 20px;-moz-border-radius: 20px;border-radius: 20px;background-color:#C3E38D;-webkit-box-shadow: #B3B3B3 2px 2px 2px;-moz-box-shadow: #B3B3B3 2px 2px 2px;box-shadow: #B3B3B3 2px 2px 2px;">
            <form method="post" class="form-horizontal" role="form" action="/jsontojava">

                <div class="form-group"style="display: none">
                    <label for="inputUrl" class="col-lg-3 control-label">Url</label>
                    <div class="col-lg-9">
                        <input type="text" class="form-control" id="inputUrl" name="url" placeholder="http://example.com/folder.json">
                    </div>
                </div>

                <div class="form-group" style="display: block;">
                    <label for="inputJson" class="col-lg-3 control-label">Json</label>
                    <div class="col-lg-9">
                        <input type="text" class="form-control" id="inputJson" name="json" placeholder="{description: 'aaaaaaaaaaaabb',tipsid: 10000, updatetime: 1408464000000}" value="{description: 'aaaaaaaaaaaabb',tipsid: 10000, updatetime: 1408464000000}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputPackage" class="col-lg-3 control-label">Package</label>

                    <div class="col-lg-9">
                        <input type="text" class="form-control" id="inputPackage" name="package" value="com.example.api.model"
                               placeholder="com.example.api.model">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputClass" class="col-lg-3 control-label">Root class name</label>

                    <div class="col-lg-5">
                        <input type="text" class="form-control" id="inputClass" value="bean" name="class" placeholder="Folder">
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-9">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="options" value="ONLYRESULT"> Only Result
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-9">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" checked="true" name="options" value="PARCELABLE"> Implement
                                Parcelable
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-9">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" checked="true" name="options" value="GSON"> Include Gson
                                Annotations
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-9">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" checked="true" name="options" value="TO_STRING"> Override
                                toString()
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-3 col-lg-9">
                        <button type="submit" class="btn btn-large btn-primary">生成java对象</button>
                    </div>
                </div>
            </form>
        </div>
    </div>


    <div id="content">
        <div>
            <iframe src="/format" name="main" src="main" id="main" height="100%" width="100%" border="0"
                    frameborder="0">
                浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
            </iframe>
        </div>
    </div>
</div>
</body>
<script src="../../js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script>
    function testSelect() {
        var product = $("#product").val();
        if (product == "") {
            $("select[id^='child_']").css("display", "none");
            return;
        }
        $("select[id^='child_']").css("display", "none");
        $('#child_' + product).css("display", "");

        clearInput();
    }

    $(document).ready(function () {
        testSelect();

        $("select[name='api'],select[name='env']").change(function(){
            clearInput();
        });

    });

    function clearInput(){
        $("input[name='key']").each(function(index,ele){
            $(this).val("");
            $("input[name='value']:eq("+index+")").val("");
        });
    }

    $("#addrow").click(function () {
        var trStr = '<tr>';
        trStr += '<td> <input type="text" name="key" size="15" placeholder="参数的key"/></td>';
        trStr += '<td> <input type="text" name="value" size="50" placeholder="参数的value"/></td>';
        trStr += '</tr>';
        $('#table1 tbody').append(trStr);
    });


    $("#submit").click(function () {
        var url = $("#url").val();
        var env = $("#env").val();
        var product = $("#product").val();
        var api = "";
        if (product != "") {
            api = $("#child_" + product).val();
        }

        var key = $("input[name='key']");
        var value = $("input[name='value']");
        var awardArr = new Array();
        for (i = 0; i < key.length; i++) {
            var key1 = key[i].value;
            var value2 = value[i].value;
            if (key1 == "" || value2 == "") {
                continue;
            }
            var obj = {
                'key': $.trim(key1),
                'value': $.trim(value2)
            };
            awardArr.push(obj);
        }
        $.ajax({
            url: '/api2',
            type: 'post',
            data: {'url': url, 'params': JSON.stringify(awardArr), 'env': env, 'product': product, 'api': api},
            async: false,
            dataType: "json",
            success: function (data) {

                    $("#getUrl").html("<a href='" + data.msg + "' target='_blank'>" + data.msg + "</a>");


                    //填充参数
                    var params = JSON.stringify(data.params).substr(1,data.params.length);
                    var paramsarr= params.split("&");
                    var addInputLen = paramsarr.length-$("input[name='key']").length;
                    for(var i=0;i<addInputLen;i++){
                        $("#addrow").click();
                    }
                    $("input[name='key']").each(function(index,ele){
                        if(index<paramsarr.length){
                            $(this).val(paramsarr[index].split("=")[0]);
                            $("input[name='value']:eq("+index+")").val(paramsarr[index].split("=")[1]);
                        }

                    });

                    main.window.refreshframe(data.result);
                    $("#inputJson").val(data.result);

            }
        });
    });
</script>
</html>