package com.enjoyf.mcms.bean;

public class DedeTagArchive {
    private int id;
    private String title = null;
    private String writer = null;
    private String litpic = null;
    private long publishdate;
    private String typedir = null;
    private String namerule = null;
    private String description =null;
    private int Channel;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public String getLitpic() {
        return litpic;
    }
    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }
    public long getPublishdate() {
        return publishdate;
    }
    public void setPublishdate(long publishdate) {
        this.publishdate = publishdate;
    }
    public String getTypedir() {
        return typedir;
    }
    public void setTypedir(String typedir) {
        this.typedir = typedir;
    }
    public String getNamerule() {
        return namerule;
    }
    public void setNamerule(String namerule) {
        this.namerule = namerule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChannel() {
        return Channel;
    }

    public void setChannel(int channel) {
        Channel = channel;
    }
}
