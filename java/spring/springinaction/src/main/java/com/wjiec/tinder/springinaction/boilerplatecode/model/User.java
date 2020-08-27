package com.wjiec.tinder.springinaction.boilerplatecode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    private String username;

    private String password;

}
