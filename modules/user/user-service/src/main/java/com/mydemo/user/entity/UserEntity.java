package com.mydemo.user.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
public class UserEntity extends BaseEntity {

    @Indexed(unique = true)
    private String username;

    private String nickname;

    private String avatar;

    @Indexed(unique = true, sparse = true)
    private String phone;

    @Indexed(unique = true, sparse = true)
    private String email;

    private String password;

    /** 0-正常 1-禁用 */
    private Integer status;

    /** USER / ANCHOR / ADMIN */
    private String role;
}
