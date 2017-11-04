package com.enjoyf.webapps.joyme.webpage.dto.lscs;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-10-16 下午3:26
 * Description:
 */

/**
 * [{"categoryId":1,"manualId":1,"name":"战歌指挥官","picUrl":"zhihuiguan","job":"战士","type":"仆从","mana":3,"attack":2,"life":3,"desc":"你的其他仆从获得冲锋。"},
{"categoryId":1,"manualId":2,"name":"库卡隆精英","picUrl":"kukalong","job":"战士","type":"仆从","mana":4,"attack":4,"life":3,"desc":"冲锋"},
{"categoryId":1,"manualId":3,"name":"残忍的工头","picUrl":"cangren","job":"战士","type":"仆从","mana":2,"attack":2,"life":1,"desc":"战斗怒吼：对1名仆从造成1点伤害，并使其获得+2攻击。"},
{"categoryId":2,"manualId":4,"name":"空气之怒图腾","picUrl":"kongqi","job":"萨满","type":"仆从","mana":1,"attack":null,"life":2,"desc":"法术强度+1"}]
 */
public class CardObject {
    private long categoryId;
    private long manualId;
    private String name;
    private String picUrl;
    private String job;
    private String type;
    private int mana;
    private int attack;
    private int life;
    private String desc;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getManualId() {
        return manualId;
    }

    public void setManualId(long manualId) {
        this.manualId = manualId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", manualId=" + manualId +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", job='" + job + '\'' +
                ", type='" + type + '\'' +
                ", mana=" + mana +
                ", attack=" + attack +
                ", life=" + life +
                ", desc='" + desc + '\'' +
                '}';
    }
}
