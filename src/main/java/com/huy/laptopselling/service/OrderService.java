package com.huy.laptopselling.service;

import com.huy.laptopselling.dto.OrderItemRequestDTO;
import com.huy.laptopselling.dto.OrderRequestDTO;
import com.huy.laptopselling.entity.Order;
import com.huy.laptopselling.entity.OrderDetail;
import com.huy.laptopselling.entity.Product;
import com.huy.laptopselling.entity.User;
import com.huy.laptopselling.exception.BadRequestException;
import com.huy.laptopselling.repository.OrderDetailRepository;
import com.huy.laptopselling.repository.OrderRepository;
import com.huy.laptopselling.repository.ProductRepository;
import com.huy.laptopselling.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO dto) {
        // Kiem tra User
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(dto.getShippingAddress());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);

        float totalAmount = 0;

        // Duyet qua tung sp trong don
        for (OrderItemRequestDTO item : dto.getItems()){
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));

            // Ktra ton kho
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BadRequestException("Sản phẩm " + product.getName() + "không đủ số lượng trong kho!");
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(product.getBasePrice().floatValue());

            orderDetailRepository.save(orderDetail);
            totalAmount += item.getQuantity() * product.getBasePrice().floatValue();
        }
        savedOrder.setTotalAmount(totalAmount);
        return orderRepository.save(savedOrder);
    }
}
