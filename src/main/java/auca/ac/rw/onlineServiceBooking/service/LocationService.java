package auca.ac.rw.onlineServiceBooking.service;

import auca.ac.rw.onlineServiceBooking.model.Location;
import auca.ac.rw.onlineServiceBooking.model.enums.ELocationType;
import auca.ac.rw.onlineServiceBooking.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public String saveLocation(Location location, String parentId) {

        if (parentId != null) {
            Location parent = locationRepository.findById(UUID.fromString(parentId))
                    .orElse(null);
            if (parent == null) {
                return "Parent location not found with id: " + parentId;
            }
            location.setParent(parent);
        }

        if (locationRepository.existsByCode(location.getCode())) {
            return "Location with code " + location.getCode() + " already exists";
        }

        // Step 3: Save
        locationRepository.save(location);
        return "Location saved successfully";
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<Location> getByType(ELocationType type) {
        return locationRepository.findByType(type);
    }

    public Location getById(String id) {
        return locationRepository.findById(UUID.fromString(id)).orElse(null);
    }
}