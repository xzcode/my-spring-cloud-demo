package com.mydemo.tracking.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Map;

@Data
public class TrackingEventDTO implements Serializable {

    private String eventType;
    private String userId;
    private String deviceId;
    private String platform;
    private Map<String, Object> data;
}
