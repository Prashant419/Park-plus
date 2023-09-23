package com.driver.controllers;

import com.driver.services.ParkingLotService;
import com.driver.services.impl.ParkingLotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.driver.model.*;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    //findById and deleteById should be used wherever necessary
    //findAll should never be used
    @Autowired
    ParkingLotServiceImpl parkingLotService;
    public ParkingLotController() {
    }

    @PostMapping("/add")
    public ResponseEntity<ParkingLot> addParkingLot(@RequestParam String name, @RequestParam String address) {
        //add a new parking lot to the database
        ParkingLot newParkingLot = this.parkingLotService.addParkingLot(name, address);
        return new ResponseEntity<>(newParkingLot, HttpStatus.CREATED);
    }

    @PostMapping("/{parkingLotId}/spot/add")
    public ResponseEntity<Spot> addSpot(@PathVariable int parkingLotId, @RequestParam Integer numberOfWheels, @RequestParam Integer pricePerHour) {
        //create a new spot in the parkingLot with given id

        //the spot type should be the next biggest type in case the number of wheels are not 2 or 4, for 4+ wheels, it is others
        Spot newSpot = this.parkingLotService.addSpot(parkingLotId, numberOfWheels, pricePerHour);

        return new ResponseEntity<>(newSpot, HttpStatus.CREATED);

    }

    @DeleteMapping("/spot/{spotId}/delete")
    public ResponseEntity<Void> deleteSpot(@PathVariable int spotId) {
        //delete a spot from given parking lot
        this.parkingLotService.deleteSpot(spotId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{parkingLotId}/spot/{spotId}/update")
    public ResponseEntity<Spot> updateSpot(@PathVariable int parkingLotId, @PathVariable int spotId, @RequestParam int pricePerHour) {
        //update the details of a spot
        Spot updatedSpot = this.parkingLotService.updateSpot(parkingLotId, spotId, pricePerHour);
        return new ResponseEntity<>(updatedSpot, HttpStatus.OK);
    }

    @DeleteMapping("/{parkingLotId}/delete")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable int parkingLotId) {
        //delete a parkingLot
        this.parkingLotService.deleteParkingLot(parkingLotId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}