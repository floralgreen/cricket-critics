package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.ReviewRatingEnum;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String description;
    @Column(nullable = false)
    private Integer score;
    @Column(nullable = false, name = "review_rating", columnDefinition = "enum('1','2','3','4','5') ")
    @Enumerated(value = EnumType.STRING)
    private ReviewRatingEnum reviewRatingEnum;
    @Column(nullable = false)
    private OffsetDateTime reviewDate;
    @Column(nullable = false, name = "record_status", columnDefinition = "enum('A','D') default 'A'")
    @Enumerated(value = EnumType.STRING)
    private RecordStatusEnum recordStatusEnum;

    public Review() {}

    public Review(Long id, String description, Integer score, ReviewRatingEnum reviewRatingEnum, OffsetDateTime reviewDate, RecordStatusEnum recordStatusEnum) {
        this.id = id;
        this.description = description;
        this.score = score;
        this.reviewRatingEnum = reviewRatingEnum;
        this.reviewDate = reviewDate;
        this.recordStatusEnum = recordStatusEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ReviewRatingEnum getReviewRatingEnum() {
        return reviewRatingEnum;
    }

    public void setReviewRatingEnum(ReviewRatingEnum reviewRatingEnum) {
        this.reviewRatingEnum = reviewRatingEnum;
    }

    public OffsetDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(OffsetDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }
}
