package com.sakshi.nursery.dto;
import com.sakshi.nursery.model.OrderStatus;
import com.sakshi.nursery.model.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerOrderResponseDTO {
    private String productName;
    private int quantity;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private Date Delivrydate;
    private  String address;
    private PaymentStatus paymentStatus;
    private String orderId;


    public CustomerOrderResponseDTO(String productName, int quantity, OrderStatus status, BigDecimal totalPrice, Date delivrydate, String address, PaymentStatus paymentStatus, String orderId) {
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
        this.totalPrice = totalPrice;
        Delivrydate = delivrydate;
        this.address = address;
        this.paymentStatus = paymentStatus;
        this.orderId = orderId;
    }

}
