package com.tank.tankadminbackend.services;

import com.tank.tankadminbackend.models.unas.order.*;
import com.tank.tankadminbackend.services.unas.UnasAuthTokenService;
import com.tank.tankadminbackend.services.unas.UnasOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class DailyStatistics {
    @Autowired
    UnasAuthTokenService unasAuthTokenService;

    @Autowired
    UnasOrderService unasOrderService;

    @Value("${unas.lm.api.key}")
    String lmUnasApiKey;

    private int numberOfOrdersToday = 0;
    private int numberOfOrdersYesterday = 0;
    private float incomeToday = 0;
    private float incomeYesterday = 0;
    private float numberOfOrderChange = 0;
    private float incomeChange = 0;


    public void getDailyStatistics() throws IOException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String token = unasAuthTokenService.getAuthToken(lmUnasApiKey);

        List<Order> openNormalOrdersToday = unasOrderService.getOrders(token, OrderType.open_normal.toString(), today);
        numberOfOrdersToday = openNormalOrdersToday.size();
        List<Order> openNormalOrdersYesterday = unasOrderService.getOrders(token, OrderType.open_normal.toString(), yesterday);
        numberOfOrdersYesterday = openNormalOrdersYesterday.size();
        numberOfOrderChange = 100 - ((numberOfOrdersToday / numberOfOrdersYesterday) * 100);

    }

    public void getOrdersInformation(List<Order> orders, float income) {
        for (Order order : orders) {
            Customer customer = order.getCustomer();
            Contact contact = customer.getContact();
            String customerName = contact.getName();

            if (!Objects.equals(customerName, "TANK Kft.")) {
                List<Item> items = List.of(order.getItems());



            }
        }
    }
}
