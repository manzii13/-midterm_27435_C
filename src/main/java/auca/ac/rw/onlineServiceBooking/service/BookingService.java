package auca.ac.rw.onlineServiceBooking.service;

import auca.ac.rw.onlineServiceBooking.model.Booking;
import auca.ac.rw.onlineServiceBooking.model.ServiceEntity;
import auca.ac.rw.onlineServiceBooking.model.User;
import auca.ac.rw.onlineServiceBooking.model.enums.EBookingStatus;
import auca.ac.rw.onlineServiceBooking.repository.BookingRepository;
import auca.ac.rw.onlineServiceBooking.repository.ServiceRepository;
import auca.ac.rw.onlineServiceBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public String createBooking(String clientId, List<String> serviceIds) {

        User client = userRepository.findById(UUID.fromString(clientId)).orElse(null);
        if (client == null) {
            return "Client not found with id: " + clientId;
        }

        if (!client.getRole().name().equals("CLIENT")) {
            return "Only CLIENT users can make bookings";
        }

        List<ServiceEntity> services = new ArrayList<>();
        for (String serviceId : serviceIds) {
            ServiceEntity service = serviceRepository
                    .findById(UUID.fromString(serviceId)).orElse(null);
            if (service == null) {
                return "Service not found with id: " + serviceId;
            }
            services.add(service);
        }

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setServices(services);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(EBookingStatus.PENDING);

        bookingRepository.save(booking);
        return "Booking created successfully with id: " + booking.getId();
    }

    public String updateBookingStatus(String bookingId, EBookingStatus newStatus) {
        Booking booking = bookingRepository.findById(UUID.fromString(bookingId)).orElse(null);
        if (booking == null) {
            return "Booking not found";
        }
        booking.setStatus(newStatus);
        bookingRepository.save(booking);
        return "Booking status updated to " + newStatus;
    }

    public Page<Booking> getAllBookings(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("bookingDate").descending());
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getBookingsByClient(String clientId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by("bookingDate").descending());
        return bookingRepository.findByClientId(UUID.fromString(clientId), pageable);
    }

    public List<Booking> getBookingsByStatus(EBookingStatus status) {
        return bookingRepository.findByStatus(status);
    }
}