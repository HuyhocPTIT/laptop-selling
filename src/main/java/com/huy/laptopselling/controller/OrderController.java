package com.huy.laptopselling.controller;

import com.huy.laptopselling.dto.OrderRequestDTO;
import com.huy.laptopselling.entity.Order;
import com.huy.laptopselling.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestBody OrderRequestDTO dto) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
    }
}
