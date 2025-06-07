// ReviewRequest.java
package com.sakshi.nursery.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private int rating; // from 1 to 5
    private String comment;
}
