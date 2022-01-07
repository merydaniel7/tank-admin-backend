package com.tank.tankadminbackend.services.update;

import com.tank.tankadminbackend.services.marketingcost.LegjobbMunkaruhaMarketingCostService;
import com.tank.tankadminbackend.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class DailyUpdateScheduler {

    private final LegjobbMunkaruhaMarketingCostService legjobbMunkaruhaMarketingCostService;
    private final DateHelper dateHelper;

    @Autowired
    public DailyUpdateScheduler(LegjobbMunkaruhaMarketingCostService legjobbMunkaruhaMarketingCostService, DateHelper dateHelper) {
        this.legjobbMunkaruhaMarketingCostService = legjobbMunkaruhaMarketingCostService;
        this.dateHelper = dateHelper;
    }

    @Scheduled(cron = "${schedule.cron}")
    public void saveMarketingCost() throws IOException, InterruptedException, GeneralSecurityException {
        String date = dateHelper.getYesterdayString();
        float sumAdCost = 0;
        float argepAdCost = legjobbMunkaruhaMarketingCostService.getArgepAdCost(date);
        System.out.println("Argep: " + argepAdCost);
        sumAdCost += argepAdCost;
        float arukeresoAdCost = legjobbMunkaruhaMarketingCostService.getArukeresoAdCost(date);
        System.out.println("Arukereso: " + arukeresoAdCost);
        sumAdCost += arukeresoAdCost;
        float facebookAdCost = legjobbMunkaruhaMarketingCostService.getFacebookAdCost(date);
        System.out.println("Facebook: " + facebookAdCost);
        sumAdCost += facebookAdCost;
        float googleAdCost = legjobbMunkaruhaMarketingCostService.getGoogleAdCost(date);
        System.out.println("Google: " + googleAdCost);
        sumAdCost += googleAdCost;
        System.out.println(sumAdCost);
    }
}
