package com.sakshi.nursery.repository;
import com.sakshi.nursery.model.OrderItem;
import com.sakshi.nursery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(String orderId);
    Optional<OrderItem> findByOrder_IdAndProduct_Id(String orderId, Long productId);
    List<OrderItem> findByProduct(Product product);


}

