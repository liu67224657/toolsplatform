package com.enjoyf.mcms.bean;

public class JoymeCount {
    private String date;
    private int pvcount;
    private int uvcount;
    private String channel;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getPvcount() {
        return pvcount;
    }

    public void setPvcount(int pvcount) {
        this.pvcount = pvcount;
    }

    public int getUvcount() {
        return uvcount;
    }

    public void setUvcount(int uvcount) {
        this.uvcount = uvcount;
    }
}
