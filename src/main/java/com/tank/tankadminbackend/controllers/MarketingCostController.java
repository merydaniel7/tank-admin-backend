package com.tank.tankadminbackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import com.tank.tankadminbackend.services.marketingcost.LegjobbMunkaruhaMarketingCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/marketing-cost")
public class MarketingCostController {
    @Autowired
    LegjobbMunkaruhaMarketingCostService legjobbMunkaruhaMarketingCostService;

    @Autowired
    LmMarketingCostRepository lmMarketingCostRepository;

    @PostMapping("/lm")
    public String getMarketingCost(@Valid @RequestBody String month) throws JsonProcessingException {
        return legjobbMunkaruhaMarketingCostService.getMarketingCostByMonth(month, lmMarketingCostRepository);
    }

}
