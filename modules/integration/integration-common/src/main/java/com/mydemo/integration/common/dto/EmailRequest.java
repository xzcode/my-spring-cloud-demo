package com.mydemo.integration.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class EmailRequest implements Serializable {

    private String email;
    private String bizType;
}
