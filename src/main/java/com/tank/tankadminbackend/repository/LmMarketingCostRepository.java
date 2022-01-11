package com.tank.tankadminbackend.repository;

import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LmMarketingCostRepository extends JpaRepository<LmMarketingCost, Long> {
    Boolean existsByDate(String date);

    Optional<LmMarketingCost> findByDate(String date);

    List<LmMarketingCost> findByDateStartsWith(String month);
}
