package auca.ac.rw.onlineServiceBooking.service;

import auca.ac.rw.onlineServiceBooking.model.ServiceEntity;
import auca.ac.rw.onlineServiceBooking.model.User;
import auca.ac.rw.onlineServiceBooking.repository.ServiceRepository;
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
public class ServiceEntityService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    public String addService(ServiceEntity service, String providerId) {

        if (serviceRepository.existsByName(service.getName())) {
            return "Service with name " + service.getName() + " already exists";
        }

        User provider = userRepository.findById(UUID.fromString(providerId)).orElse(null);
        if (provider == null) {
            return "Provider not found with id: " + providerId;
        }

        if (!provider.getRole().name().equals("PROVIDER")) {
            return "User is not a PROVIDER. Only providers can add services";
        }

        service.setProvider(provider);
        serviceRepository.save(service);
        return "Service added successfully";
    }

    public List<ServiceEntity> getAllServicesSortedByPrice() {
        return serviceRepository.findAll(Sort.by("price").ascending());
    }

    public List<ServiceEntity> getAllServicesSortedByPriceDesc() {
        return serviceRepository.findAll(Sort.by("price").descending());
    }

    public Page<ServiceEntity> getServicesWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        return serviceRepository.findAll(pageable);
    }

    public Page<ServiceEntity> getServicesByCategory(String category, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return serviceRepository.findByCategory(category, pageable);
    }

    public List<ServiceEntity> getServicesByProvider(String providerId) {
        return serviceRepository.findByProviderId(UUID.fromString(providerId));
    }

    public ServiceEntity getServiceById(String id) {
        return serviceRepository.findById(UUID.fromString(id)).orElse(null);
    }
}