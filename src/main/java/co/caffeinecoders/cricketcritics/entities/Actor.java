package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column
    private OffsetDateTime dateOfBirth;
    @Column
    private String nationality;
    @Column(nullable = false)
    private Integer age;
    @Column
    private String placeOFBirth;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @Column(name = "record_status", nullable = false, length = 1)
    private RecordStatusEnum recordStatusEnum = RecordStatusEnum.A;

    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    private List<Movie> movies;


    public Actor() {
    }

    public Actor(Long id, String name, String lastName, OffsetDateTime dateOfBirth, String nationality, Integer age, String placeOFBirth, RecordStatusEnum recordStatusEnum, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.age = age;
        this.placeOFBirth = placeOFBirth;
        this.recordStatusEnum = recordStatusEnum;
        this.movies = movies;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public OffsetDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPlaceOFBirth() {
        return placeOFBirth;
    }

    public void setPlaceOFBirth(String placeOFBirth) {
        this.placeOFBirth = placeOFBirth;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
