package com.sakshi.nursery.dto;

import com.sakshi.nursery.model.OrderStatus;
import com.sakshi.nursery.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class AdminOrderResponseDTO {

    private String address;
    private Date bookingDate;
    private Date deliveryDate;
    private PaymentStatus paymentStatus;
    private BigDecimal price;
    private int quantity;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String customerName;
    private String productName;

    // ✅ Constructor
    public AdminOrderResponseDTO(String address, Date bookingDate, Date deliveryDate,
                                 PaymentStatus paymentStatus, BigDecimal price, int quantity,
                                 OrderStatus status, BigDecimal totalPrice,
                                 String customerName, String productName) {
        this.address = address;
        this.bookingDate = bookingDate;
        this.deliveryDate = deliveryDate;
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerName = customerName;
        this.productName = productName;
    }


}

