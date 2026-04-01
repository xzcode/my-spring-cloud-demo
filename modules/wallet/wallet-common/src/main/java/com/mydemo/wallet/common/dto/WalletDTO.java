package com.mydemo.wallet.common.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WalletDTO implements Serializable {

    private String userId;
    private BigDecimal balance;
    private BigDecimal frozenBalance;
    private String currency;
}
