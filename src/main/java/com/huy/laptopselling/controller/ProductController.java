package com.huy.laptopselling.controller;

import com.huy.laptopselling.dto.ProductRequestDTO;
import com.huy.laptopselling.dto.ProductResponseDTO;
import com.huy.laptopselling.entity.Product;
import com.huy.laptopselling.exception.ResourceNotFoundException;
import com.huy.laptopselling.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping({"/{id}"})
    public ResponseEntity<ProductResponseDTO> getProductDetailById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(productService.convertToResponseDTO(product));
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        Product savedProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(productService.convertToResponseDTO(savedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProductResponseDTO> dtoPage = productService.getProduct(name, pageable)
                .map(productService::convertToResponseDTO);

        return ResponseEntity.ok(dtoPage);
    }
}
