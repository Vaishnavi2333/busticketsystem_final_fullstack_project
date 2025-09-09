package com.hexaware.fastx_busticketsystem.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


/*Author:Vaishnavi Suresh Vaidyanath
Modified Date:09-Aug-2025
Description: Global Exception Handler for all exceptions*/


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
	
	 @ExceptionHandler(BookingNotFoundException.class)
	    public ResponseEntity<String> handleBookingNotFound(BookingNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(BusAmenityNotFoundException.class)
	    public ResponseEntity<String> handleBusAmenityNotFound(BusAmenityNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(BusNotFoundException.class)
	    public ResponseEntity<String> handleBusNotFound(BusNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(BusOperatorAlreadyExistsException.class)
	    public ResponseEntity<String> handleBusOperatorAlreadyExists(BusOperatorAlreadyExistsException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	    }

	    @ExceptionHandler(PaymentNotFoundException.class)
	    public ResponseEntity<String> handlePaymentNotFound(PaymentNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(RouteNotFoundException.class)
	    public ResponseEntity<String> handleRouteNotFound(RouteNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(SameOriginDestinationException.class)
	    public ResponseEntity<String> handleSameOriginDestinationException(SameOriginDestinationException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(TicketNotFoundException.class)
	    public ResponseEntity<String> handleTicketNotFound(TicketNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(TripNotFoundException.class)
	    public ResponseEntity<String> handleTripNotFound(TripNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(AdminAlreadyExistsException.class)
	    @ResponseStatus(HttpStatus.CONFLICT)
	    public String handleAdminExists(AdminAlreadyExistsException ex) {
	        return ex.getMessage();
	    }

	    @ExceptionHandler(AdminNotFoundException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public String handleAuthErrors(RuntimeException ex) {
	        return ex.getMessage();
	    }

	    @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", ex.getMessage());
	        return ResponseEntity.badRequest().body(error);
	    }
	    
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", ex.getReason());  
	        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
	    }

	    @ExceptionHandler(BusOperatorNotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleBusOpNotFound(BusOperatorNotFoundException ex) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }

}
