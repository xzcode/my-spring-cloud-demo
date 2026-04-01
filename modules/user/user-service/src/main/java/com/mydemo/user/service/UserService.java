package com.mydemo.user.service;

import com.mydemo.common.model.PageResult;
import com.mydemo.user.common.dto.UserDTO;
import com.mydemo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final MongoTemplate mongoTemplate;

    public UserDTO register(String username, String nickname) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setNickname(nickname);
        entity.setStatus(0);
        entity.setRole("USER");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public UserDTO getById(String userId) {
        UserEntity entity = mongoTemplate.findById(userId, UserEntity.class);
        return entity != null ? toDTO(entity) : null;
    }

    public PageResult<UserDTO> listUsers(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, UserEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<UserDTO> list = mongoTemplate.find(query, UserEntity.class)
                .stream().map(this::toDTO).toList();
        return PageResult.of(list, total, page, size);
    }

    private UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setNickname(entity.getNickname());
        dto.setAvatar(entity.getAvatar());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
