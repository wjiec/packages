package com.wjiec.tinder.springinaction.wiring.model.food;

import com.wjiec.tinder.springinaction.wiring.model.admin.Admin;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ToString
public class Meat implements Food {

    private String name;
    private double weight;
    private Date expired;
    private Admin admin;

    public Meat(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void setExpired(String expired) throws ParseException {
        this.expired = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expired);
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
