package pl.flywithbookedseats.domain.flight;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.flight.FlightEntity;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntity;
import pl.flywithbookedseats.logic.exceptions.*;
import pl.flywithbookedseats.api.flight.FlightDtoMapperOld;
import pl.flywithbookedseats.api.flight.CreateFlightCommand;
import pl.flywithbookedseats.api.flight.UpdateFlightCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.api.flight.FlightDto;
import pl.flywithbookedseats.external.storage.flight.JpaFlightRepository;
import pl.flywithbookedseats.logic.repository.PassengerRepository;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepository;
import pl.flywithbookedseats.logic.service.implementation.reservation.ReservationConstsImpl;
import pl.flywithbookedseats.api.flight.CreateFlightMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.*;
import static pl.flywithbookedseats.logic.service.implementation.passenger.PassengerConstsImpl.*;

@AllArgsConstructor
@Component
public class FlightBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(FlightBusinessLogic.class);

    private static final String CLASS = "class";

    private final JpaFlightRepository jpaFlightRepository;
    private final PassengerRepository passengerRepository;
    private final JpaSeatsSchemeRepository jpaSeatsSchemeRepository;
    private final CreateFlightMapper createFlightMapper;
    private final FlightDtoMapperOld flightDtoMapperOld;

    public FlightEntity generateNewFlight(CreateFlightCommand createFlightCommand) {
        FlightEntity newFlightEntity = createFlightMapper.apply(createFlightCommand);
        retrieveSeatsSchemeForPlaneTypeIfNeeded(newFlightEntity);
        jpaFlightRepository.save(newFlightEntity);
        return newFlightEntity;
    }

    public FlightEntity updateSpecigiedFlight(UpdateFlightCommand updateFlightCommand, FlightEntity flightEntityToUpdate) {
        if (!(exists(updateFlightCommand) || existsByFlightServiceId(updateFlightCommand))) {
            flightEntityToUpdate.setFlightName(updateFlightCommand.flightName());
            flightEntityToUpdate.setPlaneTypeName(updateFlightCommand.planeTypeName());
            flightEntityToUpdate.setFlightServiceId(updateFlightCommand.flightServiceId());
            setBookedSeatsInPlaneMapIfPossible(updateFlightCommand.bookedSeatsInPlaneMap(), flightEntityToUpdate);
            jpaFlightRepository.saveAndFlush(flightEntityToUpdate);
            logger.info(FLIGHT_UPDATED.formatted(flightEntityToUpdate.getFlightName()));
            return flightEntityToUpdate;
        } else {
            logger.warn(FLIGHT_NOT_UPDATED.formatted(flightEntityToUpdate.getFlightServiceId()));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(updateFlightCommand.flightName()));
        }
    }

    public String bookSeatInFlightSeatsScheme(String flightName, String seatClassType, Long passengerId,
                                             boolean disability, LocalDate birthDate) {
        if (exists(flightName)) {
            FlightEntity savedFlightEntity = retrieveFlightEntityFromDb(flightName);
            Map<String, Long> currentBookedSeatsInTheFlight = savedFlightEntity.getBookedSeatsInPlaneMap();
            String assignedSeat = findAndAssignSeatForPassenger(seatClassType, passengerId, disability,
                    birthDate, currentBookedSeatsInTheFlight);
            savedFlightEntity.setBookedSeatsInPlaneMap(currentBookedSeatsInTheFlight);
            jpaFlightRepository.save(savedFlightEntity);
            return assignedSeat;
        } else {
            logger.warn(ReservationConstsImpl.RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public String findAndAssignSeatForPassenger(String seatClassType, Long passengerId, boolean disability,
                                                LocalDate birthDate, Map<String, Long> bookedSeatsInPlaneMap) {
        List<String> retrievedSeatsFromSpecifiedClassList = retrieveSeatsFromSpecifiedClass(bookedSeatsInPlaneMap,
                 seatClassType);
        retrievedSeatsFromSpecifiedClassList.sort(new SortSeats());
        logger.info(retrievedSeatsFromSpecifiedClassList.toString());
        String seatToAssign = searchSeatForPassenger(convertTo2DArray(retrievedSeatsFromSpecifiedClassList),
                bookedSeatsInPlaneMap, disability, passengerId, retrievedSeatsFromSpecifiedClassList, birthDate);
        if (!seatToAssign.equals(NO_SEATS_AVAILABLE)) {
            return seatToAssign;
        } else {
            logger.warn(NO_SEATS_AVAILABLE_MSG);
            throw new FullFlightException(RESERVATION_NOT_MADE_FULL_FLIGHT);
        }

    }

    public FlightEntity makeSpecifiedBookedSeatFree(String bookedSeat, String flightName) {
        FlightEntity savedFlightEntity = retrieveFlightEntityFromDb(flightName);
        Map<String, Long> assignedBookedSeatsInPlaneMap = savedFlightEntity.getBookedSeatsInPlaneMap();
        stopIterating:
        for (Map.Entry<String, Long> entry : assignedBookedSeatsInPlaneMap.entrySet()) {
            if (entry.getKey().contains(bookedSeat)) {
                entry.setValue(0L);
                break stopIterating;
            }
        }

        savedFlightEntity.setBookedSeatsInPlaneMap(assignedBookedSeatsInPlaneMap);
        return savedFlightEntity;
    }

    public List<FlightDto> convertIntoListFlightDto(List<FlightEntity> flightEntityList) {
        if (!flightEntityList.isEmpty()) {
            List<FlightDto> savedFlightDtoList = new ArrayList<>();
            flightEntityList.forEach(flightEntity -> savedFlightDtoList.add(flightDtoMapperOld.apply(flightEntity)));
            return savedFlightDtoList;
        } else {
            logger.warn(FLIGHTS_NOT_RETRIEVED);
            throw new FlightDatabaseIsEmptyException(FLIGHT_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    public FlightEntity retrieveFlightEntityFromDb(String flightName) {
        return jpaFlightRepository.findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));
    }

    public FlightEntity retrieveFlightEntityFromDb(Long flightServiceId) {
        return jpaFlightRepository.findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));
    }

    //Duplicated - PassengerBL
    public Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_ID.formatted(passengerId)));
    }

    public boolean exists(String flightName) {
        return jpaFlightRepository.existsByFlightName(flightName);
    }

    public boolean exists(CreateFlightCommand createFlightCommand) {
        return jpaFlightRepository.existsByFlightName(createFlightCommand.flightName());
    }

    public boolean exists(UpdateFlightCommand updateFlightCommand) {
        return jpaFlightRepository.existsByFlightName(updateFlightCommand.flightName());
    }

    public boolean existsByFlightServiceId(UpdateFlightCommand updateFlightCommand) {
        return jpaFlightRepository.existsByFlightServiceId(updateFlightCommand.flightServiceId());
    }

    private void retrieveSeatsSchemeForPlaneTypeIfNeeded(FlightEntity flightEntity) {
        Map<String, String> savedSeatsSchemeMap;
        Map<String, Long> generatedBookedSeatsInPlaneMap;
        String planeTypeName = flightEntity.getPlaneTypeName();
        if (flightEntity.getBookedSeatsInPlaneMap() == null) {

            savedSeatsSchemeMap = new TreeMap<>(retrieveSeatsSchemeModelFromDb(planeTypeName, flightEntity)
                    .getSeatsSchemeMap());
            generatedBookedSeatsInPlaneMap = createReservedSeatsSchemeMap(savedSeatsSchemeMap);
            flightEntity.setBookedSeatsInPlaneMap(generatedBookedSeatsInPlaneMap);
            System.out.println(flightEntity);
        }
    }

    private Map<String, Long> createReservedSeatsSchemeMap(Map<String, String> savedSeatsSchemeMap) {
        Map<String, Long> localBookedSeatsInPlaneMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : savedSeatsSchemeMap.entrySet()) {
            StringBuilder fullSeatName = new StringBuilder();
            fullSeatName.append(entry.getValue())
                    .append(" ")
                    .append(entry.getKey());
            localBookedSeatsInPlaneMap.put(fullSeatName.toString(), 0L);
            fullSeatName.delete(0, fullSeatName.length());
        }

        return localBookedSeatsInPlaneMap;
    }

    private void setBookedSeatsInPlaneMapIfPossible(Map<String, Long> bookedSeatsInPlaneMapToSet
            , FlightEntity flightEntityToUpdate) {
        if (bookedSeatsInPlaneMapToSet != null) {
            if (!bookedSeatsInPlaneMapToSet.isEmpty()) {
                flightEntityToUpdate.setBookedSeatsInPlaneMap(bookedSeatsInPlaneMapToSet);
            }
        }
    }

    //Duplicated - SeatsSchemeModelServiceImp - if not, it's going to be after SeatsSchemeModelService redesign
    private SeatsSchemeEntity retrieveSeatsSchemeModelFromDb(String planeTypeName, FlightEntity flightEntity) {
        return jpaSeatsSchemeRepository.findByPlaneModelName(planeTypeName)
                .orElseThrow(() -> new FlightNotCreatedException(SEATS_SCHEME_NOT_FOUND_FLIGHT_NOT_CREATED_EXCEPTION
                        .formatted(planeTypeName, flightEntity.getFlightName())));
    }

    private List<String> retrieveSeatsFromSpecifiedClass(Map<String, Long> bookedSeatsInPlaneMap,
                                                         String seatClassType) {
        List<String> retrievedSeats = new ArrayList<>();
        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
            String seat = entry.getKey().toLowerCase();
            if (seat.contains(seatClassType.toLowerCase())) {
                retrievedSeats.add(findPattern(seat, CLASS));
            }
        }

        return retrievedSeats;
    }

    private String searchSeatForPassenger(String[][] convertedSeatsDataArray, Map<String, Long> bookedSeatsInPlaneMap,
                                          boolean disability, Long passengerId, List<String> seatsInThePlaneList,
                                          LocalDate birthDate) {
        String seatFound = "";
        boolean disabilitySearchingModeDone = false;
        boolean kidSearchingModeDone = false;
        boolean kidUnder10 = LocalDate.now().getYear() - birthDate.getYear() <= 10;
        int seatsInTheRowAmount = countSeatsInTheRow(seatsInThePlaneList);
        while (seatFound.isEmpty()) {
            if ((disability) && !disabilitySearchingModeDone) {
                List<Integer> seatsToCheckForDisabled = findCorridorSeats(seatsInTheRowAmount);
                seatFound = assignSeatToPassengerInThePlane(convertedSeatsDataArray, bookedSeatsInPlaneMap, seatsToCheckForDisabled, passengerId, seatFound);
                disabilitySearchingModeDone = true;
            } else if (kidUnder10 && !kidSearchingModeDone) {
                List<Integer> seatsToCheckForKids = findWindowSeats(seatsInTheRowAmount);
                seatFound = assignSeatToPassengerInThePlane(convertedSeatsDataArray, bookedSeatsInPlaneMap, seatsToCheckForKids, passengerId, seatFound);
                kidSearchingModeDone = true;
            } else {
                List<Integer> remainingSeatsToCheck = findRemainingEmptySeats(seatsInTheRowAmount);
                seatFound = assignSeatToPassengerInThePlane(convertedSeatsDataArray, bookedSeatsInPlaneMap, remainingSeatsToCheck, passengerId, seatFound);
                if (seatFound.isEmpty()) {
                    seatFound = NO_SEATS_AVAILABLE;
                }
            }
        }

        return seatFound;
    }

    private String assignSeatToPassengerInThePlane(String[][] convertedSeatsDataArray,
                                                   Map<String, Long> bookedSeatsInPlaneMap,
                                                   List<Integer> seatsToCheckFirst,
                                                   Long passengerId, String seatFound) {
        int seatsInTheRow = convertedSeatsDataArray[0].length;

        stopSeatSearching:
        for (String[] strings : convertedSeatsDataArray) {
            for (int j = 0; j < seatsInTheRow; j++) {
                Iterator<Integer> iterator = seatsToCheckFirst.iterator();
                while (iterator.hasNext() && seatFound.isEmpty()) {
                    if ((j + 1) == iterator.next()) {
                        String seatToCheck = " " + strings[j];
                        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
                            logger.debug(entry.getKey() + ": " + entry.getValue());
                            if (entry.getKey().contains(seatToCheck)) {
                                if (entry.getValue() == 0L) {
                                    entry.setValue(passengerId);
                                    seatFound = seatToCheck;
                                    break stopSeatSearching;
                                }
                            }
                        }
                    }
                }
            }
        }

        return seatFound;
    }
    private String findPattern(String inputString, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        matcher.find();
        return inputString.substring(matcher.end() + 1).toUpperCase();
    }

    private String[][] convertTo2DArray(List<String> seatsInThePlane) {
        int seatCounter = 0;
        String[][] seatsScheme = new String[countSeatsRows(seatsInThePlane)][countSeatsInTheRow(seatsInThePlane)];
        for (int i = 0; i < seatsScheme.length; i++) {
            for (int j = 0; j < seatsScheme[0].length; j++) {
                seatsScheme[i][j] = seatsInThePlane.get(seatCounter);
                seatCounter++;
            }
        }
        logger.debug(Arrays.deepToString(seatsScheme));

        return seatsScheme;
    }

    private int countSeatsInTheRow(List<String> seatsInThePlaneList) {
        int i = 0;
        int seatCounter = 1;
        while (Integer.parseInt(seatsInThePlaneList.get(i).replaceAll("\\D", ""))
                == Integer.parseInt(seatsInThePlaneList.get(i + 1).replaceAll("\\D", ""))) {
            seatCounter++;
            i++;
        }

        return seatCounter;
    }

    private int countSeatsRows(List<String> seatsInThePlaneList) {
        return seatsInThePlaneList.size()/countSeatsInTheRow(seatsInThePlaneList);
    }

    /**
     * The improvement for this method is planned. To make this algorithm more flexible for any type of seats
     * combination on the plane, during creating seats scheme for specified plane, additional param need to be passed -
     * sequence of seats in the row groups (row groups are separated each other by corridor in the plane between rows).
     * This information will allow to modify algorithm below to find corridor seats for disabled people for any plane
     * type.
     * @param seatsInTheRowsNumber
     * @return
     */
    private List<Integer> findCorridorSeats(int seatsInTheRowsNumber) {
        int corridorSeatsNumber = 0;
        List<Integer> seatsNumbersToCheck = new ArrayList<>();
        if (seatsInTheRowsNumber % 3 == 0 && seatsInTheRowsNumber % 2 == 0) {
            corridorSeatsNumber = (seatsInTheRowsNumber / 3 - 1) * 2;
            for (int i = 1; i < (corridorSeatsNumber / 2) + 1; i++) {
                seatsNumbersToCheck.add(i * 3);
                seatsNumbersToCheck.add(i * (3 + i));
            }

            return seatsNumbersToCheck;
        } else if (seatsInTheRowsNumber % 3 == 0 && seatsInTheRowsNumber % 2 != 0) {
            corridorSeatsNumber = (seatsInTheRowsNumber / 3 - 1) * 2;
            for (int i = 1; i < (corridorSeatsNumber / 2) + 1; i++) {
                seatsNumbersToCheck.add(i * 3);
                seatsNumbersToCheck.add(i * (3 + i));
            }

            return seatsNumbersToCheck;
        } else if (seatsInTheRowsNumber % 3 != 0 && seatsInTheRowsNumber % 2 == 0) {
            corridorSeatsNumber = seatsInTheRowsNumber - 2;
            for (int i = 1; i < (corridorSeatsNumber / 2) + 1; i++) {
                if (!seatsNumbersToCheck.contains(i * 2)) {
                    seatsNumbersToCheck.add(i * 2);
                }

                if (!seatsNumbersToCheck.contains(i * 2 + 1)) {
                    seatsNumbersToCheck.add(i * 2 + 1);
                }
            }

            return seatsNumbersToCheck;
        }

        return null;
    }

    private List<Integer> findWindowSeats(int seatsInTheRowsNumber) {
        return Arrays.asList(1, seatsInTheRowsNumber);
    }

    private List<Integer> findRemainingEmptySeats(int seatsInTheRowsNumber) {
        List<Integer> remainingSeatsList = new ArrayList<>();
        for (int i = 1; i <= seatsInTheRowsNumber; i++) {
            if (!(findWindowSeats(seatsInTheRowsNumber).contains(i)
                    || findCorridorSeats(seatsInTheRowsNumber).contains(i))) {
                remainingSeatsList.add(i);
            }
        }
        return remainingSeatsList;
    }
}
