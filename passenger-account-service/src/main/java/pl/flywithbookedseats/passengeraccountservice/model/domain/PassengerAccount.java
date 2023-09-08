package pl.flywithbookedseats.passengeraccountservice.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
public class PassengerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate birthDate;
    private String healthState;
    private String lifeStage;
    @JsonIgnore
    private List<PassengerAccount> accompangingPassengers;

    protected PassengerAccount() {

    }

    public PassengerAccount(Long id, String name, String surname, String email, LocalDate birthDate, String healthState, String lifeStage, List<PassengerAccount> accompangingPassengers) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.healthState = healthState;
        this.lifeStage = lifeStage;
        this.accompangingPassengers = accompangingPassengers;
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

    public List<PassengerAccount> getAccompangingPassengers() {
        return accompangingPassengers;
    }

    public void setAccompangingPassengers(List<PassengerAccount> accompangingPassengers) {
        this.accompangingPassengers = accompangingPassengers;
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
                ", accompangingPassengers=" + accompangingPassengers +
                '}';
    }
}
