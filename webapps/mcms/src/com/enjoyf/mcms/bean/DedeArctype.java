package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class DedeArctype {
    private Integer id;
    private Integer reid;
    private Integer topid;
    private Integer sortrank;
    private String typename;
    private String typedir;
    private Integer isdefault;
    private String defaultname;
    private Integer issend;
    private Integer channeltype;
    private Integer maxpage;
    private Integer ispart;
    private Integer corank;
    private String tempindex;
    private String templist;
    private String temparticle;
    private String namerule;
    private String namerule2;
    private String modname;
    private String description;
    private String keywords;
    private String seotitle;
    private Boolean moresite;
    private String sitepath;
    private String siteurl;
    private Integer ishidden;
    private Boolean cross;
    private String crossid;
    private String content;
    private String smalltypes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReid() {
        return reid;
    }

    public void setReid(Integer reid) {
        this.reid = reid;
    }

    public Integer getTopid() {
        return topid;
    }

    public void setTopid(Integer topid) {
        this.topid = topid;
    }

    public Integer getSortrank() {
        return sortrank;
    }

    public void setSortrank(Integer sortrank) {
        this.sortrank = sortrank;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypedir() {
        return typedir;
    }

    public void setTypedir(String typedir) {
        this.typedir = typedir;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public String getDefaultname() {
        return defaultname;
    }

    public void setDefaultname(String defaultname) {
        this.defaultname = defaultname;
    }

    public Integer getIssend() {
        return issend;
    }

    public void setIssend(Integer issend) {
        this.issend = issend;
    }

    public Integer getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(Integer channeltype) {
        this.channeltype = channeltype;
    }

    public Integer getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(Integer maxpage) {
        this.maxpage = maxpage;
    }

    public Integer getIspart() {
        return ispart;
    }

    public void setIspart(Integer ispart) {
        this.ispart = ispart;
    }

    public Integer getCorank() {
        return corank;
    }

    public void setCorank(Integer corank) {
        this.corank = corank;
    }

    public String getTempindex() {
        return tempindex;
    }

    public void setTempindex(String tempindex) {
        this.tempindex = tempindex;
    }

    public String getTemplist() {
        return templist;
    }

    public void setTemplist(String templist) {
        this.templist = templist;
    }

    public String getTemparticle() {
        return temparticle;
    }

    public void setTemparticle(String temparticle) {
        this.temparticle = temparticle;
    }

    public String getNamerule() {
        return namerule;
    }

    public void setNamerule(String namerule) {
        this.namerule = namerule;
    }

    public String getNamerule2() {
        return namerule2;
    }

    public void setNamerule2(String namerule2) {
        this.namerule2 = namerule2;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSeotitle() {
        return seotitle;
    }

    public void setSeotitle(String seotitle) {
        this.seotitle = seotitle;
    }

    public Boolean getMoresite() {
        return moresite;
    }

    public void setMoresite(Boolean moresite) {
        this.moresite = moresite;
    }

    public String getSitepath() {
        return sitepath;
    }

    public void setSitepath(String sitepath) {
        this.sitepath = sitepath;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    public Integer getIshidden() {
        return ishidden;
    }

    public void setIshidden(Integer ishidden) {
        this.ishidden = ishidden;
    }

    public Boolean getCross() {
        return cross;
    }

    public void setCross(Boolean cross) {
        this.cross = cross;
    }

    public String getCrossid() {
        return crossid;
    }

    public void setCrossid(String crossid) {
        this.crossid = crossid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmalltypes() {
        return smalltypes;
    }

    public void setSmalltypes(String smalltypes) {
        this.smalltypes = smalltypes;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (reid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("reid");
            bean.setObj(reid);
            columnList.add(bean);
        }
        if (topid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("topid");
            bean.setObj(topid);
            columnList.add(bean);
        }
        if (sortrank != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("sortrank");
            bean.setObj(sortrank);
            columnList.add(bean);
        }
        if (typename != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typename");
            bean.setObj(typename);
            columnList.add(bean);
        }
        if (typedir != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typedir");
            bean.setObj(typedir);
            columnList.add(bean);
        }
        if (isdefault != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("isdefault");
            bean.setObj(isdefault);
            columnList.add(bean);
        }
        if (defaultname != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("defaultname");
            bean.setObj(defaultname);
            columnList.add(bean);
        }
        if (issend != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("issend");
            bean.setObj(issend);
            columnList.add(bean);
        }
        if (channeltype != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channeltype");
            bean.setObj(channeltype);
            columnList.add(bean);
        }
        if (maxpage != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("maxpage");
            bean.setObj(maxpage);
            columnList.add(bean);
        }
        if (ispart != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("ispart");
            bean.setObj(ispart);
            columnList.add(bean);
        }
        if (corank != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("corank");
            bean.setObj(corank);
            columnList.add(bean);
        }
        if (tempindex != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("tempindex");
            bean.setObj(tempindex);
            columnList.add(bean);
        }
        if (templist != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("templist");
            bean.setObj(templist);
            columnList.add(bean);
        }
        if (temparticle != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("temparticle");
            bean.setObj(temparticle);
            columnList.add(bean);
        }
        if (namerule != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("namerule");
            bean.setObj(namerule);
            columnList.add(bean);
        }
        if (namerule2 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("namerule2");
            bean.setObj(namerule2);
            columnList.add(bean);
        }
        if (modname != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("modname");
            bean.setObj(modname);
            columnList.add(bean);
        }
        if (description != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("description");
            bean.setObj(description);
            columnList.add(bean);
        }
        if (keywords != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("keywords");
            bean.setObj(keywords);
            columnList.add(bean);
        }
        if (seotitle != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("seotitle");
            bean.setObj(seotitle);
            columnList.add(bean);
        }
        if (moresite != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("moresite");
            bean.setObj(moresite);
            columnList.add(bean);
        }
        if (sitepath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("sitepath");
            bean.setObj(sitepath);
            columnList.add(bean);
        }
        if (siteurl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("siteurl");
            bean.setObj(siteurl);
            columnList.add(bean);
        }
        if (ishidden != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("ishidden");
            bean.setObj(ishidden);
            columnList.add(bean);
        }
        if (cross != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("cross");
            bean.setObj(cross);
            columnList.add(bean);
        }
        if (crossid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("crossid");
            bean.setObj(crossid);
            columnList.add(bean);
        }
        if (content != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("content");
            bean.setObj(content);
            columnList.add(bean);
        }
        if (smalltypes != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("smalltypes");
            bean.setObj(smalltypes);
            columnList.add(bean);
        }
        return columnList;
    }
}
