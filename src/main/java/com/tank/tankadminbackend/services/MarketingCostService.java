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

    public String getLegjobbMunkaruhaMarketingCostByMonth(String month) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ActualMonth actualMonth = mapper.readValue(month, ActualMonth.class);
        List<LmMarketingCost> lmMarketingCosts = lmMarketingCostRepository.findByDateStartsWithOrderById(actualMonth.getMonth());
        List<Map<String, Float>> costs = new ArrayList<>();
        List<Map<String, Float>> costsArgep = new ArrayList<>();
        List<Map<String, Float>> costsArukereso = new ArrayList<>();
        List<Map<String, Float>> costsFacebook = new ArrayList<>();
        List<Map<String, Float>> costsGoogle = new ArrayList<>();
        for (LmMarketingCost marketingCost : lmMarketingCosts) {
            Map<String, Float> dayWithCost = new HashMap<>();
            Map<String, Float> dayWithArgepCost = new HashMap<>();
            Map<String, Float> dayWithArukeresoCost = new HashMap<>();
            Map<String, Float> dayWithFacebookCost = new HashMap<>();
            Map<String, Float> dayWithGoogleCost = new HashMap<>();
            dayWithCost.put(marketingCost.getDate(), marketingCost.getSum());
            dayWithArgepCost.put(marketingCost.getDate(), marketingCost.getArgep());
            dayWithArukeresoCost.put(marketingCost.getDate(), marketingCost.getArukereso());
            dayWithFacebookCost.put(marketingCost.getDate(), marketingCost.getFacebook());
            dayWithGoogleCost.put(marketingCost.getDate(), marketingCost.getGoogle());
            costs.add(dayWithCost);
            costsArgep.add(dayWithArgepCost);
            costsArukereso.add(dayWithArukeresoCost);
            costsFacebook.add(dayWithFacebookCost);
            costsGoogle.add(dayWithGoogleCost);
        }
        Map<String, List<Map<String, Float>>> allCost = new HashMap<>();
        allCost.put("sum", costs);
        allCost.put("argep", costsArgep);
        allCost.put("arukereso", costsArukereso);
        allCost.put("facebook", costsFacebook);
        allCost.put("google", costsGoogle);
        return new ObjectMapper().writeValueAsString(allCost);
    }

    public String getMonths() throws JsonProcessingException {
        List<String> months = lmMarketingCostRepository.findMonths();
        return new ObjectMapper().writeValueAsString(months);
    }
}
