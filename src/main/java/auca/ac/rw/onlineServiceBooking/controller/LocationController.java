package auca.ac.rw.onlineServiceBooking.controller;

import auca.ac.rw.onlineServiceBooking.model.Location;
import auca.ac.rw.onlineServiceBooking.model.enums.ELocationType;
import auca.ac.rw.onlineServiceBooking.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveLocation(
            @RequestBody Location location,
            @RequestParam(required = false) String parentId) {

        String result = locationService.saveLocation(location, parentId);

        if (result.equals("Location saved successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(409).body(result);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping(value = "/by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByType(@RequestParam ELocationType type) {
        return ResponseEntity.ok(locationService.getByType(type));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable String id) {
        Location location = locationService.getById(id);
        if (location == null) {
            return ResponseEntity.status(404).body("Location not found");
        }
        return ResponseEntity.ok(location);
    }
}