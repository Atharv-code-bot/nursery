package com.sakshi.nursery.Controller;
import com.sakshi.nursery.DTO.ProductResponseDTO;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.repository.CategoryRepository;
import com.sakshi.nursery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    public CategoryRepository categoryRepository;

    @GetMapping("/all")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts()
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("name") String name) {
        List<Product> product = productService.getProductsByCategoryName(name);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody Product productRequest) {
        Product savedProduct = productService.createProduct(productRequest);
        ProductResponseDTO responseDTO = new ProductResponseDTO(savedProduct);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody Product productRequest) {
        Product updatedProduct = productService.updateProduct(productRequest);
        return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
    }


    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productService.deleteProductByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/product/{name}")
    public ProductResponseDTO getProductsByName(@PathVariable String name) {
        return new ProductResponseDTO(productService.getProductsByName(name));
    }
}


