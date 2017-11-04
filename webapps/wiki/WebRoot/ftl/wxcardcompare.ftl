<div id="lt_wrap">
<#list cardInfoList as cardInfo>
    <div class="lt_compare">
        <div class="lt_vs"></div>
        <div class="lt_box">
            <div class="lt_boxMain">
                <dl>
                    <dt><em><#if cardInfo.cardName??>${ cardInfo.cardName}</#if></em>
                    <b>  <#if cardInfo.cardImage??><img src="${cardInfo.cardImage}" alt="" title=""
                                                        style="width:100%; height:auto;"/></b></#if></dt>
                    <dd>
                        <table>
                            <tbody>
                                <#list keyList as key>
                                    <#if key!="小编点评">
                                    <tr>
                                        <td>${key}</td>
                                        <td>${cardInfo.cardMap[key]}</td>
                                    </tr>
                                    </#if>
                                </#list>
                            </tbody>
                        </table>
                    </dd>
                    <dd>
                        <#list keyList as key>
                            <#if key=="小编点评">
                                <em>${key}</em>
                                <span>${cardInfo.cardMap[key]}</span>
                            </#if>
                        </#list>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
</#list>
</div>