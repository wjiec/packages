package com.wjiec.springaio.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParam {

    @Size(min = 6, max = 18, message = "The length of username must be between {min} and {max}")
    private String username;

    @Size(min = 6, max = 18, message = "The length of passcode must be between {min} and {max}")
    private String passcode;

    @Size(min = 6, max = 18, message = "The length of passcode must be between {min} and {max}")
    private String checkedPasscode;

}
