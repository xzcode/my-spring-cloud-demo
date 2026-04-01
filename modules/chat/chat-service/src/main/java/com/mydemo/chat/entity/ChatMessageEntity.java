package com.mydemo.chat.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "chat_message")
public class ChatMessageEntity extends BaseEntity {

    @Indexed
    private String roomId;

    private String senderId;

    private String senderName;

    private String content;

    /** TEXT / IMAGE / GIFT / SYSTEM */
    private String type;

    /** NORMAL / DELETED */
    private String status;
}
