package pl.flywithbookedseats.passengeraccountservice.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Entity(name = "passenger_account")
@Builder
public class PassengerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "name field can't be empty")
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String name;
    @NotBlank(message = "surname field can't be empty")
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String surname;
    @NotBlank(message = "email field can't be empty")
    @Size(min = 7, message = "The name should have at least 7 signs")
    private String email;
    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "health_state")
    private String healthState;
    @Column(name = "life_stage")
    private String lifeStage;


    protected PassengerAccount() {

    }

    public PassengerAccount(Long id, String name, String surname, String email, LocalDate birthDate, String healthState, String lifeStage) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.healthState = healthState;
        this.lifeStage = lifeStage;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public String getLifeStage() {
        return lifeStage;
    }

    public void setLifeStage(String lifeStage) {
        this.lifeStage = lifeStage;
    }



    @Override
    public String toString() {
        return "PassengerAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", healthState='" + healthState + '\'' +
                ", lifeStage='" + lifeStage + '\'' +
                '}';
    }
}
