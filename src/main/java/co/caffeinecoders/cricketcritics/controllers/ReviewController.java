package co.caffeinecoders.cricketcritics.controllers;

import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/userReviews/{userId}")
    @Operation(summary = "This API retrieves all the reviews written by a User by giving the User ID")
    public ResponseEntity<List<Review>> findAllReviewsByUserId(@PathVariable Long userId){
        List<Review> reviewList = reviewService.findAllReviewsByUserId(userId);
        return ResponseEntity.ok(reviewList);
    }
    @GetMapping("/movieReviews/{movieId}")
    @Operation(summary = "This API retrieves all the reviews written by users on a movie by giving the Movie ID")
    public ResponseEntity<List<Review>> findAllReviewsByMovieId(@PathVariable Long movieId){
        List<Review> reviewList = reviewService.findAllReviewsByUserId(movieId);
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
