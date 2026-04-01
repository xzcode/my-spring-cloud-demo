package com.mydemo.tracking.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "tracking_event")
public class TrackingEventEntity extends BaseEntity {

    @Indexed
    private String eventType;

    @Indexed
    private String userId;

    private String deviceId;

    private String platform;

    private String ip;

    private Map<String, Object> data;
}
