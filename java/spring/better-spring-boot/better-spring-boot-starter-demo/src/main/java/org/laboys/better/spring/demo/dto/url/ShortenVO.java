package org.laboys.better.spring.demo.dto.url;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenVO {

    /**
     * 原始链接
     */
    private String logUrl;

    /**
     * 短链接
     */
    private String shortUrl;

}
