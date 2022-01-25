package com.tank.tankadminbackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.api.ActualMonth;
import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import com.tank.tankadminbackend.models.lmdata.LmProfitOnProducts;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import com.tank.tankadminbackend.repository.LmProfitOnProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProfitService {
    @Autowired
    LmMarketingCostRepository lmMarketingCostRepository;

    @Autowired
    LmProfitOnProductsRepository lmProfitOnProductsRepository;

    public String getLegjobbMunkaruhaProfitByMonth(String month) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ActualMonth actualMonth = mapper.readValue(month, ActualMonth.class);
        List<LmProfitOnProducts> lmProfitOnProducts = lmProfitOnProductsRepository.findByDateStartsWithOrderById(actualMonth.getMonth());
        List<LmMarketingCost> lmMarketingCosts = lmMarketingCostRepository.findByDateStartsWithOrderById(actualMonth.getMonth());

        List<Map<String, Float>> profits = new ArrayList<>();
        for (LmProfitOnProducts lmProfitOnProduct : lmProfitOnProducts) {
            Map<String, Float> dayProfit = new HashMap<>();
            String profitDate = lmProfitOnProduct.getDate();
            float profit = lmProfitOnProduct.getProfit();

            for (LmMarketingCost marketingCost : lmMarketingCosts) {
                if (Objects.equals(profitDate, marketingCost.getDate())) {
                    profit -= marketingCost.getSum();
                }
            }

            dayProfit.put(profitDate, profit);

            profits.add(dayProfit);
        }

        return new ObjectMapper().writeValueAsString(profits);
    }
}
