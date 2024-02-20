package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByLivingHomeName(String livingHomeName);
}
