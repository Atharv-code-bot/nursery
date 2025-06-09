package com.sakshi.nursery.Controller;
import com.sakshi.nursery.DTO.ProductResponseDTO;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.repository.CategoryRepository;
import com.sakshi.nursery.service.ProductService;
import com.sakshi.nursery.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private PushNotificationService pushNotificationService;

    @GetMapping("/all")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductsByCategoryName(name));
    }

    @PostMapping(path = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestPart("file") MultipartFile[] file, @RequestPart Product product, @RequestParam String token) {
        Product savedProduct = productService.createProduct(product,file);
        pushNotificationService.sendPushNotificationToToken(token,product);

        ProductResponseDTO responseDTO = new ProductResponseDTO(savedProduct);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping( path = "/update" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestPart Product productRequest,@RequestPart MultipartFile[] file) {
        Product updatedProduct = productService.updateProduct(productRequest,file);
        return ResponseEntity.ok(new ProductResponseDTO(updatedProduct));
    }


    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productService.deleteProductByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/product/{name}")
    public ProductResponseDTO getProductsByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }
}


