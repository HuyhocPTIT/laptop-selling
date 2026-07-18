package com.huy.laptopselling.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String thumbnailUrl;
    private Double basePrice;
    private Integer stockQuantity;
    private String categoryName;
    private String brandName;
}
