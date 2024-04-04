package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {


    @Autowired
    ReviewService reviewService;


    //POST
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Review reviewToCreate){
        Review createdReview = reviewService.createReview(reviewToCreate);
        return ResponseEntity.ok(createdReview);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Review> findActiveReviewById(@PathVariable Long id){
        Optional<Review> foundReview = reviewService.findActiveReviewById(id);
        if (foundReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundReview.get());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Review>> findAllActiverReviews(){
        List<Review> reviewList = reviewService.findAllActiveReviews();
        return ResponseEntity.ok(reviewList);
    }

    //PUT
    @PutMapping("/edit/{id}")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long id, @RequestBody Review reviewUpdate){
        Optional<Review> updatedReview = reviewService.updateReview(id,reviewUpdate);
        if (updatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReview.get());
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<Review> addLikeToReview(@PathVariable Long id){
        Optional<Review> increasedReview = reviewService.addLikeToCounter(id);
        if (increasedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(increasedReview.get());
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Review> deactivateReviewById(@PathVariable Long id){
        Optional<Review> deactivatedReview = reviewService.deactivateReviewById(id);
        if (deactivatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedReview.get());
    }


}
