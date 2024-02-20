package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Long> {
    List<Services> findByLivingHomeName(String livingHomeName);
}
