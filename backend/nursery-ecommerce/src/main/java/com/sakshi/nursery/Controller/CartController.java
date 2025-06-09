package com.sakshi.nursery.controller;

import com.sakshi.nursery.dto.CartItemDTO;
import com.sakshi.nursery.dto.CartItemResponseDTO;
import com.sakshi.nursery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/carts")
//@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/AddToCart")

    public ResponseEntity<String> addToCart(@RequestParam long id, @RequestBody CartItemDTO dto) {
        cartService.addToCart(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(" Added");
    }

    @GetMapping("/GetCarts")
    public ResponseEntity<List<CartItemResponseDTO>> getCart(@RequestParam Long userId) {
        List<CartItemResponseDTO> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
    @DeleteMapping("/deleteBy-ProductID")
    public ResponseEntity<String> DeleteByProductID( @RequestParam Long useId,@RequestParam Long productid){
        cartService.deleteProductAndCleanCarts( useId  ,productid);
        return ResponseEntity.ok("Deleted");
    }
    @PutMapping("/update-quantity")
    public ResponseEntity<String> updateCartQuantity( @RequestParam Long userId, @RequestBody CartItemDTO dto) {

        cartService.updateCartQuantity(userId, dto);
        return ResponseEntity.ok("Quantity updated successfully.");
    }

}

