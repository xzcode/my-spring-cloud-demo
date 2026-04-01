package com.mydemo.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 */
@Data
public abstract class BaseEntity implements Serializable {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
