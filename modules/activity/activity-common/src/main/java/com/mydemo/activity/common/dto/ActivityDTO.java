package com.mydemo.activity.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivityDTO implements Serializable {

    private String activityId;
    private String title;
    private String type;
    private String description;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
