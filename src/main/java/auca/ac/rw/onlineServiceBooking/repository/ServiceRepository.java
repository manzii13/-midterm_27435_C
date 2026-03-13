package auca.ac.rw.onlineServiceBooking.repository;

import auca.ac.rw.onlineServiceBooking.model.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {

    Boolean existsByName(String name);

    List<ServiceEntity> findByCategory(String category);

    List<ServiceEntity> findByProviderId(UUID providerId);

    List<ServiceEntity> findAll(Sort sort);

    Page<ServiceEntity> findAll(Pageable pageable);

    Page<ServiceEntity> findByCategory(String category, Pageable pageable);
}