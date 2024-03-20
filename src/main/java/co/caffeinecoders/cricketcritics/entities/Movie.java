package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.CategoryEnum;
import co.caffeinecoders.cricketcritics.enums.LanguageEnum;
import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.OffsetDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "record_status", nullable = false, length = 1)
    private RecordStatusEnum recordStatusEnum = RecordStatusEnum.A;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "category",nullable = false)
    private CategoryEnum categoryEnum;

    @ManyToMany
    @JoinTable(name = "actors_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(name = "directors_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "directos_id"))
    private List<Director> directors;

    public Movie() {
    }

    public Movie(Long id, String title, String plot, LanguageEnum languageEnum, Integer duration, Integer usersScore, Integer reviewersScore, OffsetDateTime releaseDate,
                 String movieWebSite, List<Review> reviews, RecordStatusEnum recordStatusEnum, CategoryEnum categoryEnum, List<Actor> actors, List<Director> directors) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.languageEnum = languageEnum;
        this.duration = duration;
        this.usersScore = usersScore;
        this.reviewersScore = reviewersScore;
        this.releaseDate = releaseDate;
        this.movieWebSite = movieWebSite;
        this.reviews = reviews;
        this.recordStatusEnum = recordStatusEnum;
        this.categoryEnum = categoryEnum;
        this.actors = actors;
        this.directors = directors;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }

    public CategoryEnum getCategoryEnum() {
        return categoryEnum;
    }

    public void setCategoryEnum(CategoryEnum categoryEnum) {
        this.categoryEnum = categoryEnum;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }
}
