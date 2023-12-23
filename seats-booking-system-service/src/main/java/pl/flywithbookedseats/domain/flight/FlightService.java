package pl.flywithbookedseats.domain.flight;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;
import pl.flywithbookedseats.domain.passenger.PassengerService1Impl;
import pl.flywithbookedseats.domain.reservation.ReservationConstsImpl;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.flywithbookedseats.common.Consts.SEAT_PASSENGER_DATA_UNAVAILABLE;
import static pl.flywithbookedseats.domain.flight.FlightConstImpl.*;

@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository repository;
    private final SeatsSchemeService seatsSchemeService;
    private final PassengerService passengerService;

    public Flight createNewFlight(Flight flight) {
        if (!exists(flight)) {
            return generateNewFlight(flight);
        } else {
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(flight.getFlightName()));
        }
    }

    public Flight generateNewFlight(Flight flight) {
        retrieveSeatsSchemeForPlaneTypeIfNeeded(flight);
        return repository.save(flight);
    }

    public PageFlight retrieveAllFlightsFromDb(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Flight retrieveFlightByFlightName(String flightName) {
        return repository.findByFlightName(flightName);
    }

    public Flight retrieveFlightByFlightServiceId(Long flightServiceId) {
        return repository.findByFlightServiceId(flightServiceId);
    }

    public Flight updateFlightByFlightName(String flightName, Flight flight) {
        Flight savedFlight = retrieveFlightByFlightName(flightName);
        return updateSpecifiedFlight(flight, savedFlight);
    }

    public Flight updateFlightByFlightServiceId(Long id, Flight flight) {
        Flight savedFlight = retrieveFlightByFlightServiceId(id);
        return updateSpecifiedFlight(flight, savedFlight);
    }

    private Flight updateSpecifiedFlight(Flight flightUpdateData, Flight flightToUpdate) {
        if (!exists(flightUpdateData, flightToUpdate)) {
            flightToUpdate.setFlightName(flightUpdateData.getFlightName());
            flightToUpdate.setPlaneTypeName(flightUpdateData.getPlaneTypeName());
            flightToUpdate.setFlightServiceId(flightUpdateData.getFlightServiceId());
            setBookedSeatsInPlaneMapIfPossible(flightUpdateData.getBookedSeatsInPlaneMap(), flightToUpdate);
            repository.save(flightToUpdate);
            log.info(FLIGHT_UPDATED.formatted(flightToUpdate.getFlightName()));
            return flightToUpdate;
        } else {
            log.warn(FLIGHT_NOT_UPDATED.formatted(flightToUpdate.getFlightServiceId()));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(flightUpdateData.getFlightName()));
        }
    }

    public Map<String, String> convertBookedSeatsInPlaneMapToDtoVersion(Map<String, Long> bookedSeatsInPlaneMap) {
        Map<String, String> convertedBookedSeatsInPlaneMap = new TreeMap<String, String>();
        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
            convertedBookedSeatsInPlaneMap.put(entry.getKey(), retrievePassengerNameSurname(entry.getValue()));
        }
        return convertedBookedSeatsInPlaneMap;
    }

    public void deleteAllFlights() {
        repository.deleteAll();
    }

    public void deleteFlightByFlightName(String flightName) {
        repository.deleteByFlightName(flightName);
    }

    public void deleteFlightByFlyServiceId(Long flightServiceId) {
        repository.deleteByFlightServiceId(flightServiceId);
    }

    public String bookSeatInFlightSeatsScheme(String flightName, String seatClassType, Long passengerId,
                                              boolean disability, LocalDate birthDate) {
        if (exists(flightName)) {
            Flight savedFlight = retrieveFlightByFlightName(flightName);
            Map<String, Long> currentBookedSeatsInTheFlight = savedFlight.getBookedSeatsInPlaneMap();
            String assignedSeat = findAndAssignSeatForPassenger(seatClassType, passengerId, disability,
                    birthDate, currentBookedSeatsInTheFlight);
            savedFlight.setBookedSeatsInPlaneMap(currentBookedSeatsInTheFlight);
            repository.save(savedFlight);
            return assignedSeat;
        } else {
            log.warn(ReservationConstsImpl.RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public String findAndAssignSeatForPassenger(String seatClassType, Long passengerId, boolean disability,
                                                LocalDate birthDate, Map<String, Long> bookedSeatsInPlaneMap) {
        List<String> retrievedSeatsFromSpecifiedClassList = retrieveSeatsFromSpecifiedClass(bookedSeatsInPlaneMap,
                seatClassType);
        retrievedSeatsFromSpecifiedClassList.sort(new SortSeats());
        log.info(retrievedSeatsFromSpecifiedClassList.toString());
        String seatToAssign = searchSeatForPassenger(convertTo2DArray(retrievedSeatsFromSpecifiedClassList),
                bookedSeatsInPlaneMap, disability, passengerId, retrievedSeatsFromSpecifiedClassList, birthDate);
        if (!seatToAssign.equals(NO_SEATS_AVAILABLE)) {
            return seatToAssign;
        } else {
            log.warn(NO_SEATS_AVAILABLE_MSG);
            throw new FullFlightException(RESERVATION_NOT_MADE_FULL_FLIGHT);
        }

    }

    public Flight makeSpecifiedBookedSeatFree(String bookedSeat, String flightName) {
        Flight savedFlight = retrieveFlightByFlightName(flightName);
        Map<String, Long> assignedBookedSeatsInPlaneMap = savedFlight.getBookedSeatsInPlaneMap();

        stopIterating:
        for (Map.Entry<String, Long> entry : assignedBookedSeatsInPlaneMap.entrySet()) {
            if (entry.getKey().contains(bookedSeat)) {
                entry.setValue(0L);
                break stopIterating;
            }
        }

        savedFlight.setBookedSeatsInPlaneMap(assignedBookedSeatsInPlaneMap);
        return savedFlight;
    }

    private void setBookedSeatsInPlaneMapIfPossible(Map<String, Long> bookedSeatsInPlaneMapToSet
            , Flight flightToUpdate) {
        if (bookedSeatsInPlaneMapToSet != null) {
            if (!bookedSeatsInPlaneMapToSet.isEmpty()) {
                flightToUpdate.setBookedSeatsInPlaneMap(bookedSeatsInPlaneMapToSet);
            }
        }
    }

    private void retrieveSeatsSchemeForPlaneTypeIfNeeded(Flight flight) {
        Map<String, String> savedSeatsSchemeMap;
        Map<String, Long> generatedBookedSeatsInPlaneMap;
        if (flight.getBookedSeatsInPlaneMap() == null) {

            savedSeatsSchemeMap = new TreeMap<>(seatsSchemeService
                    .retrieveSeatsSchemeModelByPlaneModel(flight.getPlaneTypeName()).getSeatsSchemeMap());
            generatedBookedSeatsInPlaneMap = createReservedSeatsSchemeMap(savedSeatsSchemeMap);
            flight.setBookedSeatsInPlaneMap(generatedBookedSeatsInPlaneMap);
            log.debug("Created Flight: {}", flight.toString());
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

    private String retrievePassengerNameSurname(Long passengerId) {
        StringBuilder passengerNameSurname = new StringBuilder();
        if (passengerId != null && passengerId != 0L) {
            Passenger savedPassenger = retrievePassengerEntityFromDb(passengerId);
            if (savedPassenger != null) {
                passengerNameSurname.append(savedPassenger.getName())
                        .append(" ")
                        .append(savedPassenger.getSurname());
            } else {
                passengerNameSurname.append(SEAT_PASSENGER_DATA_UNAVAILABLE);
            }

            return passengerNameSurname.toString();
        } else {
            passengerNameSurname.append("free");
            return passengerNameSurname.toString();
        }
    }

    private Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerService.retrievePassengerById(passengerId);
    }

    public boolean exists(String flightName) {
        return repository.existsByFlightName(flightName);
    }

    private boolean exists(Flight flight) {
        return repository.existsByFlightName(flight.getFlightName());
    }

    private boolean exists(Flight flightToUpdate, Flight flightUpdateData) {
        String flightName = flightUpdateData.getFlightName();
        if (repository.existsByFlightName(flightName)) {
            return Objects.equals(retrieveFlightByFlightName(flightName).getFlightServiceId(), flightToUpdate.getFlightServiceId());
        }

        return false;
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
                            log.debug(entry.getKey() + ": " + entry.getValue());
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
        log.debug(Arrays.deepToString(seatsScheme));

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
