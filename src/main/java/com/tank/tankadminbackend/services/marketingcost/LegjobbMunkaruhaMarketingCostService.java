package com.tank.tankadminbackend.services.marketingcost;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.api.ActualMonth;
import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.*;

@Service
public class LegjobbMunkaruhaMarketingCostService {
    @Value("${lm.facebook.access.token}")
    String facebookAccessToken;

    @Value("${lm.facebook.app.secret}")
    String facebookAppSecret;

    @Value("${lm.facebook.ad.account.id}")
    String facebookAdAccountId;

    @Value("${lm.argep.user}")
    String argepUserName;

    @Value("${lm.argep.password}")
    String argepPassword;

    @Value("${arukereso.lm.api.key}")
    String arukeresoApiKey;

    @Value("${lm.google.analytics.key.file.location}")
    String googleKeyFileLocation;

    @Value("${lm.google.analytics.view.id}")
    String googleViewId;


    public float getArgepAdCost(String date) {
        String shop = "legjobbmunkaruha";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArgepService argepService = context.getBean(ArgepService.class, shop, argepUserName, argepPassword);
        LocalDate localDate = LocalDate.parse(date);
        return argepService.getSumOfAdCost(localDate);
    }


    public float getArukeresoAdCost(String date) throws IOException, InterruptedException {
        // The server's IP must be added on Arukereso admin page.
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArukeresoService arukeresoService = context.getBean(ArukeresoService.class, arukeresoApiKey);
        return arukeresoService.getSumOfAdCost(date);
    }


    public float getFacebookAdCost(String date) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        FacebookService facebookService = context.getBean(FacebookService.class, facebookAccessToken, facebookAppSecret, facebookAdAccountId);
        return facebookService.getSumOfAdCost(date);
    }


    public float getGoogleAdCost(String date) throws GeneralSecurityException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        GoogleAnalyticsService googleAnalyticsService = context.getBean(GoogleAnalyticsService.class, googleKeyFileLocation, googleViewId, "lm");
        return googleAnalyticsService.getSumOfAdCost(date);
    }


    public String getMarketingCostByMonth(String month, LmMarketingCostRepository lmMarketingCostRepository) throws JsonProcessingException {
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
