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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product){
        return productRepository.findById(id).map(existingProduct -> {
            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }
            if (product.getDescription() != null){
                existingProduct.setDescription(product.getDescription());
            }
            if (product.getStatus() != null){
                existingProduct.setStatus(product.getStatus());
            }
            if (product.getBasePrice() != null){
                existingProduct.setBasePrice(product.getBasePrice());
            }
            if (product.getStockQuantity() != null){
                existingProduct.setStockQuantity(product.getStockQuantity());
            }
            if (product.getThumbnailUrl() != null){
                existingProduct.setThumbnailUrl(product.getThumbnailUrl());
            }
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
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
