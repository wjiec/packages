package com.wjiec.springaio.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spittle {

    private long id;
    private String content;
    private Date createdAt;
    private double latitude;
    private double longitude;

}
