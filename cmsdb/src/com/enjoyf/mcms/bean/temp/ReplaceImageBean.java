package com.enjoyf.mcms.bean.temp;

import com.enjoyf.mcms.bean.JoymeImage;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-12-12 上午9:38
 * Description:
 */
public class ReplaceImageBean {
    private String filePath = null;
    private String localPath = null;
    private int imageId;
    private int specId;
    private JoymeImage joymeImage;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public JoymeImage getJoymeImage() {
        return joymeImage;
    }

    public void setJoymeImage(JoymeImage joymeImage) {
        this.joymeImage = joymeImage;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }
}
