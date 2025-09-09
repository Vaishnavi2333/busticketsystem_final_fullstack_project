package com.hexaware.fastx_busticketsystem.service;

import com.hexaware.fastx_busticketsystem.dto.PaymentDto;
import com.hexaware.fastx_busticketsystem.entities.Payment;

public interface IPaymentService {
	
	Payment makePayment(PaymentDto paymentDto);
	
    Payment getPaymentById(int paymentId);
    
    Payment getPaymentByBookingId(int bookingId);
    
    void refundPayment(int paymentId);

}
