package com.sakshi.nursery.Controller;
import com.sakshi.nursery.DTO.ProductResponseDTO;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.repository.CategoryRepository;
import com.sakshi.nursery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/api/customer/products")
public class CustomerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    public CategoryRepository categoryRepository;

    @GetMapping("/all")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByCategoryName(name));
    }

    @GetMapping("/product/{name}")
    public ProductResponseDTO getProductsByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }
}

