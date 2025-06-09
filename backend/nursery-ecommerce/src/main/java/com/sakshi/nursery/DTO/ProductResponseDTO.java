package com.sakshi.nursery.DTO;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private List<String> imageUrls; // ✅ Change from single imageUrl to list
    private CategoryDTO category;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();

        // ✅ Extract all image paths from the product's image list
        this.imageUrls = product.getImages().stream()
                .map(ProductImage::getImagePath)
                .collect(Collectors.toList());

        this.category = new CategoryDTO(
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }



    // Getters and Setters (or use Lombok @Getter/@Setter if you prefer)
}


