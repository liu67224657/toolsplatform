package com.enjoyf.mcms.container;

import java.util.HashMap;
import java.util.Map;

public class TemplateContainer {
    public static Map gameTemplateMap = new HashMap();
    public static Map archiveTemplateMap = new HashMap();
    public static Map tagTemplateMap = new HashMap();
    public static Map articleTypeTemplateMap = new HashMap();
     public static Map archiveListTemplateMap=new HashMap();

    public static String getTagTemplate(String channel) {
        String template = ConfigContainer.getTagTemplate(channel);
        return (String) tagTemplateMap.get(template);
    }
}
