package com.tank.tankadminbackend.services.unas;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tank.tankadminbackend.models.unas.auth.Login;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class UnasAuthTokenService {
    private final String unasApiKey;

    public UnasAuthTokenService(String unasApiKey1) {
        this.unasApiKey = unasApiKey1;
    }

    private String sendLoginXmlRequest() throws IOException {
        String xmlRequest = "<?xml version='1.0' encoding='UTF-8' ?><Params><ApiKey>" + unasApiKey + "</ApiKey></Params>";

        URL url = new URL("https://api.unas.eu/shop/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
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
        int i;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        return response.toString();
    }


    String getAuthToken() throws IOException {
        String response = sendLoginXmlRequest();
        XmlMapper xmlMapper = new XmlMapper();
        Login token = xmlMapper.readValue(response, Login.class);
        return token.getToken();
    }
}
