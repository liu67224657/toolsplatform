package com.enjoyf.mcms.bean.temp;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-11 上午10:01
 * Description:
 */
public class AppRedirectType implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4178050807184309756L;
    //content, profile, gameres
    private static Map<Integer, AppRedirectType> map = new HashMap<Integer, AppRedirectType>();
    //content, profile, gameres

//    0	新游列表（native-list）
//    1	新游单页（native-article）
//    2	攻略库专区首页（native）
//    3	攻略库专区列表(native-list)

//    4	攻略库wiki首页/（web-）
//    5	标签筛选页面（native-list）
//    6	礼包中心首页（native）
//    7	礼包详情页面（native）
//    8	攻略库wiki列表（native-list）
    //    9	下载（native-list）


    //the content
    public static final AppRedirectType NEWGAMELIST = new AppRedirectType(0);
    public static final AppRedirectType CMSARTICLE = new AppRedirectType(1);

    public static final AppRedirectType CMSGAMEINDEX = new AppRedirectType(2);
    public static final AppRedirectType CMSGAMELIST = new AppRedirectType(3);

    public static final AppRedirectType WEBVIEW = new AppRedirectType(4);
    public static final AppRedirectType TAGLIST = new AppRedirectType(5);

    public static final AppRedirectType GIFTINDEX = new AppRedirectType(6);
    public static final AppRedirectType GIFTDETAIL = new AppRedirectType(7);

    public static final AppRedirectType WIKILIST = new AppRedirectType(8);
    public static final AppRedirectType DOWNLOAD = new AppRedirectType(9);
    public static final AppRedirectType SPECELLIST = new AppRedirectType(10);

    //
    private int code;

    private AppRedirectType(int c) {
        this.code = c;

        map.put(code, this);
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "AppRedirectType: code=" + code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        if (code != ((AppRedirectType) o).code) return false;

        return true;
    }

    public static AppRedirectType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<AppRedirectType> getAll() {
        return map.values();
    }

}
