package com.tank.tankadminbackend.repository;

import com.tank.tankadminbackend.models.lmdata.LmProfitOnProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmProfitOnProductsRepository extends JpaRepository<LmProfitOnProducts, Long> {
    Boolean existsByDate(String date);
}
