package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
        //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
        //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
        //Note that the reservationId always exists

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        Spot spot = reservation.getSpot();

        int totalCost = reservation.getNumberOfHours() * spot.getPricePerHour();

        mode = mode.toUpperCase();
        boolean marker = true;
        switch (mode) {
            case "CASH":
                marker = false;
                break;
            case "CARD":
                marker = false;
                break;
            case "UPI":
                marker = false;
                break;
        }


        try {
            if (totalCost > amountSent) {
                throw new Exception("Insufficient Amount");
            }
            if (marker) {
                throw new Exception("Payment mode not detected");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


        Payment payment = new Payment();
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);

//        reservation.setPayment(payment);

//        paymentRepository2.save(payment);
        reservationRepository2.save(reservation);
        return payment;

    }
}