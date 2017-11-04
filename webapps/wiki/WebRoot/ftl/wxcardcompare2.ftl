<div class="title">武器对比</div>
<div class="main result">
<#list cardInfoList as cardInfo>
    <#if cardInfo_index==0>
    <div class="l">
        <#else>
        <div class="r">
    </#if>
    <dl class="weapon">
        <dt><#if cardInfo.cardName??>${ cardInfo.cardName}</#if></dt>
        <dd>
           <span class="sel-btn">
            <a href="javascript:;">
                <i><#if cardInfo.cardImage??>
                    <img src="${cardInfo.cardImage}" alt="" title="" style="width:100%; height:auto;"/>
                </#if>
                </i>
            </a>
           </span>

            <div class="weaponType">
                <em><b>类型</b>： <#list keyList as key>
                    <#if key=="类型">
                      ${cardInfo.cardMap[key]}
                    </#if>
                </#list></em>
                <ul>
                    <#list keyList as key>
                        <#if key!="对比点评"&&key!='类型'>
                            <li><em>${key}</em><b><i style="width:${cardInfo.cardMap[key]}%"></i></b></li>
                        </#if>
                    </#list>
                </ul>
            </div>
        </dd>
    </dl>
</div>
</#list>
    <span class="vs">VS</span>
</div>

<#if dianping??>
    <div class="box">
        <div class="title">对比点评</div>
        <div class="main result">
                    <span>${dianping}</span>
        </div>
    </div>
</#if>
