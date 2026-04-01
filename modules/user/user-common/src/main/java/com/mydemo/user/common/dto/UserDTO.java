package com.mydemo.user.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String userId;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String role;
    private Integer status;
}
