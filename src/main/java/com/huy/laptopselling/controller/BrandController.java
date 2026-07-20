package com.huy.laptopselling.controller;

import com.huy.laptopselling.entity.Brand;
import com.huy.laptopselling.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.createBrand(brand));
    }
}