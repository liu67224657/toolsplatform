<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<div class="alertBox" style="display:none">
    <div id="ab-1">
        <h1><span>补充数据</span><i class="close-btn" title="关闭"></i></h1>
        <table class="formTable">
            <tbody>
            <tr>
                <td class="t-1" id="key"><span class="ovf" title="图片" id="opinionKey" >图片：</span></td>
                <td class="t-2"><span  id="pic"><input type="text" class="a_inputTxt" name="opinionValue" id="opinionValue"/></span></td>
                <td class="t-3"><span style="display:none"  id="icon-error1"><i class="icon-error"></i></span></td>
            </tr>
            <tr>
                <td class="t-1">姓名：</td>
                <td class="t-2"><span><input type="text" class="a_inputTxt" name="nickName" id="nickName" placeholder="真实姓名/昵称" /></span></td>
                <td class="t-3"><span style="display:none" id="icon-error2"><i class="icon-error"></i></span></td>
            </tr>
            <tr>
                <td class="t-1">联系方式：</td>
                <td class="t-2"><span><input type="text" class="a_inputTxt" name="contacts" id="contacts" placeholder="手机/QQ/邮箱" /></span></td>
                <td class="t-3"><span style="display:none" id="icon-error3"><i class="icon-error"></i></span></td>
            </tr>
            </tbody>
        </table>
        <p id="sub"><a href="javascript:void(0)" onclick="saveq()" class="a_inputBtn">提交</a></p>
    </div>
    <div id="ab-2">
        <div class="tips-box">
            <p><img src="${templateSourceUrl}/css/dtcq/wiki/default/2/images/tips-img.png" title="感谢您对着迷wiki的支持！新的数据审核过后会在wiki页面留下您的大名,
记得常来看看哦！" alt="感谢您对着迷wiki的支持！新的数据审核过后会在wiki页面留下您的大名,
记得常来看看" /></p>
            <p class="p1"><a href="javascript:;" class="a_inputBtn" onclick="closeW()">关闭</a></p>
        </div>
    </div>
</div>


<table class="supTable">
    <tbody>
    <tr>
        <th colspan="2">${cardName}</th>
    </tr>
    <tr>
        <td class="st-1">图片</td>
        <td class="sub-btn" style="position: relative;">
            <span class="sup-img"><img src="${cardImage}" alt="${cardName}" title="${cardName}" /></span>
            <span class="update-btn icon-compose" onclick="_sqClick(-1)" style="position: absolute;right: 10px;bottom: 5px;">修改</span>
        </td>
    </tr>
    <#if cardMap?exists>
        <#list cardMap?keys as key>
        <tr>
            <td  class="st-1" id="_id${key_index}">${key}</td>
            <td><b>${cardMap[key]}</b><#if cardMap[key]!=""><span class="update-btn icon-compose"  onclick="_sqClick(${key_index})">修改</span></#if>
                <#if  cardMap[key]==""><span class="add-btn icon-googleplus" onclick="_sqClick(${key_index})">添加</span></#if>
            </td>
        </tr>
        </#list>
    </#if>

    </tbody>
</table>

<script type="text/javascript" src="http://reswiki.joyme.com/js/jquery.uploadify.min.js"></script>
<script>
    var wikiCode ="${wikiCode}";
    var pageid ="${pageid}";
    var cardName ="${cardName}";
    var wikitype = "${wikitype}";
</script>
<script type="text/javascript" src="http://reswiki.joyme.com/js/cardopinion.js"></script>


<noscript><p><img src="//stat.joyme.com/piwik.php?idsite=4" style="border:0;" alt="" /></p></noscript>
<!-- End Piwik Code -->


<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fc2179ea9086f2f356144d03b9737230a' type='text/javascript'%3E%3C/script%3E"));
</script>