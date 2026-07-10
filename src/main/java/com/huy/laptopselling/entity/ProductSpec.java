package com.huy.laptopselling.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_specs")
@Data
public class ProductSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpu;

    @Column(nullable = false)
    private Integer ram;

    @Column(nullable = false)
    private Integer ssd;

    private String gpu;

    @Column(name = "screen_size")
    private String screenSize;

    private Double weight;

    private String battery;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;
}