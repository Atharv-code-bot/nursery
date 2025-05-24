package com.sakshi.nursery.service;
import com.sakshi.nursery.model.Category;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.repository.CategoryRepository;
import com.sakshi.nursery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByname(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + name));
    }


    public List<Product> getProductsByCategoryName(String name) {
        Category category = categoryRepository.findByName(name);
        return productRepository.findByCategoryId(category.getId());
    }

    public Product createProduct(Product productRequest) {
        // Check for duplicate product name
        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new RuntimeException("Product name already exists: " + productRequest.getName());
        }
        // Ensure category is present and valid
        if (productRequest.getCategory() == null || productRequest.getCategory().getName() == null) {
            throw new RuntimeException("Category name must not be null");
        }

        Category category = categoryRepository.findByName(productRequest.getCategory().getName());
        if (category == null) {
            category = new Category();
            category.setName(productRequest.getCategory().getName());
            category = categoryRepository.save(category);
        }
        productRequest.setCategory(category);
        return productRepository.save(productRequest);
    }


    public Product updateProduct(Product productRequest) {
        Product existingProduct = productRepository.findByName(productRequest.getName())
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + productRequest.getName()));
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setImageUrl(productRequest.getImageUrl());
        if (productRequest.getCategory() != null && productRequest.getCategory().getName() != null) {
            String newCategoryName = productRequest.getCategory().getName();
            Category category = existingProduct.getCategory();
            if (category != null) {
                category.setName(newCategoryName);
                categoryRepository.save(category);
            }
        }
        return productRepository.save(existingProduct);
    }



    public void deleteProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
        productRepository.delete(product);
    }

    public Product getProductsByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
    }
}

