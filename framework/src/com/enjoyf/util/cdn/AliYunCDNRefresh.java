package com.enjoyf.util.cdn;

import com.aliyun.api.AliyunClient;
import com.aliyun.api.DefaultAliyunClient;

import com.aliyun.api.cdn.cdn20141111.request.RefreshObjectCachesRequest;
import com.aliyun.api.cdn.cdn20141111.response.RefreshObjectCachesResponse;
import com.enjoyf.framework.log.LogService;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/6/9
 * Description:
 */
public class AliYunCDNRefresh extends AbstractCdnRefresh {

    private static final String ACCESS_KEY = "m2LJu94lrAKPMGBm";
    private static final String ACCESS_SECRET = "jO3aBvvxQKfBoBEHXadiLhG0YFi8OJ";

    public static final String CDN_TYPE_FILE = "File";
    public static final String CDN_TYPE_DIRECTORY = "Directory";

    @Override
    public void clearCDN(String url, Map map) {
        String type = AliYunCDNRefresh.CDN_TYPE_DIRECTORY;
        if (!url.endsWith("/")) {
            type = AliYunCDNRefresh.CDN_TYPE_FILE;
        }
        //刷新CDN
        try {
            //如果是youku.com需要特殊处理
            if (url.contains("youku.com")) {
                type = AliYunCDNRefresh.CDN_TYPE_DIRECTORY;
                url = url.replace("joyme.youku.com", "youku.joyme.com");

                //http://joyme.youku.com/discoveryvideo/xiaotu/2015/0819/87426.html?sid=YKXFX1
                url = url.substring(0, url.lastIndexOf("/")) + "/";
            }
            RefreshObjectCachesResponse rocr = refreshCDN(url, type);
            if (map != null) {
                String taskId = rocr.getRefreshTaskId();
                map.put("taskid", taskId);
            }
            LogService.infoFreshLog("clear cdn:aly,taskId:" + rocr.getRefreshTaskId(), true);
        } catch (Exception e) {
            LogService.errorFreshLog("clear cdn:aly," + url, e);
            e.printStackTrace();
        }
    }

    /**
     * @param path       域名以及要刷的文件夹，不要加http:// 例如 v.joyme.com/xxx 或者 v.joyme.com/
     * @param objectType File | Direcotry
     */
    public static RefreshObjectCachesResponse refreshCDN(String path, String objectType) throws Exception {
        LogService.infoFreshLog("clear cdn:aly," + objectType+","+path, true);
        path = path.replace("http://", "").replace("https://", "");
        RefreshObjectCachesRequest request = new RefreshObjectCachesRequest();
        request.setObjectPath(path);
        request.setObjectType(objectType);
        request.setTimestamp(System.currentTimeMillis());
        AliyunClient client = new DefaultAliyunClient("https://cdn.aliyuncs.com", ACCESS_KEY, ACCESS_SECRET);
        return client.execute(request);
    }

//    public static void main(String[] args) {
//        try {
//            RefreshObjectCachesResponse rcr=refreshCDN("vtest.joyme.com/","Directory");
//            System.out.println(rcr.getRefreshTaskId());
//            System.out.println(rcr.getBody());
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }
}
