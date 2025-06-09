package com.sakshi.nursery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {



    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

    public Order(String id) {
        this.id = id;
    }
}
