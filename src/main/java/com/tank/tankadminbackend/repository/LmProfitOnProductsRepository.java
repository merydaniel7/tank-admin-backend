package com.tank.tankadminbackend.repository;

import com.tank.tankadminbackend.models.lmdata.LmProfitOnProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LmProfitOnProductsRepository extends JpaRepository<LmProfitOnProducts, Long> {
    Boolean existsByDate(String date);

    Optional<LmProfitOnProducts> findByDate(String date);

    List<LmProfitOnProducts> findByDateStartsWith(String month);
}
