package com.mydemo.appconfig.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AppConfigDTO implements Serializable {

    private String configId;
    private String configKey;
    private String configValue;
    private String description;
    private String configGroup;
    private Integer status;
}
