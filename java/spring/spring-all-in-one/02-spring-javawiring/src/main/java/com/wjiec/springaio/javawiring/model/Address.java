package com.wjiec.springaio.javawiring.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String province;
    private String city;
    private String district;

}
