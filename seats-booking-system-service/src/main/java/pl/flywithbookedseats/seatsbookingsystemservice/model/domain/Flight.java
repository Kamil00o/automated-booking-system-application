package pl.flywithbookedseats.seatsbookingsystemservice.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "flight_TABLE"
)
public class Flight {

    @Id
    @SequenceGenerator(
            name = "flight_id_sequence",
            sequenceName = "flight_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "flight_id_seq"
    )
    @Column(name = "ID")
    private Long id;
    private String flightName;
    private String planeType;
    private String fromToAirports;
    private Long passengerAccountID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public String getFromToAirports() {
        return fromToAirports;
    }

    public void setFromToAirports(String fromToAirports) {
        this.fromToAirports = fromToAirports;
    }

    public Long getPassengerAccountID() {
        return passengerAccountID;
    }

    public void setPassengerAccountID(Long passengerAccountID) {
        this.passengerAccountID = passengerAccountID;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightName='" + flightName + '\'' +
                ", planeType='" + planeType + '\'' +
                ", fromToAirports='" + fromToAirports + '\'' +
                ", passengerAccountID=" + passengerAccountID +
                '}';
    }
}
