package com.enjoyf.mcms.bean.temp;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-24
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public class ArchivesBean {
    private DedeArchives archives;
    private DedeAddonarticle dedeAddonarticle;
    private String linkUrl;


    public ArchivesBean(DedeArchives archives, String linkUrl, DedeAddonarticle dedeAddonarticle) {
        this.archives = archives;
        this.linkUrl = linkUrl;
        this.dedeAddonarticle = dedeAddonarticle;
    }

    public DedeArchives getArchives() {
        return archives;
    }

    public void setArchives(DedeArchives archives) {
        this.archives = archives;
    }

    public DedeAddonarticle getDedeAddonarticle() {
        return dedeAddonarticle;
    }

    public void setDedeAddonarticle(DedeAddonarticle dedeAddonarticle) {
        this.dedeAddonarticle = dedeAddonarticle;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
