package com.wjiec.springboot.rbac.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDocument {

    /**
     * Mongo主键Id
     */
    @Id
    @Getter
    private String id;

    /**
     * 创建时间
     */
    @Getter
    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    /**
     * 最后更新时间
     */
    @Getter
    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 更新版本号
     */
    @Getter
    @Version
    private Long _version;

}
