package co.caffeinecoders.cricketcritics.services;

import co.caffeinecoders.cricketcritics.entities.DTO.PersonalizedResponse;
import co.caffeinecoders.cricketcritics.entities.Review;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.repositories.ReviewRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final Integer MINIMUM_SCORE = 0;
    private final Integer MAX_SCORE = 100;


    //BASIC CRUD

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * @param review
     * @return the created review saved in the database
     *          plus each time this method is called updates the MovieScore
     */
    public PersonalizedResponse createReview(Review review) {
        boolean isValid = checkValidScore(review.getScore());
        PersonalizedResponse response = new PersonalizedResponse(HttpServletResponse.SC_BAD_REQUEST, "Review creation failed", Optional.empty());

        if (isValid) {
            Review createdReview = reviewRepository.save(review);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Review created successfully");
            response.setEntity(createdReview);
        }

        return response;
    }

    /**
     * @param id
     * @return returns an Optional containing the Review Object with the matching ID and if the status is Active or returns an empty Optional if nothing is found
     */
    public Optional<Review> findActiveReviewById(Long id) {
        Optional<Review> foundReview = reviewRepository.findActiveReviewById(id);
        return foundReview;
    }

    /**
     * .
     *
     * @return a List with all the Active Review Object in the DB or an empty List if none is found
     */
    public List<Review> findAllActiveReviews() {
        List<Review> reviewList = reviewRepository.findAllActiveReviews();
        return reviewList;
    }


    /**
     * @param userId given a specific userId
     * @return a list with all the Reviews done By the User
     */
    public List<Review> findAllReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.findAllReviewsByUserId(userId);
        return reviewList;
    }

    /**
     * @param movieId given a specific movieId
     * @return a List with all the Reviews written on that Movie by the users
     */
    public List<Review> findAllReviewsByMovieId(Long movieId) {
        List<Review> reviewList = reviewRepository.findAllReviewsByMovieId(movieId);
        return reviewList;
    }


    /**
     * @param id
     * @param updateReview
     * @return Optional containing the updated Review object saved in the DB or returns an empty Optional if the object not found
     */
    public Optional<Review> updateReview(Long id, Review updateReview) {
        Optional<Review> reviewToUpdate = reviewRepository.findActiveReviewById(id);
        if (reviewToUpdate.isPresent()) {
            reviewToUpdate.get().setDescription(updateReview.getDescription());
            reviewToUpdate.get().setScore(updateReview.getScore());
            reviewRepository.save(reviewToUpdate.get());
        }
        return reviewToUpdate;
    }

    /**
     * @param id given the review id to which increase the likes
     * @return the reviews with a +1 like to the counter if the id is found and the Review object is active else returns an empyu Optional
     */
    public Optional<Review> addLikeToCounter(Long id) {
        Optional<Review> reviewToIncrement = reviewRepository.findActiveReviewById(id);
        if (reviewToIncrement.isPresent()) {
            Integer increasedLikes = reviewToIncrement.get().getLikesCounter() + 1;
            reviewToIncrement.get().setLikesCounter(increasedLikes);
            reviewRepository.save(reviewToIncrement.get());
        }
        return reviewToIncrement;
    }


    /**
     * @param id
     * @return an Optional containing the deactivated Review object, or an empty Optional if the object is not found or already deactivated
     */
    public Optional<Review> deactivateReviewById(Long id) {
        Optional<Review> reviewToDeactivate = reviewRepository.findActiveReviewById(id);
        if (reviewToDeactivate.isPresent()) {
            reviewToDeactivate.get().setRecordStatusEnum(RecordStatusEnum.D);
            reviewRepository.save(reviewToDeactivate.get());
        }
        return reviewToDeactivate;
    }

    /**
     *
     * @param scoreToCheck given an integer as input
     * @return true if the Integer is within the bounds ( > 0 and <= 100)
     * false in the other case
     */
    private boolean checkValidScore(Integer scoreToCheck) {
        boolean isValid = false;
        if (scoreToCheck >= MINIMUM_SCORE && scoreToCheck <= MAX_SCORE) {
            isValid = true;
        }

        return isValid;
    }


}
