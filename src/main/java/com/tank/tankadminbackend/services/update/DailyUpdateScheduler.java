package com.tank.tankadminbackend.services.update;

import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import com.tank.tankadminbackend.models.lmdata.LmProfitOnProducts;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import com.tank.tankadminbackend.repository.LmProfitOnProductsRepository;
import com.tank.tankadminbackend.services.marketingcost.LmMarketingCostService;
import com.tank.tankadminbackend.services.unas.DailyProfitOnProductsService;
import com.tank.tankadminbackend.services.unas.UnasAuthTokenService;
import com.tank.tankadminbackend.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class DailyUpdateScheduler {

    private final LmMarketingCostService lmMarketingCostService;
    private final LmMarketingCostRepository lmMarketingCostRepository;
    private final LmProfitOnProductsRepository lmProfitOnProductsRepository;
    private final DailyProfitOnProductsService dailyProfitOnProductsService;
    private final UnasAuthTokenService unasAuthTokenService;
    private final DateHelper dateHelper;
    @Value("${unas.lm.api.key}")
    String lmUnasApiKey;


    @Autowired
    public DailyUpdateScheduler(LmMarketingCostService lmMarketingCostService, DailyProfitOnProductsService dailyProfitOnProductsService, LmMarketingCostRepository lmMarketingCostRepository, LmProfitOnProductsRepository lmProfitOnProductsRepository, UnasAuthTokenService unasAuthTokenService, DateHelper dateHelper) {
        this.lmMarketingCostService = lmMarketingCostService;
        this.dailyProfitOnProductsService = dailyProfitOnProductsService;
        this.lmMarketingCostRepository = lmMarketingCostRepository;
        this.lmProfitOnProductsRepository = lmProfitOnProductsRepository;
        this.unasAuthTokenService = unasAuthTokenService;
        this.dateHelper = dateHelper;
    }

    @Scheduled(cron = "${schedule.cron.cost}")
    @Transactional
    public void saveMarketingCost() throws IOException, InterruptedException, GeneralSecurityException {
        String date = dateHelper.getYesterdayString();
        float sumAdCost = 0;
        float argepAdCost = lmMarketingCostService.getArgepAdCost(date);
        System.out.println("Argep: " + argepAdCost);
        sumAdCost += argepAdCost;
        float arukeresoAdCost = lmMarketingCostService.getArukeresoAdCost(date);
        System.out.println("Arukereso: " + arukeresoAdCost);
        sumAdCost += arukeresoAdCost;
        float facebookAdCost = lmMarketingCostService.getFacebookAdCost(date);
        System.out.println("Facebook: " + facebookAdCost);
        sumAdCost += facebookAdCost;
        float googleAdCost = lmMarketingCostService.getGoogleAdCost(date);
        System.out.println("Google: " + googleAdCost);
        sumAdCost += googleAdCost;
        System.out.println("Sum of ad cost - "+ date + " : " + sumAdCost);

        if (!lmMarketingCostRepository.existsByDate(date)) {
            LmMarketingCost lmMarketingCost = new LmMarketingCost();
            lmMarketingCost.setArgep(argepAdCost);
            lmMarketingCost.setArukereso(arukeresoAdCost);
            lmMarketingCost.setFacebook(facebookAdCost);
            lmMarketingCost.setGoogle(googleAdCost);
            lmMarketingCost.setSum(sumAdCost);
            lmMarketingCost.setDate(date);
            lmMarketingCostRepository.save(lmMarketingCost);
        } else {
            Optional<LmMarketingCost> costOptional = lmMarketingCostRepository.findByDate(date);
            float finalSumAdCost = sumAdCost;
            costOptional.ifPresent(lmMarketingCost -> {
                lmMarketingCost.setArgep(argepAdCost);
                lmMarketingCost.setArukereso(arukeresoAdCost);
                lmMarketingCost.setFacebook(facebookAdCost);
                lmMarketingCost.setGoogle(googleAdCost);
                lmMarketingCost.setSum(finalSumAdCost);
            });
        }
    }


    @Scheduled(cron = "${schedule.cron.profit}")
    @Transactional
    public void saveProfitOnProducts() throws IOException, InterruptedException {
        List<String> dates = dateHelper.getDatesOfDaysReverse(1, 30);
        String token = unasAuthTokenService.getAuthToken(lmUnasApiKey);

        for (String date : dates) {
            float profit = dailyProfitOnProductsService.getDailyProfitOnProducts(token, date);
            System.out.println("Profit - "+ date + " : " + profit);
            if (!lmProfitOnProductsRepository.existsByDate(date)) {
                LmProfitOnProducts profitOnProducts = new LmProfitOnProducts();
                profitOnProducts.setProfit(profit);
                profitOnProducts.setDate(date);
                lmProfitOnProductsRepository.save(profitOnProducts);
            } else {
                Optional<LmProfitOnProducts> profitOptional = lmProfitOnProductsRepository.findByDate(date);
                profitOptional.ifPresent(profitOnProducts -> profitOnProducts.setProfit(profit));
            }
            Thread.sleep(2000);
        }
    }
}