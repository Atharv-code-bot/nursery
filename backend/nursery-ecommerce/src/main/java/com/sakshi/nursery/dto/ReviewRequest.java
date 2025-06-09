// ReviewRequest.java
package com.sakshi.nursery.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private int rating; // from 1 to 5
    private String comment;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
