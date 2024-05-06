package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @Column(name = "record_status", nullable = false, length = 1)
    private RecordStatusEnum recordStatusEnum = RecordStatusEnum.A;
    @OneToMany(mappedBy = "community")
    @JsonIgnore
    private List<Post> posts;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Community() {
    }

    public Community(Long id, String name, RecordStatusEnum recordStatusEnum, List<Post> posts, Movie movie, User user) {
        this.id = id;
        this.name = name;
        this.recordStatusEnum = recordStatusEnum;
        this.posts = posts;
        this.movie = movie;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
