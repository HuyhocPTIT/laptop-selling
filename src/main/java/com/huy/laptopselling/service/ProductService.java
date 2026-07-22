package com.huy.laptopselling.service;

import com.huy.laptopselling.dto.ProductRequestDTO;
import com.huy.laptopselling.dto.ProductResponseDTO;
import com.huy.laptopselling.entity.Brand;
import com.huy.laptopselling.entity.Category;
import com.huy.laptopselling.entity.Product;
import com.huy.laptopselling.exception.ResourceNotFoundException;
import com.huy.laptopselling.repository.BrandRepository;
import com.huy.laptopselling.repository.CategoryRepository;
import com.huy.laptopselling.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (dto.getName() != null) existingProduct.setName(dto.getName());
        if (dto.getDescription() != null) existingProduct.setDescription(dto.getDescription());
        if (dto.getStatus() != null) existingProduct.setStatus(dto.getStatus());
        if (dto.getBasePrice() != null) existingProduct.setBasePrice(dto.getBasePrice());
        if (dto.getStockQuantity() != null) existingProduct.setStockQuantity(dto.getStockQuantity());
        if (dto.getThumbnailUrl() != null) existingProduct.setThumbnailUrl(dto.getThumbnailUrl());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            existingProduct.setCategory(category);
        }

        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + dto.getBrandId()));
            existingProduct.setBrand(brand);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDTO(updatedProduct);
    }

    public Page<Product> getProduct(String name, Pageable pageable){
        if (name != null && !name.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setStatus(product.getStatus());
        dto.setBasePrice(product.getBasePrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setThumbnailUrl(product.getThumbnailUrl());

        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getBrand() != null) {
            dto.setBrandName(product.getBrand().getName());
        }
        return dto;
    }

    public Product createProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setBasePrice(dto.getBasePrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setThumbnailUrl(dto.getThumbnailUrl());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + dto.getBrandId()));
            product.setBrand(brand);
        }

        return productRepository.save(product);
    }
}
