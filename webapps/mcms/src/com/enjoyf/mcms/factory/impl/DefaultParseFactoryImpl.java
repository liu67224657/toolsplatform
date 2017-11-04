package com.enjoyf.mcms.factory.impl;

import java.util.List;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.JoymeChannelContent;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.util.VideoTools;

public class DefaultParseFactoryImpl extends AbstractParseFactoryImpl implements IHtmlParseFactory {
    private static VideoTools tools = new VideoTools();
    
    public String makeSpecHtml(JoymeSpec spec, List specList, JoymeChannelContent content, String channel, String localPath) throws Exception {
        return super.makeSpecHtml(spec, specList, content, channel, localPath);
    }

    public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
            String localPath) throws Exception {
        return super.makeArchiveHtml(dedeArchives, dedeAddonarticle, filePath, fileName, channel, localPath);
    }

//    @Override
//    protected String getMovieUrl(String id) {
//        return tools.getMP4ViedoUrl(id);
//    }
}
