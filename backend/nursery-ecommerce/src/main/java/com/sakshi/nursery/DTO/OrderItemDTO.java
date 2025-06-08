package com.sakshi.nursery.DTO;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private int quantity;
    private BigDecimal price;
}
