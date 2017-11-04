<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/jstllibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>青云志投票</title>
    <link rel="stylesheet" type="text/css" href="http://static.joyme.com/mobile/cms/wxqyz/vote/style.css">
    <script type="text/javascript">
        window.addEventListener('DOMContentLoaded', function () {
            document.addEventListener('touchstart', function () {return false}, true)
        }, true);
        var openId = '${openid}';
    </script>
</head>
<body>
<div class="banner-box">
    <img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/banner-img.jpg">
</div>
<div class="vote-rules">
    <h2>投票规则：</h2>
    <p>1、投票截止日期：2016年11月2日18:00。</p>
    <p>2、不允许刷票行为，一旦发现取消其参赛资格。</p>
    <p>3、<u style="color: red;">需要关注并进入公众号(搜索:qyzgame),点击菜单【家族投票】进行投票。</u></p>
    <p>4、每个玩家每天最多可以投一票。</p>
    <p>5、最终解释权规归《青云志》手游运营团队。</p>
    <p>6、（家族以投稿时间先后排序，序号本身不代表名次）查看更多家族信息猛戳：<a href="http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247486395&idx=1&sn=ca6cb602c29bb122e5db6fdb73345a2b&chksm=9724a971a05320673e6f7ff2e398fcdd0325c5679e8f721425156aae7b61c583400282cf306d&scene=4#wechat_redirect">第一波家族展示</a>、<a href="http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247486413&idx=1&sn=6198ad898e6c66308b5a61405ef3e403&chksm=9724a907a05320111afcd57a95be822c2b0d952ff51c16465cfed6b9c988b7c47c5c008a7d45&scene=4#wechat_redirect">第二波家族展示</a>，<a href="http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247486430&idx=1&sn=10dc6ef33ea84e54a7216615879bddd0&chksm=9724a914a0532002f6507b5b259204db688cd0e701d1276c64993521b6fc905fed2d95fe660e&scene=4#wechat_redirect">第三波家族展示</a>。</p>
