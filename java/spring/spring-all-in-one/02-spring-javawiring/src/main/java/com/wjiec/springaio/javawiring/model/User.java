package com.wjiec.springaio.javawiring.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String name;
    private Address address;

}
