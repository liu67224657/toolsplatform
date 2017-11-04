package com.enjoyf.wiki.service;

import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.PropertiesContainer;

import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/6/1
 * Description:
 */
public class WikiMemcachedService {
    private static String KEY_WIKISITE = "wikisite_";
    private static String KEY_JOYMEWIKIKEY = KEY_WIKISITE + "joymewikikey_";
    private static String KEY_JOYMEWIKISET = KEY_WIKISITE + "joymewikiset";
    private static final int WIKIKEY_TIMEOUT_SEC = 60 * 60 * 6;

    public void putJoymeWiki(JoymeWiki joymeWiki, String wikiType) {
        PropertiesContainer.getInstance().getMemCachedManager().put(KEY_JOYMEWIKIKEY + joymeWiki.getJoymeWikiKey() + wikiType, joymeWiki, WIKIKEY_TIMEOUT_SEC);
    }

    public JoymeWiki getJoymeWiki(String wikiKey, String wikiType) {
        Object obj = PropertiesContainer.getInstance().getMemCachedManager().get(KEY_JOYMEWIKIKEY + wikiKey + wikiType);

        if (obj == null) {
            return null;
        }

        return (JoymeWiki) obj;
    }

    public boolean deleteJoymeWiki(String wikiKey, String wikiType) {
        return PropertiesContainer.getInstance().getMemCachedManager().remove(KEY_JOYMEWIKIKEY + wikiKey + wikiType);
    }

    public void putJoymeWikiSet(Set<String> set) {
        PropertiesContainer.getInstance().getMemCachedManager().put(KEY_JOYMEWIKISET, set, WIKIKEY_TIMEOUT_SEC);
    }

    public Set<String> getJoymeWikiSet() {
        Object obj = PropertiesContainer.getInstance().getMemCachedManager().get(KEY_JOYMEWIKISET);

        if (obj == null) {
            return null;
        }

        return (Set<String>) obj;
    }

    public boolean deleteJoymeWikiSet() {
        return PropertiesContainer.getInstance().getMemCachedManager().remove(KEY_JOYMEWIKISET);
    }
}
