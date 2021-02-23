package com.wjiec.springaio.artemis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Administrator {

    private String nickname;

    private String password;

    private LocalDateTime birthday;

}
