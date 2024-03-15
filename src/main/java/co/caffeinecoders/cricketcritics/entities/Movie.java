package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.LanguageEnum;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.OffsetDateTime;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String plot;

    @Column(nullable = false, name = "language")
    @Enumerated(value = EnumType.STRING)
    private LanguageEnum languageEnum;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false, name = "users_score")
    private Integer usersScore;

    @Column(nullable = false, name = "reviewers_score")
    private Integer reviewersScore;

    @Column(name = "release_date")
    private OffsetDateTime releaseDate;

    @Column(name = "movie_website", length = 500)
    private String movieWebSite;

    @Column(nullable = false, name = "record_status", columnDefinition = "enum('A','D') default 'A'")
    @Enumerated(value = EnumType.STRING)
    private RecordStatusEnum recordStatusEnum;

    public Movie() {}

    public Movie(Long id, String title, String plot, LanguageEnum languageEnum, Integer duration, Integer usersScore,
                 Integer reviewersScore, OffsetDateTime releaseDate, String movieWebSite, RecordStatusEnum recordStatusEnum) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.languageEnum = languageEnum;
        this.duration = duration;
        this.usersScore = usersScore;
        this.reviewersScore = reviewersScore;
        this.releaseDate = releaseDate;
        this.movieWebSite = movieWebSite;
        this.recordStatusEnum = recordStatusEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public LanguageEnum getLanguageEnum() {
        return languageEnum;
    }

    public void setLanguageEnum(LanguageEnum languageEnum) {
        this.languageEnum = languageEnum;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getUsersScore() {
        return usersScore;
    }

    public void setUsersScore(Integer usersScore) {
        this.usersScore = usersScore;
    }

    public Integer getReviewersScore() {
        return reviewersScore;
    }

    public void setReviewersScore(Integer reviewersScore) {
        this.reviewersScore = reviewersScore;
    }

    public OffsetDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(OffsetDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieWebSite() {
        return movieWebSite;
    }

    public void setMovieWebSite(String movieWebSite) {
        this.movieWebSite = movieWebSite;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }
}
