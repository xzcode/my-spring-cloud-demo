package com.mydemo.live.service;

import com.mydemo.common.feign.client.UserFeignClient;
import com.mydemo.common.model.PageResult;
import com.mydemo.common.model.Result;
import com.mydemo.live.common.dto.LiveRoomDTO;
import com.mydemo.live.entity.LiveRoomEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService {

    private final MongoTemplate mongoTemplate;
    private final UserFeignClient userFeignClient;

    public LiveRoomDTO createRoom(String title, String hostId) {
        LiveRoomEntity entity = new LiveRoomEntity();
        entity.setTitle(title);
        entity.setHostId(hostId);
        entity.setStatus("PREPARING");
        entity.setViewerCount(0L);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        // 跨服务调用：获取主播信息
        enrichHostInfo(entity);

        mongoTemplate.save(entity);
        return toDTO(entity, null);
    }

    public LiveRoomDTO getRoomById(String roomId) {
        LiveRoomEntity entity = mongoTemplate.findById(roomId, LiveRoomEntity.class);
        if (entity == null) {
            return null;
        }
        // 跨服务调用：获取主播头像
        String hostAvatar = null;
        try {
            Result<Map<String, Object>> userResult = userFeignClient.getUserById(entity.getHostId());
            if (userResult.isSuccess() && userResult.getData() != null) {
                hostAvatar = (String) userResult.getData().get("avatar");
            }
        } catch (Exception e) {
            log.warn("获取主播信息失败: hostId={}", entity.getHostId(), e);
        }
        return toDTO(entity, hostAvatar);
    }

    public PageResult<LiveRoomDTO> listRooms(String status, int page, int size) {
        Query query = new Query();
        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, LiveRoomEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<LiveRoomDTO> list = mongoTemplate.find(query, LiveRoomEntity.class)
                .stream().map(e -> toDTO(e, null)).toList();
        return PageResult.of(list, total, page, size);
    }

    private void enrichHostInfo(LiveRoomEntity entity) {
        try {
            Result<Map<String, Object>> userResult = userFeignClient.getUserById(entity.getHostId());
            if (userResult.isSuccess() && userResult.getData() != null) {
                entity.setHostName((String) userResult.getData().get("nickname"));
            }
        } catch (Exception e) {
            log.warn("获取主播信息失败: hostId={}", entity.getHostId(), e);
        }
    }

    private LiveRoomDTO toDTO(LiveRoomEntity entity, String hostAvatar) {
        LiveRoomDTO dto = new LiveRoomDTO();
        dto.setRoomId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCoverUrl(entity.getCoverUrl());
        dto.setHostId(entity.getHostId());
        dto.setHostName(entity.getHostName());
        dto.setHostAvatar(hostAvatar);
        dto.setStatus(entity.getStatus());
        dto.setViewerCount(entity.getViewerCount());
        dto.setStartTime(entity.getStartTime());
        return dto;
    }
}
