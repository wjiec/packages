package org.laboys.better.spring.core.support.web.result;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@ApiModel(description = "操作数据通用包装")
public class OperateResult implements Serializable {
    private static final long serialVersionUID = -8930436995568201035L;

    /**
     * 影响的行数
     */
    private int affectedRows;

    /**
     * 根据一个布尔值生成结果, 表示单行操作结果
     */
    public static OperateResult of(boolean ok) {
        return OperateResult.builder().affectedRows(ok ? 1 : 0).build();
    }

}
