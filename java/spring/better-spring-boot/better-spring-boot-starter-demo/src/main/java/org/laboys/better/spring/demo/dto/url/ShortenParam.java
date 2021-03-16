package org.laboys.better.spring.demo.dto.url;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.laboys.better.spring.core.annotation.constraints.NullablePattern;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenParam {

    /**
     * 原始地址
     */
    @NullablePattern(value = @Pattern(regexp = "https?://(\\w+)\\.(\\w+).*"), message = "参数地址无效")
    private String logUrl;

}
