package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;

    private int getWheelerType(SpotType spotType){
        if(spotType.equals(SpotType.TWO_WHEELER)){
            return 2;
        } else if (spotType.equals(SpotType.FOUR_WHEELER)) {
            return 4;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.

        User user;
        ParkingLot parkingLot;
        Spot spot = null;

        String wheeler;
        if (numberOfWheels == 2) {  // <= why?
            wheeler = "TWO_WHEELER";
        } else if (numberOfWheels == 4) {   // <= why?
            wheeler = "FOUR_WHEELER";
        } else {
            wheeler = "OTHERS";
        }
//        wheels >= numberOfWheels

        int totalCost = Integer.MAX_VALUE;
        boolean marker = true;

        try {
            user = userRepository3.findById(userId).get();
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
            for (Spot s : parkingLot.getSpotList()) {
                int wheels = getWheelerType(s.getSpotType());
                if (!s.getOccupied() && s.getPricePerHour() < totalCost && s.getSpotType().equals(SpotType.valueOf(wheeler))) {
                    totalCost = s.getPricePerHour();
                    spot = s;
                    marker = false;
                }
            }
            if (marker) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);

        user.getReservationList().add(reservation);
        reservation.setUser(user);

        spot.setOccupied(true);
        spot.getReservationList().add(reservation);
        reservation.setSpot(spot);

        parkingLot.getSpotList().add(spot);

        userRepository3.save(user);
        spotRepository3.save(spot);


        return reservation;
    }
}