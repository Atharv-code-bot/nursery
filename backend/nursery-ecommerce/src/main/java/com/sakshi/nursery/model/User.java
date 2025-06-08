package com.sakshi.nursery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates UUID as primary key
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    public User(Long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true, length = 255)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email format")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // CUSTOMER or ADMIN

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 20, unique = true)
    private String phoneNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;






    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Automatically set registration date
    }
}
