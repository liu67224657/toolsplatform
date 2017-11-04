<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>apitest_gps</title>
    <meta name="description" content="rice">
    <meta name="keywords" content="rice">
</head>
<link href="../css/index.css" type="text/css" rel="stylesheet"/>
<base target='_blank'>
<body style="overflow: hidden;">
<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
    table.gridtable {
        font-family: verdana, arial, sans-serif;
        font-size: 11px;
        color: #333333;
        border-width: 1px;
        border-color: #666666;
        border-collapse: collapse;
    }

    table.gridtable th {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #dedede;
    }

    table.gridtable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #ffffff;
    }

    a:hover {
        color: red;
    }
    #footer {position: absolute;
        bottom: 0px;
        height: 60px;
        width: 100%;
        clear:both;
    }
</style>
<div id="header">
    常用地址：<a href="http://apitest.joyme.dev/" target="_blank">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="/format" target="_blank">json格式化</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a target="_blank" href="http://wiki.enjoyf.com/index.php?title=Api_test">wiki帮助</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a target="_blank" href="/gps">内部导航</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a target="_blank" href="/patch/page">JAVA补丁格式化</a>&nbsp;&nbsp;&nbsp;&nbsp;
</div>

<!-- Table goes in the document BODY -->
<div style="width:100%;margin-top: 5px;">
    <table class="gridtable" style="float:left;margin-left: 10px;">
        <tr>
            <th>项目</th>
            <th>dev</th>
            <th>alpha</th>
            <th>beta</th>
            <th>com</th>
        </tr>
        <tr>
            <td>Tools</td>
            <td><a href="http://tools.joyme.dev">dev环境</a></td>
            <td><a href="http://tools.joyme.alpha">alpha环境</a></td>
            <td><a href="http://tools.joyme.beta">beta环境</a></td>
            <td><a href="http://tools.joyme.com">com环境</a></td>
        </tr>
        <tr>
            <td>WIKI数字站</td>
            <td><a href="http://wiki.joyme.dev/wiki/ac/login.jsp">dev环境</a></td>
            <td><a href="http://wiki.joyme.alpha/wiki/ac/login.jsp">alpha环境</a></td>
            <td><a href="http://wiki.joyme.beta/wiki/ac/login.jsp">beta环境</a></td>
            <td><a href="http://wiki.joyme.com/wiki/ac/login.jsp">com环境_aliyun  </a>&nbsp;&nbsp;<a
                    href="http://wikiIDC.joyme.com/wiki/ac/login.jsp">com环境_IDC</a></td>
        </tr>
        <tr>
            <td>Marticle</td>
            <td><a href="http://marticle.joyme.dev/marticle/ac/login.jsp">dev环境</a></td>
            <td><a href="http://marticle.joyme.alpha/marticle/ac/login.jsp">alpha环境</a></td>
            <td><a href="http://marticle.joyme.beta/marticle/ac/login.jsp">beta环境</a></td>
            <td><a href="http://marticle.joyme.com/marticle/ac/login.jsp">com环境</a></td>
        </tr>
        <tr>
            <td>Cmsimage</td>
            <td><a href="http://cmsimage.joyme.dev/ac/login.jsp">dev环境</a></td>
            <td><a href="http://cmsimage.joyme.alpha/ac/login.jsp">alpha环境</a></td>
            <td><a href="http://cmsimage.joyme.beta/ac/login.jsp">beta环境</a></td>
            <td><a href="http://cmsimage.joyme.com/ac/login.jsp">com环境</a>&nbsp;&nbsp;<a href="http://cmsimage.joyme.com/ac/joymedomain/list.do">域名管理</a>&nbsp;&nbsp;<a href="http://cmsimage.joyme.com/ac/sitemap/list.do">sitemap</a></td>
        </tr>

        <tr>
            <td>PHP_CMS</td>
            <td><a href="http://article.joyme.dev/ja/login.php">dev环境</a></td>
            <td><a href="http://article.joyme.alpha/ja/login.php">alpha环境</a></td>
            <td><a href="http://article.joyme.beta/ja/login.php">beta环境</a></td>
            <td><a href="http://article.joyme.com/ja/login.php">com环境</a></td>
        </tr>
        <tr>
            <td>PHP发布后台</td>
            <td colspan="2" align="center"><a href="http://phptools.joyme.alpha/">dev_alpha环境</a></td>
            <td colspan="2" align="center"><a href="http://phptools.joyme.com/">beta_com环境</a></td>
        </tr>
        <tr>
            <td>来玩vsdk</td>
            <td><a href="http://vsdktools.joyme.dev/">dev环境</a></td>
            <td><a href="http://vsdktools.joyme.alpha/">alpha环境</a></td>
            <td><a href="http://vsdktools.joyme.beta/">beta环境</a></td>
            <td><a href="http://vsdktools.joyme.com/">com环境</a></td>
        </tr>
        <tr>
            <td>wiki审核(joymewiki)</td>
            <td colspan="2" align="center"><a href="http://joymewiki.joyme.alpha/">alpha环境</a></td>
            <td><a href="http://joymewiki.joyme.beta/">beta环境</a></td>
            <td><a href="http://joymewiki.joyme.com/">com环境</a></td>
        </tr>
        <tr>
            <td>webcache</td>
            <td><a href="http://webcache.joyme.dev/ac/login.jsp">dev环境</a></td>
            <td><a href="http://webcache.joyme.alpha/ac/login.jsp">alpha环境</a></td>
            <td><a href="http://webcache.joyme.beta/ac/login.jsp">beta环境</a></td>
            <td><a href="http://webcache.joyme.com/ac/login.jsp">com环境</a></td>
        </tr>
    </table>







    <table class="gridtable" style="float:right; margin-left:8px">
        <tr>
            <th>常用地址</th>
        </tr>

        <tr style="display: none;">
            <td><a href="http://192.168.20.211:9000/login.cgi">IDC发布</a></td>
        </tr>
        <tr>
            <td><a href="http://staffoa.joyme.com">OA</a></td>
        </tr>
        <tr>
            <td><a href="http://wiki.enjoyf.com/wiki/首页">内部wiki</a></td>
        </tr>
        <tr>
            <td><a href="http://mantis.enjoyf.com/my_view_page.php">mantis</a></td>
        </tr>

        <tr>
            <td><a href="http://172.16.75.66/testlink/login.php?note=expired">testlink</a></td>
        </tr>

        <tr>
            <td><a href="https://staffmail.joyme.com/owa">WEB邮箱地址</a></td>
        </tr>

        <tr>
            <td><a href="http://chatme.joyme.com">微擎后台</a></td>
        </tr>

    </table>

    <table class="gridtable" style="float:right; margin-left:8px">
        <tr>
            <th>PC编译地址</th>
        </tr>
        <tr>
            <td><a href="http://172.16.78.40:8090/login.cgi">内网发布(适用dev|alpha)</a></td>
        </tr>
        <tr>
            <td><a href="http://pcdeploy.joyme.com:9080/login.cgi">正式发布(适用beta|com)</a></td>
        </tr>
    </table>
    <table class="gridtable" style="float:right; margin-left:8px">
        <tr>
            <th>主站编译地址</th>
        </tr>
        <tr>
            <td><a href="http://172.16.75.51:9000/login.cgi">内网发布(适用dev|alpha)</a></td>
        </tr>
        <tr>
            <td><a href="http://123.56.134.149:9080/login.cgi">正式发布(适用beta|com)</a></td>
        </tr>
    </table>
    <table class="gridtable" style="float:right; margin-left:8px">
        <tr>
            <th>来玩编译地址</th>
        </tr>
        <tr>
            <td><a href="http://172.16.75.100:9000">内网发布(适用dev|alpha)</a></td>
        </tr>
        <tr>
            <td><a href="http://10.1.10.1:9000">正式发布(适用beta|com)</a></td>
        </tr>
    </table>
    <div>


        <div id="footer" style="margin-bottom: 100px;margin-left: 10px;">
            <a href="http://wiki.enjoyf.com/wiki/%E6%9C%8D%E5%8A%A1%E5%99%A8%E6%9E%B6%E6%9E%84%E8%A7%84%E5%88%92">服务器架构规划</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="http://wiki.enjoyf.com/wiki/Gameclient_client">玩霸接口地址</a>&nbsp;&nbsp;&nbsp;&nbsp;
        </div>

</body>
</html>

