package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class EmailVerifyRequest implements Serializable {

    private String email;
    private String code;
    private String bizType;
}
