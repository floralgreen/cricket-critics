package co.caffeinecoders.cricketcritics.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "directors")
public class Director {
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

    public Director() {
    }

    public Director(Long id, String name, String lastName, Date dataOfBirth, String nationality, Integer age, String placeOFBirth) {
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
}
