// ReviewResponse.java
package com.sakshi.nursery.dto;

import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;
    private String username;
    private Long productId;
    private int rating;
    private String comment;
}
