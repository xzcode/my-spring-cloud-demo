package com.mydemo.live.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LiveRoomDTO implements Serializable {

    private String roomId;
    private String title;
    private String coverUrl;
    private String hostId;
    private String hostName;
    private String hostAvatar;
    private String status;
    private Long viewerCount;
    private LocalDateTime startTime;
}
