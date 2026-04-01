package com.mydemo.admin.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AdminUserDTO implements Serializable {

    private String adminId;
    private String username;
    private String realName;
    private String role;
    private Integer status;
}
