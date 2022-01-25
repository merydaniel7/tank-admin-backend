package com.tank.tankadminbackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.api.ActualMonth;
import com.tank.tankadminbackend.models.lmdata.LmProfitOnProducts;
import com.tank.tankadminbackend.repository.LmProfitOnProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfitOnProductsService {
    @Autowired
    LmProfitOnProductsRepository lmProfitOnProductsRepository;

    public String getLegjobbMunkaruhaProfitOnProductsByMonth(String month) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ActualMonth actualMonth = mapper.readValue(month, ActualMonth.class);
        List<LmProfitOnProducts> profitOnProducts = lmProfitOnProductsRepository.findByDateStartsWithOrderById(actualMonth.getMonth());
        List<Map<String, Float>> profits = new ArrayList<>();
        for (LmProfitOnProducts lmProfitOnProduct : profitOnProducts) {
            Map<String, Float> dayProfit = new HashMap<>();
            dayProfit.put(lmProfitOnProduct.getDate(), lmProfitOnProduct.getProfit());
            profits.add(dayProfit);
        }

        return new ObjectMapper().writeValueAsString(profits);
    }
}
