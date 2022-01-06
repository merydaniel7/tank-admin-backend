package com.tank.tankadminbackend;

import com.tank.tankadminbackend.services.marketingcost.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TankAdminBackendApplicationTests {
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


    @Test
    void contextLoads() {
    }


    @Test
    public void argepAdCostTestOnSpecificDate() {
        String shop = "legjobbmunkaruha";
        String date = "2022-01-05";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArgepService argepService = context.getBean(ArgepService.class, shop, argepUserName, argepPassword);
        /*LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);*/
        LocalDate localDate = LocalDate.parse(date);
        float adCost = argepService.getSumOfAdCost(localDate);
        assertEquals(44765.0, adCost);
    }


    @Test
    public void arukeresoAdCostTestOnSpecificDate() throws IOException, InterruptedException {
        // The server's IP must be added on Arukereso admin page.
        String date = "2022-01-05";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArukeresoService arukeresoService = context.getBean(ArukeresoService.class, arukeresoApiKey);
        float adCost = arukeresoService.getSumOfAdCost(date);
        assertEquals(1859.5509033203125, adCost);
    }


    @Test
    public void facebookAdCostTestOnSpecificDate() {
        String date = "2022-01-05";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        FacebookService facebookService = context.getBean(FacebookService.class, facebookAccessToken, facebookAppSecret, facebookAdAccountId);
        float adCost = facebookService.getSumOfAdCost(date);
        assertEquals(21986.0, adCost);
    }


    @Test
    public void googleAdCostTestOnSpecificDate() throws GeneralSecurityException, IOException {
        String date = "2022-01-05";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        GoogleAnalyticsService googleAnalyticsService = context.getBean(GoogleAnalyticsService.class, googleKeyFileLocation, googleViewId, "lm");
        float adCost = googleAnalyticsService.getSumOfAdCost(date);
        assertEquals(15103.984375, adCost);
    }
}
