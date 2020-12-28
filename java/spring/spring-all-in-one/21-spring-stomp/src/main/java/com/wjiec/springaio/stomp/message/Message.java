package com.wjiec.springaio.stomp.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String content;

}
