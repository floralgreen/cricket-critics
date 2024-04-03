package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
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
    private Date dataOfBirth;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;


    public Actor() {
    }

    public Actor(Long id, String name, String lastName, Date dataOfBirth, String nationality, Integer age, String placeOFBirth, RecordStatusEnum recordStatusEnum, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dataOfBirth = dataOfBirth;
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

    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Date dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
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
