package com.enjoyf.mcms.bean.temp;

import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-12 上午9:38
 * Description:
 */
public class ReplaceChannelBean {
    private String filePath = null;
    private String specId = null;
    private String localPath = null;
    private Map channelMap = null;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Map getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map channelMap) {
        this.channelMap = channelMap;
    }
}
