package com.mydemo.game.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "game")
public class GameEntity extends BaseEntity {

    private String name;

    /** SLOTS / CARD / DICE / WHEEL */
    private String type;

    private String rules;

    /** ACTIVE / INACTIVE */
    private String status;

    private BigDecimal minBet;

    private BigDecimal maxBet;
}
