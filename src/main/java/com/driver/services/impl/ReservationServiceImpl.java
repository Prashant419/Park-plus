package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public SpotType getSpotTypeByWheels(int numberOfWheels){
        if(numberOfWheels<=2){
            return  SpotType.TWO_WHEELER;
        }

        else if (numberOfWheels==4 || numberOfWheels==3) {
            return  SpotType.FOUR_WHEELER;

        }
        return  SpotType.OTHERS;
    }
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.

        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("Cannot make reservation");
        }

        User user;
        try{
            user = userRepository3.findById(userId).get();
        }
        catch (Exception e){
            throw new Exception("Cannot make reservation");
        }


        List<Spot> spots = parkingLot.getSpotList();
        //Spot spot = null;
        List<Spot> spotListFollowsCondition = new ArrayList<>();

        for(Spot s : spots){
            if(s.getOccupied()==false){
                int capacity;
                if(s.getSpotType()==SpotType.TWO_WHEELER) {
                    capacity = 2;
                }
                else if (s.getSpotType()==SpotType.FOUR_WHEELER) {
                    capacity = 4;
                }
                else{
                    capacity = Integer.MAX_VALUE;
                }

                if(capacity>=numberOfWheels){
                    //spot = s;
                    spotListFollowsCondition.add(s);
                }
            }
        }

        //check whether spots are available in the list or not
        if(spotListFollowsCondition.isEmpty()==true){
            throw new Exception("Cannot make reservation");
        }

        //now final check for low price spot reservation
        Spot reserveSpot = null;
        int minimumPrice = Integer.MAX_VALUE;
        for(Spot spot : spotListFollowsCondition){
            int price = spot.getPricePerHour() * timeInHours;
            if(price<minimumPrice){
                minimumPrice = price;
                reserveSpot = spot;
            }
        }

        reserveSpot.setOccupied(true);

        //create reservation object
        Reservation reservation = new Reservation();
        reservation.setSpot(reserveSpot);
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        reservation.setPayment(null);

        user.getReservationList().add(reservation);

        userRepository3.save(user);
        spotRepository3.save(reserveSpot);

        //reservationRepository3.save(reservation);


        return reservation;


    }
}