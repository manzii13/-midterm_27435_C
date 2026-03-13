package auca.ac.rw.onlineServiceBooking.repository;

import auca.ac.rw.onlineServiceBooking.model.User;
import auca.ac.rw.onlineServiceBooking.model.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    List<User> findByRole(ERole role);

    Page<User> findAll(Pageable pageable);

    Page<User> findByRole(ERole role, Pageable pageable);

    @Query("""
                SELECT u FROM User u
                WHERE u.village.parent.parent.parent.parent.name = :provinceName
            """)
    List<User> findUsersByProvinceName(@Param("provinceName") String provinceName);

    @Query("""
                SELECT u FROM User u
                WHERE u.village.parent.parent.parent.parent.code = :provinceCode
            """)
    List<User> findUsersByProvinceCode(@Param("provinceCode") String provinceCode);
}