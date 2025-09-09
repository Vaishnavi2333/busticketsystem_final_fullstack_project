package com.hexaware.fastx_busticketsystem.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.fastx_busticketsystem.dto.PaymentDto;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Payment;
import com.hexaware.fastx_busticketsystem.exception.PaymentNotFoundException;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.PaymentRepo;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description:  Payment Service Implementation Class*/

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private PaymentRepo repo;

    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public Payment makePayment(PaymentDto paymentDto) {
       
        Booking booking = bookingRepo.findById(paymentDto.getBookingId())
                .orElseThrow(() -> new RuntimeException(
                        "Booking not found with ID: " + paymentDto.getBookingId()));

      
        if (booking.getTotalPrice() != paymentDto.getAmount()) {
            throw new IllegalArgumentException(
                "Payment amount does not match booking amount. Expected: "
                + booking.getTotalPrice() + ", Received: " + paymentDto.getAmount());
        }



       
        Payment pay = new Payment();
        pay.setAmount(paymentDto.getAmount());
        pay.setPaymentDate(LocalDate.now());
        pay.setPaymentMethod(paymentDto.getPaymentMethod());
        pay.setStatus("Paid");

      
        pay.setBooking(booking);

       
        Payment savedPayment = repo.save(pay);

        
        booking.confirmBooking(savedPayment);
        booking.setPaymentDone("Paid".equalsIgnoreCase(savedPayment.getStatus()));
        bookingRepo.save(booking);

        return savedPayment;
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        return repo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
    }

    @Override
    public Payment getPaymentByBookingId(int bookingId) {
        return repo.findByBooking_BookingId(bookingId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for booking id: " + bookingId));
    }

    @Override
    public void refundPayment(int paymentId) {
        Payment payment = repo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
        payment.setStatus("Refunded");
        repo.save(payment);
    }
}
