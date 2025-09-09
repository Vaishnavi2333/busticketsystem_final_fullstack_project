package com.hexaware.fastx_busticketsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.fastx_busticketsystem.dto.PaymentDto;
import com.hexaware.fastx_busticketsystem.entities.Booking;
import com.hexaware.fastx_busticketsystem.entities.Payment;
import com.hexaware.fastx_busticketsystem.repository.BookingRepo;
import com.hexaware.fastx_busticketsystem.repository.PaymentRepo;

import lombok.extern.slf4j.Slf4j;




/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:14-Aug-2025
Description:  PaymentService Class test case*/

@SpringBootTest
@Slf4j
public class PaymentServiceImplTest {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Test
    void testMakePayment() {
       
        int bookingId = 8;
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        PaymentDto dto = new PaymentDto();
        dto.setBookingId(bookingId);
        dto.setAmount(booking.getTotalPrice());
        dto.setPaymentMethod("Card");

        Payment payment = paymentService.makePayment(dto);
        log.info("Payment made: ", payment);

        assertNotNull(payment);
        assertEquals("Paid", payment.getStatus());
        
    }

    @Test
    void testGetPaymentById() {
       
        int paymentId = 8;
        Payment payment = paymentService.getPaymentById(paymentId);
        log.info("Fetched Payment by ID ", paymentId, payment);

        assertNotNull(payment);
        assertEquals(paymentId, payment.getPaymentId());
    }

   
}