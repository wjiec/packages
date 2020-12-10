package com.wjiec.springaio.xmlwiring.model;

import lombok.Data;

@Data
public class Music {

    private String title;
    private String subTitle;
    private Author author;

    public Music(String title) {
        this.title = title;
    }

}
