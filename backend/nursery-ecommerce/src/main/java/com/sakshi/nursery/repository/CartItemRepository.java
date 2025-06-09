package com.sakshi.nursery.repository;
import com.sakshi.nursery.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    List<CartItem> findByCartId(Long cartId);
    CartItem findByProductId(Long productId);
    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.product.id = :productId")
    void deleteByCartIdAndProductId(Long cartId,Long productId);
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.cart.id = :cartId AND c.product.id = :productId")
    void updateQuantityByCartIdAndProductId( Long cartId, Long productId,int quantity);

}
