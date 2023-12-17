package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingPassenger {

    public boolean notExistingPassenger = false;

    public ExistingPassenger(boolean notExistingPassenger) {
        this.notExistingPassenger = notExistingPassenger;
    }
}
