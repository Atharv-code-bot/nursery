package com.sakshi.nursery.DTO;
import com.sakshi.nursery.model.OrderStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates UUID as primary key
    private long orderId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}

