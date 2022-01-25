package com.tank.tankadminbackend;

import com.tank.tankadminbackend.models.lmdata.LmMarketingCost;
import com.tank.tankadminbackend.models.unas.order.Order;
import com.tank.tankadminbackend.models.unas.order.OrderType;
import com.tank.tankadminbackend.repository.LmMarketingCostRepository;
import com.tank.tankadminbackend.services.marketingcost.*;
import com.tank.tankadminbackend.services.unas.DailyProfitOnProductsService;
import com.tank.tankadminbackend.services.unas.UnasAuthTokenService;
import com.tank.tankadminbackend.services.unas.UnasOrderService;
import com.tank.tankadminbackend.services.unas.UnasServiceConfig;
import com.tank.tankadminbackend.util.DateHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Value("${unas.lm.api.key}")
    String lmUnasApiKey;

    @Autowired
    LmMarketingCostRepository lmMarketingCostRepository;


    @Test
    void contextLoads() {
    }


    @Test
    public void argepAdCostTestOnSpecificDate() {
        String shop = "legjobbmunkaruha";
        String date = "2022-01-05";
        ApplicationContext context = new AnnotationConfigApplicationContext(MarketingCostConfig.class);
        ArgepService argepService = context.getBean(ArgepService.class, shop, argepUserName, argepPassword);
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
        assertEquals(20454.353515625, adCost);
    }


    @Test
    public void getUnasTokenTest() throws IOException {
        UnasAuthTokenService unasAuthTokenService = new UnasAuthTokenService();
        String token = unasAuthTokenService.getAuthToken(lmUnasApiKey);
        assertNotNull(token);
    }

    @Test
    public void getUnasOrderTest() throws IOException {
        String date = "2022.01.09";
        UnasAuthTokenService unasAuthTokenService = new UnasAuthTokenService();
        String token = unasAuthTokenService.getAuthToken(lmUnasApiKey);
        UnasOrderService orderService = new UnasOrderService();
        List<Order> orders = orderService.getOrders(token, OrderType.close_ok.toString(), date);
        assertNotNull(orders);
    }


    @Test
    public void getProfitOnProductsTest() throws IOException, InterruptedException {
        int minusDaysStart = 1;
        int minusDaysEnd = 30;
        ApplicationContext context = new AnnotationConfigApplicationContext(UnasServiceConfig.class);
        DailyProfitOnProductsService dailyProfitOnProductsService = context.getBean(DailyProfitOnProductsService.class);
        DateHelper dateHelper = new DateHelper();
        List<String> dates = dateHelper.getDatesOfDays(minusDaysStart, minusDaysEnd);
        UnasAuthTokenService unasAuthTokenService = new UnasAuthTokenService();
        String token = unasAuthTokenService.getAuthToken(lmUnasApiKey);
        List<Float> profits = new ArrayList<>();

        for (String date : dates) {
            float profit = dailyProfitOnProductsService.getDailyProfitOnProducts(token, date);
            System.out.println(profit);
            profits.add(profit);
            Thread.sleep(2000);
        }
        assertEquals(minusDaysEnd, profits.size());
    }

    @Test
    public void getMarketingCostByMonth() {
        int expected = 1;
        List<LmMarketingCost> marketingCosts = lmMarketingCostRepository.findByDateStartsWithOrderById("2022-01");

        assertEquals(expected, marketingCosts.size());
    }
}
