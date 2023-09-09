package pl.flywithbookedseats.passengeraccountservice.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "passenger_account")
@Table(
        name = "passenger_account_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(name = "email", columnNames = "email")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PassengerAccount {

    @Id
    @SequenceGenerator(
            name = "passenger_account_squence",
            sequenceName = "customized_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator =  "customized_sequence"
    )
    @Column(
            name = "ID",
            updatable = false
    )
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private String healthState;
    private String lifeStage;

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
