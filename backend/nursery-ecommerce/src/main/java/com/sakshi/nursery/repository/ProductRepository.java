package com.sakshi.nursery.repository;
import com.sakshi.nursery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Optional<Product> findByName(String name);

    Optional< Product> findByNameAndCategoryId(String name, Long id);
}
