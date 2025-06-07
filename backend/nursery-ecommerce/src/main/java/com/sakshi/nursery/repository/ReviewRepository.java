// ReviewRepository.java
package com.sakshi.nursery.repository;

import com.sakshi.nursery.model.Review;
import com.sakshi.nursery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
