package com.sakshi.nursery.controller;



import com.sakshi.nursery.dto.ReviewRequest;
import com.sakshi.nursery.dto.ReviewResponse;
import com.sakshi.nursery.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{userId}")
    public ReviewResponse addReview(@PathVariable UUID userId, @RequestBody ReviewRequest request) {
        return reviewService.addReview(userId, request);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponse> getProductReviews(@PathVariable Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }
}

