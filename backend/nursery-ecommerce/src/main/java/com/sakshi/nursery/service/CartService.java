package com.sakshi.nursery.service;



import com.sakshi.nursery.dto.CartItemDTO;
import com.sakshi.nursery.dto.CartItemResponseDTO;
import com.sakshi.nursery.model.Cart;
import com.sakshi.nursery.model.CartItem;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.model.User;
import com.sakshi.nursery.repository.CartItemRepository;
import com.sakshi.nursery.repository.CartRepo;
import com.sakshi.nursery.repository.ProductRepository;
import com.sakshi.nursery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class CartService {
    @Autowired
    private CartItemRepository cartRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CartRepo crepo;
    @Autowired
     private UserRepository userRepository;

    public void addToCart(Long userId, CartItemDTO dto) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new RuntimeException("User Not Found"));

        Cart cart = crepo.findByUserId(userId).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = crepo.save(cart);
        }
        Product product = productRepo.findById(dto.getProductId()).orElseThrow();
        CartItem item = new CartItem();
        item.setCart(cart); // Attach to the existing or newly created cart
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        cartRepo.save(item);

    }
    public List<CartItemResponseDTO> getCartItems(Long userId) {
            Cart cart = crepo.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("Cart not found for user"));

    List<CartItem> items = cartRepo.findByCartId(cart.getId());

    return items.stream()
            .map(item -> new CartItemResponseDTO(
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity()
            ))
            .collect(Collectors.toList());
    }
    public void deleteProductAndCleanCarts( Long userId ,Long prductid) {
        Cart cart=crepo.findByUserId(userId).orElseThrow();
        cartRepo.deleteByCartIdAndProductId(cart.getId(),prductid);
    }
    public void updateCartQuantity(Long userId, CartItemDTO   dto) {
        Cart cart=crepo.findByUserId(userId).orElseThrow();
        cartRepo.updateQuantityByCartIdAndProductId(cart.getId(), dto.getProductId(), dto.getQuantity());
    }
}
