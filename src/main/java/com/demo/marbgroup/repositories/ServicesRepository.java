package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    List<Services> findByLivingHomeName(String livingHomeName);
}
