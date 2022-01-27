package com.tank.tankadminbackend.repository;

import com.tank.tankadminbackend.models.cost.FixedCosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FixedCostRepository extends JpaRepository<FixedCosts, Long> {
    FixedCosts findByMonth(String month);
}
