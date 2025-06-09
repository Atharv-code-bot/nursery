package com.sakshi.nursery.dto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates UUID as primary key
    private long productId;
    private int quantity;
}

