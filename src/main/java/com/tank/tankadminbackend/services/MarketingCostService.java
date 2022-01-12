package com.tank.tankadminbackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.api.ActualMonth;
import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketingCostService {
    @Autowired
    LmMarketingCostRepository lmMarketingCostRepository;

    public String getMarketingCostByMonth(String month) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ActualMonth actualMonth = mapper.readValue(month, ActualMonth.class);
        List<LmMarketingCost> lmMarketingCosts = lmMarketingCostRepository.findByDateStartsWith(actualMonth.getMonth());
        List<Map<String, Float>> costs = new ArrayList<>();
        for (LmMarketingCost marketingCost : lmMarketingCosts) {
            Map<String, Float> dayWithCost = new HashMap<>();
            dayWithCost.put(marketingCost.getDate(), marketingCost.getSum());
            costs.add(dayWithCost);
        }
        return new ObjectMapper().writeValueAsString(costs);
    }
}
