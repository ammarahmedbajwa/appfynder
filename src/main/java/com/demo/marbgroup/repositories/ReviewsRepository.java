package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByLivingHomeId(Long livingHomeId);
}
