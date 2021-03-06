package com.tank.tankadminbackend.services.unas;

import com.tank.tankadminbackend.models.unas.order.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DailyProfitOnProductsService {
    private final float MIN_SHIPPING_COST;
    private float netProfit;

    public DailyProfitOnProductsService() {
        this.MIN_SHIPPING_COST = 1050;
    }

    public float getDailyProfitOnProducts(String token, String date) throws IOException {
        netProfit = 0;
        UnasOrderService unasOrderService = new UnasOrderService();
        List<Order> openNormalOrders = unasOrderService.getOrders(token, OrderType.open_normal.toString(), date);
        List<Order> closedOkOrders = unasOrderService.getOrders(token, OrderType.close_ok.toString(), date);
        if (openNormalOrders != null) {
            getProfitOnOrders(openNormalOrders);
        }
        if (closedOkOrders != null) {
            getProfitOnOrders(closedOkOrders);
        }

        return netProfit;
    }

    private void getProfitOnOrders(List<Order> orders) {
        for (Order order : orders) {
            Customer customer = order.getCustomer();
            Contact contact = customer.getContact();
            String customerName = contact.getName();
            String shippingName = order.getShipping().getName();
            String payment = order.getPayment().getStatus();
            float weight = order.getWeight();
            String source;
            float netPrice;
            float quantity;
            float sum;
            float sumPurchasePrice;

            if (!Objects.equals(payment, "unpaid") && !Objects.equals(customerName, "TANK Kft.")) {
                List<Item> items = List.of(order.getItems());

                for (Item item : items) {
                    switch (item.getId()) {
                        case "discount-percent":
                        case "discount-amount":
                        case "handel-cost":
                            netProfit += item.getNetPrice();
                            break;

                        case "shipping-cost":
                            float shippingCost = item.getNetPrice();
                            if (shippingCost < MIN_SHIPPING_COST) {
                                if (Objects.equals(shippingName, "GLS Csomagpont")) {
                                    netProfit -= 1050;
                                }
                                if (Objects.equals(shippingName, "GLS H??zhozsz??ll??t??s")) {
                                    netProfit -= getGlsShippingCost(weight);
                                }
                                if (Objects.equals(shippingName, "24H Fut??r")) {
                                    netProfit -= get24hShippingCost(weight);
                                }
                            }
                            break;

                        default:
                            if (item.getProductParams() != null) {
                                source = "";
                                sumPurchasePrice = 0;

                                List<ProductParam> productParams = List.of(item.getProductParams());
                                quantity = item.getQuantity();
                                netPrice = item.getNetPrice();
                                sum = netPrice * quantity;

                                for (ProductParam param : productParams) {
                                    if (Objects.equals(param.getName(), "TAS")) {
                                        sumPurchasePrice = Float.parseFloat(param.getValue()) * quantity;
                                    }

                                    if(Objects.equals(param.getName(), "Forr??s")) {
                                        source = param.getValue();
                                    }
                                }

                                if (!Objects.equals(source, "") ||
                                        !Objects.equals(source, "nincs") ||
                                        !item.getId().contains("RRRR") ||
                                        !item.getId().contains("XXXX") ||
                                        sumPurchasePrice != 0) {
                                    netProfit += sum - sumPurchasePrice;
                                }
                            }
                    }
                }
            }
        }
    }

    private float getGlsShippingCost(float weight) {
        if (weight > 0 && weight < 2) {
            return 1190;
        }
        else if (weight >= 2 && weight < 5) {
            return 1350;
        }
        else if (weight >= 5 && weight < 10) {
            return 1590;
        }
        else if (weight >= 10 && weight < 20) {
            return 1950;
        }
        else if (weight >= 20 && weight < 30) {
            return 2590;
        }
        else if (weight >= 30) {
            return 3050;
        }
        else {
            return 1190;
        }
    }


    private float get24hShippingCost(float weight) {
        if (weight > 0 && weight < 1) {
            return 1250;
        }
        else if (weight >= 1 && weight < 5) {
            return 1390;
        }
        else if (weight >= 5 && weight < 10) {
            return 1650;
        }
        else if (weight >= 10 && weight < 20) {
            return 1990;
        }
        else if (weight >= 20 && weight < 30) {
            return 2690;
        }
        else if (weight >= 30 && weight < 40) {
            return 3190;
        }
        else if (weight >= 40 && weight < 50) {
            return 3750;
        }
        else if (weight >= 50 && weight < 80) {
            return 4950;
        }
        else if (weight >= 80 && weight < 100) {
            return 7090;
        }
        else if (weight >= 100 && weight < 200) {
            return 10190;
        }
        else if (weight >= 200 && weight < 500) {
            return 14250;
        }
        else if (weight >= 500) {
            return 28500;
        }
        else {
            return 1250;
        }
    }




}
