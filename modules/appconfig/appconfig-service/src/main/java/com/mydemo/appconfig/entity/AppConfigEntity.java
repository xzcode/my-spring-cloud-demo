package com.mydemo.appconfig.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "app_config")
@CompoundIndex(name = "idx_group_key", def = "{'configGroup': 1, 'configKey': 1}", unique = true)
public class AppConfigEntity extends BaseEntity {

    private String configKey;

    private String configValue;

    private String description;

    private String configGroup;

    /** 0-启用 1-禁用 */
    private Integer status;
}
