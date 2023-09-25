package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot newParkingLot=new ParkingLot();
        newParkingLot.setName(name);
        newParkingLot.setAddress(address);

        parkingLotRepository1.save(newParkingLot);

        return newParkingLot;

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour)  {
        ParkingLot newParkingLot;

        newParkingLot = parkingLotRepository1.findById(parkingLotId).get();


        Spot newSpot = new Spot();
        SpotType st;
        if(numberOfWheels<=2){
            st=SpotType.TWO_WHEELER;
        }

        else if (numberOfWheels==4 || numberOfWheels==3) {
            st=SpotType.FOUR_WHEELER;

        }
        else st=SpotType.OTHERS;

        newSpot.setSpotType(st);
        newSpot.setPricePerHour(pricePerHour);

        newParkingLot.getSpotList().add(newSpot);

        parkingLotRepository1.save(newParkingLot);

        newSpot.setParkingLot(newParkingLot);

//        spotRepository1.save(newSpot);

        return newSpot;


    }

    @Override
    public void deleteSpot(int spotId)  {

        spotRepository1.deleteById(spotId);



    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot;

        parkingLot=parkingLotRepository1.findById(parkingLotId).get();



        List<Spot> spots = parkingLot.getSpotList();

        Spot updatedSpot = null;
        for(Spot s : spots){
            if(s.getId() == spotId){
                s.setPricePerHour(pricePerHour);
                updatedSpot = s;
                spotRepository1.save(updatedSpot);
                break;
            }
        }

        return updatedSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);


    }
}