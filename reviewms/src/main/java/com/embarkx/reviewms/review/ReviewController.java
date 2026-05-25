package com.embarkx.reviewms.review;

import com.embarkx.reviewms.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    ReviewService reviewService;
    ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
   public ResponseEntity<List<Review>> getAllReview(@RequestParam Long companyId){
        return new ResponseEntity<>( reviewService.getAllReview(companyId), HttpStatus.OK);
   }

   @PostMapping
   public ResponseEntity<String> createReview(@RequestParam Long companyId,@RequestBody Review review){
      boolean isRevieCreate = reviewService.createReview(companyId,review);
      if(isRevieCreate) {
          reviewMessageProducer.sendMessage(review);
          return new ResponseEntity<>("created", HttpStatus.CREATED);
      }
      else {
          return new ResponseEntity<>("Couldnt found company", HttpStatus.NOT_FOUND);
      }

   }

   @GetMapping("/{reviewId}")
   public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
       return new ResponseEntity<>(reviewService.getReviewByCompanyReviewId(reviewId),HttpStatus.OK);
   }

   @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review){
        boolean isUpdated = reviewService.updateReviewById(reviewId, review);
       if(isUpdated)
           return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
       return new ResponseEntity<>("Failed to update", HttpStatus.NOT_MODIFIED);
   }

   @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isDeleted = reviewService.deleteReviewById(reviewId);
        if(isDeleted)
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
       return new ResponseEntity<>("Failed to delete", HttpStatus.NOT_MODIFIED);

   }

   @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
      List<Review> reviewList = reviewService.getAllReview(companyId);
      return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);


   }

}
