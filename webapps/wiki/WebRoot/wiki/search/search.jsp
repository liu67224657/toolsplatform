<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.enjoyf.wiki.search.Search"%>
<%@page import="com.enjoyf.wiki.bean.PageBean"%>

<%
  Search search = new Search();
  search.search(request, response);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript">
    var joyconfig = {
        infostatus : '',
        ctx : '',
        URL_WWW : 'http://www.joyme.com',
        URL_LIB : 'http://lib.joyme.com',
        DOMAIN : 'joyme.com',
        urlUpload : 'http://up001.joyme.com',
        joyuserno : '',
        joyblogname : '',
        joyblogdomain : '',
        joyheadimg : '',
        ctrlFlag : false,
        shutDownRDomain : '[]',
        viptitle:{c:'着迷机构认证',p:'着迷达人认证',i:'着迷人物认证'},
        token : '',
        profileDomain:'',
        syncFlag:false,
        version:13070802
    }
     var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                                (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                                        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta name="Keywords" content="着迷,着迷网,joyme,joyme.com,游戏,游戏社区,好玩,攻略,最新游戏,最热游戏,游戏资讯,达人,高手,游戏经历,游戏成绩,美图,游戏原声,代言人">
<meta name="description" content="着迷网（Joyme.com）是一个以游戏为主题的游戏玩家社区，记录你的游戏生活和情感 ，相遇结交志同道合的朋友，互动属于自己的游戏文化 ，有趣、新鲜的游戏话题，每天等你来讨论!,着迷,着迷网,joyme,joyme.com,游戏,游戏社区,好玩,攻略,最新游戏,最热游戏,游戏资讯,达人,高手,游戏经历,游戏成绩,美图,游戏原声,代言人"/>
<link rel="icon" href="http://lib.joyme.com/static/img/favicon.ico" type="image/x-icon" /><title>搜索${param.search}-Joyme.com</title>
<link href="http://lib.joyme.com/static/theme/default/css/core.css?13070802" rel="stylesheet" type="text/css"/>
<link href="http://lib.joyme.com/static/theme/default/css/global.css?13070802" rel="stylesheet" type="text/css"/>
<link href="http://lib.joyme.com/static/theme/default/css/common.css?13070802" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
    var key = '${param.search}';
</script>
<!-- &******************新增的样式************* -->
<style type="text/css">
.sqcontent{width:1005px;}

/*
.wsearcht{background:url(http://lib.joyme.com/static/theme/default/img/searcht-2.png) no-repeat}
.wsearchcon{background:url(http://lib.joyme.com/static/theme/default/img/searchc-2.png) repeat-y}
.wsearchb{background:url(http://lib.joyme.com/static/theme/default/img/searcht-2.png) no-repeat}
*/

.wsearcht{background:url(http://reswiki.joyme.com/img/searcht-2.png) no-repeat}
.wsearchcon{background:url(http://reswiki.joyme.com/img/searchc-2.png) repeat-y}
.wsearchb{background:url(http://reswiki.joyme.com/img/searchb-2.png) no-repeat}

.searchl{width:768px;}
.search-choose span{font-weight:bold;}
.search-choose a{margin:0;}
.result-article li{line-height:46px; border-bottom:1px dashed #e2e2e2; font-size:14px;}
</style>
</head>
<body>
<!--头部开始-->
<div id="joyme-head-2013">
	<div class="head-2013-box clearfix">
		<a id="joyme-logo" href="http://www.joyme.com" alt="着迷网"></a>
		<div class="head-2013-left">
			<span><a href="http://www.joyme.com">首页</a></span>
            <span><a href="http://www.joyme.com/wiki">WIKI</a></span>
            <span><a href="http://www.joyme.com/games">专区</a></span>
            <span style="display:none;" id="home"><a href="http://www.joyme.com/home">个人中心</a></span>
            <span><a href="http://www.joyme.com/giftmarket">礼包中心</a></span>
            <span><a href="http://html.joyme.com/mobile/gameguides.html">手机看攻略</a></span>
            <span><a href="http://bbs.joyme.com">论坛</a></span>
        </div>
		<div class="head-2013-right">
			<!-- 搜索框 -->
		<!-- 弹出菜单 -->
		<div id="hoverMenu" style="display:none">

            <div>
				<h2>WIKI</h2>
                 <ul>
                <li><a href="http://ma.joyme.com"><span>扩散性百万亚瑟王</span>
                         <img src="http://lib.joyme.com/static/theme/default/img/new.png" />
                         </a></li>
                    <li><a href="http://mxm.joyme.com"><span>怪物x联盟</span>
                         <img src="http://lib.joyme.com/static/theme/default/img/hot.png" />
                         </a></li>
                    <li><a href="http://mt.joyme.com"><span>我叫 MT</span>
                         <img src="http://lib.joyme.com/static/theme/default/img/hot.png" />
                         </a></li>
                    <li><a href="http://ds.joyme.com"><span>动物之森</span>
                         </a></li>
                    <li><a href="http://mkhx.joyme.com"><span>魔卡幻想</span>
                         </a></li>
                    </ul>
				        <ul>
                    <li><a href="http://pm.joyme.com"><span>精灵宝可梦</span>
                         </a></li>
                    <li><a href="http://wzzj.joyme.com"><span>王者之剑</span>
                         </a></li>
                    <li><a href="http://dzm.joyme.com"><span>大掌门</span>
                         </a></li>
                    <li><a href="http://mjh.joyme.com"><span>萌江湖</span>
                         </a></li>
                    <li><a href="http://thmz.joyme.com"><span>童话迷踪</span>
                         </a></li>
                    </ul>
				        <ul>
                    </ul>
				<p class="more"><a href="http://www.joyme.com/game">更多&gt;&gt;</a></p>
			</div>
            <div>
				<h2>手机游戏</h2>
                 <ul>
                <li><a href="http://www.joyme.com/game/carrotfantasy"><span>保卫萝卜</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/wimw"><span>鳄鱼小顽皮爱洗澡</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/mjz"><span>喵将传</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/cuttheropetimetravel"><span>割绳子：时光之旅</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/WheresMyPerry"><span>鸭嘴兽泰瑞在哪里</span>
                         </a></li>
                    </ul>
				        <ul>
                    </ul>
				<p class="more"><a href="http://www.joyme.com/moregame#mobile">更多&gt;&gt;</a></p>
			</div>
            <div>
				<h2>掌机单机</h2>
                 <ul>
                <li><a href="http://ds.joyme.com"><span>动物之森</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/monsterhunter4"><span>怪物猎人4</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/biohazard6"><span>生化危机6</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/borderlands2"><span>无主之地2</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/assassincreed3"><span>刺客信条3</span>
                         </a></li>
                    </ul>
				        <ul>
                    </ul>
				<p class="more"><a href="http://www.joyme.com/moregame#zhangji">更多&gt;&gt;</a></p>
			</div>
            <div>
				<h2>网络游戏</h2>
                 <ul>
                <li><a href="http://www.joyme.com/game/xdzw"><span>炫斗之王</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/dn"><span>龙之谷</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/nizhan"><span>逆战</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/bns"><span>剑灵</span>
                         </a></li>
                    <li><a href="http://www.joyme.com/game/lol"><span>英雄联盟</span>
                         </a></li>
                    </ul>
				        <ul>
                    </ul>
				<p class="more"><a href="http://www.joyme.com/moregame#mmo">更多&gt;&gt;</a></p>
			</div>
            </div>
	</div>
    <span id="memo_f"></span>
        <span id="login_f"></span>
</div><div class="sqcontent clearfix">
    <div class="wsearcht"></div>
    <div class="wsearchcon clearfix">
        <div class="searchl">
            <div class="w_search">
    <!-- 搜索选项 -->
    <div class="search-choose">
        <span>搜索</span>
	</div>


	 <form name="form1" action="./search.jsp" id="searchform" method="get"> 
	    <div class="w_searchcon">
	        <input type="text" class="w_text" value="${param.search}"
	               onfocus="if(this.value=='找找你感兴趣的...'){this.value=''; this.style.color='#666';}"
	               onblur="if(this.value==''){this.value='找找你感兴趣的...'; this.style.color='#989898';}" name="search"
	               id="hold_txt_search" autocomplete="off" style="color: rgb(102, 102, 102);">
	        <input type="submit" class="w_searchbtn" id="holdsearchbtn" value="" />
	    </div>
	      <input type="hidden" name="key" value="${param.key}">
	      <input type="hidden" name="pageNum" value="${param.pageNum}">
	      <input type="hidden" name="pageCount" value="${param.pageCount}">
	</form>
    
    <!-- 搜索提示框 -->
    </div>
	<div class="result-report"></div>
	<div class="result-article">
		<c:forEach  var="item" items="${pageBean.retList}">
			<li><a href="${item.httpUrl}">${item.wikiUrl}</a></li>
		</c:forEach>
	</div>
	<div class="page">
		<c:if test="${param.pageNum > 1}">
			<a href="./search.jsp?search=<%=URLEncoder.encode(request.getParameter("search") , "utf-8")%>&pageCount=${param.pageCount}&pageNum=${param.pageNum - 1}&key=${param.key}" class="nextpage"><span>上一页</span></a>
		</c:if>
		<c:if test="${pageBean.hasNextPage==true}">
			<a href="./search.jsp?search=<%=URLEncoder.encode(request.getParameter("search") , "utf-8")%>&pageCount=${param.pageCount}&pageNum=${param.pageNum + 1}&key=${param.key}" class="nextpage"><span>下一页</span></a>
		</c:if>
	</div>
	
	
<!--wsearch-->
        </div>
        <!--search right-->
        <div class="searchr w_game">
    <div class="w_area clearfix">
		<!-- *********此处只有 返回wiki链接 ********** -->
		<h3 style="font-size:14px;"><a href="http://wiki.joyme.com/${param.key}/">点击返回</a><br /></h3>
    </div>
</div><!--search right-->
    </div>
    <!--searchc-->
    <div class="wsearchb"></div>

</div>
<div class="footercon clearfix">
    <div class="footerarea">
        </div>
 <div class="footer">
     <span>© 2011－2015 joyme.com, all rights reserved</span>
     <span> 京ICP备11029291号</span>
     <a target="_blank" href="http://www.joyme.com/help/aboutus" rel="nofollow">关于着迷</a> |
     <a target="_blank" href="http://www.joyme.com/about/job/zhaopin" rel="nofollow">工作在着迷</a> |
     <%--<a target="_blank" href="http://www.joyme.com/note/1CGoLChZ18yHHQJW_jVV1S" rel="nofollow">着迷认证</a> |--%>
     <a target="_blank" href="http://www.joyme.com/about/contactus" rel="nofollow">商务合作</a> |
     <a href="../../games/ttfc/mailto:contactus@joyme.com">联系我们</a>|
     <a href="http://www.joyme.com/sitemap.htm">网站地图</a>
 </div>
</div>
<div style="display:none">
<script src="http://s4.cnzz.com/stat.php?id=5437085&web_id=5437085" language="JavaScript"></script></div>
<div class="scroll_top">
	<a class="home_gotop" href="javascript:void(0)" id="linkHome" title="返回" style="display:none"></a>
</div>
<script src="http://lib.joyme.com/static/js/common/seajs.js"></script>
<script src="http://lib.joyme.com/static/js/common/seajs-config.js"></script>
<script>
    seajs.use('http://lib.joyme.com/static/js/init/search-content-init.js')
</script>
<script src="http://lib.joyme.com/static/js/common/pv.js"></script>
<script type="text/javascript">
    lz_main();
</script>

</body>
</html>



