package com.mydemo.game.service;

import com.mydemo.common.model.PageResult;
import com.mydemo.game.common.dto.GameDTO;
import com.mydemo.game.entity.GameEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final MongoTemplate mongoTemplate;

    public GameDTO create(String name, String type, BigDecimal minBet, BigDecimal maxBet) {
        GameEntity entity = new GameEntity();
        entity.setName(name);
        entity.setType(type);
        entity.setMinBet(minBet);
        entity.setMaxBet(maxBet);
        entity.setStatus("ACTIVE");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public GameDTO getById(String gameId) {
        GameEntity entity = mongoTemplate.findById(gameId, GameEntity.class);
        return entity != null ? toDTO(entity) : null;
    }

    public PageResult<GameDTO> listGames(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, GameEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<GameDTO> list = mongoTemplate.find(query, GameEntity.class)
                .stream().map(this::toDTO).toList();
        return PageResult.of(list, total, page, size);
    }

    private GameDTO toDTO(GameEntity entity) {
        GameDTO dto = new GameDTO();
        dto.setGameId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setRules(entity.getRules());
        dto.setStatus(entity.getStatus());
        dto.setMinBet(entity.getMinBet());
        dto.setMaxBet(entity.getMaxBet());
        return dto;
    }
}
