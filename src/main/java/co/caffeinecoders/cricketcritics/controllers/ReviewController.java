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
    @Operation(summary = "This API creates a Review on the DB based on the User who authored it and the Movie for which the review has been written. And returns the object created for confirmation")
    public ResponseEntity<Review> createReview(@RequestBody Review reviewToCreate){
        Review createdReview = reviewService.createReview(reviewToCreate);
        return ResponseEntity.ok(createdReview);
    }

    //GET
    @GetMapping("/{id}")
    @Operation(summary = "This API retrives the Review object by the given ID, it returns notFound if the resource is not present in the DB")
    public ResponseEntity<Review> findActiveReviewById(@PathVariable Long id){
        Optional<Review> foundReview = reviewService.findActiveReviewById(id);
        if (foundReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundReview.get());
    }

    @GetMapping("/all")
    @Operation(summary = "This API retrives a List with all the Review objects setted as 'Active' in the DB, it returns an empty List if none of it is Active")
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
    @Operation(summary = "This API updates the Review matching by the given ID with the new Review given by RequestBody, only updatable fields are Description and Score, it returns notFound if the resource is not present or set as status 'Deactivated'")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long id, @RequestBody Review reviewUpdate){
        Optional<Review> updatedReview = reviewService.updateReview(id,reviewUpdate);
        if (updatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReview.get());
    }

    //TODO creare controllo per un solo like a persona
    //TODO creare metodo unlike
    @PutMapping("/like/{id}")
    @Operation(summary = "This API adds to the likesCounter of the Review found by the given ID a +1 value each time this is called, it returns the Review updated, or notFound if the resource is absent")
    public ResponseEntity<Review> addLikeToReview(@PathVariable Long id){
        Optional<Review> increasedReview = reviewService.addLikeToCounter(id);
        if (increasedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(increasedReview.get());
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "This API deactivates the Review by the given ID, and set the status as ìDeactivated', it returns the deactivated object or notFound if the resource is Absent or already deactivated")
    public ResponseEntity<Review> deactivateReviewById(@PathVariable Long id){
        Optional<Review> deactivatedReview = reviewService.deactivateReviewById(id);
        if (deactivatedReview.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedReview.get());
    }


}
