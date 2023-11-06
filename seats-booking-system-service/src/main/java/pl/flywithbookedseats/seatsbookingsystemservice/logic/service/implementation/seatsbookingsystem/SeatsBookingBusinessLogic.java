package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsbookingsystem;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsschememodel.SeatsSchemeModelServiceImpl;

@AllArgsConstructor
@Component
public class SeatsBookingBusinessLogic {

    private final FlightBusinessLogic flightBusinessLogic;
    private final PassengerBusinessLogic passengerBusinessLogic;
    private final ReservationBusinessLogic reservationBusinessLogic;

    public void bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {

    }
}
