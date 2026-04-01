package com.mydemo.wallet.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "wallet_transaction")
public class WalletTransactionEntity extends BaseEntity {

    @Indexed
    private String userId;

    /** RECHARGE / DEDUCT / GIFT / WITHDRAW */
    private String type;

    private BigDecimal amount;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    private String remark;
}
