package com.mydemo.admin.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "admin_user")
public class AdminUserEntity extends BaseEntity {

    @Indexed(unique = true)
    private String username;

    private String password;

    private String realName;

    /** SUPER_ADMIN / ADMIN / OPERATOR */
    private String role;

    /** 0-正常 1-禁用 */
    private Integer status;

    private LocalDateTime lastLoginTime;
}
