package com.app.evotingapp.Entities;

import com.google.cloud.Timestamp;

public class election {
    String desc, name;
    Timestamp start_date, end_date;
    public election() {
        super();
    }
    public election(String desc, String name, Timestamp start_date, Timestamp end_date) {
        super();
        this.desc = desc;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    public String getDesc() {
        return desc;
    }
    public String getName() {
        return name;
    }
    public Timestamp getStart_date() {
        return start_date;
    }
    public Timestamp getEnd_date() {
        return end_date;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }
}
