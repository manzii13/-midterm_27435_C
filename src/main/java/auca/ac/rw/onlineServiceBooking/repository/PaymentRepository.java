package auca.ac.rw.onlineServiceBooking.repository;

import auca.ac.rw.onlineServiceBooking.model.Payment;
import auca.ac.rw.onlineServiceBooking.model.enums.EPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Boolean existsByBookingId(UUID bookingId);

    Optional<Payment> findByBookingId(UUID bookingId);

    List<Payment> findByStatus(EPaymentStatus status);
}