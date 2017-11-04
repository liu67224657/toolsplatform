    <div class="wap-search">
    <form action="/mwiki/${wikicode}/search.do" method="post" id="searchform">
    <div><input type="text" placeholder="请输入要搜索的内容" value="<#if word??>${word}</#if>" name="word" id="search"></div>
    <#--<a class="icon-search"  href="javascript:void(0)" onclick="document.getElementById('searchform').submit();">搜索</a>-->
        <input type="submit" class="icon-search">
    <input type="hidden" name="pageNum" value="${pageNum}" id="pageNum">
    <input type="hidden" name="pageCount" value="10" id="pageCount">
    </div>
    </form>
    <!--wap-search-list-->
    <div class="wap-search-list">
        <div></div>
        <ul>
           <#if pageBean?? && pageBean.retList??>
            <#list  pageBean.retList as item>
                <li><a href="${item.httpUrl}">${item.wikiUrl}</a></li>
            </#list>
           </#if>
        </ul>
    </div>
    <!--wap-search-list==end-->
    <!--wap-search-page-->
    <div class="wap-search-page">
        <ul>
    <#if pageBean??>
            <#if pageNum gt 1>
                <li><a href="" class="nextpage"><span>上一页</span></a></li>
            </#if>
            <#if pageBean.hasNextPage>
                <li><a href="" class="nextpage"><span>下一页</span></a></li>
            </#if>
    </#if>
        </ul>
    </div>