package auca.ac.rw.onlineServiceBooking.controller;

import auca.ac.rw.onlineServiceBooking.model.Booking;
import auca.ac.rw.onlineServiceBooking.model.enums.EBookingStatus;
import auca.ac.rw.onlineServiceBooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> request) {

        String clientId = (String) request.get("clientId");
        List<String> serviceIds = (List<String>) request.get("serviceIds");

        String result = bookingService.createBooking(clientId, serviceIds);

        if (result.startsWith("Booking created")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    @PutMapping(value = "/update-status/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(
            @PathVariable String bookingId,
            @RequestParam EBookingStatus status) {

        String result = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {

        Page<Booking> bookings = bookingService.getAllBookings(pageNumber, pageSize);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping(value = "/by-client", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByClient(
            @RequestParam String clientId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {

        Page<Booking> bookings = bookingService.getBookingsByClient(clientId, pageNumber, pageSize);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping(value = "/by-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByStatus(@RequestParam EBookingStatus status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }
}