package co.caffeinecoders.cricketcritics.entities;

import co.caffeinecoders.cricketcritics.enums.RecordStatusEnum;
import co.caffeinecoders.cricketcritics.enums.ReviewRatingEnum;
import co.caffeinecoders.cricketcritics.enums.UserEnum;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, name = "record_status", columnDefinition = "enum('A','D') default 'A'")
    @Enumerated(value = EnumType.STRING)
    private RecordStatusEnum recordStatusEnum;
    @Column(nullable = false, name = "user_enum", columnDefinition = "enum('BASICUSER','REVIEWER','ADMIN') ")
    @Enumerated(value = EnumType.STRING)
    private UserEnum userEnum;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
    public User() {
    }

    public User(Long id, String userName, String name, String lastName, String email, String password, RecordStatusEnum recordStatusEnum, UserEnum userEnum, List<Review> reviews) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.recordStatusEnum = recordStatusEnum;
        this.userEnum = userEnum;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RecordStatusEnum getRecordStatusEnum() {
        return recordStatusEnum;
    }

    public void setRecordStatusEnum(RecordStatusEnum recordStatusEnum) {
        this.recordStatusEnum = recordStatusEnum;
    }

    public UserEnum getUserEnum() {
        return userEnum;
    }

    public void setUserEnum(UserEnum userEnum) {
        this.userEnum = userEnum;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}