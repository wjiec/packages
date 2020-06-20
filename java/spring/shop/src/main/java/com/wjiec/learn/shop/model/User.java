package com.wjiec.learn.shop.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
public class User {

    @Column(name = "user_id", updatable = false, insertable = false)
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_age")
    private int age;

    @Column(name = "user_email")
    private String email;

}
