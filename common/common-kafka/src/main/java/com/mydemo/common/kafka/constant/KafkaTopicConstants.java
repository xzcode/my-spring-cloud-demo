package com.mydemo.common.kafka.constant;

/**
 * Kafka Topic 常量
 */
public final class KafkaTopicConstants {

    private KafkaTopicConstants() {
    }

    /** 用户事件 */
    public static final String TOPIC_USER_EVENT = "topic-user-event";
    /** 直播事件 */
    public static final String TOPIC_LIVE_EVENT = "topic-live-event";
    /** 钱包事件 */
    public static final String TOPIC_WALLET_EVENT = "topic-wallet-event";
    /** 活动事件 */
    public static final String TOPIC_ACTIVITY_EVENT = "topic-activity-event";
    /** 游戏事件 */
    public static final String TOPIC_GAME_EVENT = "topic-game-event";
    /** 商城事件 */
    public static final String TOPIC_MALL_EVENT = "topic-mall-event";
    /** 聊天消息 */
    public static final String TOPIC_CHAT_MESSAGE = "topic-chat-message";
    /** 数据埋点 */
    public static final String TOPIC_TRACKING = "topic-tracking";
}
