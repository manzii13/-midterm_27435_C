package auca.ac.rw.onlineServiceBooking.service;

import auca.ac.rw.onlineServiceBooking.model.Location;
import auca.ac.rw.onlineServiceBooking.model.User;
import auca.ac.rw.onlineServiceBooking.model.enums.ERole;
import auca.ac.rw.onlineServiceBooking.repository.LocationRepository;
import auca.ac.rw.onlineServiceBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    public String registerUser(User user, String villageCode) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email " + user.getEmail() + " is already registered";
        }

        if (userRepository.existsByPhone(user.getPhone())) {
            return "Phone " + user.getPhone() + " is already registered";
        }

        Location village = locationRepository.findByCode(villageCode).orElse(null);
        if (village == null) {
            return "Village with code " + villageCode + " not found";
        }

        if (!village.getType().name().equals("VILLAGE")) {
            return "Location with code " + villageCode + " is not a Village";
        }

        user.setVillage(village);

        // Step 6: Save
        userRepository.save(user);
        return "User registered successfully";
    }

    public Page<User> getAllUsers(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable);
    }

    public Page<User> getUsersByRole(ERole role, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("fullName").ascending());
        return userRepository.findByRole(role, pageable);
    }

    public List<User> getAllByRole(ERole role) {
        return userRepository.findByRole(role);
    }

    public User getUserById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<User> getUsersByProvinceName(String provinceName) {
        return userRepository.findUsersByProvinceName(provinceName);
    }

    public List<User> getUsersByProvinceCode(String provinceCode) {
        return userRepository.findUsersByProvinceCode(provinceCode);
    }
}