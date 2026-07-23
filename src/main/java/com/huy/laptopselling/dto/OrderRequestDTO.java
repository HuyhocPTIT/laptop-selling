package com.huy.laptopselling.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Long userId;
    private String shippingAddress;
    private String phoneNumber;
    private List<OrderItemRequestDTO> items;
}
