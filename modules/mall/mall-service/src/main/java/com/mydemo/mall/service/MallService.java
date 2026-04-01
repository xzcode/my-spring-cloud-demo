package com.mydemo.mall.service;

import com.mydemo.common.model.PageResult;
import com.mydemo.mall.common.dto.ProductDTO;
import com.mydemo.mall.entity.ProductEntity;
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
public class MallService {

    private final MongoTemplate mongoTemplate;

    public ProductDTO createProduct(String name, String description, BigDecimal price, Integer stock) {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setStock(stock);
        entity.setStatus("ON_SALE");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mongoTemplate.save(entity);
        return toDTO(entity);
    }

    public ProductDTO getById(String productId) {
        ProductEntity entity = mongoTemplate.findById(productId, ProductEntity.class);
        return entity != null ? toDTO(entity) : null;
    }

    public PageResult<ProductDTO> listProducts(int page, int size) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "createTime"));
        long total = mongoTemplate.count(query, ProductEntity.class);
        query.skip((long) (page - 1) * size).limit(size);
        List<ProductDTO> list = mongoTemplate.find(query, ProductEntity.class)
                .stream().map(this::toDTO).toList();
        return PageResult.of(list, total, page, size);
    }

    private ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCategoryId(entity.getCategoryId());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
