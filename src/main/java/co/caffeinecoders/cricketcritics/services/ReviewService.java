package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    //BASIC CRUD

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     *
     * @param review
     * @return the created review saved on the database
     */
    public Review createReview(Review review){
        Review createdReview = reviewRepository.save(review);
        return createdReview;
    }

    /**
     *
     * @param id
     * @return returns an Optional containing the Review Object with the matching ID or returns an empty Optional
     */
    public Optional<Review> findReviewById(Long id){
        Optional<Review> foundReview = reviewRepository.findById(id);
        return foundReview;
    }

    /**
     *
     * @param id
     * @param updateReview
     * @return Optional containing the updated Review object saved in the DB or returns an empty Optional if the object not found
     */
    public Optional<Review> updateReview(Long id, Review updateReview){
        Optional<Review> reviewToUpdate = reviewRepository.findById(id);
        if (reviewToUpdate.isPresent()){
            reviewToUpdate.get().setDescription(updateReview.getDescription());
            reviewToUpdate.get().setScore(updateReview.getScore());
            reviewRepository.save(reviewToUpdate.get());
        }
        return reviewToUpdate;
    }


    /**
     *
     * @param id
     * @return an Optional containing the deactivated Review object, or an empty Optional if the object is not found
     */
    public Optional<Review> deactivateReviewById(Long id){
        Optional<Review> reviewToDeactivate = reviewRepository.findById(id);
        if (reviewToDeactivate.isPresent()){
            reviewToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            reviewRepository.save(reviewToDeactivate.get());
        }
        return reviewToDeactivate;
    }



}
