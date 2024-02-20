package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.LivingHome;
import com.demo.marbgroup.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
//    Location findByLivingHome(String livingHomeName);
    List<Location> findByAddress(String location);
}
