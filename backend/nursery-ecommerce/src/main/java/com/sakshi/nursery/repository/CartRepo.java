package com.sakshi.nursery.repository;
import com.sakshi.nursery.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    void deleteByUserId(Long userId);
    Optional<Cart> findByUserId(Long userId);
}
