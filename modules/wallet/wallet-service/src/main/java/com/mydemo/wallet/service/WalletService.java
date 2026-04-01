package com.mydemo.wallet.service;

import com.mydemo.common.feign.client.UserFeignClient;
import com.mydemo.common.model.Result;
import com.mydemo.wallet.common.dto.WalletDTO;
import com.mydemo.wallet.entity.WalletEntity;
import com.mydemo.wallet.entity.WalletTransactionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final MongoTemplate mongoTemplate;
    private final UserFeignClient userFeignClient;

    public WalletDTO getOrCreateWallet(String userId) {
        // 跨服务调用：验证用户是否存在
        try {
            Result<Map<String, Object>> userResult = userFeignClient.getUserById(userId);
            if (!userResult.isSuccess() || userResult.getData() == null) {
                log.warn("用户不存在: userId={}", userId);
            }
        } catch (Exception e) {
            log.warn("用户服务调用失败: userId={}", userId, e);
        }

        WalletEntity wallet = mongoTemplate.findOne(
                Query.query(Criteria.where("userId").is(userId)), WalletEntity.class);
        if (wallet == null) {
            wallet = new WalletEntity();
            wallet.setUserId(userId);
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setFrozenBalance(BigDecimal.ZERO);
            wallet.setCurrency("CNY");
            wallet.setCreateTime(LocalDateTime.now());
            wallet.setUpdateTime(LocalDateTime.now());
            mongoTemplate.save(wallet);
        }
        return toDTO(wallet);
    }

    public WalletDTO recharge(String userId, BigDecimal amount) {
        WalletDTO wallet = getOrCreateWallet(userId);
        WalletEntity entity = mongoTemplate.findOne(
                Query.query(Criteria.where("userId").is(userId)), WalletEntity.class);

        BigDecimal balanceBefore = entity.getBalance();
        entity.setBalance(balanceBefore.add(amount));
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);

        // 记录流水
        saveTransaction(userId, "RECHARGE", amount, balanceBefore, entity.getBalance(), "充值");

        return toDTO(entity);
    }

    private void saveTransaction(String userId, String type, BigDecimal amount,
                                 BigDecimal before, BigDecimal after, String remark) {
        WalletTransactionEntity tx = new WalletTransactionEntity();
        tx.setUserId(userId);
        tx.setType(type);
        tx.setAmount(amount);
        tx.setBalanceBefore(before);
        tx.setBalanceAfter(after);
        tx.setRemark(remark);
        tx.setCreateTime(LocalDateTime.now());
        tx.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(tx);
    }

    private WalletDTO toDTO(WalletEntity entity) {
        WalletDTO dto = new WalletDTO();
        dto.setUserId(entity.getUserId());
        dto.setBalance(entity.getBalance());
        dto.setFrozenBalance(entity.getFrozenBalance());
        dto.setCurrency(entity.getCurrency());
        return dto;
    }
}
