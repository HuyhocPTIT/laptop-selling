package com.huy.laptopselling.repository;

import com.huy.laptopselling.entity.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecRepository extends JpaRepository<ProductSpec, Long> {
}
