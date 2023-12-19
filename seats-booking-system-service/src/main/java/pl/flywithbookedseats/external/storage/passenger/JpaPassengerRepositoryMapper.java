package pl.flywithbookedseats.external.storage.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JpaPassengerRepositoryMapper {

    //@Mapping(source = "domain", target = "reservationsList", qualifiedByName = "convertedList")
    PassengerEntity toEntity(Passenger domain);

    Passenger toDomain(PassengerEntity entity);

    /*@Named("convertedList")
    default List<ReservationEntity> convertList(Passenger domain) {
        List<ReservationEntity> reservationEntityList = new ArrayList<>();

        domain.getReservationsList().forEach(reservation -> {
            reservationEntityList.add(new ReservationEntity(
                    reservation.getId(),
                    reservation.getFlightNumber(),
                    reservation.getSeatNumber(),
                    reservation.getSeatTypeClass(),
                    reservation.getPassengerEmail(),
                    reservation.getPassengerEntity()));
        });

        return reservationEntityList;
    }*/
}
