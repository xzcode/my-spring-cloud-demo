package com.mydemo.activity.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "activity")
public class ActivityEntity extends BaseEntity {

    private String title;

    /** LOTTERY / COUPON / TASK */
    private String type;

    private String description;

    private String rules;

    /** DRAFT / ACTIVE / ENDED */
    private String status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
