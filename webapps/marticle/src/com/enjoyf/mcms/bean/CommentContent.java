package com.enjoyf.mcms.bean;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-19 上午10:39
 * Description:
 */
public class CommentContent {
    private String contentId;
    private Integer replyNum;

    public CommentContent(String contentId, Integer replyNum) {
        this.contentId = contentId;
        this.replyNum = replyNum;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }
}
