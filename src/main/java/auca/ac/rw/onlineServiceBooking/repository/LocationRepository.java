package auca.ac.rw.onlineServiceBooking.repository;

import auca.ac.rw.onlineServiceBooking.model.Location;
import auca.ac.rw.onlineServiceBooking.model.enums.ELocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    Boolean existsByCode(String code);

    Optional<Location> findByCode(String code);

    List<Location> findByType(ELocationType type);

    @Query("SELECT l FROM Location l WHERE l.type = 'PROVINCE' AND LOWER(l.name) = LOWER(:name)")
    Optional<Location> findProvinceByName(@Param("name") String name);

    @Query("SELECT l FROM Location l WHERE l.type = 'PROVINCE' AND l.code = :code")
    Optional<Location> findProvinceByCode(@Param("code") String code);
}