package com.mydemo.chat.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatMessageDTO implements Serializable {

    private String messageId;
    private String roomId;
    private String senderId;
    private String senderName;
    private String content;
    private String type;
    private LocalDateTime createTime;
}
