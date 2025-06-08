package com.sakshi.nursery.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;
    private BigDecimal price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",nullable = false) // Match to 'id' in Order
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id",nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalPrice;
    @Column(name = "BookingDate")
    private Date bookingDate;

    private String address;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Date deliveryDate;


}