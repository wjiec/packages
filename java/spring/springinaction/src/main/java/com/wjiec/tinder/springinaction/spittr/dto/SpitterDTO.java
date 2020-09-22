package com.wjiec.tinder.springinaction.spittr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpitterDTO {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 16)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 18)
    private String password;

}
