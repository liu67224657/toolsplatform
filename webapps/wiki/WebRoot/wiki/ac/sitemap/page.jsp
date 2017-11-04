<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common.jsp" %>

<html>
<head>
    <style>
        ul {
            list-style-type: none;
        }

        li {
            list-style-type: none;
        }
    </style>
    <script type="text/javascript" src="http://reswiki.joyme.com/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var wikkeyReg=/[^a-zA-Z0-9_]+/;
        $(document).ready(function () {
            $('#form_1').submit(function () {
                var val=$('#input_wiki').val();
                if(val== null ||val.length==0 ){
                    alert('请填写wikikey')
                    return false;
                }
                if(wikkeyReg.test(val)){
                    alert('请填写正确的wikikey')
                    return false;
                }

            });

            $('#form_2').submit(function () {
                var val=$('#input_ugcwiki').val();
                if(val== null ||val.length==0){
                    alert('请填写UGC的wikikey')
                    return false;
                }
                if(wikkeyReg.test(val)){
                    alert('请填写正确的wikikey')
                    return false;
                }
            });
        });
    </script>
</head>
<body>
<div align="center">

    <div style="float: left;">
        <form action="/wiki/ac/st/addwiki.do" method="post" id="form_1">
           wikikey: <input name="wiki" value="" size="20" id="input_wiki"/><input type="submit" value="添加数字站的key">*必须填写
        </form>
        <ul style="margin:0;padding: 0; text-align: left">
            <c:forEach var="wk" items="${wikiSet}">
            <li>
                <span style="padding-right: 30px;">${wk}</span><span>
                <form action="/wiki/ac/st/delwiki.do" method="post"  style="display: inline;">
                    <input name="wiki" value="${wk}" size="20" type="hidden"/><input type="submit" value="删除">
                </form>

                </span>
            <li>
                </c:forEach>
        </ul>
    </div>

    <div style="float: left;margin-left: 20px;">
        <form action="/wiki/ac/st/addugc.do" method="post" id="form_2">
            wikikey: <input name="wiki" value="" size="20" id="input_ugcwiki"/> <input type="submit" value="添加UGC的wikikey">*必须填写
        </form>

        <ul style="margin:0;padding: 0; text-align: left">
            <c:forEach var="wk" items="${ugcWikiSet}">
            <li>
                <span  style="padding-right: 30px;" >${wk}</span><span><form action="/wiki/ac/st/delugc.do" method="post" id="formdel_2" style="display: inline;">
                <input name="wiki" value="${wk}" size="20" type="hidden" id="input_delugc"/><input type="submit" value="删除">
            </form></span>
            <li>
                </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>