</div>
<div class="vote-list">
    <div class="vote-item">
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/1.jpg"></cite>
            <span class="vote-det">
					<font>1、歌者灬梵天</font>
					<b>(<i id="familyCount1">${family1}</i>票)</b>
				</span>
            <span class="vote-btn" id="family1">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/2.jpg"></cite>
            <span class="vote-det">
					<font>2、银星门</font>
					<b>(<i id="familyCount2">${family2}</i>票)</b>
				</span>
            <span class="vote-btn" id="family2">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/3.jpg"></cite>
            <span class="vote-det">
					<font>3、东极至高涯</font>
					<b>(<i id="familyCount3">${family3}</i>票)</b>
				</span>
            <span class="vote-btn" id="family3">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/4.jpg"></cite>
            <span class="vote-det">
					<font>4、末世流年</font>
					<b>(<i id="familyCount4">${family4}</i>票)</b>
				</span>
            <span class="vote-btn" id="family4">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/5.jpg"></cite>
            <span class="vote-det">
					<font>5、风雪缘梦阁</font>
					<b>(<i id="familyCount5">${family5}</i>票)</b>
				</span>
            <span class="vote-btn" id="family5">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/6.jpg"></cite>
            <span class="vote-det">
					<font>6、【風】</font>
					<b>(<i id="familyCount6">${family6}</i>票)</b>
				</span>
            <span class="vote-btn" id="family6">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/7.jpg"></cite>
            <span class="vote-det">
					<font>7、世有桃花</font>
					<b>(<i id="familyCount7">${family7}</i>票)</b>
				</span>
            <span class="vote-btn" id="family7">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/8.jpg"></cite>
            <span class="vote-det">
					<font>8、天下第一</font>
					<b>(<i id="familyCount8">${family8}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family8">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/9.jpg"></cite>
            <span class="vote-det">
					<font>9、战途灬紫轩阁</font>
					<b>(<i id="familyCount9">${family9}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family9">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/10.jpg"></cite>
            <span class="vote-det">
					<font>10、情系江山</font>
					<b>(<i id="familyCount10">${family10}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family10">投票</span>
        </div>
    </div>
    <div class="vote-item">
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/11.png"></cite>
            <span class="vote-det">
					<font>11、紫情丶茗门</font>
					<b>(<i id="familyCount11">${family11}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family11">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/12.jpg"></cite>
            <span class="vote-det">
					<font>12、问情双重会</font>
					<b>(<i id="familyCount12">${family12}</i>票)</b>
				</span>
            <span class="vote-btn" id="family12">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/13.jpg"></cite>
            <span class="vote-det">
					<font>13、巅峰阁</font>
					<b>(<i id="familyCount13">${family13}</i>票)</b>
				</span>
            <span class="vote-btn" id="family13">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/14.png"></cite>
            <span class="vote-det">
					<font>14、天罡诛仙教</font>
					<b>(<i id="familyCount14">${family14}</i>票)</b>
				</span>
            <span class="vote-btn" id="family14">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/15.jpg"></cite>
            <span class="vote-det">
					<font>15、誓言</font>
					<b>(<i id="familyCount15">${family15}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family15">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/16.jpg"></cite>
            <span class="vote-det">
					<font>16、情撼九天</font>
					<b>(<i id="familyCount16">${family16}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family16">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/17.jpg"></cite>
            <span class="vote-det">
					<font>17、歌者释灵</font>
					<b>(<i id="familyCount17">${family17}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family17">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/18.jpg"></cite>
            <span class="vote-det">
					<font>18、战灬天下</font>
					<b>(<i id="familyCount18">${family18}</i>票)</b>
				</span>
            <span class="vote-btn" id="family18">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/19.jpg"></cite>
            <span class="vote-det">
					<font>19、一票饮酒醉</font>
					<b>(<i id="familyCount19">${family19}</i>票)</b>
				</span>
            <span class="vote-btn" id="family19">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/20.jpg"></cite>
            <span class="vote-det">
					<font>20、琅琊阁</font>
					<b>(<i id="familyCount20">${family20}</i>票)</b>
				</span>
            <span class="vote-btn" id="family20">投票</span>
        </div>
    </div>
    <div class="vote-item">
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/21.jpg"></cite>
            <span class="vote-det">
					<font>21、滴血洞庭</font>
					<b>(<i  id="familyCount21">${family21}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family21">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/22.jpg"></cite>
            <span class="vote-det">
					<font>22、神仙道</font>
					<b>(<i  id="familyCount22">${family22}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family22">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/23.jpg"></cite>
            <span class="vote-det">
					<font>23、TT|桃园</font>
					<b>(<i  id="familyCount23">${family23}</i>票)</b>
				</span>
            <span class="vote-btn" id="family23">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/24.jpg"></cite>
            <span class="vote-det">
					<font>24、烟花</font>
					<b>(<i  id="familyCount24">${family24}</i>票)</b>
				</span>
            <span class="vote-btn" id="family24">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/25.jpg"></cite>
            <span class="vote-det">
					<font>25、青云神殿</font>
					<b>(<i  id="familyCount25">${family25}</i>票)</b>
				</span>
            <span class="vote-btn" id="family25">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/26.jpg"></cite>
            <span class="vote-det">
					<font>26、剑阁</font>
					<b>(<i  id="familyCount26">${family26}</i>票)</b>
				</span>
            <span class="vote-btn" id="family26">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/27.jpg"></cite>
            <span class="vote-det">
					<font>27、歌者梵音</font>
					<b>(<i  id="familyCount27">${family27}</i>票)</b>
				</span>
            <span class="vote-btn" id="family27">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/28.jpg"></cite>
            <span class="vote-det">
					<font>28、魂梦丶相依</font>
					<b>(<i  id="familyCount28">${family28}</i>票)</b>
				</span>
            <span class="vote-btn" id="family28">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/29.jpg"></cite>
            <span class="vote-det">
					<font>29、众神殿</font>
					<b>(<i  id="familyCount29">${family29}</i>票)</b>
				</span>
            <span class="vote-btn" id="family29">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/30.jpg"></cite>
            <span class="vote-det">
					<font>30、缘浅奈何情深</font>
					<b>(<i  id="familyCount30">${family30}</i>票)</b>
				</span>
            <span class="vote-btn" id="family30">投票</span>
        </div>
    </div>
    <div class="vote-item">
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/31.jpg"></cite>
            <span class="vote-det">
					<font>31、江山如画</font>
					<b>(<i id="familyCount31">${family31}</i>票)</b>
				</span>
            <span class="vote-btn" id="family31">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/32.jpg"></cite>
            <span class="vote-det">
					<font>32、歌者灬天籁</font>
					<b>(<i id="familyCount32">${family32}</i>票)</b>
				</span>
            <span class="vote-btn" id="family32">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/33.jpg"></cite>
            <span class="vote-det">
					<font>33、风云天下</font>
					<b>(<i id="familyCount33">${family33}</i>票)</b>
				</span>
            <span class="vote-btn" id="family33">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/34.jpg"></cite>
            <span class="vote-det">
					<font>34、夜岚烟雨</font>
					<b>(<i id="familyCount34">${family34}</i>票)</b>
				</span>
            <span class="vote-btn" id="family34">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/35.jpg"></cite>
            <span class="vote-det">
					<font>35、"TeAm神</font>
					<b>(<i id="familyCount35">${family35}</i>票)</b>
				</span>
            <span class="vote-btn" id="family35">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/36.jpg"></cite>
            <span class="vote-det">
					<font>36、MJ丶帝都</font>
					<b>(<i id="familyCount36">${family36}</i>票)</b>
				</span>
            <span class="vote-btn" id="family36">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/37.jpg"></cite>
            <span class="vote-det">
					<font>37、沧澜阁</font>
					<b>(<i id="familyCount37">${family37}</i>票)</b>
				</span>
            <span class="vote-btn" id="family37">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/38.jpg"></cite>
            <span class="vote-det">
					<font>38、大花瓶</font>
					<b>(<i id="familyCount38">${family38}</i>票)</b>
				</span>
            <span class="vote-btn" id="family38">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/39.jpg"></cite>
            <span class="vote-det">
					<font>39、画楼西畔</font>
					<b>(<i id="familyCount39">${family39}</i>票)</b>
				</span>
            <span class="vote-btn" id="family39">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/40.jpg"></cite>
            <span class="vote-det">
					<font>40、情谊灬丿</font>
					<b>(<i id="familyCount40">${family40}</i>票)</b>
				</span>
            <span class="vote-btn"  id="family40">投票</span>
        </div>
    </div>
    <div class="vote-item">
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/41.jpg"></cite>
            <span class="vote-det">
					<font>41、战歌</font>
					<b>(<i id="familyCount41">${family41}</i>票)</b>
				</span>
            <span class="vote-btn" id="family41">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/42.jpg"></cite>
            <span class="vote-det">
					<font>42、楚风丿老腊肉</font>
					<b>(<i id="familyCount42">${family42}</i>票)</b>
				</span>
            <span class="vote-btn" id="family42">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/43.jpg"></cite>
            <span class="vote-det">
					<font>43、omg古笙阁</font>
					<b>(<i id="familyCount43">${family43}</i>票)</b>
				</span>
            <span class="vote-btn" id="family43">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/44.jpg"></cite>
            <span class="vote-det">
					<font>44、梦萦メ谪仙阁</font>
					<b>(<i id="familyCount44">${family44}</i>票)</b>
				</span>
            <span class="vote-btn" id="family44">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/45.jpg"></cite>
            <span class="vote-det">
					<font>45、鬼亡宗</font>
					<b>(<i id="familyCount45">${family45}</i>票)</b>
				</span>
            <span class="vote-btn" id="family45">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/46.jpg"></cite>
            <span class="vote-det">
					<font>46、千禧龙年</font>
					<b>(<i id="familyCount46">${family46}</i>票)</b>
				</span>
            <span class="vote-btn" id="family46">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/47.jpg"></cite>
            <span class="vote-det">
					<font>47、风云聚</font>
					<b>(<i id="familyCount47">${family47}</i>票)</b>
				</span>
            <span class="vote-btn" id="family47">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/48.jpg"></cite>
            <span class="vote-det">
					<font>48、灬纵メ横灬</font>
					<b>(<i id="familyCount48">${family48}</i>票)</b>
				</span>
            <span class="vote-btn" id="family48">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/49.jpg"></cite>
            <span class="vote-det">
					<font>49、岁月如歌</font>
					<b>(<i id="familyCount49">${family49}</i>票)</b>
				</span>
            <span class="vote-btn" id="family49">投票</span>
        </div>
        <div>
            <cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/50.jpg"></cite>
            <span class="vote-det">
					<font>50、千城墨白</font>
					<b>(<i id="familyCount50">${family50}</i>票)</b>
				</span>
            <span class="vote-btn" id="family50">投票</span>
        </div>
    </div>
</div>
<div class="load-more">查看更多</div>
<div class="mask"></div>
<div class="suss">投票成功</div>
<div class="rep">少侠一天仅可投一票哦。 </div>
<div class="unrefi">长按识别二维码</br>进入微信公众号进行投票<cite><img src="http://static.joyme.com/mobile/cms/wxqyz/vote/images/code-img.png"></cite></div>
<div class="ending">投票结束了。</br>并且不能再进行投票操作！</div>
<script src="http://static.joyme.com/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/static/js/preloadjs.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
    function getTime() {
        var myDate = new Date();
        return myDate.getFullYear() + "_" + myDate.getMonth() + "_" + myDate.getDate()  + "_"+ openId;
    }
    function voteBox(){
        var ele=$('.vote-item')
        loadBtn = $('.load-more'),
                voteBtn = $('.vote-btn'),
                oMack = $('.mask'),
                len = ele.length,
                timmer = null,tim = null,
                ind = 0;

        voteBtn.click(function(){
            if(openId=="" || openId==null){
                $('.mask,.unrefi').addClass('on');
            }else {
                var voteDet = $(this).siblings('span.vote-det'),
                        voteI = voteDet.find('i'),
                        num = parseInt(voteI.text())
                var polls = window.localStorage.getItem('qyzVote-'+ getTime());
                if (polls != null && parseInt(polls) ==1) {//投票过
                    $('.mask,.rep').addClass('on');
                    if(!voteI.hasClass('on')){
                        voteI.removeClass('on');
                    }
                }else{
                    var familyCode = $(this).attr('id');
                    $.post("/weixinop/qyzVote/addPoll.do", {openid: openId, familyCode: familyCode}, function (data) {
                        if (data != "") {
                            var voteObj = eval("(" + data + ")");
                            if(voteObj.rs=="1"){
                                voteI.addClass('on');
                                voteI.text(num+1);
                                clearTimeout(tim);
                                tim = setTimeout(function(){
                                    $('.mask,.suss').addClass('on');
                                },800);
                                window.localStorage.setItem('qyzVote-'+ getTime(), 1);
                            }else if(voteObj.rs=="2"){
                                $('.mask,.rep').addClass('on');
                                if(!voteI.hasClass('on')){
                                    voteI.removeClass('on');
                                }
                            }
                        }
                    })
                }
            }
        });
        oMack.on('touchmove',function(e){
            e.preventDefault();
            e.stopPropagation();
        });
        oMack.click(function(e){
            e.preventDefault();
            e.stopPropagation();
            if($(this).hasClass('off')){
                return false;
            }else{
                $(this).removeClass('on');
                $('.suss,.rep,.unrefi,.ending').removeClass('on');
            }

        });
        loadBtn.click(function(){
            clearTimeout(timmer);
            var $this = $(this);
            $this.hide();
            ind++;
            if(ind<=len){
                ele.eq(ind).css({'height':'auto','opacity':1});
                timmer = setTimeout(function(){
                    if(ind < len-1){
                        $this.show();
                    }else{
                        $this.hide();
                    }
                },200);
            }
        });

        function oTimer(config){
            var  fuTime = config;
            clearInterval(oTime);
            oTime = setInterval(function(){
                oGetime();
            },1000);
            function oGetime(){
                var future = Date.parse(fuTime),
                        nowD = new Date(),
                        nowTime = nowD.getTime();
                if(nowTime>future){
                    clearInterval(oTime);
                    oMack.addClass('on off');
                    $('.ending').addClass('on');
                }
            }
        }
        oTimer('2016/11/02 18:00');
    }
    voteBox();
</script>
<script type="text/javascript" src="http://pingjs.qq.com/h5/stats.js" name="MTAH5" sid="500329814" ></script>
</body>

<script>
    $(document).ready(function () {
        //设置openid
        wx.config({
            debug: false,
            appId: '${appId}', // 必填，公众号的唯一标识
            timestamp: '${timestamp}', // 必填，生成签名的时间戳
            nonceStr: '${nonceStr}', // 必填，生成签名的随机串
            signature: '${signature}',// 必填，签名，见附录1
            jsApiList: [
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareQZone'
            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });


        wx.ready(function () {
            //分享到朋友圈
            wx.onMenuShareTimeline({
                title: '青云志第一届家族争霸赛投票火热进行中！',
                link: 'http://api.activity.joyme.com/weixinop/qyzVote/page.do?fromPath=share',
                imgUrl: 'http://static.joyme.com/mobile/cms/wxqyz/vote/images/vote.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            wx.onMenuShareAppMessage({
                title: '青云志第一届家族争霸赛投票火热进行中！',
                desc: '50个家族火热比拼中，赶快来看看有没有你喜欢的家族？',
                link: 'http://api.activity.joyme.com/weixinop/qyzVote/page.do?fromPath=share',
                imgUrl: 'http://static.joyme.com/mobile/cms/wxqyz/vote/images/vote.png',
                success: function (res) {
                    alert("分享成功");
                }
            });


            //分享到QQ
            wx.onMenuShareQQ({
                title: '青云志第一届家族争霸赛投票火热进行中！',
                desc: '50个家族火热比拼中，赶快来看看有没有你喜欢的家族？',
                link: 'http://api.activity.joyme.com/weixinop/qyzVote/page.do?fromPath=share',
                imgUrl: 'http://static.joyme.com/mobile/cms/wxqyz/vote/images/vote.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

            //分享到QQ空间
            wx.onMenuShareQZone({
                title: '青云志第一届家族争霸赛投票火热进行中！',
                desc: '50个家族火热比拼中，赶快来看看有没有你喜欢的家族？',
                link: 'http://api.activity.joyme.com/weixinop/qyzVote/page.do?fromPath=share',
                imgUrl: 'http://static.joyme.com/mobile/cms/wxqyz/vote/images/vote.png',
                success: function (res) {
                    alert("分享成功");
                }
            });

        });

    });
</script>
</html>
