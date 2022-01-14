package com.tank.tankadminbackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tank.tankadminbackend.services.MarketingCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/marketing-cost")
public class MarketingCostController {
    @Autowired
    MarketingCostService marketingCostService;

    @PostMapping("/lm")
    public String getMarketingCost(@Valid @RequestBody String month) throws JsonProcessingException {
        return marketingCostService.getLegjobbMunkaruhaMarketingCostByMonth(month);
    }
}
