package com.tank.tankadminbackend.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tank.tankadminbackend.models.unas.order.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UnasGetOrderService {

    private String sendGetOrderRequest(String token, String type, String date) throws IOException {
        String xmlRequest = "<?xml version='1.0' encoding='UTF-8' ?><Params><Status>"
                            + type + "</Status><DateStart>" + date + "</DateStart><DateEnd>"
                            + date + "</DateEnd></Params>";

        URL url = new URL("https://api.unas.eu/shop/getOrder");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setDoOutput(true);
        connection.setUseCaches(true);

        // Write XML
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = xmlRequest.getBytes(StandardCharsets.UTF_8);
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();

        // Read XML
        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[2048];
        StringBuilder response = new StringBuilder();
        int i;
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        return response.toString();
    }


    List<Order> getOrders(String token, String type, String date) throws IOException {
        String response = sendGetOrderRequest(token, type, date);
        XmlMapper xmlMapper = new XmlMapper();
        return List.of(xmlMapper.readValue(response, Orders.class).getOrders());
    }
}
