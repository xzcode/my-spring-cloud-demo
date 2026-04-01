package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class SmsVerifyRequest implements Serializable {

    private String phone;
    private String code;
    private String bizType;
}
