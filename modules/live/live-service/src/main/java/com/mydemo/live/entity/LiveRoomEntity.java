package com.mydemo.live.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "live_room")
public class LiveRoomEntity extends BaseEntity {

    private String title;

    private String coverUrl;

    @Indexed
    private String hostId;

    private String hostName;

    /** PREPARING / LIVING / ENDED */
    private String status;

    private Long viewerCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
