package auca.ac.rw.onlineServiceBooking.service;

import auca.ac.rw.onlineServiceBooking.model.Booking;
import auca.ac.rw.onlineServiceBooking.model.Payment;
import auca.ac.rw.onlineServiceBooking.model.enums.EBookingStatus;
import auca.ac.rw.onlineServiceBooking.model.enums.EPaymentStatus;
import auca.ac.rw.onlineServiceBooking.repository.BookingRepository;
import auca.ac.rw.onlineServiceBooking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public String makePayment(String bookingId, String paymentMethod) {

        if (paymentRepository.existsByBookingId(UUID.fromString(bookingId))) {
            return "Payment already exists for booking: " + bookingId;
        }

        Booking booking = bookingRepository.findById(UUID.fromString(bookingId)).orElse(null);
        if (booking == null) {
            return "Booking not found with id: " + bookingId;
        }

        double totalAmount = booking.getServices()
                .stream()
                .mapToDouble(service -> service.getPrice())
                .sum();

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(totalAmount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(EPaymentStatus.PAID);
        payment.setPaymentMethod(paymentMethod);

        paymentRepository.save(payment);

        booking.setStatus(EBookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return "Payment of " + totalAmount + " RWF processed successfully";
    }

    public Payment getPaymentByBookingId(String bookingId) {
        return paymentRepository.findByBookingId(UUID.fromString(bookingId)).orElse(null);
    }

    public List<Payment> getPaymentsByStatus(EPaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
}