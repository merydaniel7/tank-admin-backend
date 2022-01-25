package com.tank.tankadminbackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tank.tankadminbackend.services.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profit")
public class ProfitController {
    @Autowired
    ProfitService profitService;

    @PostMapping("/lm")
    public String getProfitOnProducts(@Valid @RequestBody String month) throws JsonProcessingException {
        return profitService.getLegjobbMunkaruhaProfitByMonth(month);
    }
}
