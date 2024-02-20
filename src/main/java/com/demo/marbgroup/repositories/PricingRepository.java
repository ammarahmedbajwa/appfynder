package com.demo.marbgroup.repositories;

import com.demo.marbgroup.models.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<Pricing, Long> {
    Pricing findByLivingHomeName(String livingHomeName);
}
