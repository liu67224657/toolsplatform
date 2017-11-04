package com.enjoyf.wiki.container;

import com.enjoyf.wiki.bean.JoymeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TemplateContainer {
    private static Logger logger = LoggerFactory.getLogger(TemplateContainer.class);

    //wiki_channel_isIndex--->joymeTemplate
    public static Map<String, JoymeTemplate> templateMap = new HashMap<String, JoymeTemplate>();


    public static void putTemplate(JoymeTemplate joymeTemplate, String wikiType) {
        String key = joymeTemplate.getWiki() + "_" + joymeTemplate.getChannel() + "_" + joymeTemplate.getIsIndex() + "_" + wikiType;
        logger.info("putTemplate key:" + key);
        TemplateContainer.templateMap.put(key, joymeTemplate);
    }


    public static JoymeTemplate getTemplate(String key, String channel, int isIndex, String wikiType) {
        String templateKey = key + "_" + channel + "_" + isIndex + "_" + wikiType;
        return templateMap.get(templateKey);
    }
}
