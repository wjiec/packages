package com.wjiec.springaio.artemis.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {

    private String title;

    private String content;

    private LocalDateTime sendTime;

}
