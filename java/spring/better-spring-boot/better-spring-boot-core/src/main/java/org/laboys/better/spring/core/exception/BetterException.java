package org.laboys.better.spring.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
public class BetterException extends RuntimeException {
    private static final long serialVersionUID = -349938673820766214L;

    /**
     * 错误码
     */
    @Getter
    private final String code;

    public BetterException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BetterException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
