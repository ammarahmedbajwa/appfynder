package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.LivingHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivingHomeRepository extends JpaRepository<LivingHome, Long> {
    List<LivingHome> findByLocationName(String location);

    List<LivingHome> findByServiceName(String service);
}
