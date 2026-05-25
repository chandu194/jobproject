package com.embarkx.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReview(Long companyId);

    boolean createReview(Long companyId, Review review);

    Review getReviewByCompanyReviewId( Long reviewId);

    boolean updateReviewById( Long reviewId, Review review);

    boolean deleteReviewById( Long reviewId);
}
