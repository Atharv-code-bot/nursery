package com.sakshi.nursery.service;
import com.sakshi.nursery.DTO.ProductResponseDTO;
import com.sakshi.nursery.model.Category;
import com.sakshi.nursery.model.OrderItem;
import com.sakshi.nursery.model.Product;
import com.sakshi.nursery.model.ProductImage;
import com.sakshi.nursery.repository.CategoryRepository;
import com.sakshi.nursery.repository.OrderItemRepository;
import com.sakshi.nursery.repository.ProductImageRepository;
import com.sakshi.nursery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    private final Map<String, List<String>> productImageDictionary = new ConcurrentHashMap<>();

    @Autowired
    public CategoryRepository categoryRepository;
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAllWithImages() .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }
    public ProductResponseDTO getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));
        return new ProductResponseDTO(product); // will include all images
    }


    public Product createProduct(Product productRequest, MultipartFile[] files) {
        // Validate product name
        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new RuntimeException("Product name already exists: " + productRequest.getName());
        }

        // Validate and get/create category
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
        Product savedProduct = productRepository.save(productRequest);

        // Process multiple images
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        File uploadPath = new File("Products/" + category.getName() + "/" + savedProduct.getName());
                        if (!uploadPath.exists()) uploadPath.mkdirs();

                        Path filePath = Paths.get(uploadPath.getAbsolutePath(), file.getOriginalFilename());
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        ProductImage image = new ProductImage();
                        image.setImagePath(filePath.toString());
                        image.setProduct(savedProduct);

                        savedProduct.getImages().add(image); // Add image to product's image list
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file: " + e.getMessage());
                    }
                }
            }
        }

        return productRepository.save(savedProduct); // Save product again with images
    }

    public Product updateProduct(Product productRequest, MultipartFile[] files) {
        Product existingProduct = productRepository.findByName(productRequest.getName())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productRequest.getId()));

        // Update basic fields
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());

        // Update category
        if (productRequest.getCategory() != null && productRequest.getCategory().getName() != null) {
            Category category = categoryRepository.findByName(productRequest.getCategory().getName());
            if (category == null) {
                category = new Category();
                category.setName(productRequest.getCategory().getName());
                category = categoryRepository.save(category);
            }
            existingProduct.setCategory(category);
        }

        // OPTIONAL: Delete old images
        List<ProductImage> oldImages = existingProduct.getImages();
        for (ProductImage image : oldImages) {
            File imgFile = new File(image.getImagePath());
            if (imgFile.exists()) {
                imgFile.delete(); // delete file from disk
            }
        }
        oldImages.clear(); // clear old references

        // Save new images
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        File uploadPath = new File("Products/" + existingProduct.getCategory().getName() + "/" + existingProduct.getName());
                        if (!uploadPath.exists()) uploadPath.mkdirs();

                        Path filePath = Paths.get(uploadPath.getAbsolutePath(), file.getOriginalFilename());
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        ProductImage newImage = new ProductImage();
                        newImage.setImagePath(filePath.toString());
                        newImage.setProduct(existingProduct);

                        existingProduct.getImages().add(newImage); // add to product image list
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file: " + e.getMessage());
                    }
                }
            }
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + name));

        // Fetch all images of the product
        List<ProductImage> productImages = productImageRepository.findByProductId(product.getId());

        // Get all image paths to check for duplicates
        Set<String> imagePathsToDelete = new HashSet<>();

        for (ProductImage image : productImages) {
            String path = image.getImagePath();
            if (path != null && !path.isEmpty()) {
                imagePathsToDelete.add(path); // avoid duplicates
            }
        }

        // Delete physical files from disk
        for (String path : imagePathsToDelete) {
            File file = new File(path);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("Failed to delete image file: " + path);
                }
            }
        }

        // Delete image records from DB
        productImageRepository.deleteAll(productImages);
        List<OrderItem> items = orderItemRepository.findByProduct(product);
        orderItemRepository.deleteAll(items);
        // Delete the product
        productRepository.delete(product);
    }



    public List<ProductResponseDTO> getProductsByCategoryName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new RuntimeException("Category not found: " + name);
        }

        List<Product> products = productRepository.findByCategoryId(category.getId());

        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

}

