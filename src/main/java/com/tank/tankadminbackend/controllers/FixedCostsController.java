package com.tank.tankadminbackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tank.tankadminbackend.services.FixedCostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/fixed-costs")
public class FixedCostsController {
    @Autowired
    FixedCostsService fixedCostsService;

    @PostMapping("/tank")
    public String getFixedCosts(@Valid @RequestBody String month) throws JsonProcessingException {
        return fixedCostsService.getFixedCosts(month);
    }

    @PostMapping("/tank/save")
    public String saveFixedCosts(@Valid @RequestBody String fixedCost) throws JsonProcessingException {
        return fixedCostsService.saveFixedCost(fixedCost);
    }
}
