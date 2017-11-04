package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
import com.enjoyf.util.JsonBinder;
import com.google.common.base.Strings;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-6-18
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
public class CardOpinion {
    //来源页面 wiki 项目 数字 昵称 联系方式
    private Long opinionId; //主键ID
    private String wikiSource; //来源页面
    private String wiki;        //wiki
    private String title;
    private String opinionKey;  //项目
    private String opinionValue; //数字
    private String nickName;    //昵称
    private String contacts;   //联系方式
    private Date createtime;
    private Integer removeState; //0为删除 1删除

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (removeState != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("remove_state");
            bean.setObj(removeState);
            columnList.add(bean);
        }
        if (wiki != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki");
            bean.setObj(wiki);
            columnList.add(bean);
        }
        return columnList;
    }

    public Long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(Long opinionId) {
        this.opinionId = opinionId;
    }

    public String getWikiSource() {
        return wikiSource;
    }

    public void setWikiSource(String wikiSource) {
        this.wikiSource = wikiSource;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getOpinionKey() {
        return opinionKey;
    }

    public void setOpinionKey(String opinionKey) {
        this.opinionKey = opinionKey;
    }

    public String getOpinionValue() {
        return opinionValue;
    }

    public void setOpinionValue(String opinionValue) {
        this.opinionValue = opinionValue;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getRemoveState() {
        return removeState;
    }

    public void setRemoveState(Integer removeState) {
        this.removeState = removeState;
    }

    public static CardOpinion parse(String jsonStr) {
        CardOpinion returnValue = null;
        if (!Strings.isNullOrEmpty(jsonStr)) {
            try {
                returnValue = JsonBinder.buildNormalBinder().getMapper().readValue(jsonStr, new TypeReference<CardOpinion>() {
                });
            } catch (IOException e) {
            }
        }

        return returnValue;
    }

    public String toJson() {
        return JsonBinder.buildNormalBinder().toJson(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
