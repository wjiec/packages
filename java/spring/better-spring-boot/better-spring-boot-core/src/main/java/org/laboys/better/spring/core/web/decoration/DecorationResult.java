package org.laboys.better.spring.core.web.decoration;

import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DecorationResult<T> implements Serializable {
    private static final long serialVersionUID = 9120193004740901452L;

    /**
     * 错误码
     */
    private StatusCode code;

    /**
     * 错误列表
     */
    private List<?> errors;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 额外的请求元数据
     */
    private Map<String, Object> extra;

    /**
     * 添加额外元数据
     */
    public void mergeExtra(Map<String, Object> e) {
        extra.putAll(e);
    }

    /**
     * 成功请求
     */
    public static <R> DecorationResult<R> success(@Nullable R data, Map<String, Object> extra) {
        return DecorationResult.<R>builder()
            .code(StatusCode.SUCCESS)
            .errors(Collections.emptyList())
            .data(data)
            .extra(extra)
            .build();
    }

    /**
     * 失败请求
     */
    public static <R> DecorationResult<R> error(StatusCode code, List<?> errs, Map<String, Object> extra) {
        return DecorationResult.<R>builder().code(code).errors(errs).extra(extra).build();
    }

}
