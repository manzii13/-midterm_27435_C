package auca.ac.rw.onlineServiceBooking.controller;

import auca.ac.rw.onlineServiceBooking.model.User;
import auca.ac.rw.onlineServiceBooking.model.enums.ERole;
import auca.ac.rw.onlineServiceBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestBody User user,
            @RequestParam String villageCode) {

        String result = userService.registerUser(user, villageCode);

        if (result.equals("User registered successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(409).body(result);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "fullName") String sortBy) {

        Page<User> users = userService.getAllUsers(pageNumber, pageSize, sortBy);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/by-role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByRole(
            @RequestParam ERole role,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {

        Page<User> users = userService.getUsersByRole(role, pageNumber, pageSize);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/by-province/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByProvinceName(@RequestParam String provinceName) {
        List<User> users = userService.getUsersByProvinceName(provinceName);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No users found in province: " + provinceName);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/by-province/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersByProvinceCode(@RequestParam String provinceCode) {
        List<User> users = userService.getUsersByProvinceCode(provinceCode);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No users found in province: " + provinceCode);
        }
        return ResponseEntity.ok(users);
    }
}
