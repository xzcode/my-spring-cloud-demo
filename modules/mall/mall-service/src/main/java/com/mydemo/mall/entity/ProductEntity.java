package com.mydemo.mall.entity;

import com.mydemo.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "product")
public class ProductEntity extends BaseEntity {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUrl;

    @Indexed
    private String categoryId;

    /** ON_SALE / OFF_SALE */
    private String status;
}
