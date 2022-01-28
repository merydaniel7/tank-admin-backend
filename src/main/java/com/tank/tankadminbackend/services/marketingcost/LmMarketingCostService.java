package com.tank.tankadminbackend.services.marketingcost;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;

@Service
public class LmMarketingCostService {
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

    @Value("${lm.argep.ftp.cpc}")
    String lmArgepCPC;

    @Value("${lm.argep.ftp.gs}")
    String lmArgepGS;

    @Value("${argep.ftp.url}")
    String argepFTPUrl;

    @Value("${argep.ftp.user}")
    String argepFTPUserName;

    @Value("${argep.ftp.password}")
    String argepFTPPassword;


    /*public float getArgepAdCostWebDriver(String date) {
        String shop = "legjobbmunkaruha";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArgepServiceWebDriver argepServiceWebDriver = context.getBean(ArgepServiceWebDriver.class, shop, argepUserName, argepPassword);
        LocalDate localDate = LocalDate.parse(date);
        return argepServiceWebDriver.getSumOfAdCost(localDate);
    }*/

    public float getArgepAdCostFTP(String date) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArgepServiceFTP argepServiceFTP = context.getBean(ArgepServiceFTP.class, lmArgepCPC, lmArgepGS, argepFTPUrl, argepUserName, argepPassword);
        return argepServiceFTP.getSumOfAdCost(date);
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
}
