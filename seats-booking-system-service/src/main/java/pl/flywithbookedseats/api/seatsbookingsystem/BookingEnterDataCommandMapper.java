package pl.flywithbookedseats.api.seatsbookingsystem;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsbookingsystem.BookingEnterData;

@Mapper(componentModel = "spring")
public interface BookingEnterDataCommandMapper {

    BookingEnterData toDomain(BookingEnterDataCommand command);
}
