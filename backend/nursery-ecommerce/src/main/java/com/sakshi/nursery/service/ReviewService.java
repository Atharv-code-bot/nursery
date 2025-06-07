// ReviewService.java
package com.sakshi.nursery.service;

import com.sakshi.nursery.dto.ReviewRequest;
import com.sakshi.nursery.dto.ReviewResponse;
import com.sakshi.nursery.model.*;
import com.sakshi.nursery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewResponse addReview(UUID userId, ReviewRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);

        return mapToResponse(saved);
    }

    public List<ReviewResponse> getReviewsForProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Review> reviews = reviewRepository.findByProduct(product);
        return reviews.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ReviewResponse mapToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setUsername(review.getUser().getName());
        response.setProductId(review.getProduct().getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        return response;
    }
}
