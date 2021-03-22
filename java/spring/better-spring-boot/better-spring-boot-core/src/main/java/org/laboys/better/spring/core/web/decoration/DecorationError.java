package org.laboys.better.spring.core.web.decoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DecorationError {

    /**
     * 错误码
     */
    private String error;

    /**
     * 错误原因
     */
    private String message;

}
