package auca.ac.rw.onlineServiceBooking.controller;

import auca.ac.rw.onlineServiceBooking.model.Payment;
import auca.ac.rw.onlineServiceBooking.model.enums.EPaymentStatus;
import auca.ac.rw.onlineServiceBooking.service.PaymentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    
    @PostMapping(
        value = "/pay",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> makePayment(
            @RequestParam String bookingId,
            @RequestParam String paymentMethod) {

        String result = paymentService.makePayment(bookingId, paymentMethod);

        if (result.startsWith("Payment of")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(409).body(result);
        }
    }

    
    @GetMapping(value = "/by-booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByBooking(@RequestParam String bookingId) {
        Payment payment = paymentService.getPaymentByBookingId(bookingId);
        if (payment == null) {
            return ResponseEntity.status(404).body("No payment found for booking: " + bookingId);
        }
        return ResponseEntity.ok(payment);
    }

    
    @GetMapping(value = "/by-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByStatus(@RequestParam EPaymentStatus status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
}