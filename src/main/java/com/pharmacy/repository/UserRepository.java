package com.pharmacy.repository;

import com.pharmacy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Assessment Point 7: Implementation of existBy()
    boolean existsByEmail(String email);

    // Assessment Point 8: Retrieve all users from a given province using province
    // code OR province name. Users are linked to villages; province is 4 levels up.
    @Query("SELECT u FROM User u " +
           "JOIN u.location v " +
           "JOIN v.parent c " +
           "JOIN c.parent s " +
           "JOIN s.parent d " +
           "JOIN d.parent p " +
           "WHERE (p.code = :provinceCode OR p.name = :provinceName)")
    List<User> findByProvinceCodeOrName(@Param("provinceCode") String provinceCode,
                                        @Param("provinceName") String provinceName);
}
