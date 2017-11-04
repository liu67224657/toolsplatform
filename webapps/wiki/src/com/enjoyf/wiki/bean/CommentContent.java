package com.enjoyf.wiki.bean;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-19 上午10:39
 * Description:
 */
public class CommentContent {
    private Integer contentId;
    private Integer replyNum;

    public CommentContent(Integer contentId, Integer replyNum) {
        this.contentId = contentId;
        this.replyNum = replyNum;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }
}
