package com.mydemo.admin.service;

import com.mydemo.admin.common.dto.AdminUserDTO;
import com.mydemo.admin.entity.AdminUserEntity;
import com.mydemo.common.model.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final MongoTemplate mongoTemplate;

    public AdminUserDTO createAdmin(String username, String realName, String role) {
        AdminUserEntity entity = new AdminUserEntity();
        entity.setUsername(username);
        entity.setRealName(realName);
        entity.setRole(role != null ? role : "OPERATOR");
        entity.setStatus(0);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public PageResult<AdminUserDTO> listAdmins(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, AdminUserEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<AdminUserDTO> list = mongoTemplate.find(query, AdminUserEntity.class)
                .stream().map(this::toDTO).toList();
        return PageResult.of(list, total, page, size);
    }

    private AdminUserDTO toDTO(AdminUserEntity entity) {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setAdminId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRealName(entity.getRealName());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
