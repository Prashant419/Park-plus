package com.driver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numberOfHours;

    @ManyToOne
    @JoinColumn
    Spot spot;

    @ManyToOne
    @JoinColumn
    User user;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    Payment payment;
}