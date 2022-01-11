package com.tank.tankadminbackend.repository;

import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmMarketingCostRepository extends JpaRepository<LmMarketingCost, Long> {
    Boolean existsByDate(String date);
}
