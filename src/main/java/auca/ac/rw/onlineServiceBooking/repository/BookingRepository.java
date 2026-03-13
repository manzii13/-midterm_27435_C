package auca.ac.rw.onlineServiceBooking.repository;

import auca.ac.rw.onlineServiceBooking.model.Booking;
import auca.ac.rw.onlineServiceBooking.model.enums.EBookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Boolean existsByClientId(UUID clientId);

    List<Booking> findByClientId(UUID clientId);

    List<Booking> findByStatus(EBookingStatus status);

    Page<Booking> findAll(Pageable pageable);

    Page<Booking> findByClientId(UUID clientId, Pageable pageable);
}