package com.mydemo.game.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameDTO implements Serializable {

    private String gameId;
    private String name;
    private String type;
    private String rules;
    private String status;
    private BigDecimal minBet;
    private BigDecimal maxBet;
}
