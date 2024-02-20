package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.LivingHome;
import com.demo.marbgroup.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLivingHomeName(String livingHomeName);
}
