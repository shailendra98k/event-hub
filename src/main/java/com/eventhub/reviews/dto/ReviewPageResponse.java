package com.eventhub.reviews.dto;

import java.util.List;

public class ReviewPageResponse {
    public List<ReviewItem> reviews;
    public int page;
    public int pageSize;
    public int total;

    public static class ReviewItem {
        public Long id;
        public Long buyerUserId;
        public Integer rating;
        public String text;
        public String createdAt;
    }
}

