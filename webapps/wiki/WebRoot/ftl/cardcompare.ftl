<table class="data-table" border="1">
    <#--名称-->
    <thead>
    <tr>
        <th></th>
    <#list cardObj.cardInfoList as cardInfo>
        <th width="30%"><#if cardInfo.cardName??><label>${cardInfo.cardName}</label></#if></th>
    </#list>
    <#if cardObj.cardInfoList?size lt 3>
        <th width="30%">-</th>
    </#if>
    </tr>
    </thead>

        <#--图片-->
    <tbody>
    <tr>
        <td>
            <div class="td_tit">图片</div>
        </td>
    <#list cardObj.cardInfoList as cardInfo>
        <td>
            <#if cardInfo.cardImage??>
            <img src="${cardInfo.cardImage}"/>
            </#if>
        </td>
    </#list>
    <#if cardObj.cardInfoList?size lt 3>
        <td>-</td>
    </#if>
    </tr>
    <#list cardObj.keyList as key>
        <tr>
            <td>
                <div class="td_tit">${key}</div>
            </td>
            <#list cardObj.cardInfoList as cardInfo>
                <#if cardInfo.cardMap[key]??>
                    <td <#if returnMap[key]??&&returnMap[key]=cardInfo.cardMap[key]>style="color:red"</#if>>

                    ${cardInfo.cardMap[key]}</td>
                <#else>
                    <td>-</td>
                </#if>
            </#list>
            <#if cardObj.cardInfoList?size lt 3>
                <td>-</td>
            </#if>
        </tr>
    </#list>
    </tbody>
</table>

