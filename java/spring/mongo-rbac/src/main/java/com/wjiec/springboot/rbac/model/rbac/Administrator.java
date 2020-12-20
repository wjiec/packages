package com.wjiec.springboot.rbac.model.rbac;

import com.wjiec.springboot.rbac.model.AbstractDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "administrator")
public class Administrator extends AbstractDocument {

    /**
     * 管理员用户名
     */
    private String username;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 管理员是否已激活
     */
    private boolean activated;

    /**
     * 管理员是否已锁定
     */
    private boolean locked;

}
