package auca.ac.rw.onlineServiceBooking.controller;

import auca.ac.rw.onlineServiceBooking.model.ServiceEntity;
import auca.ac.rw.onlineServiceBooking.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceEntityService serviceEntityService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addService(
            @RequestBody ServiceEntity service,
            @RequestParam String providerId) {

        String result = serviceEntityService.addService(service, providerId);

        if (result.equals("Service added successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(409).body(result);
        }
    }

    @GetMapping(value = "/all/sorted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSorted(
            @RequestParam(defaultValue = "asc") String direction) {

        List<ServiceEntity> services;
        if (direction.equalsIgnoreCase("desc")) {
            services = serviceEntityService.getAllServicesSortedByPriceDesc();
        } else {
            services = serviceEntityService.getAllServicesSortedByPrice();
        }
        return ResponseEntity.ok(services);
    }

    @GetMapping(value = "/all/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPaged(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {

        Page<ServiceEntity> services = serviceEntityService.getServicesWithPagination(pageNumber, pageSize);
        return ResponseEntity.ok(services);
    }

    @GetMapping(value = "/by-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {

        Page<ServiceEntity> services = serviceEntityService.getServicesByCategory(category, pageNumber, pageSize);
        return ResponseEntity.ok(services);
    }

    @GetMapping(value = "/by-provider", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByProvider(@RequestParam String providerId) {
        List<ServiceEntity> services = serviceEntityService.getServicesByProvider(providerId);
        return ResponseEntity.ok(services);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable String id) {
        ServiceEntity service = serviceEntityService.getServiceById(id);
        if (service == null) {
            return ResponseEntity.status(404).body("Service not found");
        }
        return ResponseEntity.ok(service);
    }
}