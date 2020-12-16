package com.wjiec.springaio.webflow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String name;
    private String address;
    private String phoneNumber;

}
