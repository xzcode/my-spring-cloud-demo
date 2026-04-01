package com.mydemo.wallet.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "wallet")
public class WalletEntity extends BaseEntity {

    @Indexed(unique = true)
    private String userId;

    private BigDecimal balance;

    private BigDecimal frozenBalance;

    private String currency;
}
