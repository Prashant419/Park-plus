package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String  name;
    private String address;


    @OneToMany(mappedBy = "parkingLot",cascade = CascadeType.ALL)
    List<Spot> spotList = new ArrayList<>();

}