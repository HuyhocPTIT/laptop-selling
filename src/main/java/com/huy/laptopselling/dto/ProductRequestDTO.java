package com.huy.laptopselling.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private String status;
    private String thumbnailUrl;
    private Double basePrice;
    private Integer stockQuantity;
    private Long categoryId;
    private Long brandId;
}
