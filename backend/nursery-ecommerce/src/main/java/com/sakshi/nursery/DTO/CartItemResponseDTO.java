package com.sakshi.nursery.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class CartItemResponseDTO {
    private String productName;
    private BigDecimal price;
    private int quantity;

    public CartItemResponseDTO(String productName, BigDecimal price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

}
