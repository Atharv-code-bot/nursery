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
public  class CategoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Or AUTO
    private Long id;
    private String name;
}
