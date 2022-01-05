package com.tank.tankadminbackend.services.marketingcost;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdsInsights;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.facebook.FacebookAdCost;


public class FacebookService {
    private float sumAdCoast;
    private final String accessToken;
    private final String appSecret;
    private final String adAccountId;


    public FacebookService(String accessToken, String appSecret, String adAccountId) {
        this.accessToken = accessToken;
        this.appSecret = appSecret;
        this.adAccountId = adAccountId;
        this.sumAdCoast = 0;
    }


    private float getSumOfAdCost(String date) {
        String timeRange = "{'since':'" + date + "','until':'" + date + "'}";
        APIContext context = new APIContext(
                accessToken,
                appSecret
        );
        AdAccount account = new AdAccount(adAccountId, context);
        try {
            APINodeList<AdsInsights> adsInsights = account.getInsights()
                    .setTimeRange(timeRange)
                    .setFiltering("[]")
                    .requestField("spend")
                    .execute();

            String cost = adsInsights.get(0).toString();
            ObjectMapper mapper = new ObjectMapper();
            FacebookAdCost facebookAdCost = mapper.readValue(cost, FacebookAdCost.class);
            sumAdCoast += facebookAdCost.getCost();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sumAdCoast;
    }
}
