package com.embarkx.reviewms.review.impl;


import com.embarkx.reviewms.review.Review;
import com.embarkx.reviewms.review.ReviewRepository;
import com.embarkx.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
   // private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository ) {
        this.reviewRepository = reviewRepository;
       // this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReview(Long companyId) {
       List<Review> reviews = reviewRepository.findByCompanyId(companyId);
       return reviews;
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        if(companyId!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Review getReviewByCompanyReviewId( Long reviewId) {
       return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReviewById( Long reviewId, Review review) {
        Review review1 = reviewRepository.findById(reviewId).orElse(null);
      if(review1 != null){
          review1.setTitle(review.getTitle());
          review1.setCompanyId(review.getCompanyId());
          review1.setDescription(review.getDescription());
          review1.setRating(review.getRating());
          reviewRepository.save(review1);
          return true;
      }
        return false;
    }

    @Override
    public boolean deleteReviewById(Long reviewId) {

            Review review = reviewRepository.findById(reviewId).orElse(null);
            if(review != null){
                reviewRepository.delete(review);
                return true;
            }

            return true;

    }


}
