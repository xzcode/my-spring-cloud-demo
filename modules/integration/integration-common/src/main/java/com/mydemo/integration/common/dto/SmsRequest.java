package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class SmsRequest implements Serializable {

    /** 手机号 */
    private String phone;

    /** 业务类型：REGISTER / LOGIN / RESET_PASSWORD / VERIFY */
    private String bizType;
}
