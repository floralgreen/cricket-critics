package co.caffeinecoders.cricketcritics.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
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

    public Actor() {
    }

    public Actor(Long id, String name, String lastName, Date dataOfBirth, String nationality, Integer age, String placeOFBirth) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dataOfBirth = dataOfBirth;
        this.nationality = nationality;
        this.age = age;
        this.placeOFBirth = placeOFBirth;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public Integer getAge() {
        return age;
    }

    public String getPlaceOFBirth() {
        return placeOFBirth;
    }
}
