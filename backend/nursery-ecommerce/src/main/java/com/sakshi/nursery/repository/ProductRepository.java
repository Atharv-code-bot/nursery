package com.sakshi.nursery.repository;

import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProduct(Long id);
}
