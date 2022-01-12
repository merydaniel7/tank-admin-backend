package com.tank.tankadminbackend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tank.tankadminbackend.repository.LmProfitOnProductsRepository;
import com.tank.tankadminbackend.services.unas.DailyProfitOnProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profit-on-products")
public class ProfitOnProductsController {
    @Autowired
    DailyProfitOnProductsService dailyProfitOnProductsService;

    @Autowired
    LmProfitOnProductsRepository lmProfitOnProductsRepository;

    @PostMapping("/lm")
    public String getProfitOnProducts(@Valid @RequestBody String month) throws JsonProcessingException {
        return dailyProfitOnProductsService.getProfitOnProductsByMonth(month, lmProfitOnProductsRepository);
    }
}
